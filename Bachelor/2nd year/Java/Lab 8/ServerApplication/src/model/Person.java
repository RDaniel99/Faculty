package model;

import java.util.ArrayList;
import java.util.List;

public class Person {
    private String name;
    private List<Person> friends;
    private List<String> messages;

    @Override
    public boolean equals(Object obj) {
        if (obj==this) return true;
        if (!(obj instanceof Person)) return false;
        Person person = (Person)obj;
        return person.getName().equals(this.getName());
    }

    public Person(String name){
        this.setName(name);
        this.setFriends(new ArrayList<>());
        this.setMessages(new ArrayList<>());
    }

    void addFriend(Person person){
        friends.add(person);
    }

    void addMessage(String message){
        messages.add(message);
    }

    void emptyMessages(){
        messages.clear();
    }

    public String getName() {
        return name;
    }

    private void setName(String name) {
        this.name = name;
    }

    List<Person> getFriends() {
        return friends;
    }

    private void setFriends(List<Person> friends) {
        this.friends = friends;
    }

    List<String> getMessages() {
        return messages;
    }

    private void setMessages(List<String> messages) {
        this.messages = messages;
    }
}
