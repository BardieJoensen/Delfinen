package CompetitionAdministration;

import java.time.LocalDate;
import java.time.LocalTime;

public class TrainingResult extends Result{

    public TrainingResult(int memberID, LocalTime resultTime, LocalDate date, SwimDisciplin swimDisciplin) {
        super(memberID, resultTime, date, swimDisciplin);
    }
}
