import com.google.gson.Gson;
import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.security.PrivateKey;
import java.security.PublicKey;

public class Bank {
    RSA rsa;
    PublicKey publicKey;
    private PrivateKey privateKey;
    public BankAccount bankAccount;


    public Bank() {
        try {
            rsa = new RSA();
            this.publicKey = rsa.publicKey;
            this.saveBankPublicKey(this.publicKey);
            this.privateKey = rsa.getPrivateKey();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void saveBankPublicKey(PublicKey publicKey) throws IOException {
        try {
            FileOutputStream fileOut = new FileOutputStream("D:\\ADRIAN\\ady_info\\Homework1\\bankPublicKey.txt");
            ObjectOutputStream objectOut = new ObjectOutputStream(fileOut);
            objectOut.writeObject(publicKey);
            objectOut.close();

        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public static void main(String args[]) {
        int bankPort = 4000;
        int merchantPort = 4001;
        Bank bank = new Bank();
        BankAccount client = new BankAccount("Adrian","21/12",123,"12345","1000");
        ServerSocket serverSocket = null;

        try {

            serverSocket = new ServerSocket(bankPort);
            Socket socket = serverSocket.accept();

            FileInputStream fileIn1 = new FileInputStream("D:\\ADRIAN\\ady_info\\Homework1\\merchantPublicKey.txt");
            ObjectInputStream objectOut1 = new ObjectInputStream(fileIn1);
            PublicKey merchantPublicKey = (PublicKey)objectOut1.readObject();
            objectOut1.close();

            boolean succes;

            ObjectInputStream in = new ObjectInputStream(socket.getInputStream());
            String paymentDataJson = (String) in.readObject();
            BiggerTransferObject paymentData = (new Gson().fromJson(paymentDataJson, BiggerTransferObject.class));

            ExchangeTransferObject PM = (ExchangeTransferObject)Tools.BytesToObject(paymentData.PM.paymentDetails);

            if( PM.card.clientName.equals(client.ownerName) && PM.card.expireDate.equals(client.expireDate) && PM.card.cardNumber.equals(client.cardNumber) && (PM.card.cvv == client.cvv)){
                if(Integer.parseInt(client.stockMoney) - Integer.parseInt(PM.ammount) > 0){
                    System.out.println("Succes!Enough money in the bank account");
                    client.stockMoney = Integer.toString(Integer.parseInt(client.stockMoney) - Integer.parseInt(PM.ammount));


//                    succes = true;
                    //send respond to
//                    ObjectOutputStream merchantOut = new ObjectOutputStream(socket.getOutputStream());
//                    merchantOut.writeObject(Tools.ObjectToBytes(succes));
//                    merchantOut.flush();
                }
                else {
                    System.out.println("You re poor!");
//                    succes = false;
//                    ObjectOutputStream merchantOut = new ObjectOutputStream(socket.getOutputStream());
//                    merchantOut.writeObject(Tools.ObjectToBytes(succes));
//                    merchantOut.flush();
                }
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