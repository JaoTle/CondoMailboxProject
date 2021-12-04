package Condo.manage;

import Condo.models.Account;
import Condo.models.Staff;
import Condo.models.StaffData;

import java.io.*;

public class AdminSource {

    private String fileDirName;
    private String fileName;
    private Account ad;

    public AdminSource(String fileDirName,String fileName){
        this.fileDirName = fileDirName;
        this.fileName = fileName;
        checkFileExisted();
    }

    private void checkFileExisted(){
        File file = new File(fileDirName);
        if (!file.exists()) {
            file.mkdirs();
        }
        String filePath = fileDirName + File.separator + "csv" + File.separator + fileName;
        file = new File(filePath);
        if (!file.exists()) {
            try {
                file.createNewFile();
            } catch (IOException e) {
                System.err.println("Can't create " + filePath);
            }
        }
    }

    private Account readAdminData() throws IOException {
        String path = fileDirName + File.separator + "csv" + File.separator + fileName;
        File file = new File(path);
        FileReader fileRead = new FileReader(file);
        BufferedReader read = new BufferedReader(fileRead);
        String text = "";
        while ((text = read.readLine()) != null){
            String[] dataAd = text.split(",");
            ad = new Account(dataAd[0].trim(),dataAd[1].trim());
        }
        read.close();
        return ad;
    }

    public Account getAdminAccount(){
        try {
            ad = readAdminData();
        }
        catch (FileNotFoundException notfound){
            System.err.println(this.fileName + " not found");
        }
        catch (IOException excep){
            System.err.println("IOException from reading " + this.fileName);
        }
        return ad;
    }

    public void setAdminNewPassword(Account admin){
        String path = fileDirName + File.separator + "csv" + File.separator + fileName;
        File file = new File(path);
        FileWriter write = null;
        try {
            write = new FileWriter(file);
            BufferedWriter writer = new BufferedWriter(write);
            String line = admin.getUser() + " , " + admin.getPassword();
            writer.append(line);
            writer.close();
        }
        catch (IOException ex){
            System.err.println("Can't write " + path);
        }
    }
}
