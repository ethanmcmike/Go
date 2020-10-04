package ethanmcmike.go.models;

public class Player {

    public char id;
    public int color;
    private int prisoners, land, deltaPrisoners, deltaLand;

    public Player(char id, int color){
        this.id = id;
        this.color = color;
        prisoners = 0;
        land = 0;
    }

    public void setPrisoners(int prisoners){
        deltaPrisoners = prisoners - this.prisoners;
        this.prisoners = prisoners;
    }

    public void addPrisoners(int prisoners){
        this.prisoners += prisoners;
        deltaPrisoners = prisoners;
    }

    public int getPrisoners(){
        return prisoners;
    }

    public int getDeltaPrisoners(){
        return deltaPrisoners;
    }

    public void setLand(int land){
        deltaLand = land - this.land;
        this.land = land;
    }

    public void addLand(int land){
        this.land += land;
        deltaLand = land;
    }

    public int getLand(){
        return land;
    }

    public int getDeltaLand(){
        return deltaLand;
    }
}
