package Utilities;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

public class DateUtil {
    public static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern("d.M.yyyy");

    public static LocalDate parseDate(String dateStr) throws DateTimeParseException {
        try {
            String[] parts = dateStr.split("[./-]");
            int day = Integer.parseInt(parts[0]);
            int month = Integer.parseInt(parts[1]);
            int year = Integer.parseInt(parts[2]);
            //handle 2-digit years
            if(year<100){
                int currentYear = LocalDate.now().getYear();
                int currentCentury = currentYear/100;
                year = year + currentCentury*100;
                if(year > currentYear) year -= 100;
            }
            return LocalDate.of(year, month, day);
        } catch (Exception e) {
            throw new DateTimeParseException("Invalid date format: " + dateStr, dateStr, 0);
        }
    }

    public static int calculateAge(LocalDate birthday) {
        LocalDate today = LocalDate.now();
        int age = today.getYear() - birthday.getYear();

        // Check if birthday has occurred this year
        if (today.getMonthValue() < birthday.getMonthValue() ||
                (today.getMonthValue() == birthday.getMonthValue() && today.getDayOfMonth() < birthday.getDayOfMonth())) {
            age--;
        }
        return age;
    }
}
