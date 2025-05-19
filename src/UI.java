import CompetitionAdministration.SwimDisciplin;
import Utilities.DateUtil;
import Utilities.TimeUtil;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Scanner;

public class UI {
    private final Scanner scanner = new Scanner(System.in);

    public void printMainMenu() {
        System.out.println("""
                
                1. Medlemsadministration
                2. Økonomi
                3. Konkurrencesvømning
                4. Afslut""");
    }

    public void printMemberAdministrationMenu() {
        System.out.println("""
                
                1. Registrer medlem
                2. Søg efter medlem
                3. Se medlemsliste
                4. Tilbage til hovedmenu""");
    }

    public void printOptionOrBack(String option) {
        System.out.println("\n1. " + option);
        System.out.println("2. Tilbage");
    }

    public void printEditMember() {
        System.out.println("""
                
                1. Skift Konkurrencestatus
                2. Skift aktivitetsstatus
                3. Udmeld medlem
                4. Tilbage til medlemslisten""");
    }

    public void printMemberOverviewMenu() {
        System.out.println("""
                
                1. Næste side
                2. Forrige side
                3. Vælg medlem (ID)
                4. Tilbage til medlemsadministration""");
    }

    public void printEconomyMenu() {
        System.out.println("""
                
                1. Se forventet indkomst
                2. Registrer betaling
                3. Se restance-liste
                4. Tilbage til hovedmenu""");
    }

    public void printCompetitionMenu() {
        System.out.println("""
                
                1. Registrer resultat
                2. Se holdliste
                3. Se topresultater
                4. Tilbage til hovedmenu""");
    }

    public void printResultRegistrationMenu() {
        System.out.println("""
                
                1. Registrer træningsresultat
                2. Registrer konkurrenceresultat
                3. Tilbage til konkurrencesvømning""");
    }

    public void printWhichTeam() {
        System.out.println("""
                
                1. Se juniorhold
                2. Se seniorhold
                3. Tilbage til konkurrencesvømning""");
    }

    public void showMessage(String message) {
        System.out.println(message);
    }

    public String getInputString(String prompt) {
        System.out.print(prompt);
        return scanner.nextLine();
    }

    public String getInputNumber(int amountOptions) {

        int userInt;

        System.out.print("Indtast valg: ");

        while (true) {
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

    public LocalDate getInputDate(String prompt) {
        System.out.print(prompt);
        LocalDate date;
        while (true) {
            try {
                String input = scanner.nextLine();
                date = DateUtil.parseDate(input);
                break;
            } catch (Exception e) {
                System.out.print("Fejl - ugyldig dato, indtast venligst gyldig dato i formatet [dd.mm.åååå]:");
            }
        }

        return date;
    }

    public LocalTime getInputTime(String prompt) {
        System.out.print(prompt);
        LocalTime time;
        while (true) {
            try {
                String input = scanner.nextLine();
                time = TimeUtil.parseTime(input);
                break;
            } catch (Exception e) {
                System.out.print("Fejl - ugyldig tid, indtast venligst gyldig tid i formatet [mm:ss.SS]:");
            }
        }

        return time;
    }

    public SwimDisciplin getInputDisciplin(String prompt) {
        System.out.print(prompt);
        SwimDisciplin disciplin;
        while (true) {
            try {
                String input = scanner.nextLine();
                disciplin = SwimDisciplin.fromString(input);
                break;
            } catch (Exception e) {
                StringBuilder message = new StringBuilder("Fejl - ugyldig disciplin, indtast venligst gyldig disciplin: ");
                String[] disciplines = new String[SwimDisciplin.values().length];
                for (int i = 0; i < disciplines.length; i++) {
                    disciplines[i] = SwimDisciplin.values()[i].getName();
                }
                message.append("[").append(String.join("/", disciplines)).append("]");
                System.out.println(message);
            }
        }

        return disciplin;
    }

}
