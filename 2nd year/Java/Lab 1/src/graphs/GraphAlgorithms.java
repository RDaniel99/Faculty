package graphs;

public interface GraphAlgorithms {
    void DFS(int node,int[] visited);
    void BFS(int node,int[] visited);
    boolean connected();
    int numberOfConnectedComponents();
    void directedOrUndirected();
    boolean stronglyConnected();
    int degree(int node);
    int degree(int node,String internalOrExternal);
    boolean regular();
    boolean degreeSum();
    int maxDegree();
    int minDegree();
}
