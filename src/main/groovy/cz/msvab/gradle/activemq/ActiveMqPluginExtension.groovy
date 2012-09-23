package cz.msvab.gradle.activemq

class ActiveMqPluginExtension {
    int port = 61616
    boolean enabled = true
    String connector = 'nio'
    boolean jmxEnabled = false
}
