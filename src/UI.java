import java.time.LocalDate;
import java.util.Scanner;

public class UI {
    private final Scanner scanner = new Scanner(System.in);

    public void printMainMenu() {
        for (MainMenuOption option : MainMenuOption.values()) {
            System.out.println(option.getValue() + ". " + option.getDisplayText());
        }
    }

    public void printMemberAdministrationMenu() {
        for (AdminMenuOption option : AdminMenuOption.values()) {
            System.out.println(option.getValue() + ". " + option.getDisplayText());
        }
    }

    public void printEconomyMenu() {
        for (EconomyMenuOption option : EconomyMenuOption.values()) {
            System.out.println(option.getValue() + ". " + option.getDisplayText());
        }
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

    public void printArrearsMenu(){
        System.out.println("""
                1. Udmeld alle medlemmer i restance (3-4 måneder uden betaling)
                2. Tilbage til økonomimenu""");
    }

    public void showMessage(String message) {
        System.out.println(message);
    }

    public String getInputString(String prompt){
        System.out.println(prompt);
        return scanner.nextLine();
    }

    public MainMenuOption getMainMenuInput() {
        int userInt = getInputNumberValue(MainMenuOption.values().length);
        return MainMenuOption.fromValue(userInt);
    }

    public AdminMenuOption getAdminMenuInput() {
        int userInt = getInputNumberValue(AdminMenuOption.values().length);
        return AdminMenuOption.fromValue(userInt);
    }

    public EconomyMenuOption getEconomyMenuInput() {
        int userInt = getInputNumberValue(EconomyMenuOption.values().length);
        return EconomyMenuOption.fromValue(userInt);
    }

    // Helper method to get numeric input
    private int getInputNumberValue(int amountOptions) {
        System.out.println("Indtast valg: ");
        int userInt;

        while(true) {
            try {
                userInt = Integer.parseInt(scanner.nextLine());
            } catch (NumberFormatException e) {
                System.out.println("Indtast venligst et gyldigt tal");
                continue;
            }

            if (userInt > amountOptions || userInt <= 0) {
                System.out.println("Indtast venligst et tal mellem 1 og " + amountOptions);
                continue;
            }
            break;
        }
        return userInt;
    }

    // Keep this method for backward compatibility
    public String getInputNumber(int amountOptions) {
        int userInt = getInputNumberValue(amountOptions);
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