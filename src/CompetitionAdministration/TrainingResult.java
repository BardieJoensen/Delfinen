package CompetitionAdministration;

import java.time.LocalDate;
import java.time.LocalTime;

public class TrainingResult implements Result{
    private int memberID;
    private LocalTime resultTime;
    private LocalDate date;
    private SwimDisciplin swimDisciplin;

    public TrainingResult(int memberID, LocalTime resultTime, LocalDate date, SwimDisciplin swimDisciplin) {
        this.memberID = memberID;
        this.resultTime = resultTime;
        this.date = date;
        this.swimDisciplin = swimDisciplin;
    }

    @Override
    public LocalTime getResultTime() {
        return resultTime;
    }

    @Override
    public SwimDisciplin getSwimDisciplin() {
        return swimDisciplin;
    }

    public int getMemberId(){
        return memberID;
    }

    public LocalDate getDate(){
        return date;
    }

    @Override
    public void setResultTime(LocalTime resultTime) {
        this.resultTime = resultTime;
    }

    @Override
    public void setDate(LocalDate date) {
        this.date = date;
    }
}
