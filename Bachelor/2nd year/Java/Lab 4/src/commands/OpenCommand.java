package commands;

import catalog.Catalog;
import graph.Graph;

import java.awt.*;
import java.io.File;
import java.io.IOException;

public class OpenCommand extends AbstractCommand{
    private String nameOfGraph;

    public OpenCommand(Catalog catalog, String nameOfGraph){
        setCatalog(catalog);
        setNameOfGraph(nameOfGraph);
    }

    @Override
    public void run(){
        Desktop desktop = Desktop.getDesktop();
        File file=null;
        for (Graph graph: catalog.getGraphList()){
            System.out.println(graph.getName());
            if (graph.getName().equals(nameOfGraph)){
                file=new File(catalog.getPath()+"\\"+graph.getDefinitionFile().getName());
            }
        }
        try {
            desktop.open(file);
        }
        catch(IOException ioe){
            System.out.println(ioe.getMessage() + " - " + ioe.getCause());
        }
    }

    public String getNameOfGraph() {
        return nameOfGraph;
    }

    public void setNameOfGraph(String nameOfGraph) {
        this.nameOfGraph = nameOfGraph;
    }
}
