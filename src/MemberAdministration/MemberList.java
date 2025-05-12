package MemberAdministration;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Scanner;

public class MemberList {
    private final String path;
    private final ArrayList<Member> memberList;

    public MemberList(String path){
        this.memberList = new ArrayList<>();
        this.path = path;
        loadMemberListFile(path);
    }

    private void loadMemberListFile(String path){
        File csvFile = new File(path);
        int counter = 1;
        try (Scanner reader = new Scanner(csvFile)){
            //skip header
            reader.nextLine();
            //load members
            while(reader.hasNextLine()){
                counter++;
                String[] attributes = reader.nextLine().split(";");
                int memberId = Integer.parseInt(attributes[0]);
                String name = attributes[1];
                LocalDate birthday = convertStringToDate(attributes[2]);
                LocalDate signUpDate = convertStringToDate(attributes[3]);
                LocalDate membershipExpirationDate = convertStringToDate(attributes[4]);
                boolean isActiveMember = (attributes[5].equalsIgnoreCase("aktiv"));
                boolean isCompetitive = (attributes[6].equalsIgnoreCase("ja"));

                Member member = new Member(memberId,name,birthday,signUpDate,membershipExpirationDate,isActiveMember,isCompetitive);
                memberList.add(member);
            }
        }catch(FileNotFoundException e){
            throw new RuntimeException("File not found: " + path, e);
        }catch(Exception e){
            throw new RuntimeException("Incompatible file: error at line " + counter + " in file: " + csvFile.getAbsolutePath());
        }
        sortMemberList();
    }

    public LocalDate convertStringToDate(String string){
        String[] arr = string.split("\\.");
        int day = Integer.parseInt(arr[0]);
        int month = Integer.parseInt(arr[1]);
        int year = Integer.parseInt(arr[2]);
        return LocalDate.of(year,month,day);
    }

    public void saveMemberList(){
        sortMemberList();
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

    public double calculateExpectedPayments(){
        double expectedPayment = 0.0;
        for(Member m : memberList){
            expectedPayment += MembershipFee.calculatePayment(m );
        }
        return expectedPayment;
    }

    public ArrayList<Member> getMemberList() {
        return  new ArrayList<Member>(memberList);
    }

    public Member getMember(int memberId){
        int index = Collections.binarySearch(memberList, new Member(memberId, null, null, null, null, false, false), Comparator.comparing(Member::getMemberId));
        return (index >= 0) ? memberList.get(index) : null;
    }

    private void sortMemberList(){
        Collections.sort(memberList);
    }

    public void addMember(Member member){
        memberList.add(member);
    }

    public void removeMember(Member member){
        memberList.remove(member);
    }

    public ArrayList<Member> getMembersInArrears(Member members){
        ArrayList<Member> list = new ArrayList<>();
        for(Member m : memberList){
            if (!m.hasPaid()){
                list.add(m);
            }
        }
        return list;
    }
}
