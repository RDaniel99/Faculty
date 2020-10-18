package random.adjacency.matrix;
import graphs.GraphWithMatrix;

public class CompleteGraphGenerator extends GraphGenerator {
    public CompleteGraphGenerator(int nodes){
        int edges = nodes*(nodes-1)/2;
        int[][] adjacency=new int[nodes+2][nodes+2];
        for (int i=1;i<=nodes;i++){
            for (int j=1;j<i;j++){
                adjacency[i][j]=adjacency[j][i]=1;
            }
        }
        graph=new GraphWithMatrix(nodes, edges, adjacency);
    }
}
