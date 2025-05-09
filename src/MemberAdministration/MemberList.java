package MemberAdministration;

import java.util.ArrayList;

public class MemberList {
    private final ArrayList<Member> memberList;

    public MemberList(String path){
        this.memberList = new ArrayList<>();
        loadMemberListFile(path);
    }

    public void loadMemberListFile(String path){
        
    }

    public void saveMemberList

    public ArrayList<Member> showMembersInArray(){
        return null;
    }

    public ArrayList<Member> showMembers(){
        return null;
    }

    public double calculateExpectedPayments(){
        double expectedPayment = 0.0;
        for(Member m : memberList){
            expectedPayment += MembershipFee.calculatePayment(m );
        }
        return expectedPayment;
    }

    public Member getMember(int memberId){
        return null;
    }

    public void addMember(Member member){
        memberList.add(member);
    }

    public void removeMember(Member member){
        memberList.remove(member);
    }

    public void showMembersInArrears(Member members){
        for(Member m : memberList){
            if (!m.hasPaid()){
                System.out.println("Disse medlemmer har ikke betalt:");
                System.out.println(m);
            }
        }

    }
}
