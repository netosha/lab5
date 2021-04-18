package collection;

public enum Color {
    BLUE("BLUE"),
    WHITE("WHITE"),
    BROWN("BROWN");

    private String name;

    Color(String name) {
        this.name = name;
    }

    public String getName(){
        return this.name;
    }
}