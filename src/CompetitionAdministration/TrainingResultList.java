package CompetitionAdministration;

import java.util.ArrayList;

public class TrainingResultList implements ResultList{
    private ArrayList<Result> results;

    public TrainingResultList(String path){
        this.results = new ArrayList<>();
        loadResultsFromFile(path);
    }

    @Override
    public void addResult(Result result) {
        int id = result.getMemberId();

        boolean disciplinFound = false;

        for(int i = 0; i<results.size(); i++){
            Result oldResult = results.get(i);
            if(oldResult.getSwimDisciplin().equals(result.getSwimDisciplin()) && oldResult.getMemberId() == id){
                disciplinFound = true;
                if(oldResult.getResultTime().isAfter(result.getResultTime())){
                    results.set(i, result);
                    break;
                }
            }
        }

        if(!disciplinFound){
            results.add(result);
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
