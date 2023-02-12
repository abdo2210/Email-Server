package web.lab.email.database;

import web.lab.email.model.Email;
import web.lab.email.model.User;

import java.util.*;

public class DataCreator {

    // facade dp, to interact with the entire database classes

    //main properties of the class
    private DatabaseHandler dbHandler;

    //****************************************************************************************
    //names of tables and columns

    public static final String USERS_TABLE = "usersTable";
    public static final String EMAILS_COLUMN = "email";
    public static final String FIRST_NAME_COLUMN = "firstName";
    public static final String LAST_NAME_COLUMN = "lastName";
    public static final String JOIN_DATE_COLUMN = "joinDate";
    public static final String PASSWORD_COLUMN = "password";
    public static final List<String> USER_TABLE_COLUMNS = List.of(
            EMAILS_COLUMN,
            FIRST_NAME_COLUMN,
            LAST_NAME_COLUMN,
            JOIN_DATE_COLUMN,
            PASSWORD_COLUMN
    );

    //**********************************************************************************
    public static final String EMAILS_TABLE = "emailsTable";
    public static final String SENDER_COLUMN = "sender";
    public static final String RECEIVER_COLUMN = "receiver";
    public static final String CONTEXT_COLUMN = "context";
    public static final String DATE_COLUMN = "date";
    public static final String SUBJECT_COLUMN = "subject";
    public static final String SENT_DELETE_COLUMN = "sentDelete";
    public static final String REC_DELETE_COLUMN = "recDelete";
    public static final String PRIORITY_COLUMN = "priority";
    public static final String DRAFT_COLUMN = "draft";
    public static final String FOLDER_SENDER_COLUMN = "folderSender";
    public static final String FOLDER_REC_COLUMN="folderRec";
    public static final String FIRST_LINK_COLUMN="firstLink";
    public static final String SECOND_LINK_COLUMN="secondLink";
    public static final String THIRD_LINK_COLUMN="thirdLink";
    public static final String FOURTH_LINK_COLUMN="fourthLink";
    public static final String FIFTH_LINK_COLUMN="fifthLink";
    public static final String SIXTH_LINK_COLUMN="sixthLink";

    public static final String NO = "No";
    public static final String YES = "Yes";
    public static final String SENDER = "sender";
    public static final String RECEIVER="receiver";
    public static final List<String> EMAILS_TABLE_COLUMNS = List.of(
            SENDER_COLUMN,
            RECEIVER_COLUMN,
            CONTEXT_COLUMN,
            DATE_COLUMN,
            SUBJECT_COLUMN,
            SENT_DELETE_COLUMN,
            REC_DELETE_COLUMN,
            PRIORITY_COLUMN,
            DRAFT_COLUMN,
            FOLDER_SENDER_COLUMN,
            FOLDER_REC_COLUMN,
            FIRST_LINK_COLUMN,
            SECOND_LINK_COLUMN,
            THIRD_LINK_COLUMN,
            FOURTH_LINK_COLUMN,
            FIFTH_LINK_COLUMN,
            SIXTH_LINK_COLUMN
            );

    //*****************************************************************************************
    public static final String CONTACTS_TABLE = "contactsTable";
    public static final String CONTACT_OWNER_COLUMN = "contactOwner";
    public static final String CONTACT_NAME_COLUMN = "name";
    public static final String EMAIL_1_COLUMN = "email1";
    public static final String EMAIL_2_COLUMN = "email2";
    public static final String EMAIL_3_COLUMN = "email3";
    public static final String EMAIL_4_COLUMN = "email4";
    public static final String EMAIL_5_COLUMN = "email5";
    public static final List<String> CONTACTS_TABLE_COLUMNS = List.of(
            CONTACT_NAME_COLUMN,
            CONTACT_OWNER_COLUMN,
            EMAIL_1_COLUMN,
            EMAIL_2_COLUMN,
            EMAIL_3_COLUMN,
            EMAIL_4_COLUMN,
            EMAIL_5_COLUMN
    );

    //**************************************************************************
    //constructor
    public DataCreator(){
        this.dbHandler = new DatabaseHandler();
        //loads tables from json files, or creates json files first if they do not exist

        //users
        try{
            this.dbHandler.loadTable(USERS_TABLE);
        }catch (Exception e){
            this.dbHandler.createTable(USERS_TABLE, USER_TABLE_COLUMNS);
        }

        //emails
        try{
            this.dbHandler.loadTable(EMAILS_TABLE);
        }catch (Exception e){
            this.dbHandler.createTable(EMAILS_TABLE, EMAILS_TABLE_COLUMNS);
        }

        //contacts
        try{
            this.dbHandler.loadTable(CONTACTS_TABLE);
        }catch(Exception e){
            this.dbHandler.createTable(CONTACTS_TABLE, CONTACTS_TABLE_COLUMNS);
        }
    }

    //*********************************************************************************************
    //methods

    public void addNewUser(User user) throws NoSuchFieldException, IllegalAccessException {
        this.dbHandler.addRow(USERS_TABLE, user);
    }

    public void addNewEmail(Email email) throws NoSuchFieldException, IllegalAccessException {
        //Email message
        this.dbHandler.addRow(EMAILS_TABLE, email);
    }

    public void addNewContact(Contact contact) throws NoSuchFieldException, IllegalAccessException {
        this.dbHandler.addRow(CONTACTS_TABLE, contact);
    }
    //.....

    public User getUserByEmail(String email){

        //returns null if user is not registered!!!!!!

        List<HashMap<String, String>> userList = this.dbHandler.filterTable(USERS_TABLE, new HashMap<String, String>(){{
            put(EMAILS_COLUMN, email);
        }});

        if(userList.isEmpty()){
            return null;
        }

        HashMap<String, String> userData = userList.get(0);

        return new User(
                userData.get(EMAILS_COLUMN),
                userData.get(FIRST_NAME_COLUMN),
                userData.get(LAST_NAME_COLUMN),
                userData.get(JOIN_DATE_COLUMN),
                userData.get(PASSWORD_COLUMN)

        );
    }

    public String getPassword(String email){
        //returns null if user is not registered!!!!!!

        List<HashMap<String, String>> userList = this.dbHandler.filterTable(USERS_TABLE, new HashMap<String, String>(){{
            put(EMAILS_COLUMN, email);
        }});

        if(userList.isEmpty()){
            return null;
        }
        HashMap<String, String> userData = userList.get(0);

        return userData.get(PASSWORD_COLUMN);
    }
    public List<Email> getRecEmailsOfUserDelete(String userEmailAddress){
        // gets messages sent by a user (excluding drafts, deleted messages)
        List<HashMap<String,String>> emailsData =  this.dbHandler.filterTable(EMAILS_TABLE,
                new HashMap<>(){{
                    put(RECEIVER_COLUMN, userEmailAddress);
                    put(DRAFT_COLUMN, NO);
                }});
        List<Email> emailsList = new ArrayList<>();

        emailsData.forEach((item)->{
            if(!item.get(REC_DELETE_COLUMN).equals("No") && !item.get(REC_DELETE_COLUMN).equals("DELETED")) {
                Email email = new Email(
                        item.get(SENDER_COLUMN),
                        item.get(RECEIVER_COLUMN),
                        item.get(CONTEXT_COLUMN),
                        item.get(DATE_COLUMN),
                        item.get(SUBJECT_COLUMN),
                        item.get(DRAFT_COLUMN),
                        item.get(PRIORITY_COLUMN),
                        item.get(FOLDER_REC_COLUMN),
                        item.get(FOLDER_SENDER_COLUMN),
                        item.get(FIRST_LINK_COLUMN),
                        item.get(SECOND_LINK_COLUMN),
                        item.get(THIRD_LINK_COLUMN),
                        item.get(FOURTH_LINK_COLUMN),
                        item.get(FIFTH_LINK_COLUMN),
                        item.get(SIXTH_LINK_COLUMN)
                );
                email.setRecDelete(item.get(REC_DELETE_COLUMN));
                email.setSentDelete(item.get(SENT_DELETE_COLUMN));
                emailsList.add(email);
            }
        });
        return emailsList;
    }
    public List<Email> getSentEmailsOfUserDelete(String userEmailAddress){
        // gets messages sent by a user (excluding drafts, deleted messages)
        List<HashMap<String,String>> emailsData =  this.dbHandler.filterTable(EMAILS_TABLE,
                new HashMap<>(){{
                    put(SENDER_COLUMN, userEmailAddress);
                    put(DRAFT_COLUMN, NO);
                }});
        List<Email> emailsList = new ArrayList<>();

        emailsData.forEach((item)->{
            if(!item.get(SENT_DELETE_COLUMN).equals("No") && !item.get(SENT_DELETE_COLUMN).equals("DELETED")) {
                Email email = new Email(
                        item.get(SENDER_COLUMN),
                        item.get(RECEIVER_COLUMN),
                        item.get(CONTEXT_COLUMN),
                        item.get(DATE_COLUMN),
                        item.get(SUBJECT_COLUMN),
                        item.get(DRAFT_COLUMN),
                        item.get(PRIORITY_COLUMN),
                        item.get(FOLDER_REC_COLUMN),
                        item.get(FOLDER_SENDER_COLUMN),
                        item.get(FIRST_LINK_COLUMN),
                        item.get(SECOND_LINK_COLUMN),
                        item.get(THIRD_LINK_COLUMN),
                        item.get(FOURTH_LINK_COLUMN),
                        item.get(FIFTH_LINK_COLUMN),
                        item.get(SIXTH_LINK_COLUMN)
                );
                email.setSentDelete(item.get(SENT_DELETE_COLUMN));
                email.setRecDelete(item.get(REC_DELETE_COLUMN));
                emailsList.add(email);
            }
        });
        return emailsList;
    }


    public List<Email> getSentEmailsOfUser(String userEmailAddress){
        // gets messages sent by a user (excluding drafts, deleted messages)
        List<HashMap<String,String>> emailsData =  this.dbHandler.filterTable(EMAILS_TABLE,
                new HashMap<>(){{
                    put(SENDER_COLUMN, userEmailAddress);
                    put(DRAFT_COLUMN, NO);
                    put(SENT_DELETE_COLUMN, NO);
                }});

        List<Email> emailsList = new ArrayList<>();
        emailsData.forEach((item)->{
            Email email = new Email(
                    item.get(SENDER_COLUMN),
                    item.get(RECEIVER_COLUMN),
                    item.get(CONTEXT_COLUMN),
                    item.get(DATE_COLUMN),
                    item.get(SUBJECT_COLUMN),
                    item.get(DRAFT_COLUMN),
                    item.get(PRIORITY_COLUMN),
                    item.get(FOLDER_REC_COLUMN),
                    item.get(FOLDER_SENDER_COLUMN),
                    item.get(FIRST_LINK_COLUMN),
                    item.get(SECOND_LINK_COLUMN),
                    item.get(THIRD_LINK_COLUMN),
                    item.get(FOURTH_LINK_COLUMN),
                    item.get(FIFTH_LINK_COLUMN),
                    item.get(SIXTH_LINK_COLUMN)
            );
            emailsList.add(email);
        });
        return emailsList;
    }


    public List<Email> getReceivedEmailsOfUser(String userEmailAddress){
        // gets messages received by a user (excluding drafts, deleted messages)
        List<HashMap<String,String>> emailsData =  this.dbHandler.filterTable(EMAILS_TABLE,
                new HashMap<>(){{
                    put(RECEIVER_COLUMN, userEmailAddress);
                    put(DRAFT_COLUMN, NO);
                    put(REC_DELETE_COLUMN, NO);
                }});

        List<Email> emailsList = new ArrayList<>();
        emailsData.forEach((item)->{
            Email email = new Email(
                    item.get(SENDER_COLUMN),
                    item.get(RECEIVER_COLUMN),
                    item.get(CONTEXT_COLUMN),
                    item.get(DATE_COLUMN),
                    item.get(SUBJECT_COLUMN),
                    item.get(DRAFT_COLUMN),
                    item.get(PRIORITY_COLUMN),
                    item.get(FOLDER_REC_COLUMN),
                    item.get(FOLDER_SENDER_COLUMN),
                    item.get(FIRST_LINK_COLUMN),
                    item.get(SECOND_LINK_COLUMN),
                    item.get(THIRD_LINK_COLUMN),
                    item.get(FOURTH_LINK_COLUMN),
                    item.get(FIFTH_LINK_COLUMN),
                    item.get(SIXTH_LINK_COLUMN)
            );
            emailsList.add(email);
        });
        return emailsList;
    }

    public List<Email> getDraftEmailsOfUser(String userEmailAddress){
        // gets messages in drafts folder of user (excluding deleted messages)
        List<HashMap<String,String>> emailsData =  this.dbHandler.filterTable(EMAILS_TABLE,
                new HashMap<>(){{
                    put(SENDER_COLUMN, userEmailAddress);
                    put(DRAFT_COLUMN, YES);
                    put(SENT_DELETE_COLUMN, NO);
                }});

        List<Email> emailsList = new ArrayList<>();
        emailsData.forEach((item)->{
            Email email = new Email(
                    item.get(SENDER_COLUMN),
                    item.get(RECEIVER_COLUMN),
                    item.get(CONTEXT_COLUMN),
                    item.get(DATE_COLUMN),
                    item.get(SUBJECT_COLUMN),
                    item.get(DRAFT_COLUMN),
                    item.get(PRIORITY_COLUMN),
                    item.get(FOLDER_REC_COLUMN),
                    item.get(FOLDER_SENDER_COLUMN),
                    item.get(FIRST_LINK_COLUMN),
                    item.get(SECOND_LINK_COLUMN),
                    item.get(THIRD_LINK_COLUMN),
                    item.get(FOURTH_LINK_COLUMN),
                    item.get(FIFTH_LINK_COLUMN),
                    item.get(SIXTH_LINK_COLUMN)
            );
            emailsList.add(email);
        });
        return emailsList;
    }

    public List<Contact> getUserContacts(String userEmail){
        //returns empty list if user is not registered, or has no contacts
        //this method will be modified after changing Contact class
        List<HashMap<String, String>> contactsDataList = this.dbHandler.filterTable(CONTACTS_TABLE,
                new HashMap<>(){{
                    put(CONTACT_OWNER_COLUMN, userEmail);
                }});

        List<Contact> contacts = new ArrayList<>();
        contactsDataList.forEach((item)->{
            contacts.add(new Contact(
                    item.get(CONTACT_NAME_COLUMN),
                    item.get(CONTACT_OWNER_COLUMN),
                    item.get(EMAIL_1_COLUMN),
                    item.get(EMAIL_2_COLUMN),
                    item.get(EMAIL_3_COLUMN),
                    item.get(EMAIL_4_COLUMN),
                    item.get(EMAIL_5_COLUMN)
            ));
        });

        return contacts;
    }

    public Email getMessage(String senderEmail, String receiverEmail, String context, String subject, String date){
        List<HashMap<String, String>> emailDataList = this.dbHandler.filterTable(EMAILS_TABLE,
                new HashMap<>(){{
                    put(SENDER_COLUMN, senderEmail);
                    put(RECEIVER_COLUMN, receiverEmail);
                    put(CONTEXT_COLUMN, context);
                    put(SUBJECT_COLUMN, subject);
                    put(DATE_COLUMN, date);
                }});

        if(emailDataList.isEmpty()){
            return null;
        }
        HashMap<String, String> emailData = emailDataList.get(0);
        return new Email(
                emailData.get(SENDER_COLUMN),
                emailData.get(RECEIVER_COLUMN),
                emailData.get(CONTEXT_COLUMN),
                emailData.get(DATE_COLUMN),
                emailData.get(SUBJECT_COLUMN),
                emailData.get(DRAFT_COLUMN),
                emailData.get(PRIORITY_COLUMN),
                emailData.get(FOLDER_REC_COLUMN),
                emailData.get(FOLDER_SENDER_COLUMN),
                emailData.get(FIRST_LINK_COLUMN),
                emailData.get(SECOND_LINK_COLUMN),
                emailData.get(THIRD_LINK_COLUMN),
                emailData.get(FOURTH_LINK_COLUMN),
                emailData.get(FIFTH_LINK_COLUMN),
                emailData.get(SIXTH_LINK_COLUMN)
        );
    }

    public void deleteContact(String userEmail, String contactName){
        this.dbHandler.deleteRows(CONTACTS_TABLE,
                new HashMap<>(){{
                    put(CONTACT_OWNER_COLUMN, userEmail);
                    put(CONTACT_NAME_COLUMN, contactName);
                }});
    }
    public void deleteEmailMessage(Email message){
        this.dbHandler.deleteRows(EMAILS_TABLE,
                new HashMap<>(){{
                    put(SENDER_COLUMN, message.getSender());
                    put(RECEIVER_COLUMN, message.getReceiver());
                    put(CONTEXT_COLUMN, message.getContext());
                    put(DATE_COLUMN, message.getDate());
                    put(DRAFT_COLUMN, message.getDraft());
                    put(PRIORITY_COLUMN, message.getPriority());
                    put(SUBJECT_COLUMN, message.getSubject());
                    put(SENT_DELETE_COLUMN, message.getSentDelete());
                    put(REC_DELETE_COLUMN, message.getRecDelete());
                }});
    }

    public void updateMessage(Email message, String targetField, String newValue){
        HashMap<String,String> conditions = new HashMap<>(){{
            put(SENDER_COLUMN, message.getSender());
            put(RECEIVER_COLUMN, message.getReceiver());
            put(CONTEXT_COLUMN, message.getContext());
            put(DATE_COLUMN, message.getDate());
            put(SUBJECT_COLUMN, message.getSubject());
        }};
        this.dbHandler.updateRow(EMAILS_TABLE, targetField, conditions, newValue);
    }

    public void updateContact(Contact contact, String targetField, String newValue){
        HashMap<String,String> conditions = new HashMap<>(){{
            put(CONTACT_OWNER_COLUMN, contact.getContactOwner());
            put(CONTACT_NAME_COLUMN, contact.getName());
        }};
        this.dbHandler.updateRow(CONTACTS_TABLE, targetField, conditions, newValue);
    }

    public void updateUser(User user, String targetField, String newValue){
        this.dbHandler.updateRow(USERS_TABLE, targetField, new HashMap<>(){{
            put(EMAILS_COLUMN, user.getEmail());
        }}, newValue);}

    public List<String> getAllUsersEmailAccounts(){
        List<HashMap<String,String>> allData = this.dbHandler.getAllRows(USERS_TABLE);
        List<String> emailsList = new ArrayList<>();
        allData.forEach((item)->{
            emailsList.add(item.get(EMAILS_COLUMN));
        });
        return emailsList;}

    public Set<String> getUserFolders(String email){
        Set<String> userFolders = new HashSet<>();
            List<Email> userMails = getSentEmailsOfUser(email);
            for(Email mail: userMails){
                if(!mail.getFolderSender().equals("") && !userFolders.contains(mail.getFolderSender()))
                    userFolders.add(mail.getFolderSender());
            List<Email> userMails2 = getReceivedEmailsOfUser(email);
            for(Email mail2: userMails2){
                if(!mail2.getFolderRec().equals("") && !userFolders.contains(mail.getFolderRec()))
                    userFolders.add(mail2.getFolderRec());
            }
        }
        return (userFolders);
    }

    public void setFolderOfEmail(Email emailMessage, String newFolderName, String type){
        if(type.equals(SENDER)){
            updateMessage(emailMessage, FOLDER_SENDER_COLUMN, newFolderName);
        }else{
            updateMessage(emailMessage, FOLDER_REC_COLUMN, newFolderName);
        }
    }

    public LinkedList<Email> getMessagesInFolder(String userMail, String folderName){
        List<Email> userSentMails = getSentEmailsOfUser(userMail);
        List<Email> userRecMails = getReceivedEmailsOfUser(userMail);
        LinkedList<Email> mailsInFolder = new LinkedList<>();
        System.out.println("userSentMails"+userSentMails);
        System.out.println("userRecMails"+userRecMails);
        userSentMails.forEach((mail)->{
            if(mail.getFolderSender().equals(folderName)){
                mailsInFolder.add(mail);
            }
        });
        System.out.println("folderName"+folderName);
        userRecMails.forEach((mail)->{
            System.out.println(mail.getFolderRec() + "HIIII");
            if(mail.getFolderRec().equals(folderName)){
                System.out.println("HIIII");
                mailsInFolder.add(mail);
            }
        });
        System.out.println("mailsInFolder"+mailsInFolder);
        return mailsInFolder;
}



}