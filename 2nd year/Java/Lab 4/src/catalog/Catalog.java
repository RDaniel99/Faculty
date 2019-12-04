package catalog;

import graph.Graph;

import java.awt.*;
import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class Catalog extends CatalogIO implements Serializable {
    private List<Graph> graphList;
    private String path;

    public Catalog(){
        path = new String();
        graphList=new ArrayList<>();
    }

    public Catalog(String path){
        setGraphList(new ArrayList<>());
        this.setPath(path);
    }

    public void add(Graph graph){
        getGraphList().add(graph);
    }

    public void list(){
        System.out.println(this.path);
    }

    public void open(String nameOfGraph){
        Desktop desktop = Desktop.getDesktop();
        File file=null;
        for (Graph graph: getGraphList()){
            if (graph.getName()==nameOfGraph){
                file=new File(this.getPath()+"\\"+graph.getDescriptionFile().getName());
            }
        }
        try {
            desktop.open(file);
        }
        catch(IOException ioe){
            System.out.println(ioe.getMessage() + " - " + ioe.getCause());
        }
    }

    public List<Graph> getGraphList() {
        return graphList;
    }

    public void setGraphList(List<Graph> graphList) {
        this.graphList = graphList;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    @Override
    public String toString(){
        String answer="";
        for (Graph graph:getGraphList()){
            answer+=graph.toString();
        }
        answer+=path;
        return answer;
    }
}
