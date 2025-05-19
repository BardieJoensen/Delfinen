package Utilities;

import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

public class TimeUtil {
    public static final DateTimeFormatter TIME_FORMATTER =  DateTimeFormatter.ofPattern("mm:ss.SS");

    public static LocalTime parseTime(String timeStr) throws DateTimeParseException {
        try{
            String[] parts = timeStr.split("[:./-]");
            int hour = 0;
            int minute = Integer.parseInt(parts[0]);
            int seconds = Integer.parseInt(parts[1]);
            int nanoSeconds = Integer.parseInt(parts[2]) * 10000000;
            return LocalTime.of(hour,minute,seconds,nanoSeconds);

        } catch (Exception e) {
            throw new DateTimeParseException("Invalid time format: " + timeStr, timeStr, 0);
        }
    }
}
