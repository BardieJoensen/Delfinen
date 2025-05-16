package CompetitionAdministration;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.InputMismatchException;

public class CompetitionResultList extends ResultList {

    public CompetitionResultList(String path){
        super(path);
    }

    @Override
    public void addResult(Result result) {
        if(result instanceof CompetitionResult){
            getResults().add(result);
        }else {
            throw new InputMismatchException("Fejl: forventede konkurrenceresultat - fik andet");
        }
    }

    @Override
    public ArrayList<Result> getResultsOf(int memberId) {
        ArrayList<Result> memberResults = new ArrayList<>();
        for(Result r : getResults()){
            if(r.getMemberId() == memberId){
                memberResults.add(r);
            }
        }
        return memberResults;
    }

    public Result getTopResultOf(int memberId){
        ArrayList<Result> memberResults = getResultsOf(memberId);
        memberResults.sort(Comparator.comparing(Result::getResultTime));
        return memberResults.isEmpty() ? null : memberResults.get(0);
    }

    @Override
    public void loadResultsFromFile(String path) {

    }

    @Override
    public void saveResults() {

    }
}
