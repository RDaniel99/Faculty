package nodes;

import interfaces.Classifiable;

public class Hotel extends Node implements Classifiable {
    private int rank;

    public Hotel(String name){
        setName(name);
    }

    public int getRank(){
        return rank;
    }

    public void setRank(int newRank){
        rank=newRank;
    }

    /*@Override
    public int hashCode(){
        return getName().hashCode();
    }*/

    @Override
    public boolean equals(Object o){
        if (o==this) return true;
        if (!(o instanceof Hotel)) return false;
        Hotel hotel = (Hotel) o;
        if (hotel.getName().equals(getName())) return true;
        return false;
    }
}
