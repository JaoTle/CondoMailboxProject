package Condo.models;

public class Person {

    //Sender
    private String name,surname,phone;
    public Person(String name ,String surname ,String phone){
        this.name = name;
        this.surname = surname;
        this.phone = phone;
    }

    public boolean checkData(){
        if(name.equals(null)) return false;
        if(surname.equals(null)) return false;
        if(phone.equals(null)) return false;
        return true;
    }
    public String getName() {
        return name;
    }

    public String getSurname() {
        return surname;
    }

    public String getPhone() {
        return phone;
    }

    @Override
    public String toString() {
        return "Sender : " + name + " " + surname + " Tel " + phone;
    }
}
