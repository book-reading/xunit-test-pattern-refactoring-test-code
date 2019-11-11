package xunit.testpatterns;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.Calendar;
import java.util.GregorianCalendar;

import org.junit.Test;

public class TimeDisplayTest {
    /**
     * 示例：响应器，Responder（作为手动编码的测试桩）
     * 
     * 用来将有效间接输入注入 SUT 以便它能正常运行的测试桩称为响应器。
     * 当实际组件不可控制、得不到或者在开发环境中不能使用时，响应器通常用于“快乐路径”测试。
     * 这些测试始终时简单成功测试（参见“测试方法”）。
     */
    @Test
    public void testDisplayCurrentTime_AtMidnight() {
        // Fixture setup
        //      Test Double configuration
        TimeProviderTestStub tpStub = new TimeProviderTestStub();
        tpStub.setHours(0);
        tpStub.setMinutes(0);
        //      Instantiate SUT
        TimeDisplay sut = new TimeDisplay();
        //      Test Double installation
        sut.setTimeProvider(tpStub);

        // Exercise SUT
        String result = sut.getCurrentTimeAsHtmlFragment();

        // Verify outcome
        String exceptedTimeString = "<span class=\"tinyBoldText\">Midnight</span>";
        assertEquals("Midnight", exceptedTimeString, result);
    }

    /**
     * 示例：响应器（动态生成）
     */
    @Test
    public void testDisplayCurrentTime_AtMidnight_Mock() throws Exception {
        // Fixture setup
        TimeDisplay sut = new TimeDisplay();
        //      Test Double configuration
        TimeProvider tpStub = mock(TimeProvider.class);
        when(tpStub.getTime()).thenReturn(new GregorianCalendar(2020, 1, 1, 0, 0));
        //      Test Double installation
        sut.setTimeProvider(tpStub);

        // Exercise SUT
        String result = sut.getCurrentTimeAsHtmlFragment();

        // Verify outcome
        String exceptedTimeString = "<span class=\"tinyBoldText\">Midnight</span>";
        assertEquals("Midnight", exceptedTimeString, result);
    }

    /**
     * 示例：破坏者，Saboteur（作为匿名内部类）
     * 
     * 用来将无效间接输入注入 SUT 的测试桩通常称为破坏者，因为其目的是破坏 SUT 要完成的任务，这样就可以知道在这些环境下 SUT 的处理方法。
     * 返回意料之外的值或对象、发生异常或者产生运行时错误，都可能导致“出轨”。
     * 每个测试可以是简单成功测试，也可以是预期异常测试（参见“测试方法”），这取决于希望 SUT 如何响应间接输入。
     */
    @Test
    public void testDisplayCurrentTime_exception() {
        // Fixture setup
        //      Define and instantiate Test Stub
        TimeProvider testStub = new TimeProvider(){
            @Override
            public Calendar getTime() throws Exception {
                throw new Exception("Sample");
            }
        };
        //      Instantiate SUT
        TimeDisplay sut = new TimeDisplay();
        sut.setTimeProvider(testStub);

        // Exercise SUT
        String result = sut.getCurrentTimeAsHtmlFragment();

        // Verify direct output
        String exceptedTimeString = "<span class=\"error\">Invalid Time</span>";
        assertEquals("Exception", exceptedTimeString, result);
    }
}

/**
 * TimeProviderTestStub
 */
class TimeProviderTestStub implements TimeProvider {
    private Calendar myTime = new GregorianCalendar();

    public TimeProviderTestStub() {}

    /**
     * The complete constructor for the TimeProviderTestStub
     * 
     * @param hours specifies the hours using a 24-hour clock
     *  (e.g., 10 = 10 AM, 12 = noon, 22 = 10 PM, 0 = midnight)
     * @param minutes specifies he minutes after the hour
     *  (e.g., 0 = exactly on the hour, 1 = 1 min after the hour)
     */
    public TimeProviderTestStub(int hours, int minutes) {
        setTime(hours, minutes);
    }

    public void setTime(int hours, int minutes) {
        setHours(hours);
        setMinutes(minutes);
    }

    // Configuration interface
    public void setHours(int hours) {
        // 0 is midnight; 12 is noon
        myTime.set(Calendar.HOUR_OF_DAY, hours);
    }

    public void setMinutes(int minutes) {
        myTime.set(Calendar.MINUTE, minutes);
    }

    // Interface used by SUT
    public Calendar getTime() {
        return myTime;
    }
}
