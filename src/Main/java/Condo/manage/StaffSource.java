package Condo.manage;

import Condo.MainCondo;
import Condo.models.Staff;
import Condo.models.StaffData;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.io.*;
import java.net.URISyntaxException;

public class StaffSource {

    private String fileDirectoryName;
    private String fileName;
    private StaffData staffs; // attributes ที่มี type เป็น Class StaffData

    public StaffSource(String fileDirectoryName , String fileName){  //รับ Constructor เป็นชื่อdirectory กับ ชื่อfile
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

    private void readStaffData() throws IOException {
        String path = fileDirectoryName + File.separator + "csv" + File.separator + fileName;
        File file = new File(path);
        FileReader fileRead = new FileReader(file);
        BufferedReader read = new BufferedReader(fileRead);
        String text = "";
        while ((text = read.readLine()) != null){
            String[] dataUser = text.split(",");
            Staff staff = new Staff(dataUser[0].trim(),dataUser[1].trim(),dataUser[2].trim(),dataUser[3].trim(), Integer.parseInt(dataUser[4].trim()),dataUser[5].trim(),dataUser[6].trim());
            staffs.addStaffAccount(staff);
        }
        read.close();
    }

    public StaffData getStaffData(){
        try {
            staffs = new StaffData(); //เรียก Constructor ของ StaffData ( ArrayList )
            readStaffData();
        }
        catch (FileNotFoundException notfound){
            System.err.println(this.fileName + " Not found!");
        }
        catch (IOException excep){
            System.err.println("IOException from reading " + this.fileName);
        }
        return staffs;
    }

    public void setStaffNewData(StaffData staff){
        String path = fileDirectoryName + File.separator + "csv" + File.separator + fileName;
        File file = new File(path);
        FileWriter write = null;
        try {
            write = new FileWriter(file);
            BufferedWriter writer = new BufferedWriter(write);
            for (Staff st : staff.toList()) {
                String line = st.getUser() + " , " + st.getPassword() + " , " + st.getName() + " , " + st.getSurName() + " , " + st.getAge()+ " , " + st.getTime()
                        + "," + st.getImagePath();
                writer.append(line);
                writer.newLine();
            }
            writer.close();
        }
        catch (IOException ex){
            System.err.println("Can't write " + path);
        }
    }

    public void showImage(String uploadPath, ImageView staffImg) {
        File jarDir = null;
        File codeDir = null;
        try {
            jarDir = new File(MainCondo.class.getProtectionDomain()
                    .getCodeSource().getLocation()
                    .toURI().getPath());
            codeDir = jarDir.getParentFile();

            String path = codeDir.toString() + File.separator + File.separator + uploadPath;
            File uploadFile = new File(path);
            staffImg.setImage(new Image(uploadFile.toURI().toString()));
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }
    }

}
