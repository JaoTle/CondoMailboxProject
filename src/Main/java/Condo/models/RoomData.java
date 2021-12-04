package Condo.models;

import java.util.ArrayList;

public class RoomData {

    private ArrayList<Room> roomDt;
    private Room rooms;

    public RoomData (){
        roomDt = new ArrayList<>();
    }
    public void addRoomData (Room room) {roomDt.add(room);}

    public boolean checkRoomnum(String roomnum){
        for(Room rm : roomDt){
            if(rm.getRoomnum().equalsIgnoreCase(roomnum)) {
                rooms = rm;
                return true;
            }
        }
        rooms = null;
        return false;
    }

    public boolean checkRoom(String roomnum,String floor){
        if(Integer.parseInt(floor) > 9) return false;
        if(checkRoomnum(roomnum)) return false;
        else {
            if(roomnum.substring(1).length() == 3){
                if(Integer.parseInt(roomnum.substring(2)) > 10) return false;
                if(floor.equals(roomnum.substring(1,2))) return true;
            }
            else if(roomnum.substring(1).length() == 4){
                if(Integer.parseInt(roomnum.substring(3)) > 10) return false;
                if(floor.equals(roomnum.substring(1,3))) return true;
            }
            return false;
        }
    }

    public String setRoom(String build ,String floor){
        int i = 0;
        for(Room rm : roomDt){
            if(rm.getBuilding().equals(build) && rm.getFloor().equals(floor)){
                i+=1;
            }
        }
        if(i<10) return floor + "0" + (i+1);
        else if(i==10) return floor + (i+1);
        else return null;
    }
    //set status all room
    public void setStatus(){
        for(Room room : roomDt) room.setStatus();
    }
    public Room getRoomInform(){
        return rooms;
    }

    public ArrayList<Room> toList(){ return roomDt;}

    @Override
    public String toString() {
        return "RoomData{" +
                "roomDt=" + roomDt.toString() +
                '}';
    }
}
