package classes;

public class Board {
    private final Graph complete;
    private int numberOfNodes;
    public Board(int numberOfNodes) {
        complete=new Graph(numberOfNodes);
        this.setNumberOfNodes(numberOfNodes);
    }
    public synchronized Edge extract() {
        Edge edge = getComplete().pollFirst();
        return edge;
    }
    public boolean isEmpty() {
        if (getComplete().getEdgeList().isEmpty()) return true;
        return false;
    }

    public Graph getComplete() {
        return complete;
    }

    public int getNumberOfNodes() {
        return numberOfNodes;
    }

    public void setNumberOfNodes(int numberOfNodes) {
        this.numberOfNodes = numberOfNodes;
    }
}
