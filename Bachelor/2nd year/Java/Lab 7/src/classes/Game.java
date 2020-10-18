package classes;

import java.util.ArrayList;
import java.util.List;

public class Game {
    private Board board;
    private Player winner;
    private final List<Player> players = new ArrayList<>();

    public Game(Board board){
        this.board=board;
    }

    public void addPlayer(Player player) {
        getPlayers().add(player);
        player.setGame(this);
    }

    public void start(){
        for (Player player:players){
            player.run();
        }
    }

    public Board getBoard() {
        return board;
    }

    public void setBoard(Board board) {
        this.board = board;
    }

    public List<Player> getPlayers() {
        return players;
    }

    public Player getWinner() {
        return winner;
    }

    public void setWinner(Player winner) {
        this.winner = winner;
    }
    //Create getters and setters
    //Create the method that will start the game: start one thread for each player
}
