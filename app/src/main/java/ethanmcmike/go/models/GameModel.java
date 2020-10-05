package ethanmcmike.go.models;

public interface GameModel {

    int getNumPlayers();
    int getDimensions();
    int getSize();
    Tessellation getTessellation();

    Player getCurrentPlayer();
    int getScore(Player player);
    Player get(int x, int y);
    boolean play(int x, int y);     //Returns true if move is valid
    void undo();
}
