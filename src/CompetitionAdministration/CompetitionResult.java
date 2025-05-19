package CompetitionAdministration;

import Utilities.DateUtil;
import Utilities.TimeUtil;

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

    public static Result fromString(String string){
        String[] attributes = string.split(";");
        int memberId = Integer.parseInt(attributes[0]);
        LocalTime resultTime = TimeUtil.parseTime(attributes[1]);
        LocalDate date = DateUtil.parseDate(attributes[2]);
        SwimDisciplin swimDisciplin = SwimDisciplin.fromString(attributes[3]);
        String compName = attributes[4];
        int placement = Integer.parseInt(attributes[5]);

        return new CompetitionResult(memberId,resultTime,date,swimDisciplin,compName,placement);
    }

    @Override
    public String[] toStringArray() {
        String[] superArr = super.toStringArray();
        String[] arr = new String[superArr.length + 2];

        for (int i = 0; i < superArr.length; i++) {
            arr[i] = superArr[i];
        }

        arr[arr.length-2] = this.getCompName();
        arr[arr.length-1] = String.valueOf(this.getPlacement());

        return arr;
    }

    @Override
    public String toString(){
        return String.format("%s Placering: %-12s Stævne: %-20s ", super.toString(), String.format("%3d. plads", placement), compName);
    }
}
