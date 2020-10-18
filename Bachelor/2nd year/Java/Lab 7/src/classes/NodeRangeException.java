package classes;

public class NodeRangeException extends Exception {
    NodeRangeException(int numberOfNodes){
        super("Node is either less than 1 or more than " + numberOfNodes);
    }
}
