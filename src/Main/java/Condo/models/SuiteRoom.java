package Condo.models;

public class SuiteRoom extends Room{
    private String roomer3 , roomer4;
    private int amount;
    public SuiteRoom(String roomnum, String floor,String type, String building, String roomer1, String roomer2,String roomer3,String roomer4) {
        super(roomnum, floor,type, building, roomer1, roomer2);
        this.roomer3 = roomer3;
        this.roomer4 = roomer4;
    }

    //set Text to show in tableview
    @Override
    public void setStatus() {
        if(!getRoomer1().equals("null")) amount += 1;
        if(!getRoomer2().equals("null")) amount += 1;
        if(!getRoomer3().equals("null")) amount += 1;
        if(!getRoomer4().equals("null")) amount += 1;
        if(amount == 1) status = amount + " Roomer" ;
        else if(amount > 1)status = amount + " Roomers";
    }

    @Override
    public boolean checkRoomers(String name) {
        if(name.equals(getRoomer1())) return true;
        if(name.equals(getRoomer2())) return true;
        if(name.equals(getRoomer3())) return true;
        if(name.equals(getRoomer4())) return true;
        return  false;
    }

    public String getRoomer3() {
        return roomer3;
    }

    public String getRoomer4() {
        return roomer4;
    }

    public boolean setRoomer3(String roomer3) {
        if(this.roomer3.equals("null")){
            if(!roomer3.isEmpty()){
                this.roomer3 = roomer3;
                return true;
            }
        }
        else if(!this.roomer3.equals("null")) return true;
        return false;
    }

    public boolean setRoomer4(String roomer4) {
        if(this.roomer4.equals("null")){
            if(!roomer4.isEmpty()){
                this.roomer4 = roomer4;
                return true;
            }
        }
        else if(!this.roomer4.equals("null")) return true;
        return false;
    }
}
