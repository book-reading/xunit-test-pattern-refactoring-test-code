package xunit.testpatterns;

import java.text.SimpleDateFormat;
import java.util.Calendar;

public class TimeDisplay {
    private TimeProvider timeProvider = null;

    public void setTimeProvider(TimeProvider timeProvider) {
        this.timeProvider = timeProvider;
    }

    public String getCurrentTimeAsHtmlFragment() {
        Calendar time;

        StringBuffer buffer = new StringBuffer();

        try {
            time = timeProvider.getTime();
            buffer.append("<span class=\"tinyBoldText\">");

            if ((time.get(Calendar.HOUR_OF_DAY) == 0) && (time.get(Calendar.MINUTE) <= 1)) {
                buffer.append("Midnight");
            } else if ((time.get(Calendar.HOUR_OF_DAY) == 12) && (time.get(Calendar.MINUTE) == 0)) {
                buffer.append("Noon");
            } else {
                SimpleDateFormat fr = new SimpleDateFormat("h:mm a");
                buffer.append(fr.format(time.getTime()));
            }

            buffer.append("</span>");
        } catch (Exception e) {
            buffer.append("<span class=\"error\">Invalid Time</span>");
        }

        return buffer.toString();
    }
}
