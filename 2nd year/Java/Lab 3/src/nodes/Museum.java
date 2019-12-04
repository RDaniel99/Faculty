package nodes;

import interfaces.Payable;
import interfaces.Visitable;
import utils.OpeningHours;

import java.time.LocalDate;
import java.time.LocalTime;

public class Museum extends Node implements Payable, Visitable {
    private double entryFee;
    private OpeningHours openingHours;

    public Museum(String name){
        setName(name);
    }

    public LocalTime getOpeningHour(){
        return openingHours.getOpeningHour();
    }

    public double getEntryFee(){
        return entryFee;
    }

    public void setEntryFee(double newEntryFee){
        entryFee=newEntryFee;
    }

    public OpeningHours getOpeningHours(){
        return openingHours;
    }

    public void setOpeningHours(OpeningHours openingHours){
        this.openingHours=openingHours;
    }

    @Override
    public boolean equals(Object o){
        if (o==this) return true;
        if (!(o instanceof Museum)) return false;
        Museum museum = (Museum)o;
        if (museum.getName()==getName()) return true;
        return false;
    }
}
