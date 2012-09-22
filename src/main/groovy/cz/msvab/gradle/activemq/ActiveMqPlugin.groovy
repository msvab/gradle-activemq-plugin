package cz.msvab.gradle.activemq

import org.gradle.api.Plugin
import org.gradle.api.Project

class ActiveMqPlugin implements Plugin<Project> {

    void apply(Project project) {
        project.convention.add('activemq', new ActiveMqPluginConvention())

        project.task(type: StartActiveMqTask.class, 'startActiveMq')
    }
}
