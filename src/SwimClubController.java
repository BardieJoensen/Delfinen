import MemberAdministration.Member;
import MemberAdministration.MemberList;
import MemberAdministration.MembershipFee;

import java.time.LocalDate;
import java.util.ArrayList;

public class SwimClubController {
    private final UI ui = new UI();
    private final MemberList memberList = new MemberList("./resources/MemberList.csv");
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
                case("1") -> {
                    memberAdministration();
                }
                case("2") -> {
                    economy();
                }
                case("3") -> {
                    ui.showMessage("404 not found");
                }
                case("4") ->{
                    return;
                }
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
        String name = ui.getInputString("Indtast navn på medlem");
        LocalDate birthday = ui.getInputDate("Indtast fødselsdato");
        boolean competitive = ui.getInputString("Konkurrencesvømmer [ja/nej]").equals("ja");
        Member member = new Member(name, birthday, competitive);
        memberList.addMember(member);

        ui.printMoreRegistrationOption();
        input = ui.getInputNumber(2);
        switch (input){
            case("1") -> registerMember();
            case("2") -> {return;}
        }
    }

    public void searchForMember(){
        String search = ui.getInputString("Indtast søgning:");
        ArrayList<Member> searchResults = memberList.searchForMember(search);

        ui.showMessage("Søgeresultat:");
        searchResults.forEach(m -> ui.showMessage(m.toString()));

        if(searchResults.size() == 1){
            editMember(searchResults.get(0));
        }else{
            ui.printPickMemberOption();
            input = ui.getInputNumber(2);
            if( input.equals("1")){
                pickMember();
            }else{
                return;
            }
        }
    }

    public void seeMembers(){
        for(int i = 0; i < memberList.getMemberList().size(); i++){
            ui.showMessage(memberList.getMemberList().get(i).toString());

            if((i+1)%10 == 0 || i == memberList.getMemberList().size()-1){
                ui.printMemberOverviewMenu();
                input = ui.getInputNumber(4);
                switch(input){
                    case("1") -> {continue;}
                    case("2") -> {
                        if(i >= 19) i -= (i != memberList.getMemberList().size()-1) ? 20 : ((i+1)%10 + 10);
                    }
                    case("3") -> pickMember();
                    case("4") -> {return;}
                }
            }
        }
    }

    public void pickMember(){
        input = ui.getInputNumber(Member.getLargestId());
        editMember(memberList.getMember(Integer.parseInt(input)));
    }

    public void editMember(Member member){
        ui.showMessage("Redigerer: " + member.getMemberId() + " "
                                         + member.getName() + " "
                                         + member.isActiveMember() + " "
                                         + member.isCompetitive());
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
                memberList.removeMember(member.getMemberId());
                memberList.saveMemberList();
                ui.showMessage("Fjernet " + member.getName() + " fra listen");
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
        String message = "Den samlede forventede indkomst er: " +
                memberList.calculateExpectedPayments() +
                " kr. per år.";
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
                    member.getMembershipExpirationDate().format(Member.DATE_STR_FORMATTER)));
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
            double fee = MembershipFee.calculatePayment(m);
            ui.showMessage(String.format("ID: %-6s Navn: %-20s Udestående: %6s DKK",
                    String.format("%04d", m.getMemberId()),
                    Member.formatName(m.getName()),
                    fee));
            totalArrears += fee;
        }
        ui.showMessage(String.format("\nAntal medlemmer: %d \nSamlet udestående: %.2f DKK",membersInArrears.size(),totalArrears));
    }

}
