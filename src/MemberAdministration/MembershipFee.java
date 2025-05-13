package MemberAdministration;

public class MembershipFee {
    // Keep these constants public so they can be used by MembershipType
    public static final double DISCOUNT = 0.25;
    public static final int DISCOUNT_AGE = 60;
    public static final int SENIOR_AGE_THRESHOLD = 18;

    /**
     * Calculates the membership fee for a given member
     * @param member The member to calculate fee for
     * @return The annual fee amount
     */
    public static double calculatePayment(Member member) {
        MembershipType type = MembershipType.forMember(member);
        return type.getAnnualFee();
    }

    /**
     * Gets the membership type for a member
     * @param member The member to evaluate
     * @return The membership type
     */
    public static MembershipType getMembershipType(Member member) {
        return MembershipType.forMember(member);
    }
}