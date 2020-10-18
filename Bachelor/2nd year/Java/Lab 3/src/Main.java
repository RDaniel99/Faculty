import interfaces.Payable;
import interfaces.Visitable;
import map.*;
import nodes.*;
import utils.OpeningHours;

import java.time.LocalTime;
import java.util.*;
import java.util.stream.Stream;

public class Main {

    public static void main(String[] args) {
        TravelMap map = new TravelMap();
        Hotel v1=new Hotel("Hotel");
        Museum v2=new Museum("Museum A");
        Museum v3=new Museum("Museum B");
        Church v4=new Church("Church A");
        Church v5=new Church("Church B");
        Restaurant v6=new Restaurant("Restaurant");

        v1.setRank(2);
        v2.setEntryFee(50.6);
        v2.setOpeningHours(new OpeningHours(LocalTime.of(9,0),LocalTime.of(20,0)));
        v3.setOpeningHours();
        v3.setEntryFee(50.7);
        v4.setEntryFee(50);
        v5.setEntryFee(45);
        v6.setOpeningHours(new OpeningHours(LocalTime.of(10,0),LocalTime.of(21,0)));
        v6.setRank(1);

        map.addNode(v1);
        map.addNode(v2);
        map.addNode(v3);
        map.addNode(v4);
        map.addNode(v5);
        map.addNode(v6);

        map.addEdge(v1,v2,new Double(15));
        map.addEdge(v1,v3,new Double(10));
        map.addEdge(v3,v2,new Double(4),false);
        map.addEdge(v3,v4,new Double(2));
        map.addEdge(v4,v5,new Double(1));
        map.addEdge(v5,v6,new Double(1));
        map.addEdge(v2,v6,new Double(10));
        map.addEdge(v4,v2,new Double(3),false);
        System.out.println(map);
        System.out.println("The map is: \n" + map.getNodes());
        Stream streamForPayable = map.getNodes().stream().filter(node->node instanceof Payable);
        double average = streamForPayable.mapToDouble(node->((Payable)node).getEntryFee()).average().getAsDouble();
        System.out.println(average);
        Stream streamForVisitable = map.getNodes().stream().filter(node->((node instanceof Visitable) && !(node instanceof Payable)));
        streamForVisitable.sorted(Comparator.comparing(node->((Visitable)node).getOpeningHour())).forEach(node-> System.out.println(((Node)node).getName()));
        System.out.println(map.greedyShortestPath(v1,v2));
    }
}
