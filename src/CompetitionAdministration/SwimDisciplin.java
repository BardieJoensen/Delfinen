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

    public static SwimDisciplin fromString(String disciplin) {
        for (SwimDisciplin s : values()) {
            if (s.getName().equalsIgnoreCase(disciplin)) {
                return s;
            }
        }
        throw new IllegalArgumentException("Invalid swim disciplin: " + disciplin);
    }
}
