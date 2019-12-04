package edges;

import nodes.Node;

public class Edge {
    private Node node1,node2;
    private double cost;

    public void setAll(Node node1,Node node2,double cost){
        this.node1=node1;
        this.node2=node2;
        this.cost=cost;
    }

    public Edge(Node node1, Node node2, double cost){
        setAll(node1,node2,cost);
    }

    public Node getNode1(){
        return node1;
    }

    public Node getNode2(){
        return node2;
    }

    public double getCost(){
        return cost;
    }

    @Override
    public boolean equals(Object o){
        if (o==this) return true;
        if (!(o instanceof Edge)) return false;
        Edge edge = (Edge)o;
        if (edge.getNode1()==getNode1() && edge.getNode2()==getNode2()) return true;
        return false;
    }
}
