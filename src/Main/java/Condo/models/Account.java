package Condo.models;

public class Account {
    private String user;
    private String password;


    public Account(String user,String password){
        this.user = user;
        this.password = password;
    }

    public String getUser() {
        return user;
    }
    public String getPassword() {
        return password;
    }

    public boolean changePassword(String oldPass ,String newPass , String confirmPass){
        if(!newPass.isEmpty()){
            if(oldPass.equals(password)){
                if(confirmPass.equals(newPass)) {
                    password = newPass;
                    return true;
                }
            }
        }
        return false;
    }
}
