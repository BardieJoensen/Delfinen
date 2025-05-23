package MemberAdministration;

import Utilities.DateUtil;

import java.time.LocalDate;

public class Member implements Comparable<Member>, MembershipFee {
    private static int largestId = 0;

    private final int memberId;
    private final String name;
    private final LocalDate birthday;
    private final LocalDate signUpDate;
    private LocalDate membershipExpirationDate;
    private boolean isActiveMember;
    private boolean isCompetitive;


    //constructor for creating a new member from UI
    public Member(String name, LocalDate birthday, boolean isCompetitive) {
        this(createMemberID(),
                name,
                birthday,
                LocalDate.now(),
                LocalDate.now().plusYears(1),
                true,
                isCompetitive);
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
    }
    // GETTERS OG SETTERS

    public static int getLargestId() {
        return largestId;
    }

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

    public String isActiveMemberAsString() {
        return (isActiveMember) ? "aktiv" : "inaktiv";
    }

    public boolean isCompetitive() {
        return this.isCompetitive;
    }

    public String isCompetitiveAsString() {
        return (isCompetitive) ? "ja" : "nej";
    }

    public boolean hasPaid() {
        LocalDate today = LocalDate.now();
        return (!today.isAfter(membershipExpirationDate));
    }

    public int getAge() {
        return DateUtil.calculateAge(birthday);
    }

    public boolean isJunior() {
        return getAge() < SENIOR_AGE_THRESHOLD;
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

    public static void setLargestId(int newLargestId) {
        largestId = newLargestId;
    }

    private static int createMemberID() {
        largestId++;
        return largestId;
    }

    public static Member fromString(String string) {
        String[] attributes = string.split(";");
        int memberId = Integer.parseInt(attributes[0]);
        String name = attributes[1];
        LocalDate birthday = DateUtil.parseDate(attributes[2]);
        LocalDate signUpDate = DateUtil.parseDate(attributes[3]);
        LocalDate membershipExpirationDate = DateUtil.parseDate(attributes[4]);
        boolean isActiveMember = (attributes[5].equalsIgnoreCase("aktiv"));
        boolean isCompetitive = (attributes[6].equalsIgnoreCase("ja"));

        return new Member(memberId, name, birthday, signUpDate, membershipExpirationDate, isActiveMember, isCompetitive);
    }

    public String[] toStringArray() {
        String[] attributes = new String[7];
        attributes[0] = String.valueOf(memberId);
        attributes[1] = name != null ? name : "";
        attributes[2] = birthday != null ? birthday.format(DateUtil.DATE_FORMATTER) : "";
        attributes[3] = signUpDate != null ? signUpDate.format(DateUtil.DATE_FORMATTER) : "";
        attributes[4] = membershipExpirationDate != null ? membershipExpirationDate.format(DateUtil.DATE_FORMATTER) : "";
        attributes[5] = isActiveMember ? "aktiv" : "inaktiv";
        attributes[6] = isCompetitive ? "ja" : "nej";
        return attributes;
    }

    @Override
    public String toString() {
        return String.format("ID: %-6s Navn: %-20s Født: %10s   Indmeldt: %10s   Udløber: %10s   Konkurrencesvømmer: %-12s",
                String.format("%04d", memberId),
                formatName(name),
                birthday.format(DateUtil.DATE_FORMATTER),
                signUpDate.format(DateUtil.DATE_FORMATTER),
                membershipExpirationDate.format(DateUtil.DATE_FORMATTER),
                isCompetitive ? "ja" : "nej");
    }

    public static String formatName(String name) {
        while (name.length() > 20) {
            String[] nameSplit = name.split(" ");

            int names = nameSplit.length;
            if (names > 2) {
                name = nameSplit[0] + " " + nameSplit[names - 1];
            } else if(names == 2){
                name = nameSplit[0].charAt(0) + ". " + nameSplit[names - 1];
                if (name.length() > 20) name = name.substring(0, 20);
            } else {
                name = name.substring(0, 20);
            }
        }
        return name;
    }

    @Override
    public int compareTo(Member that) {
        return Integer.compare(this.memberId, that.memberId);
    }

    @Override
    public double calculatePayment() {
        if (!isActiveMember()) {
            return PASSIVE_PRICE;
        }
        if (getAge() < SENIOR_AGE_THRESHOLD) {
            return JUNIOR_PRICE;
        }
        if (getAge() >= DISCOUNT_AGE) {
            return SENIOR_PRICE * (1 - DISCOUNT);
        } else {
            return SENIOR_PRICE;
        }
    }
}
