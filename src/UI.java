import MemberAdministration.Member;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Scanner;

public class UI {
    private final Scanner scanner = new Scanner(System.in);

    public void printMainMenu(){
        System.out.println("""
                1. Medlemsadministration
                2. Økonomi
                3. Konkurrencesvømning
                4. Afslut""");
    }

    public void printMemberAdministrationMenu(){
        System.out.println("""
                1. Registrer medlem
                2. Søg efter medlem
                3. Se medlemsliste
                4. Tilbage til hovedmenu""");
    }

    public void printMoreRegistrationOption(){
        System.out.println("""
                1. Registrer flere medlemmer
                2. Tilbage til medlemsadministration""");
    }

    public void printEditMember(){
        System.out.println("""
                1. Skift Konkurrencestatus
                2. Skift aktivitetsstatus
                3. Udmeld medlem
                4. Tilbage til medlemsadministration""");
    }

    public void printPickMemberOption(){
        System.out.println("""
                1. vælg medlem
                2. tilbage til medlemsadministration""");
    }

    public void printMemberOverviewMenu(){
        System.out.println("""
                        1. Næste side
                        2. Forrige side
                        3. Vælg medlem
                        4. Tilbage til medlemsadministration""");
    }

    public void printEconomyMenu(){
        System.out.println("""
                1. Se forventet indkomst
                2. Registrer betaling
                3. Se restance liste
                4. Tilbage til hovedmenu""");
    }

    public void printArrearsMenu(){
        System.out.println("""
                1. Udmeld alle medlemmer i restance (3-4 måneder uden betaling)
                2. Tilbage til økonomimenu""");
    }


    //TO-DO konkurrencesvømningsmenu
    public void showMessage(String message) {
        System.out.println(message);
    }

    public String getInputString(String prompt){
        System.out.println(prompt);
        return scanner.nextLine();
    }

    public String getInputNumber(int amountOptions) {

        int userInt;

        System.out.println("Indtast valg: ");

        while(true) {

            //check if input is valid number
            try {
                userInt = Integer.parseInt(scanner.nextLine());
            } catch (NumberFormatException e) {
                System.out.println("Indtast venligst et gyldigt tal");
                continue;
            }

            //check if input is within bounds
            if (userInt > amountOptions || userInt <= 0) {
                System.out.println("Indtast venligst et tal mellem 1 og " + amountOptions);
                continue;
            }
            break;
        }
        return String.valueOf(userInt);
    }

    public LocalDate getInputDate(String prompt){
        System.out.println(prompt);
        LocalDate date;
        while(true){
            try{
                String input = scanner.nextLine();
                String[] parts = input.split("\\.");
                int day = Integer.parseInt(parts[0]);
                int month = Integer.parseInt(parts[1]);
                int year = Integer.parseInt(parts[2]);
                date = LocalDate.of(year,month,day);
                break;
            } catch (Exception e) {
                System.out.println("Fejl - ugyldig dato: indtast venligst gyldig dato i formatet [dd.mm.åååå]");
            }
        }

        return date;
    }


}
