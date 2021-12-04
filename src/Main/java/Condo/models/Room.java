package Condo.models;

public class Room {
    //deluxe
    private String roomtype , roomnum , building,floor;
    private String roomer1,roomer2;
    String status;
    private int amount;
    public Room(String roomnum,String floor ,String type,String building,String roomer1,String roomer2){
        roomtype = type;
        this.roomnum = roomnum;
        this.floor = floor;
        this.building = building;
        this.roomer1 = roomer1;
        this.roomer2 = roomer2;
        status = "Empty";
    }
    //set Text to show in tableview
    public void setStatus() {
        if(!getRoomer1().equals("null")) amount += 1;
        if(!getRoomer2().equals("null")) amount += 1;
        if(amount == 1) status = amount + " Roomer";
        else if(amount > 1) status = amount + " Roomers";
    }

    public String getStatus() {
        return status;
    }

    public String getRoomtype() {
        return roomtype;
    }

    public String getRoomnum() {
        return roomnum;
    }

    public String getFloor() {
        return floor;
    }

    public String getBuilding() {
        return building;
    }

    public String getRoomer1() {
        return roomer1;
    }

    public String getRoomer2() {
        return roomer2;
    }

    public boolean checkRoomers(String name){
        if(name.equals(getRoomer1())) return true;
        if(name.equals(getRoomer2())) return true;
        return false;
    }


    public boolean setRoomer1(String roomer1) {
        if(this.roomer1.equals("null")){
            if(!roomer1.isEmpty()){
                this.roomer1 = roomer1;
                return true;
            }
        }
        else if(!this.roomer1.equals("null")) return true;
        return false;
    }

    public boolean setRoomer2(String roomer2) {
        if(this.roomer2.equals("null")){
            if(!roomer2.isEmpty()){
                this.roomer2 = roomer2;
                return true;
            }
        }
        else if(!this.roomer2.equals("null")) return true;
        return false;
    }

    @Override
    public String toString() {
        return "Room{" +
                "roomtype='" + roomtype + '\'' +
                ", roomnum='" + roomnum + '\'' +
                ", building='" + building + '\'' +
                ", roomer1='" + roomer1 + '\'' +
                ", roomer2='" + roomer2 + '\'' +
                ", floor=" + floor +
                '}';
    }
}
