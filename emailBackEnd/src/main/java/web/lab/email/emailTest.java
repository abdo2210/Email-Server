package web.lab.email;


import org.testng.annotations.Test;
import web.lab.email.Service.EmailService;
import web.lab.email.database.Contact;
import web.lab.email.model.Email;
import web.lab.email.model.User;

import static org.testng.AssertJUnit.assertEquals;

public class emailTest {

    @Test
    public void AddUser(){
        EmailService service = new EmailService();
        User check = new User();
        User user =new User("oop8@mail.com","abdo","elsayd","2/1/2023","123456");
        check = service.addUser(user);
        assertEquals("User Registered",check.getEmail());
    }
    @Test
    public void checkLogIn(){
        EmailService service = new EmailService();
        User check = new User();
        check.setEmail("Wrong Password");
        User user =new User("oop8@mail.com","abdo","elsayd","2/1/2023","123456");
        assertEquals("Wrong Password",service.login(user.getEmail(), "123455").getEmail());
    }
    @Test
    public void SendMessage(){
        EmailService service = new EmailService();
        Email message= new Email ("E", "1", "1", "1", "1","1","1", "1", "1", "1", "1","1","1", "1", "1");
        Email message2= new Email();
        message2=service.sendEmail(message);
        assertEquals("Email Sent",message2.getSender());
    }
    @Test
    public void AddContact(){
        EmailService service = new EmailService();
        Email message= new Email();
        message= service.addContact("oop8@mail.com","ahmed","ahmed@mail.com");
        assertEquals("Contact added",message.getSender());
    }
}