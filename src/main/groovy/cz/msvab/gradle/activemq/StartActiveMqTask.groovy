package cz.msvab.gradle.activemq

import cz.msvab.util.PortUtils
import org.apache.activemq.ActiveMQConnection
import org.apache.activemq.broker.BrokerService
import org.gradle.api.DefaultTask
import org.gradle.api.tasks.TaskAction

import javax.jms.Session

class StartActiveMqTask extends DefaultTask {

    static String PORT_IN_USE_MSG = 'Cannot start embedded ActiveMQ on port %s, port already in use!'
    static String DISABLED_MSG = 'Embedded ActiveMQ is disabled.'
    static String PURGING_QUEUES = 'Purging messages on all ActiveMQ queues!'


    @TaskAction
    void startActiveMq() {
        def config = project.activemq
        if (!config.enabled) {
            logger.info(DISABLED_MSG)
        } else if (PortUtils.isPortInUse(config.port as int)) {
            logger.warn(String.format(PORT_IN_USE_MSG, config.port))
            if (config.purgeQueues) {
                logger.info(PURGING_QUEUES)
                purgeQueues(config)
            }
        } else {
            startBroker(config)
        }
    }

    private void purgeQueues(ActiveMqPluginExtension config) {
        def connection = ActiveMQConnection.makeConnection(connectionString(config))
        connection.start()

        connection.getDestinationSource().queues.each { queue ->
            logger.debug("Purging queue $queue")
            def session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE)
            def consumer = session.createConsumer(queue)
            def message = consumer.receive(10)
            while (message != null) {
                logger.debug("Deleting message $message")
                message = consumer.receive(10)
            }

            consumer.close()
            session.close()
        }

        connection.close()
    }

    private void startBroker(ActiveMqPluginExtension config) {
        BrokerService broker = new BrokerService()
        broker.useJmx = config.jmxEnabled
        broker.addConnector(connectionString(config))
        broker.start()
    }

    private String connectionString(ActiveMqPluginExtension config) {
        "$config.connector://localhost:$config.port"
    }
}
