import MemberAdministration.Member;
import MemberAdministration.MemberList;

public class testMain {
    public static void main(String[] args) {
        MemberList memberList = new MemberList("./resources/TestMemberList.csv");
        memberList.getMemberList().forEach(System.out::println);
    }
}
