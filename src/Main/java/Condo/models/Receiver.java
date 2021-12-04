package Condo.models;

public class Receiver extends Person {
    private String roomnum;

    public Receiver(String name, String surname, String phone ,String roomnum) {
        super(name, surname, phone);
        this.roomnum = roomnum;
    }

    public boolean checkData(RoomData rooms) {
        for(Room rm : rooms.toList()) {
            if (rm.getRoomnum().equalsIgnoreCase(roomnum)) {
                    if (getPhone().equals(null)) return false;
                    if (getName().equals(null)) return false;
                    if (getSurname().equals(null)) return false;
                    if (rm.getRoomer1().equals("null")) return false;
                    return true;
            }
        }
        return false;
    }

    public String getRoomnum() {
        return roomnum;
    }

    @Override
    public String toString() {
        return getName() + " " + getSurname();
    }
}
