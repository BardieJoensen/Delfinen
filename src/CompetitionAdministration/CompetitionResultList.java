package CompetitionAdministration;

import Utilities.DateUtil;
import Utilities.TimeUtil;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.InputMismatchException;
import java.util.Scanner;

public class CompetitionResultList extends ResultList {

    public CompetitionResultList(String path) {
        super(path);
    }

    @Override
    public void addResult(Result result) {
        if(result instanceof CompetitionResult) {
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

    public Result getTopResultOf(int memberId) {
        ArrayList<Result> memberResults = getResultsOf(memberId);
        memberResults.sort(Comparator.comparing(Result::getResultTime));
        return memberResults.isEmpty() ? null : memberResults.get(0);
    }

    @Override
    public void loadResultsFromFile() {
        File csvFile = new File(this.getPath());
        int counter = 1;
        try (Scanner reader = new Scanner(csvFile)) {
            //skip header
            reader.nextLine();
            //load members
            while(reader.hasNextLine()) {
                counter++;
                Result result = CompetitionResult.fromString(reader.nextLine()); //new CompetitionResult(memberId,resultTime,date,swimDisciplin,compName,placement);
                getResults().add(result);
            }
        }catch(FileNotFoundException e) {
            throw new RuntimeException("File not found: " + this.getPath(), e);
        }catch(Exception e) {
            throw new RuntimeException("Incompatible file: error at line " + counter + " in file: " + csvFile.getAbsolutePath());
        }

    }

    @Override
    public void saveResults() {
        try(PrintWriter pw = new PrintWriter(this.getPath())) {
            pw.println("memberId;resultTime;date;swimDisciplin;compName;placement");
            for (Result r : getResults()){
                String[] attributes = r.toStringArray();
                String line = String.join(";", attributes);
                pw.println(line);
            }
        }catch (FileNotFoundException e){
            throw new RuntimeException("File not found: " + getPath(), e);
        }

    }
}
