package Condo.models;

import java.util.ArrayList;

public class RoomersAccountData {
    private ArrayList<RoomerAccount> roomersData;
    private  RoomerAccount roomers;
    public RoomersAccountData() {
        roomersData = new ArrayList<>();}


    public void addRoomerAccount(RoomerAccount roomer){roomersData.add(roomer);}

    public boolean checkAccount(String username,String password){
        for (RoomerAccount rm : roomersData) {
            if (rm.getUser().equals(username) && rm.getPassword().equals(password)) {
                roomers = rm;
                return true;
            }
        }
        roomers = null;
        return false;
    }


    public RoomerAccount getRoomersAccount() {return roomers;}

    public ArrayList<RoomerAccount> toList() { return roomersData; }
}

