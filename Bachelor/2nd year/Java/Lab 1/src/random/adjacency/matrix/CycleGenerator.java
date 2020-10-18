package random.adjacency.matrix;
import graphs.GraphWithMatrix;
import java.util.Random;

public class CycleGenerator extends GraphGenerator {
    public CycleGenerator(int nodes){
        int edges=nodes;
        int[][] adjacency=new int[nodes+2][nodes+2];
        Random generator=new Random();
        int previous=generator.nextInt(nodes)+1;
        int first=previous;
        for (int i=1;i<nodes;i++){
            int next = generator.nextInt(nodes)+1;
            while (adjacency[previous][next]==1 || previous==next){
                next=generator.nextInt(nodes)+1;
            }
            adjacency[previous][next]=adjacency[next][previous]=1;
            previous=next;
        }
        adjacency[first][previous]=adjacency[previous][first]=1;
        graph=new GraphWithMatrix(nodes,edges,adjacency);
    }
}
