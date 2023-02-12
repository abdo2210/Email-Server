package web.lab.email.model;


public class Email implements Comparable<Email> {
    private String sender;
    private String receiver;
    private String context;
    private String date;
    private String subject;
    private String sentDelete = "No";
    private String recDelete = "No";
    private String priority = "0";
    private String draft = "No";
    private String folderRec = "";
    private String folderSender = "";
    private String  firstLink = "";
    private String  secondLink = "";
    private String  thirdLink = "";
    private String  fourthLink = "";
    private String  fifthLink = "";
    private String  sixthLink = "";
    public Email(String sender, String receiver, String context, String date, String subject, String draft, String priority, String folderRec, String folderSender
                 , String firstLink, String secondLink, String thirdLink, String fourthLink, String fifthLink, String sixthLink
    ) {
        this.sender = sender;
        this.receiver = receiver;
        this.context = context;
        this.date = date;
        this.subject = subject;
        this.draft = draft;
        this.priority=priority;
        this.folderSender = folderSender;
        this.folderRec =folderRec;
        this.firstLink = firstLink;
        this.secondLink = secondLink;
        this.thirdLink = thirdLink;
        this.fourthLink = fourthLink;
        this.fifthLink = fifthLink;
        this.sixthLink = sixthLink;
    }

    public Email() {

    }
    public String getSentDelete() {
        return sentDelete;
    }

    public void setSentDelete(String sentDelete) {
        this.sentDelete = sentDelete;
    }

    public String getRecDelete() {
        return recDelete;
    }

    public void setRecDelete(String recDelete) {
        this.recDelete = recDelete;
    }
    public String getSender() {
        return sender;
    }

    public void setSender(String sender) {
        this.sender = sender;
    }

    public String getReceiver() {
        return receiver;
    }

    public void setReceiver(String receiver) {
        this.receiver = receiver;
    }

    public String getContext() {
        return context;
    }

    public void setContext(String context) {
        this.context = context;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }
    public String getPriority() {
        return priority;
    }
    public String getDraft() {
        return draft;
    }

    public void setDraft(String draft) {
        this.draft = draft;
    }

    @Override
    public int compareTo(Email o) {
        return getPriority().compareTo(o.getPriority());
    }
    public String getFolderRec() {
        return folderRec;
    }

    public void setFolderRec(String folderRec) {
        this.folderRec = folderRec;
    }

    public String getFolderSender() {
        return folderSender;
    }

    public void setFolderSender(String folderSender) {
        this.folderSender = folderSender;
    }
    public String getFirstLink() {
        return firstLink;
    }

    public void setFirstLink(String firstLink) {
        this.firstLink = firstLink;
    }

    public String getSecondLink() {
        return secondLink;
    }

    public void setSecondLink(String secondLink) {
        this.secondLink = secondLink;
    }

    public String getThirdLink() {
        return thirdLink;
    }

    public void setThirdLink(String thirdLink) {
        this.thirdLink = thirdLink;
    }

    public String getFourthLink() {
        return fourthLink;
    }

    public void setFourthLink(String fourthLink) {
        this.fourthLink = fourthLink;
    }

    public String getFifthLink() {
        return fifthLink;
    }

    public void setFifthLink(String fifthLink) {
        this.fifthLink = fifthLink;
    }

    public String getSixthLink() {
        return sixthLink;
    }

    public void setSixthLink(String sixthLink) {
        this.sixthLink = sixthLink;
    }

}
