package CompetitionAdministration;

import java.util.ArrayList;

public abstract class ResultList {
    private final ArrayList<Result> results;

    public ResultList(String path){
        this.results = new ArrayList<>();
        loadResultsFromFile(path);
    }

    public ArrayList<Result> getResults() {
        return results;
    }

    public abstract void addResult(Result result);
    public abstract ArrayList<Result> getResultsOf(int memberId);
    public abstract void loadResultsFromFile(String path);
    public abstract void saveResults();
}
