package random.adjacency.matrix;
import graphs.GraphWithMatrix;
import java.util.Random;

public class RandomGraphGenerator extends GraphGenerator{
    public RandomGraphGenerator(int nodes){
        Random generator=new Random();
        int edges=generator.nextInt(nodes*(nodes-1)/2);
        int[][] adjacency=new int[nodes+2][nodes+2];
        for (int i=1;i<=edges;i++){
            int edge1=generator.nextInt(nodes)+1;
            int edge2=generator.nextInt(nodes)+1;
            while (adjacency[edge1][edge2]==1 || edge1==edge2){
                edge1=generator.nextInt(nodes)+1;
                edge2=generator.nextInt(nodes)+1;
            }
            adjacency[edge1][edge2]=adjacency[edge2][edge1]=1;
        }
        graph=new GraphWithMatrix(nodes,edges,adjacency);
    }
}
