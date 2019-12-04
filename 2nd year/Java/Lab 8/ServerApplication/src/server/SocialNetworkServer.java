package server;

import model.Person;
import model.SocialNetwork;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

public class SocialNetworkServer {

    private static final int PORT = 8100;
    private ServerSocket serverSocket;
    private boolean running = false;
    private SocialNetwork socialNetwork;

    private void init() throws IOException{
        try {
            serverSocket = new ServerSocket(PORT);
            socialNetwork = new SocialNetwork();
        }
        catch(IOException e){
            System.out.println(e.getMessage());
        }
        running=true;
    }

    private void waitForClients() throws IOException{
        try {
            while(running){
                System.out.println("Waiting for clients at " + PORT);
                Socket socket = serverSocket.accept();
                new ClientThread(this, socket).start();
            }
        } catch (IOException e){
            System.out.println(e.getMessage());
        } finally {
            stop();
        }
    }

    private void stop() throws IOException {
        this.running = false;
        serverSocket.close();
    }

    String addUser(String username){
        return socialNetwork.addUser(username);
    }

    String login(ClientThread client, String username){
        return socialNetwork.login(client, username);
    }

    String friend(ClientThread client, String usersList){
        return socialNetwork.friend(client.getIdentity(), usersList);
    }

    String send(ClientThread client, String message){
        return socialNetwork.send(client.getIdentity(), message);
    }

    String read(ClientThread client){
        return socialNetwork.read(client.getIdentity());
    }

    public static void main(String[] args) throws IOException {
        SocialNetworkServer server = new SocialNetworkServer();
        server.init();
        server.waitForClients();
    }
}
