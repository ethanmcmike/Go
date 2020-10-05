package ethanmcmike.go.models;

public enum Tessellation {

    SQUARE(4),
    TRIANGLE(6),
    HEXAGON(3);

    public final int id;
    Tessellation(int id){
        this.id = id;
    }
}
