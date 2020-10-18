package client;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;

public class SocialNetworkClient {

    private final static String SERVER_ADDRESS = "127.0.0.1";
    private final static int PORT = 8100;
    private PrintWriter out;
    private BufferedReader in;

    private SocialNetworkClient() throws IOException{
        Socket clientSocket = new Socket(SERVER_ADDRESS,PORT);
        out = new PrintWriter(clientSocket.getOutputStream(), true);
        in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
    }

    private String readFromKeyboard() {
        Scanner scanner = new Scanner(System.in);
        return scanner.nextLine();
    }

    void sendRequestToServer(String request) throws  IOException{
        System.out.println(request);
        out.println(request);

        String response = in.readLine();
        System.out.println(response);
    }


    public static void main(String[] args) throws IOException {
        SocialNetworkClient client = new SocialNetworkClient();

        while (true) {
            String request = client.readFromKeyboard();
            if (request.equalsIgnoreCase("exit")) {
                break;
            } else {
                client.sendRequestToServer(request);
            }
        }
    }
}
