import MemberAdministration.Member;
import MemberAdministration.MemberList;
import MemberAdministration.MembershipFee;

import java.time.LocalDate;
import java.util.ArrayList;

public class SwimClubController {
    private final UI ui = new UI();
    private final MemberList memberList = new MemberList("./resources/MemberList.csv");
    private String input;
    private String displayContext;

    public void run(){
        mainMenu();
    }

    public void mainMenu(){

        while(true){
            ui.printMainMenu();
            input = ui.getInputNumber(4);
            // TO DO KONKURRENCESVØMMERE
            switch (input){
                case("1") -> {
                    displayContext = "Administration";
                    memberAdministration();
                }
                case("2") -> {
                    displayContext = "Economy";
                    economy();
                }
                case("3") -> {
                    displayContext = "Competition";
                    ui.showMessage("404 not found");
                }
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
        searchResults.forEach(System.out::println);

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
                input = ui.getInputNumber(3);
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
                ui.showMessage("Konkurrencestatus for " + member.getName() + " er nu: " + member.isCompetitiveAsString());
                editMember(member);
            }
            case("2") -> {
                member.setActiveMember(!member.isActiveMember());
                ui.showMessage("Aktivitetsstatus for " + member.getName() + " er nu: " + member.isActiveMemberAsString());
                editMember(member);
            }
            case("3") -> {
                memberList.removeMember(member.getMemberId());
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
        double total = 0.0;

        for(Member member : memberList.getMemberList()) {
            double fee = MembershipFee.calculatePayment(member);
            total += fee;

        }
    }
    public void registerPayment(){
        //search memberID? getMemberID?
        //memberList.getMember(memberId);
    }
    public void arrearsList() {
        double totalArrears = 0.0;
        int count = 0;

        for (Member member : memberList.getMemberList())
            if (!member.hasPaid()) {
                double fee = MembershipFee.calculatePayment(member);
                ui.showMessage(member.getName() + "ID: " + member.getMemberId() + ")");
                ui.showMessage("Udstående betaling: " + fee + " DKK");
                totalArrears += fee;
                count++;

            }
    }

}
