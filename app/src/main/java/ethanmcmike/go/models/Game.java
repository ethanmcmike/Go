package ethanmcmike.go.models;

import android.graphics.Color;

public class Game {

    int size, playerNum;
    public Tessellation tessellation;
    MultiDimDriver driver;
	public Player[] players;
    public int turn;

    public Game(int size, Player[] players, Tessellation tessellation){

        this.size = size;
        playerNum = players.length;
        this.players = players;
        this.tessellation = tessellation;

        driver = new MultiDimDriver(size, 2, tessellation.id);  //TODO just send enum

        turn = 0;
    }

    public void play(int x, int y){
        if(driver.place(new int[] {x, y}, players[turn].id))
            incPlayer();
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

    public int getColor(char id){
		if(id < 'A')
			return Color.TRANSPARENT;
		else
			return players[id-'A'].color;
    }

    public void undo(){
        if(driver.undo())
            decPlayer();
    }

    public int getScore(char id){
        int score = driver.getPrisoners(id);
        score += getLand(id);
        return score;
    }

    public int getLand(char id){
        return 0;
    }
}
