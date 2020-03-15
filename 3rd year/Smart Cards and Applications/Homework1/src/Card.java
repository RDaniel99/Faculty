import java.io.Serializable;

public class Card implements Serializable {
    public String cardNumber;
    public String clientName;
    public String expireDate;
    public int cvv;

    public Card(String cardNumber, String clientName, String expireDate, int cvv){
        this.cardNumber = cardNumber;
        this.clientName = clientName;
        this.expireDate = expireDate;
        this.cvv = cvv;
    }

}
