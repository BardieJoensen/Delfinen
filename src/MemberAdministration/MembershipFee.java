package MemberAdministration;

public interface MembershipFee {

    double DISCOUNT = 0.25;
    int DISCOUNT_AGE = 60;
    double JUNIOR_PRICE = 1000.00;
    int SENIOR_AGE_THRESHOLD = 18;
    double SENIOR_PRICE = 1600.00;
    double PASSIVE_PRICE = 500.00;


    double calculatePayment();
}
