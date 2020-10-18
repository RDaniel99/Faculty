package random.adjacency.matrix;
import graphs.GraphAlgorithms;
import graphs.GraphWithMatrix;
import java.util.Random;

public class TreeGenerator extends GraphWithMatrix {
    public TreeGenerator(int nodes) {
        numberOfNodes=nodes;
        numberOfEdges=nodes-1;
        adjacencyMatrix=new int[nodes+1][nodes+1];
        Random gen=new Random();
        int node1=gen.nextInt(nodes)+1;
        int node2=gen.nextInt(nodes)+1;
        while (node1==node2){
            node1=gen.nextInt(nodes)+1;
            node2=gen.nextInt(nodes)+1;
        }
        adjacencyMatrix[node1][node2]=1;
        for (int i=2;i<nodes;i++){
            node1=gen.nextInt(nodes)+1;
            while (degree(node1)==0){
                node1=gen.nextInt(nodes)+1;
            }
            node2=gen.nextInt(nodes)+1;
            while (degree(node2)!=0){
                node2=gen.nextInt(nodes)+1;
            }
            adjacencyMatrix[node1][node2]=1;
        }
    }

}
