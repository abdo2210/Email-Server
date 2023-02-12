package web.lab.email;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import web.lab.email.Service.EmailService;
import web.lab.email.database.Contact;
import web.lab.email.model.Contact1;
import web.lab.email.model.Email;
import web.lab.email.model.User;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

@CrossOrigin
@RestController
public class EmailController {

    @Autowired
    private EmailService emailService;

    @PostMapping("/send")
    public Email sendEmail(@RequestBody Email email){
        return emailService.sendEmail(email);
    }
    @GetMapping("/emails")
    public LinkedList<Email> getEmails(){
        return emailService.getEmails();
    }
    @PostMapping("/register")
    public User sendEmail(@RequestBody User user){
        System.out.println(user.getFirstName());
        return emailService.addUser(user);
    }

    @PostMapping("/getSentMails")
    public List<Email> getSentMails(@RequestBody HashMap<String, String> userEmail){
        return emailService.getSentMails(userEmail.get("email"));
    }
    @PostMapping("/getRecMails")
    public List<Email> getRecMails(@RequestBody HashMap<String, String> userEmail){
        System.out.println(userEmail.get("email"));
        return emailService.getRecMails(userEmail.get("email"));
    }
    @PostMapping("/getPrioritySentMails")
    public List<Email> getPrioritySentMails(@RequestBody HashMap<String, String> userEmail){
        System.out.println(userEmail.get("email"));
         return emailService.getPrioritySentMails(userEmail.get("email"), userEmail.get("priority"));
    }
    @PostMapping("/getPriorityRecMails")
    public List<Email> getPriorityRecMails(@RequestBody HashMap<String, String> userEmail){
        System.out.println(userEmail.get("email"));
        return emailService.getPriorityRecMails(userEmail.get("email"), userEmail.get("priority"));
    }
    @PostMapping("/login")
    public User logIn(@RequestBody HashMap<String, String> body){
        System.out.println("Logging in:" + body.get("email") + "\nPassword" + body.get("password"));
        return emailService.login(body.get("email"), body.get("password"));
    }
    @PostMapping("addContact")
    public Email addContact(@RequestBody HashMap<String, String> body){
        System.out.println("Adding contact:  " + body.get("contactName") + " " + body.get("contactEmail") + " to:  " + body.get("userEmail"));
        return emailService.addContact(body.get("userEmail"), body.get("contactName"), body.get("contactEmail"));
    }
    @PostMapping("getContacts")
    public LinkedList<Contact1> getContacts(@RequestBody HashMap<String, String> body){
        System.out.println("Getting contacts for:  " + body.get("email"));
        return emailService.getContacts(body.get("email"));
    }
    @PostMapping("getSortedContacts")
    public LinkedList<Contact1> getSortedContacts(@RequestBody HashMap<String, String> body){
        System.out.println("Getting sorted contacts for:  " + body.get("email"));
        return emailService.getSortedContacts(body.get("email"));
    }
    @PostMapping("addContactEmail")
    public Email addContactEmail(@RequestBody HashMap<String, String> body){
        System.out.println("Adding to contact:  " + body.get("contactName") + " the Email " + body.get("contactEmail") + " to:  " + body.get("userEmail"));
        return emailService.addEmailToContact(body.get("userEmail"), body.get("contactName"), body.get("contactEmail"));
    }
    @PostMapping("deleteContact")
    public void deleteContact(@RequestBody HashMap<String, String> body){
        System.out.println("deleting Contact: " + body.get("contactName") + " for email: " + body.get("userEmail"));
        emailService.deleteContact(body.get("userEmail"), body.get("contactName"));
    }
    @PostMapping("deleteContactEmail")
    public void deleteContactEmail(@RequestBody HashMap<String, String> body){
        System.out.println("deleting Email: " + body.get("contactEmail") + " for Contact" + body.get("contactName") + " for email: " + body.get("userEmail"));
        emailService.deleteContactEmail(body.get("userEmail"), body.get("contactName"), body.get("contactEmail"));
    }
    @PostMapping("deleteMessageSender")
    public void deleteMessageSender(@RequestBody Email email){
        System.out.println("deleting Email: " + "from the Sender " + email.getSender());
        emailService.deleteMessageSender(email);
    }
    @PostMapping("deleteMessageReceiver")
    public void deleteMessageReceiver(@RequestBody Email email){
        System.out.println("deleting Email: " + "from the Receiver " + email.getSender());
        emailService.deleteMessageReceiver(email);
    }
    @PostMapping("getDeletedMessages")
    public LinkedList<Email> getDeletedMessages(@RequestBody HashMap<String, String> body){
        System.out.println("Getting deleted messages for: " + body.get("userEmail"));
        return emailService.getDeletedMessages(body.get("userEmail"));
    }
    @PostMapping("restoreSenderMessage")
    public void restoreSenderMessage(@RequestBody Email email){
        System.out.println("Restoring Message for: " + email.getSender());
        emailService.restoreSenderMessage(email);
    }
    @PostMapping("restoreReceiverMessage")
    public void restoreReceiverMessage(@RequestBody Email email){
        System.out.println("Restoring Message for: " + email.getSender());
        emailService.restoreReceiverMessage(email);
    }
    @PostMapping("addDraft")
    public void addDraft(@RequestBody Email email){
        System.out.println("Adding Draft for: " + email.getSender());
        emailService.addDraft(email);
    }
    @PostMapping("getDrafts")
    public List<Email> getDraft(@RequestBody HashMap<String, String> body){
        System.out.println("Getting drafts for: " + body.get("userEmail"));
        return emailService.getDrafts(body.get("userEmail"));
    }
    @PostMapping("deleteDraft")
    public void deleteDraft(@RequestBody Email email){
        System.out.println("Deleting Draft for: " + email.getSender());
        emailService.deleteDraft(email);
    }
    @PostMapping("search")
    public LinkedList<User> search(@RequestBody HashMap<String, String> body){
        System.out.println("Searching for: " + body.get("searchKey"));
        return emailService.search(body.get("searchKey"));
    }
    @PostMapping("/getSentEmailsByContext")
    public List<Email> getSentEmailsByContext(@RequestBody HashMap<String, String> userEmail){
        System.out.println(userEmail.get("email"));
        return emailService.getSentEmailsByContext(userEmail.get("email"));
    }
    @PostMapping("/getRecEmailsByContext")
    public List<Email> getRecEmailsByContext(@RequestBody HashMap<String, String> userEmail){
        System.out.println("Hello");
        return emailService.getRecEmailsByContext(userEmail.get("email"));
    }
    @PostMapping("/getSentEmailsBySubject")
    public List<Email> getSentEmailsBySubject(@RequestBody HashMap<String, String> userEmail){
        System.out.println(userEmail.get("email"));
        return emailService.getSentEmailsBySubject(userEmail.get("email"));
    }
    @PostMapping("/getRecEmailsBySubject")
    public List<Email> getRecEmailsBySubject(@RequestBody HashMap<String, String> userEmail){
        System.out.println("Hello");
        return emailService.getRecEmailsBySubject(userEmail.get("email"));
    }
    @PostMapping("/searchSentMailsByContext")
    public List<Email> searchSentMailsByContext(@RequestBody HashMap<String, String> userEmail){
        System.out.println(userEmail.get("email"));
        return emailService.searchSentMailsByContext(userEmail.get("email"), userEmail.get("searchKey"));
    }
    @PostMapping("/searchRecMailsByContext")
    public List<Email> searchRecMailsByContext(@RequestBody HashMap<String, String> userEmail){
        System.out.println("Hello");
        return emailService.searchRecMailsByContext(userEmail.get("email"),  userEmail.get("searchKey"));
    }
    @PostMapping("/searchSentMailsBySubject")
    public List<Email> searchSentMailsBySubject(@RequestBody HashMap<String, String> userEmail){
        System.out.println(userEmail.get("email"));
        return emailService.searchSentMailsBySubject(userEmail.get("email"), userEmail.get("searchKey"));
    }
    @PostMapping("/searchRecMailsBySubject")
    public List<Email> searchRecMailsBySubject(@RequestBody HashMap<String, String> userEmail){
        System.out.println("Hello");
        return emailService.searchRecMailsBySubject(userEmail.get("email"),  userEmail.get("searchKey"));
    }
    @PostMapping("/searchSentEmailByReceiver")
    public List<Email> searchSentEmailByReceiver(@RequestBody HashMap<String, String> userEmail){
        System.out.println(userEmail.get("email"));
        return emailService.searchSentEmailByReceiver(userEmail.get("email"), userEmail.get("searchKey"));
    }
    @PostMapping("/searchRecEmailBySender")
    public List<Email> searchRecEmailBySender(@RequestBody HashMap<String, String> userEmail){
        System.out.println("Hello");
        return emailService.searchRecEmailBySender(userEmail.get("email"),  userEmail.get("searchKey"));
    }
    @PostMapping("/getUserFolders")
    public List<String> getUserFolders(@RequestBody HashMap<String, String> userEmail){
        System.out.println("Hello");
        return emailService.getUserFolders(userEmail.get("email"));
    }
    @PostMapping("/addEmailToFolderOfSender/{folderName}")
    public void addEmailToFolderOfSender(@RequestBody Email email, @PathVariable String folderName){
        System.out.println("addEmailToFolderOfSender " + email.getSender() + " TO " + folderName);
        emailService.addEmailToFolderOfSender(email, folderName);
    }
    @PostMapping("/addEmailToFolderOfReceiver/{folderName}")
    public void addEmailToFolderOfReceiver(@RequestBody Email email, @PathVariable String folderName){
        System.out.println("addEmailToFolderOfReceiver " + email.getReceiver() + " TO " + folderName);
        emailService.addEmailToFolderOfReceiver(email, folderName);
    }
    @PostMapping("/removeEmailFromFolderOfSender")
    public void removeEmailFromFolderOfSender(@RequestBody Email email){
        System.out.println("removeEmailFromFolderOfSender " + email.getSender());
        emailService.removeEmailFromFolderOfSender(email);
    }
    @PostMapping("/removeEmailFromFolderOfReceiver")
    public void removeEmailFromFolderOfReceiver(@RequestBody Email email){
        System.out.println("removeEmailFromFolderOfReceiver " + email.getReceiver());
        emailService.removeEmailFromFolderOfReceiver(email);
    }
    @PostMapping("/getMessagesInFolder")
    public LinkedList<Email> getMessagesInFolder(@RequestBody HashMap<String, String> body){
        System.out.println("getMessagesInFolder " + body.get("folderName") + "TO " + body.get("userEmail"));
        return emailService.getMessagesInFolder(body.get("userEmail"), body.get("folderName"));
    }
    @PostMapping("/deleteFolder")
    public void deleteFolder(@RequestBody HashMap<String, String> body){
        System.out.println("deleteFolder " + body.get("folderName") + " OF " + body.get("userEmail"));
        emailService.deleteFolder(body.get("userEmail"), body.get("folderName"));
    }
    @PostMapping("/renameFolder")
    public void renameFolder(@RequestBody HashMap<String, String> body){
        System.out.println("renameFolder " + body.get("oldFolderName") + "TO" + body.get("newFolderName") + " OF " + body.get("userEmail"));
        emailService.renameFolder(body.get("userEmail"), body.get("oldFolderName"), body.get("newFolderName"));
    }
    @PostMapping("/deleteAttachment/{attachmentNumber}")
    public void deleteAttachment(@RequestBody Email email, @PathVariable String attachmentNumber){
        System.out.println("DELETING ATTACHMENT NO: " + attachmentNumber + " SENT BETWEEN: " + email.getSender() + " AND: " + email.getReceiver());
        emailService.deleteAttachment(email, attachmentNumber);
    }
    @PostMapping("permanentlyDeleteMessage/{side}")
    public void permanentlyDeleteMessage(@RequestBody Email email, @PathVariable String side){
        System.out.println("permanently Deleting Message for: " + email.getSender());
        emailService.permanentlyDeleteMessage(email, side);
    }
    @PostMapping("changeUserName")
    public void changeUserName(@RequestBody HashMap<String, String> body){
        System.out.println("changeUserName: " + body.get("userEmail") + "\nTO: " + body.get("newFirstName") + " " + body.get("newLastName"));
        emailService.changeUserName(body.get("userEmail"), body.get("newFirstName"), body.get("newLastName"));
    }
}
