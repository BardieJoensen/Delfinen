package CompetitionAdministration;

public enum SwimDisciplin {
    CRAWL("crawl"),
    BUTTERFLY("butterfly"),
    RYGCRAWL("rygcrawl"),
    BRYST("bryst"),
    ;

    private final String name;

    SwimDisciplin(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
