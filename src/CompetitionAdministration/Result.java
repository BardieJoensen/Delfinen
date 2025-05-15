package CompetitionAdministration;

import java.time.LocalDate;
import java.time.LocalTime;

public interface Result {
    int getMemberId();
    LocalTime getResultTime();
    SwimDisciplin getSwimDisciplin();
    void setResultTime(LocalTime time);
    void setDate(LocalDate date);
}
