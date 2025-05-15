package CompetitionAdministration;

import java.util.ArrayList;
import java.util.InputMismatchException;

public class CompetitionResultList implements ResultList {
    private ArrayList<Result> results;

    public CompetitionResultList(String path){
        this.results = new ArrayList<>();
        loadResultsFromFile(path);
    }

    @Override
    public void addResult(Result result) {
        if(result instanceof CompetitionResult){
            results.add(result);
        }else {
            throw new InputMismatchException("Fejl: forventede konkurrenceresultat - fik andet");
        }
    }

    @Override
    public ArrayList<Result> getMemberResults(int memberId) {
        ArrayList<Result> memberResults = new ArrayList<>();
        for(Result r : results){
            if(r.getMemberId() == memberId){
                memberResults.add(r);
            }
        }
        return memberResults;
    }

    @Override
    public void loadResultsFromFile(String path) {

    }

    @Override
    public void saveResults() {

    }
}
