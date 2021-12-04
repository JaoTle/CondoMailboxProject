package Condo.manage;

import Condo.models.*;

import java.io.*;

public class RoomSource {

    private String fileDirectoryName;
    private String fileName;
    private RoomData rooms;

    public RoomSource(String fileDirectoryName , String fileName){  //รับ Constructor เป็นชื่อdirectory กับ ชื่อfile
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

    private void readRoomData() throws IOException {
        String path = fileDirectoryName + File.separator + "csv" + File.separator + fileName;
        File file = new File(path);
        FileReader fileRead = new FileReader(file);
        BufferedReader read = new BufferedReader(fileRead);
        String data = "";
        while ((data = read.readLine()) != null){
            String[] roomdata = data.split(",");
            if(roomdata[2].trim().equals("Deluxe")){
                Room deluxe = new Room(roomdata[0].trim(),roomdata[1].trim(),roomdata[2].trim(),roomdata[3].trim(),roomdata[4].trim(),roomdata[5].trim());
                rooms.addRoomData(deluxe);
            }
            else if(roomdata[2].trim().equals("Suite")) {
                Room suite = new SuiteRoom(roomdata[0].trim(),roomdata[1].trim(), roomdata[2].trim(), roomdata[3].trim(), roomdata[4].trim(), roomdata[5].trim(), roomdata[6].trim(),roomdata[7].trim());
                rooms.addRoomData(suite);
            }
        }
        read.close();
    }

    public RoomData getRoomData(){
        try {
            rooms = new RoomData();
            readRoomData();
        }
        catch (FileNotFoundException notfound){
            System.err.println(this.fileName + " Not found!");
        }
        catch (IOException excep){
            System.err.println("IOException error from reading " + this.fileName);
        }
        return rooms;
    }

    public void setRoomData(RoomData room){
        String path = fileDirectoryName + File.separator + "csv" + File.separator + fileName;
        File file = new File(path);
        FileWriter writefile = null;
        try {
            writefile = new FileWriter(file);
            BufferedWriter writer = new BufferedWriter(writefile);
            for (Room rm : room.toList()) {
                if(rm.getRoomtype().equals("Deluxe")){
                    String deluxe = rm.getRoomnum() + "," +rm.getFloor() + "," + rm.getRoomtype() + "," + rm.getBuilding() +"," + rm.getRoomer1() + "," + rm.getRoomer2()+ "\n";
                    writefile.append(deluxe);
                }
                else if(rm.getRoomtype().equals("Suite")){
                    String suite = rm.getRoomnum() + "," + rm.getFloor() + "," + rm.getRoomtype() + "," + rm.getBuilding() +"," + rm.getRoomer1() + "," + rm.getRoomer2() + "," + ((SuiteRoom)rm).getRoomer3() + "," + ((SuiteRoom)rm).getRoomer4() +"\n";
                    writefile.append(suite);
                }
            }
            writer.close();
        }
        catch (IOException ex){
            System.err.println("Can't write " + path);
        }
    }
}
