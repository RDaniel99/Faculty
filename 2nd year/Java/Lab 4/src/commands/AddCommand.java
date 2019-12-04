package commands;

import catalog.Catalog;
import graph.Graph;

import java.io.File;

public class AddCommand extends AbstractCommand {
    private String name;
    private String description;
    private File definitionFile;
    private File imageFile;

    public AddCommand(Catalog catalog, String name, String description, String definitionPath, String imagePath){
        setCatalog(catalog);
        setName(name);
        setDescription(description);
        setDefinitionFile(new File(definitionPath).getAbsoluteFile());
        setImageFile(new File(imagePath).getAbsoluteFile());
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public File getDefinitionFile() {
        return definitionFile;
    }

    public void setDefinitionFile(File definitionFile) {
        this.definitionFile = definitionFile;
    }

    public File getImageFile() {
        return imageFile;
    }

    public void setImageFile(File imageFile) {
        this.imageFile = imageFile;
    }

    @Override
    public void run(){
        catalog.getGraphList().add(new Graph(name,description,definitionFile,imageFile));
    }
}
