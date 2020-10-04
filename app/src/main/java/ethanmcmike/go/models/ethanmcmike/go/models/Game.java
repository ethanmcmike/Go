package ethanmcmike.go.models;

import android.graphics.Color;
import android.widget.Toast;

public class Game {

    int size, playerNum;
    MultiDimDriver board;
	Color[] colors = {Color.BLACK, Color.WHITE, Color.RED, Color.GREEN, Color.BLUE};
	public Player[] players;
    public int turn;

    public Game(){
        size = 19;
        board = new MultiDimDriver();
        init();
    }

    public Game(int size){
        this.size = size;
        board = new MultiDimDriver(size);
        init();
    }

    public void init(){
		playerNum = 2;
		for(int i = 0; i < playerNum; i++)
			players[i] = new Player('A' + i, colors[i]);
		
        turn = 0;
    }

    public void play(int x, int y){
        if(board.place(new int[] {x, y}, players[turn].id))
            incPlayer();
    }

    private void incPlayer(){
        turn = (turn+1)%playerNum;
    }

    private void decPlayer(){
        turn = (turn-1)%playerNum;
    }

    public MultiDimDriver getBoard(){
        return board;
    }

    public int getColor(char id){
		if(id < 'A')
			return Color.TRANSPARENT;
		else
			return players[id-'A'+1].color;
    }

    public void undo(){
        if(board.undo())
            decPlayer();
    }

    public int getScore(char id){
        int score = board.getPrisoners(id);
        score += getLand(id);
        return score;
    }

    public int getLand(char id){
        return 0;
    }

    public void updatePlayers(){

    }
}
