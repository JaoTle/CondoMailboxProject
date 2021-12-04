package Condo.models;

public class Package extends Mail {
    private String height,transport,track;
    public Package(Person sender, Receiver receiver, String width, String length,String height,String transport,String track,String time,String st,String path) {
        super(sender, receiver, width, length,time,st,path);
        this.height = height;
        this.transport = transport;
        this.track = track;
        mailtype = "Package";
    }

    @Override
    public void setInform() {
        inform = "Size : " + getWidth() + " x " + getLength() + " x " + getHeight() + "\n"
                + "Transport : " + transport + "\nTracking : " + track;
    }

    @Override
    public boolean checkData() {
        if(getLength().equals(null)) return false;
        if(getWidth().equals(null)) return false;
        if(height.equals(null)) return false;
        if(track.equals(null)) return false;
        if(transport.equals(null)) return false;
        return true;
    }

    public String getHeight() {
        return height;
    }

    public String getTransport() {
        return transport;
    }

    public String getTrack() {
        return track;
    }
}
