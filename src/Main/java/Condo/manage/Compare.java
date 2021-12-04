package Condo.manage;

import Condo.models.*;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Comparator;

public class Compare {
    public void staffLoginCompare(StaffData stafflist){
        stafflist.toList().sort(new Comparator<Staff>() {
            DateFormat format = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");

            public int compare(Staff staff1, Staff staff2) {
                try {
                    if(staff2.getTime().equals("Not yet login") && !staff1.getTime().equals("Not yet login")) return -1;
                    else if (!staff2.getTime().equals("Not yet login") && staff1.getTime().equals("Not yet login")) return 1;
                    else if((staff2.getTime().equals("Not yet login") && staff1.getTime().equals("Not yet login"))) return 0;
                    return format.parse(staff2.getTime()).compareTo(format.parse(staff1.getTime()));

                } catch (ParseException excep) {
                    throw new IllegalArgumentException(excep);
                }
            }
        });
    }

    public void mailReceiveCompare(MailList mails){
        mails.toList().sort(new Comparator<Mail>() {
            DateFormat format = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");

            public int compare(Mail mail1, Mail mail2) {
                try {
                    return format.parse(mail2.getTime()).compareTo(format.parse(mail1.getTime()));

                } catch (ParseException excep) {
                    throw new IllegalArgumentException(excep);
                }
            }
        });
    }

    public void mailAcceptedCompare(MailList mails){
        mails.getAcceptedMail().sort(new Comparator<Mail>() {
            DateFormat format = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");

            public int compare(Mail mail1, Mail mail2) {
                try {
                    return format.parse(mail2.getTime()).compareTo(format.parse(mail1.getTime()));

                } catch (ParseException excep) {
                    throw new IllegalArgumentException(excep);
                }
            }
        });
    }
    //** แก้ compareTo ให้เรียงชั้นสองหลักกับตึกได้
    public void roomNumCompare(RoomData rooms){
        rooms.toList().sort(new Comparator<Room>(){
           public int compare(Room room1,Room room2){
               if(room1.getRoomnum().substring(0,1).equals("A") && room2.getRoomnum().substring(0,1).equals("A")){
                   if(room1.getRoomnum().substring(1).length() == 3 && room2.getRoomnum().substring(1).length() == 4) return -1;
                   else if(room1.getRoomnum().substring(1).length() == 4 && room2.getRoomnum().substring(1).length() == 3) return 1;
                   return room1.getRoomnum().compareTo(room2.getRoomnum());
               }
               else if(room1.getRoomnum().substring(0,1).equals("B") && room2.getRoomnum().substring(0,1).equals("B")){
                   if(room1.getRoomnum().substring(1).length() == 3 && room2.getRoomnum().substring(1).length() == 4) return -1;
                   else if(room1.getRoomnum().substring(1).length() == 4 && room2.getRoomnum().substring(1).length() == 3) return 1;
                   return room1.getRoomnum().compareTo(room2.getRoomnum());
               }
               return room1.getRoomnum().compareTo(room2.getRoomnum());
           }
        });
    }
}
