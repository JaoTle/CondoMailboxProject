package Condo.manage;

import Condo.models.RoomerAccount;
import Condo.models.RoomersAccountData;

import java.io.*;

public class RoomerAccountSource {

    private String fileDirectoryName;
    private String fileName;
    private RoomersAccountData roomers;

    public RoomerAccountSource(String fileDirectoryName , String fileName){  //รับ Constructor เป็นชื่อdirectory กับ ชื่อfile
        this.fileDirectoryName = fileDirectoryName;
        this.fileName =fileName;
        checkFileExisted();
    }

    private void checkFileExisted(){
        File file = new File(fileDirectoryName);
        if (!file.exists()) {
            file.mkdirs();
        }
        String filePath = fileDirectoryName + File.separator + "csv" + File.separator + fileName;
        file = new File(filePath);
        if (!file.exists()) {
            try {
                file.createNewFile();
            } catch (IOException e) {
                System.err.println("Can't create " + filePath);
            }
        }
    }

    private void readRoomerData() throws IOException {
        String path = fileDirectoryName + File.separator + "csv" + File.separator + fileName;
        File file = new File(path);
        FileReader fileRead = new FileReader(file);
        BufferedReader read = new BufferedReader(fileRead);
        String text = "";
        while ((text = read.readLine()) != null){
            String[] dataUser = text.split(",");
            RoomerAccount roomer = new RoomerAccount(dataUser[0].trim(),dataUser[1].trim(),dataUser[2].trim(),dataUser[3].trim());
            roomers.addRoomerAccount(roomer);
        }
        read.close();
    }

    public RoomersAccountData getRoomersData(){
        try {
            roomers = new RoomersAccountData(); //เรียก Constructor ของ StaffData ( ArrayList )
            readRoomerData();
        }
        catch (FileNotFoundException notfound){
            System.err.println(this.fileName + " Not found!");
        }
        catch (IOException excep){
            System.err.println("IOException from reading " + this.fileName);
        }
        return roomers;
    }

    public void setRoomersData(RoomersAccountData roomer){
        String path = fileDirectoryName + File.separator + "csv" + File.separator + fileName;
        File file = new File(path);
        FileWriter write = null;
        try {
            write = new FileWriter(file);
            BufferedWriter writer = new BufferedWriter(write);
            for (RoomerAccount rm: roomer.toList()) {
                String line = rm.getUser() + " , " + rm.getPassword() + " , " + rm.getName() + " , " + rm.getRoomnum();
                writer.append(line);
                writer.newLine();
            }
            writer.close();
        }
        catch (IOException ex){
            System.err.println("Can't write " + path);
        }
    }
}
