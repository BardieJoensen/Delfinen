public enum MainMenuOption {
    ADMINISTRATION(1, "Medlemsadministration"),
    ECONOMY(2, "Økonomi"),
    COMPETITION(3, "Konkurrencesvømning"),
    EXIT(4, "Afslut");

    private final int value;
    private final String displayText;

    MainMenuOption(int value, String displayText) {
        this.value = value;
        this.displayText = displayText;
    }

    public int getValue() {
        return value;
    }

    public String getDisplayText() {
        return displayText;
    }

    public static MainMenuOption fromValue(int value) {
        for (MainMenuOption option : values()) {
            if (option.getValue() == value) {
                return option;
            }
        }
        throw new IllegalArgumentException("Invalid main menu option: " + value);
    }
}