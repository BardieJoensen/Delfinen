package CompetitionAdministration;

import Utilities.DateUtil;
import Utilities.TimeUtil;

import java.time.LocalDate;
import java.time.LocalTime;

public abstract class Result {
    private final int memberId;
    private final LocalTime resultTime;
    private final LocalDate date;
    private final SwimDisciplin swimDisciplin;

    public Result(int memberID, LocalTime resultTime, LocalDate date, SwimDisciplin swimDisciplin) {
        this.memberId = memberID;
        this.resultTime = resultTime;
        this.date = date;
        this.swimDisciplin = swimDisciplin;
    }
    
    public LocalTime getResultTime() {
        return resultTime;
    }
    
    public SwimDisciplin getSwimDisciplin() {
        return swimDisciplin;
    }

    public int getMemberId(){
        return memberId;
    }

    public LocalDate getDate(){
        return date;
    }

    @Override
    public String toString(){
        return String.format("ID: %-6s Tid: %10s   Dato: %10s   Disciplin: %-10s",
                String.format("%04d", memberId),
                resultTime.format(TimeUtil.TIME_FORMATTER),
                date.format(DateUtil.DATE_FORMATTER),
                swimDisciplin.getName());
    }

    public String[] toStringArray() {
        String[] arr = new String[4];
        arr[0] = String.valueOf(this.getMemberId());
        arr[1] = this.getResultTime() != null ? this.getResultTime().format(TimeUtil.TIME_FORMATTER) : " ";
        arr[2] = this.getDate() != null ? this.getDate().format(DateUtil.DATE_FORMATTER) : " ";
        arr[3] = this.getSwimDisciplin() != null ? this.getSwimDisciplin().getName() : " ";

        return arr;
    }

    public boolean isSlower(Result compared){
        return this.resultTime.isAfter(compared.resultTime);
    }
}
