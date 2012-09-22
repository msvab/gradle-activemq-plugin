package cz.msvab.gradle.activemq;


import org.hamcrest.Matchers
import org.junit.Test

import static org.hamcrest.MatcherAssert.assertThat

public class ActiveMqPluginExtensionTest {

    @Test
    void shouldHaveDefaultsForConfiguration() {
        // given
        ActiveMqPluginExtension config = new ActiveMqPluginExtension()

        // then
        assertThat(config.port, Matchers.is(61616))
        assertThat(config.enabled, Matchers.is(true))
    }
}
