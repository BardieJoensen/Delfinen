package CompetitionAdministration;

import java.time.LocalDate;
import java.time.LocalTime;

public class CompetitionResult extends Result {
    private final String compName;
    private final int placement;

    public CompetitionResult(int memberID, LocalTime resultTime, LocalDate date, SwimDisciplin swimDisciplin, String compName, int placement) {
        super(memberID, resultTime, date, swimDisciplin);
        this.compName = compName;
        this.placement = placement;
    }

    public String getCompName() {
        return compName;
    }

    public int getPlacement() {
        return placement;
    }
}
