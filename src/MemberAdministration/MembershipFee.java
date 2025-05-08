package MemberAdministration;

public class MembershipFee {

    private static final double DISCOUNT = 0.25;
    private static final int DISCOUNT_AGE = 60;
    private static final double JUNIOR_PRICE = 1000.00;
    private static final int SENIOR_AGE_THRESHOLD = 18;
    private static final double SENIOR_PRICE = 1600.00;
    private static final double PASSIVE_PRICE = 500.00;


    public static double calculatePayment(Member member){
        if(!member.isActiveMember()){
            return PASSIVE_PRICE;
        }
        if(member.getAge() < SENIOR_AGE_THRESHOLD){
            return JUNIOR_PRICE;
        }
        if (member.getAge() > DISCOUNT_AGE){
            return SENIOR_PRICE * DISCOUNT;
        }else{
            return SENIOR_PRICE;
        }
    }

}
