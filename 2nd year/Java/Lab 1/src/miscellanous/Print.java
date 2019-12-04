
package miscellanous;

import graphs.GraphWithMatrix;

public class Print {
    public void print1() {
        System.out.println("Hello World!");
    }
    public void print5(int index){
        Languages lan=new Languages();
        System.out.println("Willy-nilly, this semester I will learn " + lan.get()[index]);
    }
    public void printEdges(GraphWithMatrix graph){
        System.out.println("Number of edges = " + graph.getNumberOfEdges());
    }
    public void printMinMaxDegree(GraphWithMatrix graph){
        System.out.println((char)0x0394 + "(G) = "+graph.maxDegree());
        System.out.println((char)0x03B4 + "(G) = "+graph.minDegree());
    }
    public void printAdjacencyMatrix(GraphWithMatrix graph){
        String aboveLine=new String(), currentLine=new String(), belowLine=new String();
        for (int i=1;i<=graph.getNumberOfNodes();i++){
            aboveLine="";
            belowLine="";
            currentLine="";
            for (int j=1;j<=graph.getNumberOfNodes();j++){
                aboveLine+=(char)0x250F;
                aboveLine+=(char)0x2501;
                aboveLine+=(char)0x2513;
                currentLine+=(char)0x2503;
                currentLine+=String.valueOf(graph.getAdjacencyMatrix()[i][j]);
                currentLine+=(char)0x2503;
                belowLine+=(char)0x2517;
                belowLine+=(char)0x2501;
                belowLine+=(char)0x251B;
            }
            System.out.println(aboveLine);
            System.out.println(currentLine);
            System.out.println(belowLine);
        }
    }
}
