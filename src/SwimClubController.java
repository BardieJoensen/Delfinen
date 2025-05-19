import CompetitionAdministration.*;
import MemberAdministration.Member;
import MemberAdministration.MemberList;
import Utilities.DateUtil;
import Utilities.TimeUtil;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Comparator;

public class SwimClubController {
    private final UI ui = new UI();
    private final MemberList memberList = new MemberList("./resources/MemberList.csv");
    private final TrainingResultList trainingResultList = new TrainingResultList("./resources/TrainingResultList.csv");
    private final CompetitionResultList competitionResultList = new CompetitionResultList("./resources/CompetitionResultList.csv");
    private String input;

    public void run(){
        mainMenu();
        memberList.saveMemberList();
    }

    public void mainMenu(){
        while(true){
            ui.printMainMenu();
            input = ui.getInputNumber(4);
            // TO DO KONKURRENCESVØMMERE
            switch (input){
                case("1") -> memberAdministration();
                case("2") -> economy();
                case("3") -> competitionAdministration();
                case("4") ->{return;}
            }
        }
    }

    public void memberAdministration(){
        while(true){
            ui.printMemberAdministrationMenu();
            input = ui.getInputNumber(4);
            switch (input){
                case("1") -> registerMember();
                case("2") -> searchForMember();
                case("3") -> seeMembers();
                case("4") -> {return;}
            }
        }
    }

    public void registerMember(){
        String name = ui.getInputString("Indtast navn på medlem: ");
        LocalDate birthday = ui.getInputDate("Indtast fødselsdato: ");
        boolean competitive = ui.getInputString("Konkurrencesvømmer [ja/nej]: ").equalsIgnoreCase("ja");
        Member member = new Member(name, birthday, competitive);
        memberList.addMember(member);
        memberList.saveMemberList();

        ui.printOptionOrBack("Registrer flere medlemmer");
        input = ui.getInputNumber(2);
        switch (input){
            case("1") -> registerMember();
            case("2") -> {return;}
        }
    }

    public void searchForMember(){
        String search = ui.getInputString("Indtast søgning:");
        ArrayList<Member> searchResults = memberList.searchForMember(search);

        ui.showMessage("\nSøgeresultat:");
        searchResults.forEach(m -> ui.showMessage(m.toString()));

        if(searchResults.size() == 1){
            editMember(searchResults.get(0));
        }else{
            ui.printOptionOrBack("Vælg medlem");
            input = ui.getInputNumber(2);
            if( input.equals("1")){
                pickMember();
            }else{
                return;
            }
        }
    }

    public void seeMembers(){
        ui.showMessage("\nMedlemsoplysninger:");
        for(int i = 0; i < memberList.getMemberList().size(); i++){
            ui.showMessage(memberList.getMemberList().get(i).toString());

            if((i+1)%10 == 0 || i == memberList.getMemberList().size()-1){
                ui.showMessage("[side " + ((i)/10+1) + "/" + ((memberList.getMemberList().size()-1)/10+1) +"]");
                ui.printMemberOverviewMenu();
                input = ui.getInputNumber(3);
                switch(input){
                    case("1") -> {
                        ui.showMessage("\nMedlemsoplysninger:");
                        continue;
                    }
                    //                    case ("2") -> {
                    //                        if (i >= 19) i -= (i != memberList.getMemberList().size() - 1) ? 20 : ((i + 1) % 10 + 10);
                    //                    }
                    case("2") -> pickMember();
                    case("3") -> {return;}
                }
            }
        }
    }

    public void pickMember(){
        while (true) {
            input = ui.getInputNumber(Member.getLargestId());
            if (memberList.getMember(Integer.parseInt(input)) == null) {
                ui.showMessage("Ugyldigt medlems-ID. Indtast venligst et gyldigt ID");
                continue;
            }
            editMember(memberList.getMember(Integer.parseInt(input)));
            break;
        }
    }

    public void editMember(Member member){
        ui.showMessage(String.format("\nValgt medlem: ID: %04d, Navn: %s, Aktivitetsstatus: %s, Konkurrencesvømmer: %s",
                member.getMemberId(),
                Member.formatName(member.getName()),
                member.isActiveMemberAsString(),
                member.isCompetitiveAsString()));
        ui.printEditMember();
        input = ui.getInputNumber(4);
        switch (input){
            case("1") -> {
                member.setCompetitive(!member.isCompetitive());
                memberList.saveMemberList();
                ui.showMessage("Konkurrencestatus for " + member.getName() + " er nu: " + member.isCompetitiveAsString());
                editMember(member);
            }
            case("2") -> {
                member.setActiveMember(!member.isActiveMember());
                memberList.saveMemberList();
                ui.showMessage("Aktivitetsstatus for " + member.getName() + " er nu: " + member.isActiveMemberAsString());
                editMember(member);
            }
            case("3") -> {
                input = ui.getInputString(String.format("Bekræft udmelding af medlemmet, ID: %04d Navn: %s [ja/nej]: ",
                        member.getMemberId(),
                        Member.formatName(member.getName()))).toLowerCase();
                if(input.equalsIgnoreCase("ja")) {
                    memberList.removeMember(member.getMemberId());
                    memberList.saveMemberList();
                    competitionResultList.removeResultsOf(member.getMemberId());
                    competitionResultList.saveResults();
                    trainingResultList.removeResultsOf(member.getMemberId());
                    trainingResultList.saveResults();
                    ui.showMessage("Fjernet " + member.getName() + " fra listen");
                }else{
                    editMember(member);
                }
            }
            case("4") -> {return;}
        }
    }

    public void economy(){
        while(true){
            ui.printEconomyMenu();
            input = ui.getInputNumber(4);
            switch (input){
                case("1") -> totalIncome();
                case("2") -> registerPayment();
                case("3") -> arrearsList();
                case("4") -> {return;}
            }
        }
    }

    public void totalIncome(){
        String message = String.format("\nDen samlede forventede indkomst er %.2f kr. per år.", memberList.calculateExpectedPayments());
        ui.showMessage(message);
    }

    public void registerPayment(){
        ui.showMessage("\nVælg ID for det medlem du ønsker at registrere betaling for.");
        input = ui.getInputNumber(memberList.getMemberList().size());
        Member member = memberList.getMember(Integer.parseInt(input));
        input = ui.getInputString(String.format("Bekræft registrering på medlemmet, ID: %04d Navn: %s [ja/nej]: ",
                member.getMemberId(),
                Member.formatName(member.getName()))).toLowerCase();
        if(input.equals("ja")){
            member.incrementMembershipExpirationDate();
            ui.showMessage(String.format("Betaling er nu registreret på medlemmet %s til og med %s",
                    Member.formatName(member.getName()),
                    member.getMembershipExpirationDate().format(DateUtil.DATE_FORMATTER)));
                    memberList.saveMemberList();
        }else{
            ui.showMessage("Registrering annulleret");
        }
    }

    public void arrearsList() {
        ArrayList<Member> membersInArrears = memberList.getMembersInArrears();

        double totalArrears = 0.0;

        ui.showMessage("\nMedlemmer i restance:");
        for(Member m : membersInArrears){
            double fee = m.calculatePayment();
            ui.showMessage(String.format("ID: %-6s Navn: %-20s Udestående: %6s kr.",
                    String.format("%04d", m.getMemberId()),
                    Member.formatName(m.getName()),
                    fee));
            totalArrears += fee;
        }
        ui.showMessage(String.format("\nAntal medlemmer: %d \nSamlet udestående: %.2f kr.",membersInArrears.size(),totalArrears));
    }

    public void competitionAdministration(){
        while(true){
            ui.printCompetitionMenu();
            input = ui.getInputNumber(4);
            switch (input){
                case "1" -> registerResult();
                case "2" -> seeTeams();
                case "3" -> seeTopResults();
                case "4" -> {return;}
            }
        }
    }

    public void registerResult(){
        ui.printResultRegistrationMenu();
        input = ui.getInputNumber(3);
        switch (input){
            case "1" -> registerTrainingResult();
            case "2" -> registerCompetitionResult();
            case "3" -> {return;}
        }
    }

    public void registerTrainingResult(){

        int memberId = getCompMemberId();

        ui.showMessage("Indtaster træningsresultat for " + memberList.getMember(memberId).getName());

        LocalTime resultTime = ui.getInputTime("Indtast resultat: [mm:ss.SS]");

        LocalDate date = ui.getInputDate("Indtast dato: [dd.mm.åååå]");

        SwimDisciplin disciplin = ui.getInputDisciplin("Indtast disciplin: ");

        Result trainingResult = new TrainingResult(memberId, resultTime, date, disciplin);

        trainingResultList.addResult(trainingResult);
    }

    public void registerCompetitionResult(){

        int memberId = getCompMemberId();

        ui.showMessage("\nIndtaster konkurrenceresultat for " + memberList.getMember(memberId).getName());

        LocalTime resultTime = ui.getInputTime("Indtast resultat: [mm:ss.SS]");

        LocalDate date = ui.getInputDate("Indtast dato: [dd.mm.åååå]");

        SwimDisciplin disciplin = ui.getInputDisciplin("Indtast disciplin: ");

        String compName = ui.getInputString("Indtast navn på stævne: ");

        ui.showMessage("Vælg svømmerens placering");
        int placement = Integer.parseInt(ui.getInputNumber(999));

        Result competitionResult = new CompetitionResult(memberId, resultTime, date, disciplin, compName, placement);

        competitionResultList.addResult(competitionResult);
    }

    private int getCompMemberId(){
        ui.showMessage("\nVælg ID for det medlem du ønsker at registrere resultatet for.");
        int memberId;

        while(true){
            memberId = Integer.parseInt(ui.getInputNumber(Member.getLargestId()));
            Member member = memberList.getMember(memberId);
            if(member != null && member.isCompetitive()){
                break;
            }else if(member == null){
                ui.showMessage("Medlem er ikke fundet - indtast venligst et gyldigt ID");
            }else{
                ui.showMessage(String.format("Medlemmet, %s, er ikke konkurrencesvømmer - indtast venligst et andet ID", Member.formatName(member.getName())));
            }
        }
        return memberId;
    }

    public void seeTopResults(){
        ui.printWhichTeam();
        input = ui.getInputNumber(3);
        switch (input) {
            case "1" -> topResults(true);
            case "2" -> topResults(false);
            case "3" -> {return;}
        }
    }

    public void seeTeams(){
        ui.printWhichTeam();
        input = ui.getInputNumber(3);
        switch (input){
            case "1" -> {
                for (Member m : memberList.getMemberList()){
                    if(m.isCompetitive() && m.isJunior()){
                        ui.showMessage(m.toString());
                    }
                }
            }
            case "2" -> {
                for (Member m : memberList.getMemberList()) {
                    if (m.isCompetitive() && !m.isJunior()) {
                        ui.showMessage(m.toString());
                    }
                }
            }
            case "3" -> {return;}
        }
    }

    public void topResults(boolean getJuniors){
        for(SwimDisciplin disciplin: SwimDisciplin.values()){
            ArrayList<Result> topResults = topResultsInDisciplin(disciplin, getJuniors);
            if(!topResults.isEmpty()) ui.showMessage("\nTop 5 resultater i: " +disciplin.getName() + ":");
            topResults.forEach(
                r -> ui.showMessage(String.format("Navn: %-20s %s",
                Member.formatName(memberList.getMember(r.getMemberId()).getName()), r))
            );
        }
    }

    private ArrayList<Result> topResultsInDisciplin(SwimDisciplin disciplin, boolean getJuniors){
        ArrayList<Result> results = new ArrayList<>();
        Member member;
        for(Result r : trainingResultList.getResults()){
            member = memberList.getMember(r.getMemberId());

            if (member == null){
                continue;
            }

            if(r.getSwimDisciplin().equals(disciplin) && member.isJunior() == getJuniors && member.isCompetitive()){
                Result topCompResult = competitionResultList.getTopResultOf(r.getMemberId());

                if(topCompResult == null || topCompResult.getResultTime().isAfter(r.getResultTime())) {
                    results.add(r);

                }else{
                    results.add(topCompResult);
                }
            }
        }

        results.sort(Comparator.comparing(Result::getResultTime));
        return (results.size() > 5 ) ? (ArrayList<Result>) results.subList(0,5) : results;
    }

}
