package collection;

public enum FormOfEducation {
    DISTANCE_EDUCATION("DISTANCE"),
    FULL_TIME_EDUCATION("FULL_TIME"),
    EVENING_CLASSES("EVENING");

    private String name;

    FormOfEducation(String name) {
        this.name = name;
    }

    public String getName(){
        return this.name;
    }
}