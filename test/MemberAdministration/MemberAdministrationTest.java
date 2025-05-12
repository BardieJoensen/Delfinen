package MemberAdministration;

import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;

import java.lang.annotation.Annotation;
import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class MemberAdministrationTest {


    //member tests

    @Test
    @Order(1)
    void loadMembers() {

        final int EXPECTED_AMOUNT_OF_MEMBERS = 3;

        String path = "./resources/MemberList.csv";
        MemberList memberList = new MemberList(path);

        assertEquals(EXPECTED_AMOUNT_OF_MEMBERS, memberList.getMemberList().size());
    }

    @Test
    @Order(2)
    void idGeneration() {

        final int EXPECTED_ID = 4;
        String path = "./resources/MemberList.csv";
        MemberList memberList = new MemberList(path);

        LocalDate birthday = LocalDate.of(1969,8,14);
        Member member = new Member("Jørgen Mikkelsen", birthday, true);

        assertEquals(EXPECTED_ID, member.getMemberId());
    }

    @Test
    @Order(3)
    void idGenerationWithExistingMemberList() {

        final int EXPECTED_ID = 5;

        LocalDate birthday = LocalDate.of(1969,8,14);
        Member member = new Member("Jørgen Mikkelsen", birthday, true);

        assertEquals(EXPECTED_ID, member.getMemberId());
    }

    @Test
    void expectedExpirationDate() {
        
        final LocalDate EXPECTED_EXPIRATION_DATE = LocalDate.now().plusYears(1);
        LocalDate birthday = LocalDate.of(1969,8,14);
        Member member = new Member("Jørgen Mikkelsen", birthday, true);

        assertEquals(EXPECTED_EXPIRATION_DATE, member.getMembershipExpirationDate());
    }


    //MembershipFee test

    @Test
    void memberShipFeeJunior() {

        final double EXPECTED_PRICE = 1000.00;

        //test for 17-year-old (should get junior price)
        LocalDate birthdaySeventeenYearsOld = LocalDate.now().minusYears(17);
        Member member = new Member("Lilleper", birthdaySeventeenYearsOld, false);
        assertEquals(EXPECTED_PRICE,MembershipFee.calculatePayment(member));

        //test for 18-year-old (should NOT get junior price)
        LocalDate birthdayEighteenYearsOld = LocalDate.now().minusYears(18);
        Member member2 = new Member("Storeper", birthdayEighteenYearsOld, false);
        assertNotEquals(EXPECTED_PRICE, MembershipFee.calculatePayment(member2));
    }

    @Test
    void memberShipFeeSenior() {

        final double EXPECTED_PRICE = 1600.00;

        //test for 18-year-old (should get senior price)
        LocalDate birthdayEighteenYearsOld = LocalDate.now().minusYears(18);
        Member member = new Member("Storeper", birthdayEighteenYearsOld, false);
        assertEquals(EXPECTED_PRICE,MembershipFee.calculatePayment(member));

        //test for 59-year-old (should get senior price)
        LocalDate birthdayFiftyNineYearsOld = LocalDate.now().minusYears(59);
        Member member2 = new Member("Kæmpeper", birthdayFiftyNineYearsOld, false);
        assertEquals(EXPECTED_PRICE,MembershipFee.calculatePayment(member2));

        //test for 17-year-old (should NOT get senior price)
        LocalDate birthdaySeventeenYearsOld = LocalDate.now().minusYears(17);
        Member member3 = new Member("Lilleper", birthdaySeventeenYearsOld, false);
        assertNotEquals(EXPECTED_PRICE, MembershipFee.calculatePayment(member3));

        //test for 60-year-old (should NOT get senior price)
        LocalDate birthdaySixtyYearsOld = LocalDate.now().minusYears(60);
        Member member4 = new Member("Gamleper", birthdaySixtyYearsOld, false);
        assertNotEquals(EXPECTED_PRICE, MembershipFee.calculatePayment(member4));
    }

    @Test
    void memberShipFeeDiscount() {

        final double EXPECTED_PRICE = 1600.00 * (1-0.25);

        //test for 60-year-old (should get discount)
        LocalDate birthdaySixtyYearsOld = LocalDate.now().minusYears(60);
        Member member = new Member("Gamleper", birthdaySixtyYearsOld, false);
        assertEquals(EXPECTED_PRICE, MembershipFee.calculatePayment(member));

        //test for 59-year-old (should NOT get discount)
        LocalDate birthdayFiftyNineYearsOld = LocalDate.now().minusYears(59);
        Member member2 = new Member("Kæmpeper", birthdayFiftyNineYearsOld, false);
        assertNotEquals(EXPECTED_PRICE,MembershipFee.calculatePayment(member2));
    }

}