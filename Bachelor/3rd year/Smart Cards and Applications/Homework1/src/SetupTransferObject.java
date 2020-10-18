import javax.crypto.SealedObject;
import java.io.Serializable;
import java.security.PublicKey;

public class SetupTransferObject implements Serializable {
    public String SK;
    public SealedObject PK;
    public String id;
    public byte[] signedId;

    public SetupTransferObject(){

    }

    public SetupTransferObject(SealedObject object, String key){
        this.SK= key;
        this.PK= object;
    }

    public SetupTransferObject(String id, byte[] signedId){
        this.id = id;
        this.signedId = signedId;
    }
}

