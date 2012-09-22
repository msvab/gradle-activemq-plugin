package cz.msvab.gradle.activemq;


import org.hamcrest.Matchers
import org.junit.Test

import static org.hamcrest.MatcherAssert.assertThat

public class ActiveMqPluginConventionTest {

    @Test
    void shouldHaveDefaultsForConfiguration() {
        // given
        ActiveMqPluginConvention config = new ActiveMqPluginConvention()

        // then
        assertThat(config.port, Matchers.is(61616))
        assertThat(config.enabled, Matchers.is(true))
    }
}
