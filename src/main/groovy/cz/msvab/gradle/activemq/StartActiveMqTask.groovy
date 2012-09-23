package cz.msvab.gradle.activemq

import cz.msvab.util.PortUtils
import org.apache.activemq.broker.BrokerService
import org.gradle.api.DefaultTask
import org.gradle.api.tasks.TaskAction

class StartActiveMqTask extends DefaultTask {

    static String PORT_IN_USE_MSG = 'Cannot start embedded ActiveMQ on port %s, port already in use!'
    static String DISABLED_MSG = 'Embedded ActiveMQ is disabled.'


    @TaskAction
    void startActiveMq() {
        def config = project.activemq
        if (!config.enabled) {
            logger.info(DISABLED_MSG)
        } else if (PortUtils.isPortInUse(config.port as int)) {
            logger.warn(String.format(PORT_IN_USE_MSG, config.port))
        } else {
            startBroker(config)
        }
    }

    private void startBroker(ActiveMqPluginExtension config) {
        BrokerService broker = new BrokerService()
        broker.addConnector("$config.connector://localhost:$config.port")
        broker.start()
    }
}
