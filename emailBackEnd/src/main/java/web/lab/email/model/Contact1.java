package web.lab.email.model;

import java.util.LinkedList;

public class Contact1 implements Comparable<Contact1>{
    String name;
    LinkedList<String> emails = new LinkedList<String>();

    public Contact1() {
    }

    public Contact1(String name, LinkedList<String> emails) {
        this.name = name;
        this.emails = emails;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public LinkedList<String> getEmails() {
        return emails;
    }

    public void setEmails(LinkedList<String> emails) {
        this.emails = emails;
    }
    public void addEmail(String emailContact){
        emails.add(emailContact);
    }

    @Override
    public int compareTo(Contact1 o) {
        return this.getName().compareTo(o.getName());
    }
}
