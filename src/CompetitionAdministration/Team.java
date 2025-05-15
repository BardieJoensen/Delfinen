package CompetitionAdministration;

import MemberAdministration.Member;

import java.util.ArrayList;

public class Team {
    private ArrayList<Member> teamList;
    private String teamName;

    public Team(String teamName){
        this.teamList = new ArrayList<>();
        this.teamName = teamName;
    }

    public void addTeamMember(Member member){
        if(member.getAge() < 18 && teamName.equalsIgnoreCase("junior")){
            teamList.add(member);
        }else if(member.getAge() >= 18 && teamName.equalsIgnoreCase("senior")){
            teamList.add(member);
        }
    }
}
