package CompetitionAdministration;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Scanner;

public class TrainingResultList extends ResultList {

    public TrainingResultList(String path) {
        super(path);
    }

    @Override
    public void addResult(Result result) {
        int id = result.getMemberId();

        boolean disciplinFound = false;

        for (int i = 0; i < getResults().size(); i++) {
            Result oldResult = getResults().get(i);
            if (oldResult.getSwimDisciplin().equals(result.getSwimDisciplin()) && oldResult.getMemberId() == id) {
                disciplinFound = true;
                if (oldResult.getResultTime().isAfter(result.getResultTime())) {
                    getResults().set(i, result);
                    break;
                }
            }
        }

        if (!disciplinFound) {
            getResults().add(result);
        }
    }

    @Override
    public ArrayList<Result> getResultsOf(int memberId) {
        ArrayList<Result> memberResults = new ArrayList<>();
        for (Result r : getResults()) {
            if (r.getMemberId() == memberId) {
                memberResults.add(r);
            }
        }
        return memberResults;
    }

    @Override
    public void loadResultsFromFile() {
        File csvFile = new File(getPath());
        int counter = 1;
        try (Scanner reader = new Scanner(csvFile)) {
            //skip header
            reader.nextLine();
            //load members
            while (reader.hasNextLine()) {
                counter++;
                Result result = TrainingResult.fromString(reader.nextLine());
                getResults().add(result);
            }
        } catch (FileNotFoundException e) {
            throw new RuntimeException("File not found: " + this.getPath(), e);
        } catch (Exception e) {
            throw new RuntimeException("Incompatible file: error at line " + counter + " in file: " + csvFile.getAbsolutePath());
        }
    }

    @Override
    public void saveResults() {
        try (PrintWriter pw = new PrintWriter(this.getPath())) {
            pw.println("memberId;resultTime;date;swimDisciplin");
            for (Result r : getResults()) {
                String[] attributes = r.toStringArray();
                String line = String.join(";", attributes);
                pw.println(line);
            }
        } catch (FileNotFoundException e) {
            throw new RuntimeException("File not found: " + getPath(), e);
        }

    }
}
