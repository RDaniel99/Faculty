package model;

import server.ClientThread;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class SocialNetwork {
    private List<Person> users;
    private List<Person> loggedUsers;

    public SocialNetwork(){
        setUsers(new ArrayList<>());
        setLoggedUsers(new ArrayList<>());
    }

    private List<Person> getUsers() {
        return users;
    }

    private void setUsers(List<Person> users) {
        this.users = users;
    }

    public List<Person> getLoggedUsers() {
        return loggedUsers;
    }

    private void setLoggedUsers(List<Person> loggedUsers) {
        this.loggedUsers = loggedUsers;
    }

    public String addUser(String username){
        if (users.contains(new Person(username))) return "Username "+username+" already exists!";
        users.add(new Person(username));
        return "User " +username+" successfully added!";
    }

    public String login(ClientThread client, String username){
        if (!users.contains(new Person(username))) return "User "+username+" does not exist!";
        if (client.getIdentity()!=null) return "A user is already logged in!";
        Person clientIdentity=new Person(username);
        if (!loggedUsers.contains(clientIdentity)) loggedUsers.add(clientIdentity);
        client.setIdentity(clientIdentity);
        return "User "+username+" successfully logged in!";
    }

    public String friend(Person clientIdentity, String usersList){
        List<String> userList=new ArrayList<>(Arrays.asList(usersList.split(" ")));
        String response="";
        for (String username:userList){
            if (!users.contains(new Person(username))){
                response+="User "+username+" does not exist! ";
                continue;
            }
            Person friend=users.get(users.indexOf(new Person(username)));
            clientIdentity.addFriend(friend);
            friend.addFriend(clientIdentity);
        }
        return response+"All possible friends from the list have been added!";
    }

    public String send(Person clientIdentity, String message){
        for (Person friend:clientIdentity.getFriends()){
            friend.addMessage(message);
        }
        return "Message sent!";
    }

    public String read(Person clientIdentity){
        for (String message:clientIdentity.getMessages()){
            System.out.println(message);
        }
        clientIdentity.emptyMessages();
        return "All messages have been displayed";
    }
}
