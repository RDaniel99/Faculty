package utils;

public class Pair <T1,T2> {
    private T1 first;
    private T2 second;
    public Pair(T1 first, T2 second){
        setPair(first,second);
    }

    public void setPair(T1 first, T2 second){
        this.first=first;
        this.second=second;
    }

    public T1 getFirst(){
        return first;
    }

    public T2 getSecond(){
        return second;
    }

    @Override
    public boolean equals(Object o){
        if (o==this) return true;
        if (!(o instanceof Pair)) return false;
        Pair<T1,T2> pair = (Pair<T1,T2>)o;
        if (pair.getFirst()==getFirst() && pair.getSecond()==getSecond()) return true;
        return false;
    }
}
