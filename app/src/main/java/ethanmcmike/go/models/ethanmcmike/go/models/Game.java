package ethanmcmike.go.models;

import android.graphics.Color;
import android.widget.Toast;

public class Game {

    int size;
    MultiDimDriver board;
    public Player p1, p2, turn;

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
        p1 = new Player('A', Color.BLACK);
        p2 = new Player('B', Color.WHITE);
        turn = p1;
    }

    public void play(int x, int y){

        boolean valid = board.place(new int[] {x, y}, turn.id);

        if(valid)
            incPlayer();
    }

    private void incPlayer(){
        turn = (turn == p1) ? p2 : p1;
    }

    private void decPlayer(){
        turn = (turn == p1) ? p2 : p1;
    }

    public MultiDimDriver getBoard(){
        return board;
    }

    public int getColor(char id){
		switch(id) {
			case 'A':
				return Color.BLACK;
			case 'B':
				return Color.WHITE;
			default:
				return Color.TRANSPARENT;
		}
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
