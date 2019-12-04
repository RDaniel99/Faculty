package number.operations;

public class OperationsOn3 {
    int n;
    public OperationsOn3(int x){
        n=x;
    }
    public int solve(){
        n*=3;
        n+=Integer.parseInt("10101",2);
        n+=Integer.parseInt("FF",16);
        n*=6;
        return n;
    }
}
