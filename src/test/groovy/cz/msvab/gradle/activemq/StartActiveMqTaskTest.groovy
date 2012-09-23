package cz.msvab.gradle.activemq

import ch.qos.logback.classic.Logger
import ch.qos.logback.core.Appender
import org.apache.activemq.ActiveMQConnectionFactory
import org.gradle.api.Task
import org.gradle.testfixtures.ProjectBuilder
import org.junit.Test
import org.slf4j.LoggerFactory

import java.util.concurrent.Executors

import static cz.msvab.matcher.LogBackMatchers.isInfo
import static cz.msvab.matcher.LogBackMatchers.isWarning
import static java.lang.String.format
import static org.mockito.BDDMockito.given
import static org.mockito.Matchers.argThat
import static org.mockito.Mockito.mock
import static org.mockito.Mockito.verify

class StartActiveMqTaskTest {

    @Test
    void shouldLogWarningWhenPortIsAlreadyInUse() {
        // given
        def project = ProjectBuilder.builder().build()
        project.apply plugin: 'activemq'
        // and
        def capturedLog = mock(Appender)
        given(capturedLog.getName()).willReturn('MOCK')
        (LoggerFactory.getLogger(Task) as Logger).addAppender(capturedLog)
        // and
        project.activemq.port = blockSomePort()

        // when
        project.startActiveMq.execute()

        // then
        verify(capturedLog).doAppend(argThat(isWarning(format(StartActiveMqTask.PORT_IN_USE_MSG, project.activemq.port))))
    }

    @Test
    void shouldNotStartBrokerWhenDisabled() {
        // given
        def project = ProjectBuilder.builder().build()
        project.apply plugin: 'activemq'
        // and
        def capturedLog = mock(Appender)
        given(capturedLog.getName()).willReturn('MOCK')
        (LoggerFactory.getLogger(Task) as Logger).addAppender(capturedLog)
        // and
        project.activemq.enabled = false

        // when
        project.startActiveMq.execute()

        // then
        verify(capturedLog).doAppend(argThat(isInfo(StartActiveMqTask.DISABLED_MSG)))
    }

    @Test
    void shouldStartActiveMqOnConfiguredPort() {
        // given
        def project = ProjectBuilder.builder().build()
        project.apply plugin: 'activemq'

        // when
        project.startActiveMq.execute()

        // then
        verifyActiveMqIsRunningOnPort(project.activemq.port)
    }

    private void verifyActiveMqIsRunningOnPort(int port) {
        ActiveMQConnectionFactory connectionFactory = new ActiveMQConnectionFactory("tcp://localhost:$port")
        def connection = connectionFactory.createConnection()
        // if ActiveMQ is not running on this port, createConnection would throw exception
        connection.stop()
    }

    private int blockSomePort() {
        def port = 44444
        Executors.newSingleThreadExecutor().submit({
            new ServerSocket(port).accept()
        } as Runnable)
        Thread.sleep(500)
        return port
    }
}
