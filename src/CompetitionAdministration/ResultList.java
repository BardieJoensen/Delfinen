package CompetitionAdministration;

import java.util.ArrayList;

public abstract class ResultList {
    private final ArrayList<Result> results;
    private final String path;

    public ResultList(String path){
        this.results = new ArrayList<>();
        this.path = path;
        loadResultsFromFile();
    }

    public String getPath() {
        return path;
    }

    public ArrayList<Result> getResults() {
        return results;
    }

    public Result get(int index){
        return results.get(index);
    }

    public int size(){
        return results.size();
    }

    public abstract void addResult(Result result);

    public abstract ArrayList<Result> getResultsOf(int memberId);

    public abstract void loadResultsFromFile();

    public abstract void saveResults();

    public void removeResultsOf(int memberId) {
        ArrayList<Result> toRemove = getResultsOf(memberId);
        for(Result r : toRemove){
            results.remove(r);
        }
    }

    
}
