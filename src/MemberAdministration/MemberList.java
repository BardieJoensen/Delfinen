package MemberAdministration;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Scanner;

public class MemberList {
    private final String path;
    private final ArrayList<Member> memberList;

    public MemberList(String path){
        this.memberList = new ArrayList<>();
        this.path = path;
        loadMemberListFile(path);
    }

    public void loadMemberListFile(String path){
        File csvFile = new File(path);
        try (Scanner reader = new Scanner(csvFile)){
            //skip header
            reader.nextLine();
            //load members
            while(reader.hasNextLine()){
                String[] attributes = reader.nextLine().split(";");

                int memberId = Integer.parseInt(attributes[0]);
                String name = attributes[1];
                LocalDate birthday = convertStringToDate(attributes[2]);
                LocalDate signUpDate = convertStringToDate(attributes[3]);
                LocalDate membershipExpirationDate = convertStringToDate(attributes[4]);
                boolean isActiveMember = (attributes[5].equalsIgnoreCase("active"));
                boolean isCompetitive = (attributes[6].equalsIgnoreCase("ja"));

                Member member = new Member(memberId,name,birthday,signUpDate,membershipExpirationDate,isActiveMember,isCompetitive);
                memberList.add(member);
            }
        }catch(FileNotFoundException e){
            throw new RuntimeException("File not found: " + path, e);
        }
    }

    public LocalDate convertStringToDate(String string){
        String[] arr = string.split("\\.");
        int day = Integer.parseInt(arr[0]);
        int month = Integer.parseInt(arr[1]);
        int year = Integer.parseInt(arr[2]);
        return LocalDate.of(year,month,day);
    }

    public void saveMemberList(){
        try(PrintWriter pw = new PrintWriter(path)){
            //add header
            pw.println("memberId;name;birthday;signUpDate;membershipExpirationDate;isActiveMember;isCompetitive");
            //add members
            for(Member m : memberList){
                String[] attributes = m.toStringArray();
                String line = String.join(";",attributes);
                pw.println(line);
            }
        }catch (FileNotFoundException e){
            throw new RuntimeException("File not found: " + path, e);
        }
    }

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

    public ArrayList<Member> getMemberList() {
        return memberList;
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
