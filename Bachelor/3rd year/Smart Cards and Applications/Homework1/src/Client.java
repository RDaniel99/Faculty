import com.fasterxml.jackson.core.util.RequestPayload;
import com.google.gson.*;
import org.json.JSONObject;

import javax.crypto.SealedObject;
import java.io.*;
import java.lang.reflect.Type;
import java.net.InetAddress;
import java.net.Socket;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.Signature;


public class Client {
    public Card card;
    public RSA rsa;
    public PublicKey publicKey;
    private PrivateKey privateKey;
    public AES aes;

    public Client(String cardNumber, String name, String expireDate, int cvv ){
        this.card = new Card(cardNumber,name,expireDate,cvv);
        try {
            rsa = new RSA();
            this.publicKey = rsa.publicKey;
            this.privateKey = rsa.getPrivateKey();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) throws IOException{
        Socket socket = null;
        int merchantPort = 4001;
        try {
            InetAddress serverHost = InetAddress.getByName("localhost");
            socket = new Socket(serverHost, merchantPort);

            //initiliazing client
            Client client = new Client("12345","Adrian","21/12",123);

            //get the merchant's public key
            FileInputStream fileIn1 = new FileInputStream("D:\\ADRIAN\\ady_info\\Homework1\\merchantPublicKey.txt");
            ObjectInputStream objectOut1 = new ObjectInputStream(fileIn1);
            PublicKey merchantPublicKey = (PublicKey)objectOut1.readObject();
            objectOut1.close();

            //get the bank's public key
            FileInputStream fileIn2 = new FileInputStream("D:\\ADRIAN\\ady_info\\Homework1\\bankPublicKey.txt");
            ObjectInputStream objectOut2 = new ObjectInputStream(fileIn2);
            PublicKey bankPublicKey = (PublicKey)objectOut2.readObject();
            objectOut2.close();

            //encrypt client's public key with AES
            client.aes = new AES();
            SealedObject encryptedPublicKey = AES.encrypt(client.publicKey, client.aes.getKey());

            //encryp AES key with RSA (merchant's public key)
            String symmetricKey = client.aes.getKey();
            String encryptedSymmetricKey = client.rsa.encrypt(merchantPublicKey, symmetricKey);

            //prepare them for transfer
            SetupTransferObject keysObject= new SetupTransferObject(encryptedPublicKey, encryptedSymmetricKey);
            String keysJson = (new Gson().toJson(keysObject));

//            send them to the merchant
            ObjectOutputStream out = new ObjectOutputStream(socket.getOutputStream());
            out.writeObject(keysJson);
            out.flush();

            ObjectInputStream in = new ObjectInputStream(socket.getInputStream());
            String encryptedIdJson = (String) in.readObject();
            String idJson = AES.decrypt(encryptedIdJson, client.aes.getKey());
            SetupTransferObject paymentId = (new Gson().fromJson(idJson, SetupTransferObject.class));


            //verify signature
            boolean bool = LocalSignature.verify(merchantPublicKey, paymentId.id.getBytes(), paymentId.signedId);

            //if bool is true, all is clear. we can send card details
            if (bool){
                System.out.println("Id received and checked! Im sending payment data");
                //create PI object
                ExchangeTransferObject paymentDetailsBankObject= new ExchangeTransferObject(client.card, paymentId.id, "100", client.publicKey);
                byte[] paymentDetailsBank = Tools.ObjectToBytes(paymentDetailsBankObject);

                //sign PI object
                byte[] signedPaymentDetailsBank = LocalSignature.sign(client.privateKey, paymentDetailsBank);

                //create PM object
                ExchangeTransferObject PM = new ExchangeTransferObject(paymentDetailsBank, signedPaymentDetailsBank);
                //encrypting  PM doesnt work. data too large
//                String encryptedPM = client.rsa.encrypt(bankPublicKey, Tools.ObjectToBytes(PM));
//                System.out.println(encryptedPM);


                //Create PO object
                String orderDesc = "";
                ExchangeTransferObject paymentDetailsMerchantObject = new ExchangeTransferObject(orderDesc, paymentId.id, "100" );

//                String paymentDetailsMerchant = (new Gson().toJson(paymentDetailsMerchantObject));
                byte[] paymentDetailsMerchant = Tools.ObjectToBytes(paymentDetailsMerchantObject);

                //sign
                byte[] signedPaymentDetailsMerchant = LocalSignature.sign(client.privateKey, paymentDetailsMerchant);
                ExchangeTransferObject PO = new ExchangeTransferObject(paymentDetailsMerchant, signedPaymentDetailsMerchant);

                //create object that must be sent
                BiggerTransferObject paymentData = new BiggerTransferObject(PM,PO);
                String paymentDataJson = (new Gson().toJson(paymentData));

                //data is too big
//                String encryptedPaymentDataJson = client.rsa.encrypt(bankPublicKey, paymentDataJson);
//                System.out.println(encryptedPaymentDataJson);

                //sent id
                out.writeObject(paymentDataJson);
                out.flush();







            }
            else {
                System.out.println("Jeenkies. You got intercepted! This is not legit");
            }

        }
        catch (Exception exception)
        {
            exception.printStackTrace();
        }
        finally
        {
            //Closing the socket
            try
            {
                socket.close();
            }
            catch(Exception e)
            {
                e.printStackTrace();
            }
        }

    }
}
