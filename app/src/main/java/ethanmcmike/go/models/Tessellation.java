package ethanmcmike.go.models;

public enum Tessellation {

    SQUARE(4, "Standard"),
    TRIANGLE(3, "Triangle"),
    HEXAGON(6, "Hexagon");

    public final int id;
    public final String name;

    Tessellation(int id, String name){
        this.id = id;
        this.name = name;
    }
}