import catalog.Catalog;
import commands.*;
import exceptions.ExtensionNotSupported;
import graph.Graph;

import java.awt.*;
import java.io.File;
import java.security.cert.Extension;
import java.sql.SQLOutput;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
<<<<<<< HEAD
        String command=new String();
        Catalog catalog=new Catalog("d:/ADRIAN/ady_info/anul 2/Java/Lab4/src");
        do {
            Scanner reader=new Scanner(System.in);
            System.out.println("Comenzi disponibile: add, list, load, open, save. Introduceti numele unei comenzi si urmati instructiunile specifice.");
            command = reader.nextLine();
            if (command.equals("add")){
                System.out.println("Introduceti numele grafului pe care doriti sa il adaugati la catalog: ");
                String name = reader.nextLine();
                System.out.println("Introduceti descrierea grafului:");
                String description =reader.nextLine();
                System.out.println("Introduceti calea RELATIVA LA " + catalog.getPath() + "a fisierului tgf care contine descrierea grafului: ");
                String definitionFilename = reader.nextLine();
                System.out.println("Introduceti calea RELATIVA LA " + catalog.getPath() + "a fisierului JPEG, PNG, PDF sau SVG care contine reprezentarea grafica a grafului, daca este cazul. Pentru a trece mai departe apasati enter.");
                String imageFilename = reader.nextLine();
                AddCommand add = new AddCommand(catalog,name,description,definitionFilename,imageFilename);
                add.run();
                catalog=add.getCatalog();
                continue;
            }
            if (command.equals("list")){
                ListCommand list=new ListCommand(catalog);
                list.run();
                continue;
            }
            if (command.equals("load")){
                System.out.println("Introduceti calea RELATIVA LA" + catalog.getPath() + "a fisierului din care doriti sa incarcati catalogul: ");
                String path = reader.nextLine();
                LoadCommand load=new LoadCommand(catalog,path);
                load.run();
                continue;
            }
            if (command.equals("open")){
                System.out.println("Introduceti numele grafului pe care doriti sa il deschideti din catalogul curent: ");
                String name = reader.nextLine();
                OpenCommand open=new OpenCommand(catalog,name);
                open.run();
                continue;
            }
            if (command.equals("save")){
                System.out.println("Introduceti calea RELATIVA LA " + catalog.getPath() + " a fisierului in care doriti sa salvati catalogul: ");
                String path = reader.nextLine();
                SaveCommand save = new SaveCommand(catalog, path);
                save.run();
                continue;
            }
            if (command.equals("exit")){
                break;
            }
        } while (true);
=======
        Catalog catalog = new Catalog("D:\\ADRIAN\\ady_info\\anul 2\\Java\\Lab4\\src");
        catalog.add(new Graph("K4",new File("k4.tgf"),new File("K4.pdf")));
        //catalog.add(new Graph("Petersen",new File("petersen.tgf"), new File("Petersen.pdf"))) ;
        catalog.open("K4");
        catalog.save("catalog.dat");
        catalog.load("catalog.dat");
        catalog.list();
>>>>>>> parent of c60bac7... removed commands in classes
    }
}
