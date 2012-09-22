package cz.msvab.gradle.activemq

import org.gradle.api.Project
import org.gradle.testfixtures.ProjectBuilder
import org.hamcrest.Matchers
import org.junit.Test

import static org.hamcrest.MatcherAssert.assertThat

class ActiveMqPluginTest {

    @Test
    void shouldAddStartActiveMqTaskToProject() {
        // given
        Project project = ProjectBuilder.builder().build()
        project.apply(plugin: 'activemq')

        // then
        assertThat(project.tasks.startActiveMq, Matchers.instanceOf(StartActiveMqTask.class))
    }

    @Test
    void shouldAddActiveMqConventionToProject() {
        // given
        Project project = ProjectBuilder.builder().build()
        project.apply(plugin: 'activemq')

        // when
        def config = project.convention.findByName('activemq')

        // then
        assertThat(config, Matchers.instanceOf(ActiveMqPluginConvention.class))
    }

    @Test
    void shouldBeAbleToConfigurePlugin() {
        // given
        Project project = ProjectBuilder.builder().build()
        project.apply(plugin: 'activemq')
        // and
        def port = 44
        def enabled = true

        // when
        project.activemq.port = port
        project.activemq.enabled = enabled

        // then
        assertThat(project.convention.findByName('activemq').port, Matchers.is(port))
        assertThat(project.convention.findByName('activemq').enabled, Matchers.is(enabled))
    }
}
