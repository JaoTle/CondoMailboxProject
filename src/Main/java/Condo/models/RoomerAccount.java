package Condo.models;

public class RoomerAccount extends Account {
    private String name;
    private String roomnum;

    public RoomerAccount(String user, String password,String name,String roomnum) {
        super(user, password);
        this.name = name;
        this.roomnum = roomnum;
    }

    public String getName() {
        return name;
    }

    public String getRoomnum() {
        return roomnum;
    }
}
