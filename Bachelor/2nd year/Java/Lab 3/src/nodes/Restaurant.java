package nodes;

import interfaces.Classifiable;
import interfaces.Visitable;
import utils.OpeningHours;

import java.time.LocalTime;
import java.util.ArrayList;

public class Restaurant extends Node implements Visitable, Classifiable {
    private OpeningHours openingHours;
    private int rank;

    public Restaurant(String name){
        setName(name);
    }

    public LocalTime getOpeningHour(){
        return openingHours.getOpeningHour();
    }

    public void setRank(int rank){
        this.rank=rank;
    }

    public void setOpeningHours(OpeningHours openingHours){
        this.openingHours=openingHours;
    }

    public int getRank(){
        return rank;
    }

    public OpeningHours getOpeningHours(){
        return openingHours;
    }

    @Override
    public boolean equals(Object o){
        if (o==this) return true;
        if (!(o instanceof Restaurant)) return false;
        Restaurant restaurant = (Restaurant)o;
        if (restaurant.getRank()==getRank() && restaurant.getName()==getName()) return true;
        return false;
    }

}
