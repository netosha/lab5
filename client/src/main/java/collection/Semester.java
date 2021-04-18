package collection;

public enum Semester {
    SECOND("SECOND"),
    THIRD("THIRD"),
    FOURTH("FOURTH"),
    SIXTH("SIXTH"),
    EIGHTH("EIGHTH");

    private String name;

    Semester(String name) {
        this.name = name;
    }

    public String getName(){
        return this.name;
    }
}