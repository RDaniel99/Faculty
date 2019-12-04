package map;

import edges.Edge;
import nodes.Node;
import utils.Pair;

import java.util.*;

public class TravelMap {
    private List<Node> nodeList;
    private List<Edge> edgeList;
    private List<List<Pair<Node,Double>>> adjacencyList;
    private List<Edge> visitedEdges;
    private List<Node> path;
    private boolean foundPath;

    public TravelMap(){
        nodeList = new ArrayList<>();
        edgeList = new ArrayList<>();
        adjacencyList = new ArrayList<>();
        visitedEdges = new ArrayList<>();
        path = new ArrayList<>();
    }

    public void addNode(Node node){
        if (nodeList.contains(node)) return;
        nodeList.add(node);
        List<Pair<Node,Double>> adjList = new ArrayList<>();
        adjacencyList.add(adjList);
    }

    public void addEdge(Node node1,Node node2, double cost){
        addEdge(node1,node2,cost,true);
    }

    public void addEdge(Node node1, Node node2, double cost, boolean bothWays){
        addNode(node1);
        addNode(node2);
        edgeList.add(new Edge(node1,node2,cost));
        List <Pair<Node,Double>> adjList = adjacencyList.get(nodeList.indexOf(node1));
        if (!adjList.contains(new Pair<>(node2,cost))){
            adjList.add(new Pair<>(node2,cost));
        }
        if (bothWays) addEdge(node2,node1,cost,false);
    }

    @Override
    public String toString(){
        String answer="Map:\n\t-Nodes\n";
        for (Node node:nodeList){
            answer+="\t\t+ "+node.getName() + '\n';
        }
        answer+="\t-Edges\n";
        for (Edge edge:edgeList){
            answer+="\t\t+ "+edge.getNode1().getName()+" - "+edge.getNode2().getName()+" with a cost of "+edge.getCost()+"\n";
        }
        return answer;
    }

    public List<Node> getNodes(){
        List<Node> newNodeList = new ArrayList<>();
        newNodeList.addAll(nodeList);
        newNodeList.sort(Comparator.comparing(Node::getName));
        return newNodeList;
    }

    public List <Node> greedyShortestPath(Node node1,Node node2){
        visitedEdges.clear();
        path.clear();
        foundPath=false;
        path.add(node1);
        DFS(node1,node2,new ArrayList<Node>());
        if (foundPath) return path;
        return null;
    }

    private void DFS(Node node1, Node node2, List<Node> currentPath){
        if (node1==node2) {
            path.addAll(currentPath);
            foundPath=true;
            return;
        }
        Pair <Node,Double> availableNeighbourWithMinimumCost = getMinimumCostNeighbour(node1);
        visitedEdges.add(new Edge(node1, availableNeighbourWithMinimumCost.getFirst(), availableNeighbourWithMinimumCost.getSecond()));
        currentPath.add(availableNeighbourWithMinimumCost.getFirst());
        DFS(availableNeighbourWithMinimumCost.getFirst(),node2,currentPath);
        currentPath.remove(currentPath.size()-1);
    }

    private Pair<Node,Double> getMinimumCostNeighbour(Node node){
        List <Pair<Node,Double>> adjList=adjacencyList.get(nodeList.indexOf(node));
        Pair <Node,Double> answer=null;
        for (Pair<Node,Double> pair:adjList){
            Node neighbour = pair.getFirst();
            Double cost = pair.getSecond();
            if (!visitedEdges.contains(new Edge(node,neighbour,cost))){
                if (answer==null){
                    answer=pair;
                }
                else {
                    if (answer.getSecond()>cost){
                        answer=pair;
                    }
                }
            }
        }
        return answer;
    }


}