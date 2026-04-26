package tests;

import static org.junit.Assert.*;
import org.junit.Test;

public class TimerTest {
    //helper method
    private String formatTime(long milliseconds) {
        long totalSeconds = milliseconds / 1000;
        long minutes = totalSeconds / 60;
        long seconds = totalSeconds % 60;
        long millis = (milliseconds % 1000) / 10;

        String minStr = (minutes < 10 ? "0" : "") + minutes;
        String secStr = (seconds < 10 ? "0" : "") + seconds;
        String millisStr = (millis < 10 ? "0" : "") + millis;

        return minStr + ":" + secStr + "." + millisStr;
    }

    //tests
    @Test
    public void testFormatTime() {
        assertEquals("01:05.00", formatTime(65000));
    }

    @Test
    public void testFormatTimeMilliseconds() {
        assertEquals("01:01.54", formatTime(61543));
    }

    @Test
    public void testLevelTimeCalculation() {
        long levelStartTime = 1000;
        long levelCompleteTime = 5000;

        long levelTime = levelCompleteTime - levelStartTime;

        assertEquals(4000, levelTime);
    }

    @Test
    public void testFinalTimeSum() {
        long level1Time = 1000;
        long level2Time = 2000;
        long level3Time = 3000;

        long finalTime = level1Time + level2Time + level3Time;

        assertEquals(6000, finalTime);
    }

    @Test
    public void testTimerStartsAtZero() {
        long levelStartTime = 1000;
        long currentTime = 1000;

        long displayTime = currentTime - levelStartTime;

        assertEquals(0, displayTime);
    }
}