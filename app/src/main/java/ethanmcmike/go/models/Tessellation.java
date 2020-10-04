package ethanmcmike.go.models;

public enum Tessellation {

    SQUARE(4),
    TRIANGLE(3),
    HEXAGON(6);

    public final int id;
    Tessellation(int id){
        this.id = id;
    }
}
