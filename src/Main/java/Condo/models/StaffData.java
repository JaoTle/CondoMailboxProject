package Condo.models;

import java.util.ArrayList;

public class StaffData {
    private ArrayList<Staff> stData;
    private  Staff staff;
    public StaffData() {
        stData = new ArrayList<>();}
    public void addStaffAccount(Staff account) { stData.add(account);}
    public boolean checkAccount(String username,String password){
        for (Staff st : stData) {
            if (st.getUser().equals(username) && st.getPassword().equals(password)) {
                staff = st;
                return true;
            }
        }
        staff = null;
        return false;
    }

    public boolean checkUser(String user){
        for (Staff staff : stData){
            if(staff.getUser().equals(user)) return false;
        }
        return true;
    }

    public Staff getStaffAccount() {return staff;}

    public ArrayList<Staff> toList() { return stData; }

    @Override
    public String toString() {
        return "Account : " + stData.toString();
    }
}
