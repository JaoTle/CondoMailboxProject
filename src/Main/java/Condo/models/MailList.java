package Condo.models;

import java.util.ArrayList;

public class MailList {

    private ArrayList<Mail> mailList;
    private ArrayList<Mail> acceptedMail;
    private Mail mails;

    public MailList(){
        mailList = new ArrayList<>();
        acceptedMail = new ArrayList<>();
    }
    public void addAcceptedMail(Mail mail){ acceptedMail.add(mail);}
    public void addMailbox(Mail mail){ mailList.add(mail); }
    public void deleteMail(Mail mail) { mailList.remove(mail);}
    public Mail getMailInform(){ return mails; }
    public void setMails(){
        for(Mail mail:mailList){
            mail.setSomeData();
        }
    }
    public void setAcceptedMail(){
        for(Mail mail:acceptedMail){
            mail.setSomeData();
        }
    }
    public ArrayList<Mail> toList(){ return  mailList;}

    public ArrayList<Mail> getAcceptedMail() {
        return acceptedMail;
    }
}
