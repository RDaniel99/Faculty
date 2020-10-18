package classes;

import java.util.*;

public class Graph {
    private LinkedList<Edge> edgeList;
    private int numberOfNodes;
    public Graph(int numberOfNodes){
        setEdgeList(new LinkedList<>());
        this.setNumberOfNodes(numberOfNodes);
        for (int i=1;i<=numberOfNodes;i++){
            for (int j=i+1;j<=numberOfNodes;j++){
                getEdgeList().add(new Edge(numberOfNodes,i,j));
            }
        }
        Collections.shuffle(getEdgeList());
    }
    public Edge pollFirst(){
        Edge edge = getEdgeList().pollFirst();
        return edge;
    }
    public void add(Edge edge){
        edgeList.add(edge);
    }
    Boolean hasCycle(int node, Boolean visited[], int parent) {
        visited[node] = true;
        Integer neighbour=0;
        for (Edge edge: this.getEdgeList())
        {
            if (edge.firstNode==node) neighbour=edge.secondNode;
            else
                if (edge.secondNode==node) neighbour=edge.firstNode;
            if (!visited[neighbour]) {
                if (hasCycle(neighbour, visited, node))
                    return true;
            }
            else if (neighbour != parent)
                    return true;
        }
        return false;
    }
    Boolean isSpanningTree() {
        Boolean visited[] = new Boolean[this.getNumberOfNodes()];
        for (int i = 0; i < this.getNumberOfNodes(); i++)
            visited[i] = false;
        if (hasCycle(0, visited, -1))
            return false;
        for (int u = 0; u < this.getNumberOfNodes(); u++)
            if (!visited[u])
                return false;
        return true;
    }

    public LinkedList<Edge> getEdgeList() {
        return edgeList;
    }

    public void setEdgeList(LinkedList<Edge> edgeList) {
        this.edgeList = edgeList;
    }

    public int getNumberOfNodes() {
        return numberOfNodes;
    }

    public void setNumberOfNodes(int numberOfNodes) {
        this.numberOfNodes = numberOfNodes;
    }
}
