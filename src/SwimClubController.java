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

    public void run() {
        mainMenu();
    }

    public void mainMenu() {
        while (true) {
            ui.printMainMenu();
            MainMenuOption selectedOption = ui.getMainMenuInput();

            switch (selectedOption) {
                case ADMINISTRATION -> {
                    displayContext = "Administration";
                    memberAdministration();
                }
                case ECONOMY -> {
                    displayContext = "Economy";
                    economy();
                }
                case COMPETITION -> {
                    displayContext = "Competition";
                    ui.showMessage("404 not found");
                }
                case EXIT -> {
                    return;
                }
            }
        }
    }

    public void memberAdministration() {
        while (true) {
            ui.printMemberAdministrationMenu();
            AdminMenuOption selectedOption = ui.getAdminMenuInput();

            switch (selectedOption) {
                case REGISTER -> registerMember();
                case SEARCH -> searchForMember();
                case VIEW_LIST -> seeMembers();
                case BACK -> {
                    return;
                }
            }
        }
    }

    public void economy() {
        ui.printEconomyMenu();
        EconomyMenuOption selectedOption = ui.getEconomyMenuInput();

        switch (selectedOption) {
            case VIEW_INCOME -> totalIncome();
            case REGISTER_PAYMENT -> registerPayment();
            case VIEW_ARREARS -> arrearsList();
            case BACK -> {
                return;
            }
        }
    }


    public void registerMember() {
        String name = ui.getInputString("Indtast navn på medlem");
        LocalDate birthday = ui.getInputDate("Indtast fødselsdato");
        boolean competitive = ui.getInputString("Konkurrencesvømmer [ja/nej]").equals("ja");
        Member member = new Member(name, birthday, competitive);
        memberList.addMember(member);

        ui.printMoreRegistrationOption();
        input = ui.getInputNumber(2);
        switch (input) {
            case ("1") -> registerMember();
            case ("2") -> {
                return;
            }
        }
    }

    public void searchForMember() {
        String search = ui.getInputString("Indtast søgning:");
        ArrayList<Member> searchResults = memberList.searchForMember(search);

        ui.showMessage("Søgeresultat:");
        searchResults.forEach(m -> ui.showMessage(m.toString()));

        if (searchResults.size() == 1) {
            editMember(searchResults.get(0));
        } else {
            ui.printPickMemberOption();
            input = ui.getInputNumber(2);
            if (input.equals("1")) {
                pickMember();
            } else {
                return;
            }
        }
    }

    public void seeMembers() {
        for (int i = 0; i < memberList.getMemberList().size(); i++) {
            ui.showMessage(memberList.getMemberList().get(i).toString());

            if ((i + 1) % 10 == 0 || i == memberList.getMemberList().size() - 1) {
                ui.printMemberOverviewMenu();
                input = ui.getInputNumber(3);
                switch (input) {
                    case ("1") -> {
                        continue;
                    }
                    case ("2") -> {
                        if (i >= 19) i -= (i != memberList.getMemberList().size() - 1) ? 20 : ((i + 1) % 10 + 10);
                    }
                    case ("3") -> pickMember();
                    case ("4") -> {
                        return;
                    }
                }
            }
        }
    }

    public void pickMember() {
        input = ui.getInputNumber(Member.getLargestId());
        editMember(memberList.getMember(Integer.parseInt(input)));
    }

    public void editMember(Member member) {
        ui.showMessage("Redigerer: " + member.getMemberId() + " "
                + member.getName() + " "
                + member.isActiveMember() + " "
                + member.isCompetitive());
        ui.printEditMember();
        input = ui.getInputNumber(4);
        switch (input) {
            case ("1") -> {
                member.setCompetitive(!member.isCompetitive());
                ui.showMessage("Konkurrencestatus for " + member.getName() + " er nu: " + member.isCompetitiveAsString());
                editMember(member);
            }
            case ("2") -> {
                member.setActiveMember(!member.isActiveMember());
                ui.showMessage("Aktivitetsstatus for " + member.getName() + " er nu: " + member.isActiveMemberAsString());
                editMember(member);
            }
            case ("3") -> {
                memberList.removeMember(member.getMemberId());
                ui.showMessage("Fjernet " + member.getName() + " fra listen");
            }
            case ("4") -> {
                return;
            }
        }
    }

    public void totalIncome() {
        double expectedPayment = memberList.calculateExpectedPayments();
        ui.showMessage(String.format("Den samlede forventede indkomst er: %.2f kr. per år.", expectedPayment));
    }

    public void registerPayment() {
        String memberIdStr = ui.getInputString("Indtast medlems ID:");
        try {
            int memberId = Integer.parseInt(memberIdStr);
            Member member = memberList.getMember(memberId);

            if (member != null) {
                if (!member.hasPaid()) {
                    member.incrementMembershipExpirationDate();
                    ui.showMessage("Betaling registreret for " + member.getName());
                    ui.showMessage("Nyt udløbsdato: " + member.getMembershipExpirationDate());
                    // Save changes to file
                    memberList.saveMemberList();
                } else {
                    ui.showMessage(member.getName() + " har allerede betalt. Medlemskab udløber: " +
                            member.getMembershipExpirationDate());
                }
            } else {
                ui.showMessage("Medlem med ID " + memberId + " findes ikke.");
            }
        } catch (NumberFormatException e) {
            ui.showMessage("Ugyldigt medlem ID. Indtast venligst et tal.");
        }
    }

    public void arrearsList() {
        ArrayList<Member> membersInArrears = memberList.getMembersInArrears();

        if (membersInArrears.isEmpty()) {
            ui.showMessage("Ingen medlemmer i restance.");
            return;
        }

        double totalArrears = 0.0;
        ui.showMessage("Medlemmer i restance:");

        for (Member member : membersInArrears) {
            double fee = MembershipFee.calculatePayment(member);
            ui.showMessage(String.format("%s (ID: %d)", member.getName(), member.getMemberId()));
            ui.showMessage(String.format("Udstående betaling: %.2f DKK", fee));
            ui.showMessage(String.format("Medlemskab udløbet: %s", member.getMembershipExpirationDate()));
            ui.showMessage("---");
            totalArrears += fee;
        }

        ui.showMessage(String.format("Total udestående: %.2f DKK", totalArrears));

        ui.printArrearsMenu();
        input = ui.getInputNumber(2);
        if (input.equals("1")) {
            removeArrearsMembers();
        }
    }

    private void removeArrearsMembers() {
        LocalDate thresholdDate = LocalDate.now().minusMonths(3);
        int removedCount = 0;

        ArrayList<Member> toRemove = new ArrayList<>();
        for (Member member : memberList.getMembersInArrears()) {
            if (member.getMembershipExpirationDate().isBefore(thresholdDate)) {
                toRemove.add(member);
                removedCount++;
            }
        }

        for (Member member : toRemove) {
            ui.showMessage("Udmelder: " + member.getName() + " (ID: " + member.getMemberId() + ")");
            memberList.removeMember(member.getMemberId());
        }

        ui.showMessage("Udmeldt " + removedCount + " medlemmer i restance over 3 måneder.");
        memberList.saveMemberList();
    }
}