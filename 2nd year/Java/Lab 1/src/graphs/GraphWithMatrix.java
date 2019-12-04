package graphs;

import java.util.LinkedList;
import java.util.Queue;

public class GraphWithMatrix implements GraphAlgorithms{
    protected int[][] adjacencyMatrix;
    protected int numberOfNodes;
    protected int numberOfEdges;
    private int[] degrees, inDegrees, outDegrees;
    private String directedOrUndirected;

    public GraphWithMatrix(){}
    public GraphWithMatrix(int nodes, int edges, int[][] adjacency) {
        numberOfNodes = nodes;
        numberOfEdges = edges;
        adjacencyMatrix = adjacency;
        directedOrUndirected();
        degrees=new int[numberOfNodes+2];
        inDegrees=new int[numberOfNodes+2];
        outDegrees=new int[numberOfNodes+2];
        for (int i=1;i<=numberOfNodes;i++){
            degrees[i]=-1;
            inDegrees[i]=-1;
            outDegrees[i]=-1;
        }
    }

    public int getNumberOfNodes(){
        return numberOfNodes;
    }
    public int getNumberOfEdges(){
        return numberOfEdges;
    }
    public int[][] getAdjacencyMatrix(){
        return adjacencyMatrix;
    }

    public void DFS(int node, int[] visited) {
        if (visited[node] == 1) {
            return;
        }
        visited[node] = 1;
        for (int i = 1; i <= numberOfNodes; i++) {
            if (adjacencyMatrix[node][i] == 1) {
                DFS(i, visited);
            }
        }
    }
    public void BFS(int node,int[] visited) {
        Queue<Integer> queue = new LinkedList<Integer>();
        queue.add(node);
        while (queue.isEmpty() == false) {
            int currentNode = queue.peek();
            queue.poll();
            for (int i = 1; i <= numberOfNodes; i++)
                if (adjacencyMatrix[currentNode][i] == 1 && visited[i] == 0) {
                    visited[i] = 1;
                    queue.add(i);
                }
        }
    }
    public boolean connected() {
        int[] visited = new int[numberOfNodes + 2];
        for (int i = 1; i <= numberOfNodes; i++)
            visited[i] = 0;
        BFS(1, visited);
        for (int i = 1; i <= numberOfNodes; i++)
            if (visited[i] == 0)
                return false;
        return true;
    }
    public int numberOfConnectedComponents(){
        int answer=0;
        int[] visited=new int[numberOfNodes+2];
        for (int i=1;i<=numberOfNodes;i++)
            visited[i]=0;
        for (int i=1;i<=numberOfNodes;i++)
            if (visited[i]==0){
                answer++;
                BFS(i,visited);
            }
        return answer;
    }
    public void directedOrUndirected(){
        for (int i=1;i<=numberOfNodes;i++){
            for (int j=1;j<i;j++){
                if (adjacencyMatrix[i][j]!=adjacencyMatrix[j][i]){
                    directedOrUndirected =  "DIRECTED";
                    return;
                }
            }
        }
        directedOrUndirected = "UNDIRECTED";
    }
    public boolean stronglyConnected(){
        if (directedOrUndirected!="DIRECTED"){
            return false;
        }
        //needs more work
        return true;
    }
    public int degree(int node){
        int answer=0;
        if (directedOrUndirected!="UNDIRECTED") return 0;
        if (degrees[node]!=-1) return degrees[node];
        for (int i=1;i<=numberOfNodes;i++){
            answer+=adjacencyMatrix[node][i];
        }
        degrees[node]=answer;
        return degrees[node];
    }
    public int degree(int node,String internalOrExternal) {
        if (directedOrUndirected != "DIRECTED") return 0;
        if (internalOrExternal=="INTERNAL"&&inDegrees[node]!=-1) return inDegrees[node];
        if (internalOrExternal=="EXTERNAL"&&outDegrees[node]!=-1) return outDegrees[node];
        int answer = 0;
        for (int i = 1; i <= numberOfNodes; i++) {
            answer += (internalOrExternal == "INTERNAL") ? adjacencyMatrix[i][node] : adjacencyMatrix[node][i];
        }
        if (internalOrExternal=="INTERNAL") inDegrees[node]=answer;
        else outDegrees[node]=answer;
        return answer;
    }
    public boolean regular(){
        int value=(directedOrUndirected=="UNDIRECTED")?degree(1):degree(1,"INTERNAL");
        for (int i=1;i<=numberOfNodes;i++){
            if (directedOrUndirected=="UNDIRECTED"){
                if (degree(i)!=value) return false;
            }
            else {
                if (degree(i,"INTERNAL")!=value) return false;
                if (degree(i,"EXTERNAL")!=value) return false;
            }
        }
        return true;
    }
    public boolean degreeSum(){
        int degreeSum=0;
        for (int i=1;i<=numberOfNodes;i++){
            degreeSum+=(directedOrUndirected=="UNDIRECTED")?degree(i):degree(i,"INTERNAL")+degree(i,"EXTERNAL");
        }
        return degreeSum==2*numberOfEdges;
    }
    public int minDegree(){
        if (directedOrUndirected=="DIRECTED") return -1;
        int minim=degree(1);
        for (int i=1;i<=numberOfNodes;i++){
            if (minim>degree(i)) minim=degree(i);
        }
        return minim;
    }
    public int maxDegree(){
        if (directedOrUndirected=="DIRECTED") return -1;
        int maxim=degree(1);
        for (int i=1;i<=numberOfNodes;i++){
            if (maxim<degree(i)) maxim=degree(i);
        }
        return maxim;
    }
}
