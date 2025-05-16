import Utilities.DateUtil;

import java.time.LocalDate;
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

    public void printOptionOrBack(String option){
        System.out.println("\n1. "+option);
        System.out.println("2. Tilbage");
    }

    public void printEditMember(){
        System.out.println("""
                
                1. Skift Konkurrencestatus
                2. Skift aktivitetsstatus
                3. Udmeld medlem
                4. Tilbage til medlemsadministration""");
    }

    public void printMemberOverviewMenu(){
        System.out.println("""

                1. Næste side
                2. Vælg medlem (ID)
                3. Tilbage til medlemsadministration""");
    }

    public void printEconomyMenu(){
        System.out.println("""
                
                1. Se forventet indkomst
                2. Registrer betaling
                3. Se restance-liste
                4. Tilbage til hovedmenu""");
    }

    //TO-DO konkurrencesvømningsmenu
    public void showMessage(String message) {
        System.out.println(message);
    }

    public String getInputString(String prompt){
        System.out.print(prompt);
        return scanner.nextLine();
    }

    public String getInputNumber(int amountOptions) {

        int userInt;

        System.out.print("Indtast valg: ");

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
        System.out.print(prompt);
        LocalDate date;
        while(true){
            try{
                String input = scanner.nextLine();
                date = DateUtil.parseDate(input);
                break;
            } catch (Exception e) {
                System.out.print("Fejl - ugyldig dato: indtast venligst gyldig dato i formatet [dd.mm.åååå]:");
            }
        }

        return date;
    }


}
