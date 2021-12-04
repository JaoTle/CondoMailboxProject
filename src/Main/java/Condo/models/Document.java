package Condo.models;

public class Document extends Mail{
    private String type;
    public Document(Person sender, Receiver receiver, String width, String height,String type,String time,String st,String path) {
        super(sender, receiver, width, height,time,st,path);
        this.type = type;
        mailtype = "Document";
    }

    @Override
    public void setInform() {
        inform = "Size : " + getWidth() + " x " + getLength() + "\n"
        + "Priority : ";
        if(type.equals("quick")) inform += "ด่วน";
        else if(type.equals("secret")) inform += "ลับ";
        else if(type.equals("normal")) inform += "ธรรมดา";
    }

    @Override
    public boolean checkData() {
        if(getLength().equals(null)) return false;
        if(getWidth().equals(null)) return false;
        if(type.equals(null)) return  false;
        return true;
    }


    public String getType() {
        return type;
    }

}
