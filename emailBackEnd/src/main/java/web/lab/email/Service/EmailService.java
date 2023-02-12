package web.lab.email.Service;

import org.springframework.stereotype.Service;
import web.lab.email.database.DataCreator;
import web.lab.email.database.Contact;
import web.lab.email.model.Contact1;
import web.lab.email.model.Email;
import web.lab.email.model.User;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

@Service
public class EmailService {

    private LinkedList<Email> emails = new LinkedList<Email>();
    private LinkedList<User> users = new LinkedList<User>();
    private DataCreator dataCreator  = new DataCreator();

    public LinkedList<Email> getEmails() {
        return emails;
    }

    public Email sendEmail(Email email)  {
        /*if (!checkUser(email.getSender())) {
            return "Sender doesn't exist!!";
        }
        if (! checkUser(email.getReceiver())) {
            return "Receiver doesn't exist!!";
        }*/
        email.setDraft("No");
        String pattern = "dd/MM/yyyy hh:mm:ss";
        DateFormat df = new SimpleDateFormat(pattern);
        Date today = Calendar.getInstance().getTime();
        String todayAsString = df.format(today);
        email.setDate(todayAsString);
        System.out.println(email.getContext());
        try {
            dataCreator.addNewEmail(email);
        } catch (NoSuchFieldException e) {
            throw new RuntimeException(e);
        } catch (IllegalAccessException e) {
            throw new RuntimeException(e);
        }
        Email e = new Email("Email Sent", "1", "1", "1", "1","1","1", "1", "1", "1", "1","1","1", "1", "1");
        return e;
    }

    private boolean checkUser(String sender) {

       return  dataCreator.getUserByEmail(sender)!=null;
    }

    public User addUser(User user){
        if(checkUser(user.getEmail())){
            return new User("User Already Registered", "1", "1", "1", "1");
        }
        if(user.getEmail().equals("Wrong Email") || user.getEmail().equals("Wrong Password"))
            return new User("Invalid Email", "1", "1", "1", "1");
        String pattern = "dd/MM/yyyy";
        DateFormat df = new SimpleDateFormat(pattern);
        Date today = Calendar.getInstance().getTime();
        String todayAsString = df.format(today);
        user.setJoinDate(todayAsString);
        try {
            dataCreator.addNewUser(user);
        } catch (NoSuchFieldException e) {
            throw new RuntimeException(e);
        } catch (IllegalAccessException e) {
            throw new RuntimeException(e);
        }
        System.out.println(users);

        return new User("User Registered", "1", "1", user.getJoinDate(), "1");
    }

    public List<Email> getSentMails(String userEmail) {
        System.out.println((userEmail));
        List<Email> userMails = new LinkedList<>();
        userMails=dataCreator.getSentEmailsOfUser(userEmail);
        return userMails;
    }

   public List<Email> getRecMails(String userEmail) {
        System.out.println((userEmail));
        List<Email> userMails = new LinkedList<>();
        userMails = dataCreator.getReceivedEmailsOfUser(userEmail);
        System.out.println("Here");
        System.out.println(userEmail);
        return userMails;
    }

    public User login(String userEmail, String userPassword) {
            if(dataCreator.getPassword(userEmail)==null){
                User wrongEmail = new User();
                wrongEmail.setEmail("Email doesn't exist");
                return wrongEmail;
            }
            else if (dataCreator.getPassword(userEmail).equals(userPassword)){
                return dataCreator.getUserByEmail(userEmail);
            }
            else {
                User wrongPassword = new User();
                wrongPassword.setEmail("Wrong Password");
                return wrongPassword;
            }
    }
   public Email addContact(String userEmail, String contactName, String contactEmail){

                for (int k = 0; k < dataCreator.getUserContacts(userEmail).size();k++){
                    if(dataCreator.getUserContacts(userEmail).get(k).getName().equals(contactName)){
                        return new Email("Contact already exists", "1", "1", "1", "1","1","1", "1", "1", "1", "1","1","1", "1", "1");
                    }
                }
       try {
           dataCreator.addNewContact(new Contact(contactName, userEmail, contactEmail,"","","","" ));
       } catch (NoSuchFieldException e) {
           throw new RuntimeException(e);
       } catch (IllegalAccessException e) {
           throw new RuntimeException(e);
       }
       return new Email("Contact added", "1", "1", "1", "1","1","1", "1", "1", "1", "1","1","1", "1", "1");

    }

    public LinkedList<Contact1> getContacts(String email) {
        LinkedList<Contact1> ll = new LinkedList<>();
       if(dataCreator.getUserContacts(email).isEmpty())
           return null;
       else{
           for(int i=0; i<dataCreator.getUserContacts(email).size();i++){
               Contact1 c = new Contact1();
               c.setName(dataCreator.getUserContacts(email).get(i).getName());
               if(!dataCreator.getUserContacts(email).get(i).getEmail1().equals(""))
                   c.addEmail(dataCreator.getUserContacts(email).get(i).getEmail1());
               if(!dataCreator.getUserContacts(email).get(i).getEmail2().equals(""))
                   c.addEmail(dataCreator.getUserContacts(email).get(i).getEmail2());
               if(!dataCreator.getUserContacts(email).get(i).getEmail3().equals(""))
                   c.addEmail(dataCreator.getUserContacts(email).get(i).getEmail3());
               if(!dataCreator.getUserContacts(email).get(i).getEmail4().equals(""))
                   c.addEmail(dataCreator.getUserContacts(email).get(i).getEmail4());
               if(!dataCreator.getUserContacts(email).get(i).getEmail5().equals(""))
                   c.addEmail(dataCreator.getUserContacts(email).get(i).getEmail5());
               ll.add(c);

           }

       }
       return ll;
    }
    public LinkedList<Contact1> getSortedContacts(String email) {
        LinkedList<Contact1> contactList = getContacts(email);
        PriorityQueue<Contact1> pq = new PriorityQueue<>();
        for (int i = 0; i < contactList.size(); i++){
            pq.add(contactList.get(i));
        }
        LinkedList<Contact1> ll = new LinkedList<>();
        while(true){
            Contact1 c = pq.poll();
            if(c == null)   break;
            ll.add(c);
        }
        return ll;
    }
    public Email addEmailToContact(String userEmail, String contactName, String contactEmail){
                for (int k = 0; k < dataCreator.getUserContacts(userEmail).size();k++){
                    if(dataCreator.getUserContacts(userEmail).get(k).getName().equals(contactName)){
                            if(dataCreator.getUserContacts(userEmail).get(k).getEmail1().equals(contactEmail)||
                                    dataCreator.getUserContacts(userEmail).get(k).getEmail2().equals(contactEmail)||
                                    dataCreator.getUserContacts(userEmail).get(k).getEmail3().equals(contactEmail)||
                                    dataCreator.getUserContacts(userEmail).get(k).getEmail4().equals(contactEmail)||
                                    dataCreator.getUserContacts(userEmail).get(k).getEmail5().equals(contactEmail)
                            ){
                                return new Email("Email already exists", "1", "1", "1", "1","1","1", "1", "1", "1", "1","1","1", "1", "1");
                            }
                            if(dataCreator.getUserContacts(userEmail).get(k).getEmail1().equals(""))
                            {
                                dataCreator.updateContact(dataCreator.getUserContacts(userEmail).get(k),dataCreator.EMAIL_1_COLUMN,contactEmail);
                                return new Email("Email added to contact1", "1", "1", "1", "1","1","1", "1", "1", "1", "1","1","1", "1", "1");
                            }
                            else  if(dataCreator.getUserContacts(userEmail).get(k).getEmail2().equals(""))
                            {
                                dataCreator.updateContact(dataCreator.getUserContacts(userEmail).get(k),dataCreator.EMAIL_2_COLUMN,contactEmail);
                                return new Email("Email added to contact2", "1", "1", "1", "1","1","1", "1", "1", "1", "1","1","1", "1", "1");
                            }
                            else  if(dataCreator.getUserContacts(userEmail).get(k).getEmail3().equals(""))
                            {
                                dataCreator.updateContact(dataCreator.getUserContacts(userEmail).get(k),dataCreator.EMAIL_3_COLUMN,contactEmail);
                                return new Email("Email added to contact3", "1", "1", "1", "1","1","1", "1", "1", "1", "1","1","1", "1", "1");
                            }
                            else  if(dataCreator.getUserContacts(userEmail).get(k).getEmail4().equals(""))
                            {
                                dataCreator.updateContact(dataCreator.getUserContacts(userEmail).get(k),dataCreator.EMAIL_4_COLUMN,contactEmail);
                                return new Email("Email added to contact4", "1", "1", "1", "1","1","1", "1", "1", "1", "1","1","1", "1", "1");
                            }
                            else  if(dataCreator.getUserContacts(userEmail).get(k).getEmail5().equals(""))
                            {
                                dataCreator.updateContact(dataCreator.getUserContacts(userEmail).get(k),dataCreator.EMAIL_5_COLUMN,contactEmail);
                                return new Email("Email added to contact5", "1", "1", "1", "1","1","1", "1", "1", "1", "1","1","1", "1", "1");
                            }
                            else
                                return new Email("only 5 emails to one contact", "1", "1", "1", "1","1","1", "1", "1", "1", "1","1","1", "1", "1");
                    }
            }
        return new Email("Contact doesn't exist", "1", "1", "1", "1","1","1", "1", "1", "1", "1","1","1", "1", "1");
    }

    public void deleteContact(String email, String contactName) {

        dataCreator.deleteContact(email,contactName);
        return;
    }

    public void deleteContactEmail(String userEmail, String contactName, String contactEmail) {

        for (int k = 0; k < dataCreator.getUserContacts(userEmail).size();k++){
            if(dataCreator.getUserContacts(userEmail).get(k).getName().equals(contactName)){
                if(dataCreator.getUserContacts(userEmail).get(k).getEmail1().equals(contactEmail))
                {
                    dataCreator.updateContact(dataCreator.getUserContacts(userEmail).get(k),dataCreator.EMAIL_1_COLUMN,"");
                    return;
                }
                else  if(dataCreator.getUserContacts(userEmail).get(k).getEmail2().equals(contactEmail))
                {
                    dataCreator.updateContact(dataCreator.getUserContacts(userEmail).get(k),dataCreator.EMAIL_2_COLUMN,"");
                    return;
                }
                else  if(dataCreator.getUserContacts(userEmail).get(k).getEmail3().equals(contactEmail))
                {
                    dataCreator.updateContact(dataCreator.getUserContacts(userEmail).get(k),dataCreator.EMAIL_3_COLUMN,"");
                    return;
                }
                else  if(dataCreator.getUserContacts(userEmail).get(k).getEmail4().equals(contactEmail))
                {
                    dataCreator.updateContact(dataCreator.getUserContacts(userEmail).get(k),dataCreator.EMAIL_4_COLUMN,"");
                    return;
                }
                else  if(dataCreator.getUserContacts(userEmail).get(k).getEmail5().equals(contactEmail))
                {
                    dataCreator.updateContact(dataCreator.getUserContacts(userEmail).get(k),dataCreator.EMAIL_5_COLUMN,"");
                    return;
                }
            }
        }
    }

    public void deleteMessageSender(Email email) {
        String pattern = "dd/MM/yyyy hh:mm:ss";
        DateFormat df = new SimpleDateFormat(pattern);
        Date today = Calendar.getInstance().getTime();
        String todayAsString = df.format(today);
        dataCreator.updateMessage(dataCreator.getMessage(email.getSender(),email.getReceiver(),email.getContext(),email.getSubject(),email.getDate()),"sentDelete",todayAsString);
        System.out.print("DELETE: !!");
    }

    public void deleteMessageReceiver(Email email) {
        String pattern = "dd/MM/yyyy hh:mm:ss";
        DateFormat df = new SimpleDateFormat(pattern);
        Date today = Calendar.getInstance().getTime();
        String todayAsString = df.format(today);
        dataCreator.updateMessage(dataCreator.getMessage(email.getSender(),email.getReceiver(),email.getContext(),email.getSubject(),email.getDate()),dataCreator.REC_DELETE_COLUMN,todayAsString);
        System.out.print("DELETE: !!");
    }

    public LinkedList<Email> getDeletedMessages(String userEmail) {
        String pattern = "dd/MM/yyyy hh:mm:ss";
        SimpleDateFormat df = new SimpleDateFormat(pattern);
        Date today = Calendar.getInstance().getTime();
        String todayAsString = df.format(today);
        LinkedList<Email> userDeletedMails = new LinkedList<Email>();
        System.out.println("bin"+dataCreator.getSentEmailsOfUser(userEmail).size());
         List<Email> deleteRecEmail = dataCreator.getRecEmailsOfUserDelete(userEmail);
        List<Email> deleteSentEmail = dataCreator.getSentEmailsOfUserDelete(userEmail);
        for(int i=0;i<deleteSentEmail.size();i++){

           try {
                Date date1=df.parse(deleteSentEmail.get(i).getSentDelete());
                Date date2=df.parse(todayAsString);
                long timeDifference = (date2.getTime()-date1.getTime())/1000;
               System.out.println("seconds "+timeDifference);
                if(timeDifference/ (60.0*60.0*24.0*30.0) >= 1.0){
                    dataCreator.updateMessage(deleteSentEmail.get(i), dataCreator.SENT_DELETE_COLUMN, "DELETED");
                    continue;
                }
            } catch (ParseException e) {
                throw new RuntimeException(e);
            }
            userDeletedMails.add(deleteSentEmail.get(i));
        }


        for(int i=0;i<deleteRecEmail.size();i++){
           try {
                Date date1=df.parse(deleteRecEmail.get(i).getRecDelete());
                Date date2=df.parse(todayAsString);
                long timeDifference = (date2.getTime()-date1.getTime())/1000;
                System.out.println("seconds "+timeDifference);
               if(timeDifference/ (60.0*60.0*24.0*30.0) >= 1.0){
                   dataCreator.updateMessage(deleteRecEmail.get(i), dataCreator.REC_DELETE_COLUMN, "DELETED");;
                   continue;
               }
            } catch (ParseException e) {
                throw new RuntimeException(e);
            }
            userDeletedMails.add(deleteRecEmail.get(i));
        }
       return userDeletedMails;
    }

    public void restoreSenderMessage(Email email) {
        dataCreator.updateMessage(dataCreator.getMessage(email.getSender(),email.getReceiver(),email.getContext(),email.getSubject(),email.getDate()),"sentDelete","No");
        System.out.print("restore: !!");
    }

    public void restoreReceiverMessage(Email email) {
       dataCreator.updateMessage(dataCreator.getMessage(email.getSender(),email.getReceiver(),email.getContext(),email.getSubject(),email.getDate()),"recDelete","No");
       System.out.print("restore: !!");
    }

    public LinkedList<Email> getPrioritySentMails(String email, String priority) {
        PriorityQueue<Email> pq = new PriorityQueue<>();
        List<Email>list = new ArrayList<>();
        list=getSentMails(email);
        for(int i=0 ;i<list.size();i++){
            pq.add(list.get(i));
        }
        LinkedList<Email> ll = new LinkedList<>();
        while(true){
            Email e = pq.poll();
            if(e == null)   break;
            ll.add(e);
            System.out.println(e.getPriority());
        }
        return ll;
    }
    public List<Email> getPriorityRecMails(String email, String priority){
         PriorityQueue<Email> pq = new PriorityQueue<>();
        for (int i = 0;i<dataCreator.getReceivedEmailsOfUser(email).size();i++){
            if(dataCreator.getReceivedEmailsOfUser(email).get(i).getRecDelete().equals("No")){
                pq.add(dataCreator.getReceivedEmailsOfUser(email).get(i));
            }
        }
        List<Email> ll = new LinkedList<>();
        while(true){
            Email e = pq.poll();
            if(e == null)   break;
            ll.add(e);
        }
        return ll;
    }

    public void addDraft(Email email) {
        String pattern = "dd/MM/yyyy hh:mm:ss";
        DateFormat df = new SimpleDateFormat(pattern);
        Date today = Calendar.getInstance().getTime();
        String todayAsString = df.format(today);
        email.setDate(todayAsString);
       email.setDraft("Yes");
        try {
            dataCreator.addNewEmail(email);
        } catch (NoSuchFieldException e) {
            throw new RuntimeException(e);
        } catch (IllegalAccessException e) {
            throw new RuntimeException(e);
        }
    }
    public List<Email> getDrafts(String userEmail) {
        return dataCreator.getDraftEmailsOfUser(userEmail);
    }
    public void deleteDraft(Email email){
        dataCreator.deleteEmailMessage(dataCreator.getMessage(email.getSender(),email.getReceiver(),email.getContext(),email.getSubject(),email.getDate()));
    }
    public LinkedList<User> search(String searchKey) {
        LinkedList<User> searchedUsers = new LinkedList<>();
        for (int i = 0; i < dataCreator.getAllUsersEmailAccounts().size(); i++){
            System.out.println("search"+dataCreator.getAllUsersEmailAccounts());
            if(dataCreator.getAllUsersEmailAccounts().get(i).toLowerCase().contains(searchKey.toLowerCase())){
                searchedUsers.add(dataCreator.getUserByEmail(dataCreator.getAllUsersEmailAccounts().get(i)));
            }
        }
        return searchedUsers;
    }
    public List<Email> getSentEmailsByContext(String userEmail){
        List<Email> sentEmails = getSentMails(userEmail);
        Comparator<Email> sortContext = Comparator.comparing(Email::getContext);
        sentEmails.sort(sortContext);
        return sentEmails;
    }
    public List<Email> getRecEmailsByContext(String userEmail){
        List<Email> recEmails = getRecMails(userEmail);
        Comparator<Email> sortContext = Comparator.comparing(Email::getContext);
        recEmails.sort(sortContext);
        return recEmails;
    }
    public List<Email> getSentEmailsBySubject(String userEmail){
        List<Email> sentEmails = getSentMails(userEmail);
        Comparator<Email> sortContext = Comparator.comparing(Email::getSubject);
        sentEmails.sort(sortContext);
        return sentEmails;
    }
    public List<Email> getRecEmailsBySubject(String userEmail){
        List<Email> recEmails = getRecMails(userEmail);
        Comparator<Email> sortContext = Comparator.comparing(Email::getSubject);
        recEmails.sort(sortContext);
        return recEmails;
    }
    public LinkedList<Email> searchSentMailsByContext(String userEmail, String searchKey){
        List<Email> sentEmails = getSentMails(userEmail);
        LinkedList<Email> ll  = new LinkedList<>();
        for (int i = 0; i < sentEmails.size(); i++){
            if(sentEmails.get(i).getContext().toLowerCase().contains(searchKey.toLowerCase())){
                ll.add(sentEmails.get(i));
            }
        }
        return ll;
    }
    public LinkedList<Email> searchRecMailsByContext(String userEmail, String searchKey){
        List<Email> recEmails = getRecMails(userEmail);
        LinkedList<Email> ll  = new LinkedList<>();
        for (int i = 0; i < recEmails.size(); i++){
            if(recEmails.get(i).getContext().toLowerCase().contains(searchKey.toLowerCase())){
                ll.add(recEmails.get(i));
            }
        }
        return ll;
    }
    public LinkedList<Email> searchSentMailsBySubject(String userEmail, String searchKey){
        List<Email> sentEmails = getSentMails(userEmail);
        LinkedList<Email> ll  = new LinkedList<>();
        for (int i = 0; i < sentEmails.size(); i++){
            if(sentEmails.get(i).getSubject().toLowerCase().contains(searchKey.toLowerCase())){
                ll.add(sentEmails.get(i));
            }
        }
        return ll;
    }
    public LinkedList<Email> searchRecMailsBySubject(String userEmail, String searchKey){
        List<Email> recEmails = getRecMails(userEmail);
        LinkedList<Email> ll  = new LinkedList<>();
        for (int i = 0; i < recEmails.size(); i++){
            if(recEmails.get(i).getSubject().toLowerCase().contains(searchKey.toLowerCase())){
                ll.add(recEmails.get(i));
            }
        }
        return ll;
    }
    public LinkedList<Email> searchSentEmailByReceiver(String userEmail, String searchKey){
        List<Email> sentEmails = getSentMails(userEmail);
        LinkedList<Email> ll  = new LinkedList<>();
        for (int i = 0; i < sentEmails.size(); i++){
            if(sentEmails.get(i).getReceiver().toLowerCase().contains(searchKey.toLowerCase())){
                ll.add(sentEmails.get(i));
            }
        }
        return ll;
    }
    public LinkedList<Email> searchRecEmailBySender(String userEmail, String searchKey){
        List<Email> recEmails = getRecMails(userEmail);
        LinkedList<Email> ll  = new LinkedList<>();
        for (int i = 0; i < recEmails.size(); i++){
            if(recEmails.get(i).getSender().toLowerCase().contains(searchKey.toLowerCase())){
                ll.add(recEmails.get(i));
            }
        }
        return ll;
    }
    public LinkedList<String> getUserFolders(String userEmail){
        return new LinkedList<>(dataCreator.getUserFolders(userEmail));
    }
    public void addEmailToFolderOfSender(Email email, String folderName){
        dataCreator.setFolderOfEmail(email, folderName, dataCreator.SENDER);
    }
    public void addEmailToFolderOfReceiver(Email email, String folderName){
        dataCreator.setFolderOfEmail(email, folderName, dataCreator.RECEIVER);
    }
    public void removeEmailFromFolderOfSender(Email email){
        dataCreator.setFolderOfEmail(email, "", dataCreator.SENDER);
    }
    public void removeEmailFromFolderOfReceiver(Email email){
        dataCreator.setFolderOfEmail(email, "", dataCreator.RECEIVER);
    }
    public LinkedList<Email> getMessagesInFolder(String userEmail, String folderName){
        LinkedList<Email> ll = dataCreator.getMessagesInFolder(userEmail, folderName);
        System.out.println(ll);
        return ll;
    }
    public void deleteFolder(String userEmail, String folderName){
        LinkedList<Email> folderEmails = getMessagesInFolder(userEmail, folderName);
        for(int i = 0; i<folderEmails.size();i++){
            if(folderEmails.get(i).getSender().equals(userEmail)){
                dataCreator.updateMessage(folderEmails.get(i), dataCreator.FOLDER_SENDER_COLUMN, "");
            }else if(folderEmails.get(i).getReceiver().equals(userEmail)){
                dataCreator.updateMessage(folderEmails.get(i), dataCreator.FOLDER_REC_COLUMN, "");
            }
        }
    }
    public void renameFolder(String userEmail, String oldFolderName, String newFolderName){
        LinkedList<Email> folderEmails = getMessagesInFolder(userEmail, oldFolderName);System.out.println("size"+folderEmails.size());
        for(int i = 0; i<folderEmails.size();i++){
            if(folderEmails.get(i).getSender().equals(userEmail)){
                System.out.println("11Rename1");
                dataCreator.updateMessage(folderEmails.get(i), dataCreator.FOLDER_SENDER_COLUMN, newFolderName);
            }else if(folderEmails.get(i).getReceiver().equals(userEmail)){
                dataCreator.updateMessage(folderEmails.get(i), dataCreator.FOLDER_REC_COLUMN, newFolderName);
                System.out.println("11Rename2");
            }
        }
    }
    public void deleteAttachment(Email email, String attachmentNumber){
        switch (attachmentNumber){
            case "1":
                dataCreator.updateMessage(email, dataCreator.FIRST_LINK_COLUMN, "");
                break;
            case "2":
                dataCreator.updateMessage(email, dataCreator.SECOND_LINK_COLUMN, "");
                break;
            case "3":
                dataCreator.updateMessage(email, dataCreator.THIRD_LINK_COLUMN, "");
                break;
            case "4":
                dataCreator.updateMessage(email, dataCreator.FOURTH_LINK_COLUMN, "");
                break;
            case "5":
                dataCreator.updateMessage(email, dataCreator.FIFTH_LINK_COLUMN, "");
                break;
            case "6":
                dataCreator.updateMessage(email, dataCreator.SIXTH_LINK_COLUMN, "");
                break;
            default:
                break;
        }
    }
    public void permanentlyDeleteMessage(Email email, String side){
        if(side.equals("receiver")){
            dataCreator.updateMessage(email, dataCreator.REC_DELETE_COLUMN, "DELETED");
        }else if(side.equals("sender")){
            dataCreator.updateMessage(email, dataCreator.SENT_DELETE_COLUMN, "DELETED");
        }
    }
    public void changeUserName(String userEmail, String newFirstName, String newLastName){
        dataCreator.updateUser(dataCreator.getUserByEmail(userEmail), dataCreator.FIRST_NAME_COLUMN, newFirstName);
        dataCreator.updateUser(dataCreator.getUserByEmail(userEmail), dataCreator.LAST_NAME_COLUMN, newLastName);
    }
}
