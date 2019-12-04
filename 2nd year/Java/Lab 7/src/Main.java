import classes.Board;
import classes.Edge;
import classes.Game;
import classes.Player;

public class Main {

    public static void main(String[] args) {
	    Board board=new Board(2);
    	Game game=new Game(board);
	    game.addPlayer(new Player("Player 1",board));
	    game.addPlayer(new Player("Player 2",board));
	    game.addPlayer(new Player("Player 3",board));
	    game.start();
		for (Player player:game.getPlayers()){
			System.out.println(player.getName() + " has the following edges:");
			for (Edge edge:player.getGraph().getEdgeList()){
				System.out.println(edge.firstNode + " " + edge.secondNode);
			}
		}
    }
}
