package MemberAdministration;

public enum MembershipType {
    JUNIOR(1000.00, "Junior medlem"),
    SENIOR(1600.00, "Senior medlem"),
    SENIOR_DISCOUNTED(1200.00, "Senior medlem med rabat"),  // 1600 * (1-0.25)
    PASSIVE(500.00, "Passivt medlem");

    private final double annualFee;
    private final String displayName;

    MembershipType(double annualFee, String displayName) {
        this.annualFee = annualFee;
        this.displayName = displayName;
    }

    public double getAnnualFee() {
        return annualFee;
    }

    public String getDisplayName() {
        return displayName;
    }

    /**
     * Determines the membership type based on a member's attributes
     * @param member The member to evaluate
     * @return The appropriate membership type
     */
    public static MembershipType forMember(Member member) {
        if (!member.isActiveMember()) {
            return PASSIVE;
        }
        if (member.getAge() < MembershipFee.SENIOR_AGE_THRESHOLD) {
            return JUNIOR;
        }
        if (member.getAge() >= MembershipFee.DISCOUNT_AGE) {
            return SENIOR_DISCOUNTED;
        }
        return SENIOR;
    }
}