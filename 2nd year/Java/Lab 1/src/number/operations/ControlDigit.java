package number.operations;

public class ControlDigit {
    int n;
    public ControlDigit(int x){
        n=x;
    }
    public int solve(){
        n=n%9;
        if (n==0) n=9;
        /*while (n>9){
            int sc=0;
            while (n!=0){
                sc+=n%10;
                n/=10;
            }
            n=sc;
        }*/
        return n;
    }
}
