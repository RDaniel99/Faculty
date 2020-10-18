import miscellanous.*;
import number.operations.ControlDigit;
import number.operations.OddNumberVerify;
import number.operations.OperationsOn3;
import random.*;
import random.adjacency.matrix.CompleteGraphGenerator;
import random.adjacency.matrix.CycleGenerator;
import random.adjacency.matrix.GraphGenerator;
import random.adjacency.matrix.RandomGraphGenerator;
import random.adjacency.matrix.*;


public class Main {

    public static void main(String[] args) {
        final long startTime=System.nanoTime();
        Print printer=new Print();
        printer.print1();
        NumberGenerator ng = new NumberGenerator();
        int n = ng.gen();
        System.out.println(n);
        OperationsOn3 thirdProblem = new OperationsOn3(n);
        int newn = thirdProblem.solve();
        System.out.println(newn);
        ControlDigit cd = new ControlDigit(newn);
        int cifcontrol = cd.solve();
        System.out.println(cifcontrol);
        printer.print5(cifcontrol);
        OddNumberVerify odd = new OddNumberVerify();
        System.out.println(odd.verify(args[0]));
        if (odd.verify(args[0])==true){
            GraphGenerator gg = new GraphGenerator();
            if (args[1].compareTo("COMPLETE")==0){
                gg = new CompleteGraphGenerator(Integer.parseInt(args[0]));
            }
            if (args[1].compareTo("RANDOM")==0){
                gg=new RandomGraphGenerator(Integer.parseInt(args[0]));
            }
            if (args[1].compareTo("CYCLE")==0){
                gg=new CycleGenerator(Integer.parseInt(args[0]));
            }
            printer.printEdges(gg.getGraph());
            printer.printMinMaxDegree(gg.getGraph());
            System.out.println(gg.getGraph().degreeSum());
            System.out.println(gg.getGraph().connected());
            System.out.println(gg.getGraph().numberOfConnectedComponents());
            if (gg.getGraph().getNumberOfNodes()<50) {
                printer.printAdjacencyMatrix(gg.getGraph());
            }
            else {
                final long duration=System.nanoTime()-startTime;
                System.out.println(duration/1000000000);
            }
        }

    }
}
