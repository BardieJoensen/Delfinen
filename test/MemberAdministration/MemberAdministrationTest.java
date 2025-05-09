package MemberAdministration;

import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

class MemberAdministrationTest {


    //member tests
    @Test
    void expectedExpirationDate() {

        LocalDate expectedExpirationDate = LocalDate.now().plusYears(1);
        LocalDate birthday = LocalDate.of(1969,8,14);
        Member member = new Member("Jørgen Mikkelsen", birthday, true);

        assertEquals(expectedExpirationDate, member.getMembershipExpirationDate());
    }

    @Test
    void IdGeneration() {
        //first member that has an ID of 11
        Member member1 = new Member(11,"Erik Jensen",
                LocalDate.of(1975,3,13),
                LocalDate.now(),
                LocalDate.now().plusYears(1),
                true,
                true);

        //second member that should get an id of 12
        LocalDate birthday = LocalDate.of(1969,8,14);
        Member member2 = new Member("Jørgen Mikkelsen", birthday, true);

        assertEquals(12, member2.getMemberId());
    }

    //MembershipFee test

    @Test
    void memberShipFeeJunior() {

    }

}