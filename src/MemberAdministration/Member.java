package MemberAdministration;
import java.time.LocalDate;

public class Member {
    private static int largestId = 0;
    private final int memberId;
    private String name;
    private final LocalDate birthday;
    private final LocalDate signUpDate;
    private LocalDate membershipExpirationDate;
    private boolean isActiveMember;
    private boolean isCompetitive;

    //constructor for creating a new member from UI
    public Member(String name, LocalDate birthday, boolean isCompetitive) {
        this.memberId = createMemberID();
        this.name = name;
        this.birthday = birthday;
        this.signUpDate = LocalDate.now();
        this.membershipExpirationDate = this.signUpDate.plusYears(1);
        this.isActiveMember = true;
        this.isCompetitive = isCompetitive;
    }

    //Constructor for creating a new member with existing data from CSV file
    public Member(int memberId, String name, LocalDate birthday,
                  LocalDate signUpDate, LocalDate membershipExpirationDate,
                  boolean isActiveMember, boolean isCompetitive) {
        this.memberId = memberId;
        this.name = name;
        this.birthday = birthday;
        this.signUpDate = signUpDate;
        this.membershipExpirationDate = membershipExpirationDate;
        this.isActiveMember = isActiveMember;
        this.isCompetitive = isCompetitive;
        largestId = Math.max(memberId, largestId);
    }

    // GETTERS OG SETTERS
    public int getMemberId() {
        return this.memberId;
    }

    public String getName() {
        return this.name;
    }

    public LocalDate getBirthday() {
        return this.birthday;
    }

    public LocalDate getSignUpDate() {
        return this.signUpDate;
    }

    public LocalDate getMembershipExpirationDate() {
        return this.membershipExpirationDate;
    }

    public boolean isActiveMember() {
        return this.isActiveMember;
    }

    public boolean isCompetitive() {
        return this.isCompetitive;
    }

    public boolean hasPaid() {
        LocalDate today = LocalDate.now();
        return (!today.isAfter(membershipExpirationDate));
    }

    public int getAge() {
        LocalDate today = LocalDate.now();
        int age = today.getYear() - birthday.getYear();

        if(today.getMonthValue() < birthday.getMonthValue() ||
           (today.getMonthValue() == birthday.getMonthValue() && today.getDayOfMonth() < birthday.getDayOfMonth())){
            age--;
        }
        return age;
    }

    public void incrementMembershipExpirationDate() {
        membershipExpirationDate = membershipExpirationDate.plusYears(1);
    }

    public void setActiveMember(boolean activeMember) {
        this.isActiveMember = activeMember;
    }

    public void setCompetitive(boolean competitive) {
        this.isCompetitive = competitive;
    }

    public int createMemberID(){
        largestId++;
        return largestId;
    }

    @Override
    public String toString(){
        return String.format("");
    }
}
