import com.google.gson.Gson;

import javax.tools.Tool;
import java.io.*;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.security.*;
import java.util.UUID;

public class Merchant {
    RSA rsa;
    PublicKey publicKey;
    private PrivateKey privateKey;

    public Merchant() {
        try {
            rsa = new RSA();
            this.publicKey = rsa.publicKey;
            this.saveMerchantPublicKey(this.publicKey);
            this.privateKey = rsa.getPrivateKey();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void saveMerchantPublicKey(PublicKey publicKey) throws IOException {
        try {
            FileOutputStream fileOut = new FileOutputStream("D:\\ADRIAN\\ady_info\\Homework1\\merchantPublicKey.txt");
            ObjectOutputStream objectOut = new ObjectOutputStream(fileOut);
            objectOut.writeObject(publicKey);
            objectOut.close();

        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public static void main(String args[]) {
        int merchantPort = 4001;
        int bankPort = 4000;
        Merchant merchant = new Merchant();
        ServerSocket serverSocket = null;

        try {

            serverSocket = new ServerSocket(merchantPort);
            Socket socket = serverSocket.accept();

            //get the data
            ObjectInputStream in = new ObjectInputStream(socket.getInputStream());
            String json1 = (String) in.readObject();
            SetupTransferObject dataFromClient = (new Gson().fromJson(json1, SetupTransferObject.class));

            //decrypt data
            String symmetricKey = merchant.rsa.decrypt(merchant.rsa.getPrivateKey(), dataFromClient.SK);
            PublicKey clientPublicKey = AES.decrypt(dataFromClient.PK, symmetricKey);

            //generate id and sign it
            String uniqueID = UUID.randomUUID().toString();
            byte[] signedUniqueID = LocalSignature.sign(merchant.privateKey, uniqueID.getBytes());

            //put them into an object
            SetupTransferObject objectForClient = new SetupTransferObject(uniqueID, signedUniqueID);
            String json2 = (new Gson().toJson(objectForClient));

            String encryptedJson = AES.encrypt(json2,symmetricKey);
            //encrypt with RSA client's public key and send it to the client
            ObjectOutputStream out = new ObjectOutputStream(socket.getOutputStream());
            out.writeObject(encryptedJson);
            out.flush();


            //get payment date
            String paymentDataJson = (String) in.readObject();
            BiggerTransferObject paymentData = (new Gson().fromJson(paymentDataJson, BiggerTransferObject.class));
            ExchangeTransferObject PO = paymentData.PO;

            //verify signature
            boolean boolPaymentDetails = LocalSignature.verify(clientPublicKey, PO.paymentDetails, PO.signedPaymentDetails);


            if (boolPaymentDetails) {
                System.out.println("All good! Im going to tell the bank to prepare the payment!");
                ExchangeTransferObject paymentDetails = (ExchangeTransferObject)Tools.BytesToObject(PO.paymentDetails);
                if(paymentDetails.ammount.equals("100")) {

                    //prepare details to be sent to the bank
                    ExchangeTransferObject paymentForBank = new ExchangeTransferObject(paymentDetails.paymentId, paymentDetails.ammount, clientPublicKey);

                    //sign them
                    byte[] signedPaymentForBank = LocalSignature.sign(merchant.privateKey, Tools.ObjectToBytes(paymentForBank));

                    BiggerTransferObject dataForBank = new BiggerTransferObject(paymentData.PM, signedPaymentForBank);
                    String dataForBankJson = (new Gson().toJson(dataForBank));

                    //connect to the bank
                    InetAddress serverHost = InetAddress.getByName("localhost");
                    Socket bankSocket = new Socket(serverHost, bankPort);

                    ObjectOutputStream bankOut = new ObjectOutputStream(bankSocket.getOutputStream());
                    bankOut.writeObject(dataForBankJson);
                    bankOut.flush();

//                    ObjectInputStream bankIn = new ObjectInputStream(bankSocket.getInputStream());
//                    byte[] succes = (byte[]) in.readObject();
//                    System.out.println(Tools.BytesToObject(succes));



                }
                else {
                    System.out.println("NotEnoughMoneyPaid");
                }
            }
            else {
                System.out.println("OOPS");
            }

            serverSocket.close();
        }  catch (Exception e)
        {
            e.printStackTrace();
        }
        finally
        {
            try
            {
                serverSocket.close();
            }
            catch(Exception e){}
        }
    }
}
