package nodes;

import interfaces.Payable;

public class Church extends Node implements Payable {
    private double entryFee;

    public Church(String name){
        setName(name);
    }

    public void setEntryFee(double entryFee){
        this.entryFee=entryFee;
    }

    public double getEntryFee(){
        return entryFee;
    }

}
