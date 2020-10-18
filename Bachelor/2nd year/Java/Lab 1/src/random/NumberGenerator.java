package random;

public class NumberGenerator {
    int numberGenerated;
    public int gen(){
        numberGenerated = (int) (Math.random() * 1_000_000);
        return numberGenerated;
    }
}
