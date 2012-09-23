package cz.msvab.gradle.activemq

import ch.qos.logback.classic.Logger
import ch.qos.logback.core.Appender
import org.apache.activemq.broker.BrokerService
import org.gradle.api.Task
import org.gradle.testfixtures.ProjectBuilder
import org.junit.After
import org.junit.Test
import org.slf4j.LoggerFactory

import static cz.msvab.matcher.LogBackMatchers.isWarning
import static java.lang.String.format
import static org.mockito.BDDMockito.given
import static org.mockito.Matchers.argThat
import static org.mockito.Mockito.mock
import static org.mockito.Mockito.verify

class StartActiveMqTaskTest {

    BrokerService brokerService

    @After
    void cleanUp() {
        brokerService?.stop()
    }

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

    int blockSomePort() {
        def port = 44444
        brokerService = new BrokerService()
        brokerService.addConnector("tcp://localhost:$port")
        brokerService.start()
        return port
    }
}
