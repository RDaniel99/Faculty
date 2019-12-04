package commands;

import catalog.Catalog;
import graph.Graph;

public class ListCommand extends AbstractCommand {

    public ListCommand(Catalog catalog){
        setCatalog(catalog);
    }

    @Override
    public void run(){
        for (Graph graph:catalog.getGraphList()){
            System.out.println(graph);
        }
    }
}
