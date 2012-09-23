package cz.msvab.matcher

import ch.qos.logback.classic.Level
import ch.qos.logback.classic.spi.LoggingEvent
import org.hamcrest.Description
import org.hamcrest.Matcher
import org.hamcrest.TypeSafeDiagnosingMatcher

class LogBackMatchers {

    public static Matcher<LoggingEvent> isWarning(String msg) {
        return isLogMessage(Level.WARN, msg)
    }

    public static Matcher<LoggingEvent> isInfo(String msg) {
        return isLogMessage(Level.INFO, msg)
    }

    public static Matcher<LoggingEvent> isLogMessage(Level level, String msg) {
        return new TypeSafeDiagnosingMatcher<LoggingEvent>() {
            @Override
            protected boolean matchesSafely(LoggingEvent item, Description mismatchDescription) {
                mismatchDescription.appendText(' was ').appendValue(item.message).appendText(' on level ').appendValue(item.level)
                return item.level == level && item.message == msg
            }

            void describeTo(Description description) {
                description.appendText('should log ').appendValue(msg).appendText(' on level ').appendValue(level)
            }
        }
    }
}
