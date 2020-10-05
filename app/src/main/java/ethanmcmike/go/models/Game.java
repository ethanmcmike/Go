package ethanmcmike.go.models;

public class Game implements GameModel {

    private int playerNum;
    public Tessellation tessellation;
    MultiDimDriver driver;
	public Player[] players;
    public int turn;

    public Game(int size, Player[] players, Tessellation tessellation){

        playerNum = players.length;
        this.players = players;
        this.tessellation = tessellation;

        driver = new MultiDimDriver(size, 2, tessellation.id);  //TODO just send enum

        turn = 0;
    }



    private void incPlayer(){
        turn = (turn+1)%playerNum;
    }

    private void decPlayer(){
        turn = ((turn-1)%playerNum + playerNum) % playerNum;
    }

    public MultiDimDriver getDriver(){
        return driver;
    }

    @Override
    public boolean play(int x, int y){

        boolean success = driver.place(new int[]{x, y}, players[turn].id);

        if(success)
            incPlayer();

        return success;
    }

    @Override
    public void undo(){
        if(driver.undo())
            decPlayer();
    }

    @Override
    public int getNumPlayers() {
        return playerNum;
    }

    @Override
    public int getDimensions() {
        return driver.dimension;
    }

    @Override
    public int getSize() {
        return driver.getSize();
    }

    @Override
    public Tessellation getTessellation() {
        return tessellation;
    }

    @Override
    public Player getCurrentPlayer() {
        return players[turn];
    }

    @Override
    public int getScore(Player player) {
        int territory = driver.getTerritory(player.id);
        int prisoners = driver.getPrisoners(player.id);

        return territory + prisoners;
    }

    @Override
    public Player get(int x, int y) {

        char playerId = driver.board.get(new int[]{x, y});
        int playerIndex = playerId - 'A';

        if(playerIndex > -1 && playerIndex < playerNum){
            return players[playerIndex];
        }

        return null;
    }
}
