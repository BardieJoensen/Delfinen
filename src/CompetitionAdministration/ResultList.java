package CompetitionAdministration;

import java.util.ArrayList;

public interface ResultList {
    void addResult(Result result);
    ArrayList<Result> getMemberResults(int memberId);
    void loadResultsFromFile(String path);
    void saveResults();
}
