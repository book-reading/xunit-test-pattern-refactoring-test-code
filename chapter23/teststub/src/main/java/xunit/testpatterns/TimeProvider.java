package xunit.testpatterns;

import java.util.Calendar;

/**
 * TimeProvider
 */
public interface TimeProvider {
  Calendar getTime() throws Exception;
}