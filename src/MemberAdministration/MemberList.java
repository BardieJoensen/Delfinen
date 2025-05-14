package MemberAdministration;

import Utilities.DateUtil;

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
                LocalDate birthday = DateUtil.parseDate(attributes[2]);
                LocalDate signUpDate = DateUtil.parseDate(attributes[3]);
                LocalDate membershipExpirationDate = DateUtil.parseDate(attributes[4]);
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
        return  new ArrayList<>(memberList);
    }

    public Member getMember(int memberId){
        int index = Collections.binarySearch(memberList, new Member(memberId, null, null, null, null, false, false), Comparator.comparing(Member::getMemberId));
        return (index >= 0) ? memberList.get(index) : null;
    }

    public ArrayList<Member> searchForMember(String string){
        ArrayList<Member> searchResults = new ArrayList<>();
        try{
            int memberId = Integer.parseInt(string);
            Member member = getMember(memberId);
            if(member == null) throw new NumberFormatException();
            searchResults.add(member);
        }catch (NumberFormatException e){
            String[] searchWords = string.split(" ");
            for(Member m : memberList){
                for(String s : searchWords){
                    if(m.getName().toLowerCase().contains(s.toLowerCase())) {
                        searchResults.add(m);
                    }
                    if(String.valueOf(m.getMemberId()).contains(s)){
                        searchResults.add(m);
                    }
                }
            }
        }
        return searchResults;
    }

    private void sortMemberList(){
        Collections.sort(memberList);
    }

    public void addMember(Member member){
        memberList.add(member);
    }

    public void removeMember(int memberId){
        memberList.remove(getMember(memberId));
    }

    public ArrayList<Member> getMembersInArrears(){
        ArrayList<Member> list = new ArrayList<>();
        for(Member m : memberList){
            if (!m.hasPaid()){
                list.add(m);
            }
        }
        return list;
    }
}
