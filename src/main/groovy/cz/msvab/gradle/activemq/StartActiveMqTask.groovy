package cz.msvab.gradle.activemq

import org.gradle.api.DefaultTask
import org.gradle.api.tasks.TaskAction

class StartActiveMqTask extends DefaultTask {

    static String PORT_IN_USE_MSG = 'Cannot start embedded ActiveMQ on port %s, port already in use!'


    @TaskAction
    void startActiveMq() {
        def config = project.activemq
//        logger.warn(String.format(PORT_IN_USE_MSG, config.port))
    }
}
