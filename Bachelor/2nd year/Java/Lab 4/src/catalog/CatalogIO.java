package catalog;

import exceptions.ExtensionNotSupported;

import java.io.*;

public class CatalogIO {
    Catalog catalog;

    public void checkExtension(){
        String filename = catalog.getPath();
    }

    public void save(String filename){
        ObjectOutputStream outputStream;
        try {
            String fullPath = catalog.getPath() + "\\" + filename;
            FileOutputStream file=new FileOutputStream(fullPath);
            outputStream =new ObjectOutputStream(file);
            outputStream.writeObject(this);
        } catch (IOException ioe) {
            System.out.println(ioe.getMessage() + " - "+ioe.getCause());
        }
    }

    public Catalog load(String filename){
        ObjectInputStream inputStream;
        try {
            String fullPath = catalog.getPath() + "\\" + filename;
            FileInputStream file=new FileInputStream(fullPath);
            inputStream =new ObjectInputStream(file);
            catalog = (Catalog) inputStream.readObject();
        } catch (IOException ioe) {
            System.out.println(ioe.getMessage() + " - "+ioe.getCause());
        }
        catch (ClassNotFoundException classNotFound){
            System.out.println(classNotFound.getMessage() + " - "+classNotFound.getCause());
        }
        return catalog;
    }

    public boolean checkValidExtension(File file) throws ExtensionNotSupported {
        String filename=file.getName();
        if (filename.substring(filename.lastIndexOf('.')+1)!="tgf") {
            System.out.println(filename.substring(filename.lastIndexOf('.')+1));
            throw new ExtensionNotSupported("This extension is not supported");
        }
        return true;
    }

}
