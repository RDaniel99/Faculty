package commands;

import catalog.Catalog;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;

public class LoadCommand extends AbstractCommand {
    private String filename;

    public LoadCommand(Catalog catalog, String filename){
        setCatalog(catalog);
        setFilename(filename);
    }

    @Override
    public void run(){
        ObjectInputStream inputStream;
        try {
            String fullPath = catalog.getPath() + "\\" + filename;
            FileInputStream file=new FileInputStream(fullPath);
            inputStream =new ObjectInputStream(file);
            setCatalog((Catalog) inputStream.readObject());
        } catch (IOException ioe) {
            System.out.println(ioe.getMessage() + " - "+ioe.getCause());
        }
        catch (ClassNotFoundException classNotFound){
            System.out.println(classNotFound.getMessage() + " - "+classNotFound.getCause());
        }
    }

    public String getFilename() {
        return filename;
    }

    public void setFilename(String filename) {
        this.filename = filename;
    }
}
