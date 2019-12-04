package graph;

import catalog.CatalogIO;
import exceptions.ExtensionNotSupported;

import java.io.File;
import java.io.Serializable;

public class Graph extends CatalogIO implements Serializable {
    private String name;
    String description;
    private File definitionFile;
    private File drawFile;

    public Graph(String name,String description,File definitionFile, File drawFile){
        this.setName(name);
        this.setDescription(description);
        this.setDefinitionFile(definitionFile);
        this.setDrawFile(drawFile);
    }

<<<<<<< HEAD
    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
=======
>>>>>>> parent of c60bac7... removed commands in classes

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public File getDefinitionFile() {
        return definitionFile;
    }

<<<<<<< HEAD
    private void setDefinitionFile(File definitionFile) {
=======
    public void setDescriptionFile(File descriptionFile) {
>>>>>>> parent of c60bac7... removed commands in classes
        try {
            checkValidExtension(definitionFile);
            this.definitionFile = definitionFile;
        }
        catch (ExtensionNotSupported ens){
            System.out.println(ens.getMessage());
        }
    }

    public File getDrawFile() {
        return drawFile;
    }

    public void setDrawFile(File drawFile) {
        this.drawFile = drawFile;
    }

    @Override
    public String toString(){
        String answer="";
        answer+=name+" "+description+" "+definitionFile.getAbsolutePath()+" "+drawFile.getAbsolutePath()+"\n";
        return answer;
    }
}
