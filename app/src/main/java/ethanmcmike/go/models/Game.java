package ethanmcmike.go.models;

import android.graphics.Color;
import android.widget.Toast;

public class Game {

    int size;
    Board board;
    public Player p1, p2, turn;

    public Game(){
        size = 19;
        board = new Board(size);
        init();
    }

    public Game(int size){
        this.size = size;
        board = new Board(size);
        init();
    }

    public void init(){
        p1 = new Player('A', Color.BLACK);
        p2 = new Player('B', Color.WHITE);
        turn = p1;
    }

    public void play(int x, int y){

        boolean valid = board.set(y, x, turn.id);

        if(valid)
            incPlayer();
    }

    private void incPlayer(){
        turn = (turn == p1) ? p2 : p1;
    }

    private void decPlayer(){
        turn = (turn == p1) ? p2 : p1;
    }

    public Board getBoard(){
        return board;
    }

    public int getColor(char id){
        if(id == 'A')
            return Color.BLACK;
        else if(id == 'B')
            return Color.WHITE;

        return Color.TRANSPARENT;
    }

    public void undo(){
        if(board.undo())
            decPlayer();
    }

    public int getScore(char id){
        int score = board.getCaptures(id);
        score += getLand(id);
        return score;
    }

    public int getLand(char id){
        return 0;
    }

    public void updatePlayers(){

    }
}
