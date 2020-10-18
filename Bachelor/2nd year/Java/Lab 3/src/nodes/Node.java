package nodes;
import utils.Pair;

public abstract class Node {
    private String name;
    private double longitude;
    private double latitude;

    public void setName(String name){
        this.name=name;
    }

    public String getName(){
        return name;
    }

    public void setCoordinates(double latitude, double longitude){
        this.latitude=latitude;
        this.longitude=longitude;
    }

    public Pair <Double, Double> getCoordinates(){
        return new Pair <>(latitude,longitude);
    }

    @Override
    public String toString(){
        return getName();
    }
}
