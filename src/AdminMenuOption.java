public enum AdminMenuOption {
    REGISTER(1, "Registrer medlem"),
    SEARCH(2, "Søg efter medlem"),
    VIEW_LIST(3, "Se medlemsliste"),
    BACK(4, "Tilbage til hovedmenu");

    private final int value;
    private final String displayText;

    AdminMenuOption(int value, String displayText) {
        this.value = value;
        this.displayText = displayText;
    }

    public int getValue() {
        return value;
    }

    public String getDisplayText() {
        return displayText;
    }

    public static AdminMenuOption fromValue(int value) {
        for (AdminMenuOption option : values()) {
            if (option.getValue() == value) {
                return option;
            }
        }
        throw new IllegalArgumentException("Invalid admin menu option: " + value);
    }
}