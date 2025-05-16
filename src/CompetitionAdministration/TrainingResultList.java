package CompetitionAdministration;

import Utilities.DateUtil;
import Utilities.TimeUtil;

import java.io.File;
import java.io.FileNotFoundException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Scanner;

public class TrainingResultList extends ResultList{

    public TrainingResultList(String path){
        super(path);
    }

    @Override
    public void addResult(Result result) {
        int id = result.getMemberId();

        boolean disciplinFound = false;

        for(int i = 0; i<getResults().size(); i++){
            Result oldResult = getResults().get(i);
            if(oldResult.getSwimDisciplin().equals(result.getSwimDisciplin()) && oldResult.getMemberId() == id){
                disciplinFound = true;
                if(oldResult.getResultTime().isAfter(result.getResultTime())){
                    getResults().set(i, result);
                    break;
                }
            }
        }

        if(!disciplinFound){
            getResults().add(result);
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

    @Override
    public void loadResultsFromFile(String path) {
        File csvFile = new File(path);
        int counter = 1;
        try (Scanner reader = new Scanner(csvFile)){
            //skip header
            reader.nextLine();
            //load members
            while(reader.hasNextLine()){
                counter++;
                String[] attributes = reader.nextLine().split(";");
                int memberId = Integer.parseInt(attributes[0]);
                LocalTime resultTime = TimeUtil.parseTime(attributes[1]);
                LocalDate date = DateUtil.parseDate(attributes[2]);
                SwimDisciplin swimDisciplin = SwimDisciplin.fromString(attributes[3]);

                Result result = new TrainingResult(memberId,resultTime,date,swimDisciplin);
                getResults().add(result);
            }
        }catch(FileNotFoundException e){
            throw new RuntimeException("File not found: " + path, e);
        }catch(Exception e){
            throw new RuntimeException("Incompatible file: error at line " + counter + " in file: " + csvFile.getAbsolutePath());
        }
    }

    @Override
    public void saveResults() {

    }
}
