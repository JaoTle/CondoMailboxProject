package Condo.models;

public class Staff extends Account{
    private String name,surName;
    private int age;
    private String time;
    private String imagePath;
    public Staff(String userName ,String passWord, String name ,String surName ,int age,String time,String imagePath) {
        super(userName,passWord);
        this.age = age;
        this.name = name;
        this.surName = surName;
        this.time = time;
        this.imagePath = imagePath;
    }
    public boolean checkData(){
        if(getUser().isEmpty() || getPassword().isEmpty() || name.isEmpty() || surName.isEmpty()  || imagePath.isEmpty()) return false;
        return true;
    }
    public int checkField(String confirm){
        if(!getUser().isEmpty() && !getPassword().isEmpty() && !name.isEmpty() && !surName.isEmpty() && !(age==0) && !confirm.isEmpty()){
            if(!confirm.equals(getPassword())) return 2;
            else return 1;
        }
        else return 0;
    }
    public String getName() {
        return name;
    }

    public String getSurName() {
        return surName;
    }

    public int getAge() {
        return age;
    }

    public String getTime() {
        return time;
    }

    public String getImagePath() {
        return imagePath;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public void setImagePath(String imagePath) {
        this.imagePath = imagePath;
    }

}
