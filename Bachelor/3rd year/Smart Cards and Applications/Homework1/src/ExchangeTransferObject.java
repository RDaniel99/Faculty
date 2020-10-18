import javax.crypto.SealedObject;
import java.io.Serializable;
import java.security.PublicKey;

public class ExchangeTransferObject implements Serializable {
    public PublicKey publicKey;
    public Card card;
    public String ammount;
    public String paymentId;
    public String orderDesc;
    public byte[] paymentDetails;
    public byte[] signedPaymentDetails;

    public ExchangeTransferObject (){

    }
    public ExchangeTransferObject( Card card, String paymentId, String ammount, PublicKey publicKey) {
        this.card = card;
        this.paymentId = paymentId;
        this.ammount = ammount;
        this.publicKey = publicKey;
    }

    public ExchangeTransferObject(String paymentId, String ammount, PublicKey publicKey) {
        this.card = card;
        this.paymentId = paymentId;
        this.ammount = ammount;
        this.publicKey = publicKey;
    }


    public ExchangeTransferObject( byte[] paymentDetails, byte[] signedPaymentDetails){
        this.paymentDetails = paymentDetails;
        this.signedPaymentDetails = signedPaymentDetails;
    }

    public ExchangeTransferObject( String orderDesc, String paymentId, String ammount ){
        this.orderDesc= orderDesc;
        this.ammount = ammount;
        this.paymentId = paymentId;
    }

}

