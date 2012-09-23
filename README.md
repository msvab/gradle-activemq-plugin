[![Build Status](https://secure.travis-ci.org/msvab/gradle-activemq-plugin.png)](http://travis-ci.org/msvab/gradle-activemq-plugin)

# Gradle ActiveMQ Plugin

Plugin that starts embedded ActiveMQ. Can be useful for message based service integration tests.

## Usage

To use the plugin, include in your build script:
```groovy
apply plugin: 'activemq'
```

## Configuration options
* `port`: Port on which embedded broker will be started (default: 61616)
* `enabled`: Enables embedded broker (default: true)
* `connector`: Type of ActiveMQ connector that will be started (default: 'nio')
* `jmxEnabled`: Enables JMX for embedded broker (default: false)