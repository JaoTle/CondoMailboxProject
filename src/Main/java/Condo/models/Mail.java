package Condo.models;

import Condo.MainCondo;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.io.File;
import java.net.URISyntaxException;

public class Mail {
    private String width,length,status;
    private Person sender;
    private Receiver receiver;
    private String time,imgPath;
    String staff_receive,mailtype,name,room,roomreceive,inform,staffmanage;

    //default constructor
    public Mail(Person sender ,Receiver receiver ,String width ,String length,String time,String st,String path){
        this.length = length;
        this.width = width;
        this.receiver = receiver;
        this.sender = sender;
        this.time = time;
        imgPath = path;
        status = "Not accepted";
        mailtype = "Letter";
        staff_receive = st;
        roomreceive = "";
        inform = "";
    }

    //mail accepted constructor
    public Mail(Receiver receiver,String time,String st,String roomreceive,String staff,String path){
        this.roomreceive = roomreceive;
        this.receiver = receiver;
        this.time = time;
        staff_receive = st;
        imgPath = path;
        status = "Accepted";
        staffmanage = staff;
    }

    public void setStaffmanage(String staffmanage) {
        this.staffmanage = staffmanage;
    }

    public String getStaffmanage() {
        return staffmanage;
    }

    public void setInform() {
        inform = "Size : " + width + " x " + length;
    }

    public void setStaff_receive(String staff_receive) {
        this.staff_receive = staff_receive;
    }

    public String getInform() {
        return inform;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public void setRoomreceive(String roomreceive) {
        this.roomreceive = roomreceive;
    }

    public String getStaff_receive() {
        return staff_receive;
    }

    public String getRoomreceive() {
        return roomreceive;
    }

    public void setSomeData(){
        name = receiver.getName() + " " + receiver.getSurname();
        room = receiver.getRoomnum();
    }
    public boolean checkData(){
        if(length.equals(null)) return false;
        if(width.equals(null)) return false;
        return true;
    }

    public String getImgPath() {
        return imgPath;
    }

    public String getTime() { return time; }

    public String getWidth() {
        return width;
    }

    public String getLength() {
        return length;
    }

    public String getStatus() {
        return status;
    }

    public Person getSender() {
        return sender;
    }

    public Receiver getReceiver() {
        return receiver;
    }

    public String getMailtype() {
        return mailtype;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getName() {
        return name;
    }

    public String getRoom() {
        return room;
    }

}
