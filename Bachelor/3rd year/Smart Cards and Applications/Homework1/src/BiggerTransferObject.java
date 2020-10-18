import javax.crypto.SealedObject;

public class BiggerTransferObject {
   public ExchangeTransferObject PM;
   public ExchangeTransferObject PO;
   public byte[] signature;


    public BiggerTransferObject(ExchangeTransferObject o1, ExchangeTransferObject o2){
        this.PM = o1;
        this.PO = o2;
    }

    public BiggerTransferObject(ExchangeTransferObject o1, byte[] o2){
        this.PM = o1;
        this.signature = o2;
    }
}
