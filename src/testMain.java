import MemberAdministration.Member;
import MemberAdministration.MemberList;

public class testMain {
    public static void main(String[] args) {
        MemberList memberList = new MemberList("./resources/MemberList.csv");
        SwimClubController s = new SwimClubController();
        s.seeMembers();
    }
}
