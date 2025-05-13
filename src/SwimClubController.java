import MemberAdministration.Member;
import MemberAdministration.MemberList;
import MemberAdministration.MembershipFee;

import java.util.ArrayList;
import java.util.Locale;

public class SwimClubController {
    private final UI ui = new UI();
    private final MemberList memberList = new MemberList("./resources/TestMemberList.csv");
    private String input;


    public void run(){
        mainMenu();
    }

    public void mainMenu(){
        while(true){
            ui.printMainMenu();
            input = ui.getInputNumber(4);
            // TO DO
            switch (input){
                case("1") -> memberAdministration();
                case("2") -> economy();
                case("3") -> ;
                case("4") ->{return;}
            }
        }
    }

    public void memberAdministration(){
        while(true){
            ui.printMemberAdministrationMenu();
            input = ui.getInputNumber(4);
            switch (input){
                case("1") -> ;
                case("2") -> ;
                case("3") -> ;
                case("4") -> {return;}
            }
        }
    }

    public ArrayList<Member> searchForMember(String string){
        int memberId;
        String name;
        ArrayList<Member> searchResults = new ArrayList<>();
        try{
            memberId = Integer.parseInt(string);
            Member member = memberList.getMember(memberId);
            if(member == null) throw new NumberFormatException();
            searchResults.add(member);
        }catch (NumberFormatException e){
            String[] searchWords = string.split(" ");
            for(Member m : memberList.getMemberList()){
                for(String s : searchWords){
                    if(m.getName().toLowerCase().contains(s.toLowerCase())) {
                        searchResults.add(m);
                    }
                    if(String.valueOf(m.getMemberId()).contains(s)){
                        searchResults.add(m);
                    }
                }
            }
        }

        return searchResults;
    }

    public void editMember(){
        ui.printEditMember();
        input = ui.getInputNumber(4);
        switch (input){
            case("1") -> ;
            case("2") -> ;
            case("3") -> ;
            case("4") -> {
                return;
            }
        }
    }

    public void economy(){
        ui.printEconomyMenu();
        input = ui.getInputNumber(4);
        switch (input){
            case("1") -> ;
            case("2") -> ;
            case("3") -> ;
            case("4") -> {
                return;
            }
        }
    }
}
