package commands;

import catalog.Catalog;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;

public class SaveCommand extends AbstractCommand {
    private String filename;

    public SaveCommand(Catalog catalog, String filename){
        setCatalog(catalog);
        setFilename(filename);
    }

    @Override
    public void run(){
        ObjectOutputStream outputStream;
        try {
            String fullPath = catalog.getPath() + "\\" + filename;
            FileOutputStream file=new FileOutputStream(fullPath);
            outputStream =new ObjectOutputStream(file);
            outputStream.writeObject(catalog);
        } catch (IOException ioe) {
            System.out.println(ioe.getMessage() + " - "+ioe.getCause());
        }
    }

    public String getFilename() {
        return filename;
    }

    public void setFilename(String filename) {
        this.filename = filename;
    }
}
