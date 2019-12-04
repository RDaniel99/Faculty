package classes;

public class Player implements Runnable {
    private String name;
    public final int THINKING_TIME=150;
    private Game game;
    private Graph graph;
    private Board board;

    public Player(String name, Board board){
        this.setName(name);
        this.board=board;
        setGraph(new Graph(getBoard().getNumberOfNodes()));
    }

    private boolean play() throws InterruptedException {
        Board board = getGame().getBoard();
        if (board.isEmpty()) { return false; }
        getGraph().add( board.extract() );
        Thread.sleep(THINKING_TIME); //declare this constant
        if (getGraph().isSpanningTree()) {
            getGame().setWinner(this);
        }
        return true;
    }

    public void setGame(Game game){
        this.game=game;
    }

    public void run(){
        try {
            play();
        }
        catch (InterruptedException exception){
            System.out.println(exception.getStackTrace());
        }
    }

    public Board getBoard() {
        return board;
    }

    public void setBoard(Board board) {
        this.board = board;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Game getGame() {
        return game;
    }

    public Graph getGraph() {
        return graph;
    }

    public void setGraph(Graph graph) {
        this.graph = graph;
    }
    // implement the run() method, that will repeatedly extract edges
    // implement the toString() method
}
