import java.security.*;

public class LocalSignature {


    public static byte[] sign(PrivateKey privateKey, byte[] value) throws NoSuchAlgorithmException, InvalidKeyException, SignatureException {
        Signature sign = Signature.getInstance("SHA256withRSA");
        sign.initSign(privateKey);
        sign.update(value);
        return sign.sign();
    }

    public static boolean verify(PublicKey publicKey,byte[] value , byte[] signedValue) throws NoSuchAlgorithmException, InvalidKeyException, SignatureException {
        Signature sign = Signature.getInstance("SHA256withRSA");
        sign.initVerify(publicKey);
        sign.update(value);
        return sign.verify(signedValue);
    }
}
