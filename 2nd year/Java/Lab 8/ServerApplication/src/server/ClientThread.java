package server;

import model.Person;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

/***
 * @project ServerApplication
 * @author Gotca Adrian
 */
public class ClientThread extends Thread {

    private Socket socket = null;
    private Person identity = null;
    private final SocialNetworkServer server;
    // Create the constructor that receives a reference to the server and to the client socket

    public ClientThread(SocialNetworkServer server, Socket socket){
        this.server=server;
        this.setSocket(socket);
    }

    public void run() {
        System.out.println("S-a conectat un client");
        try {
            while(true) {
                BufferedReader in = new BufferedReader(new InputStreamReader(getSocket().getInputStream())); //client -> server stream
                String request = in.readLine();
                String response = execute(request);
                PrintWriter out = new PrintWriter(getSocket().getOutputStream()); //server -> client stream
                out.println(response);
                out.flush();
                if(request.equals("exit"))
                    getSocket().close();
            }
        }
        catch (IOException e){
            System.out.println(e.getMessage());
        }
        finally {
            identity=null;
        }
    }

    private String execute(String request) {
        if (request.startsWith("register "))
            return getServer().addUser(request.substring("register ".length()));
        if (request.startsWith("login ")){
            return getServer().login(this,request.substring("login ".length()));
        }
        if (request.startsWith("friend ")){
            return getServer().friend(this, request.substring("friend ".length()));
        }
        if (request.startsWith("send ")){
            return getServer().send(this, request.substring("send ".length()));
        }
        if (request.startsWith("read")){
            return getServer().read(this);
        }
        System.out.println(request);
        return "";
    }

    private Socket getSocket() {
        return socket;
    }

    private void setSocket(Socket socket) {
        this.socket = socket;
    }

    public Person getIdentity() {
        return identity;
    }

    public void setIdentity(Person identity) {
        this.identity = identity;
    }

    private SocialNetworkServer getServer() {
        return server;
    }
}
