public enum EconomyMenuOption {
    VIEW_INCOME(1, "Se forventet indkomst"),
    REGISTER_PAYMENT(2, "Registrer betaling"),
    VIEW_ARREARS(3, "Se restance liste"),
    BACK(4, "Tilbage til hovedmenu");

    private final int value;
    private final String displayText;

    EconomyMenuOption(int value, String displayText) {
        this.value = value;
        this.displayText = displayText;
    }

    public int getValue() {
        return value;
    }

    public String getDisplayText() {
        return displayText;
    }

    public static EconomyMenuOption fromValue(int value) {
        for (EconomyMenuOption option : values()) {
            if (option.getValue() == value) {
                return option;
            }
        }
        throw new IllegalArgumentException("Invalid economy menu option: " + value);
    }
}