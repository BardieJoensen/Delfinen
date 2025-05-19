package CompetitionAdministration;

import Utilities.DateUtil;
import Utilities.TimeUtil;

import java.time.LocalDate;
import java.time.LocalTime;

public class TrainingResult extends Result {

    public TrainingResult(int memberID, LocalTime resultTime, LocalDate date, SwimDisciplin swimDisciplin) {
        super(memberID, resultTime, date, swimDisciplin);
    }

    public static Result fromString(String string) {
        String[] attributes = string.split(";");
        int memberId = Integer.parseInt(attributes[0]);
        LocalTime resultTime = TimeUtil.parseTime(attributes[1]);
        LocalDate date = DateUtil.parseDate(attributes[2]);
        SwimDisciplin swimDisciplin = SwimDisciplin.fromString(attributes[3]);

        return new TrainingResult(memberId, resultTime, date, swimDisciplin);
    }
}
