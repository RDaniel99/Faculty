package commands;

import catalog.Catalog;

public abstract class AbstractCommand {
    abstract void run();
    Catalog catalog;

    public AbstractCommand(){
        catalog = new Catalog();
    }

    public void setCatalog(Catalog catalog){
        this.catalog=catalog;
    }

    public Catalog getCatalog(){
        return this.catalog;
    }
}
