package Condo.manage;

import Condo.models.*;
import Condo.models.Package;

import java.io.*;

public class AcceptedMailSource {
    private String fileDirectoryName;
    private String fileName;
    private MailList acceptedmails;
    public AcceptedMailSource(String fileDirectoryName,String fileName){
        this.fileDirectoryName = fileDirectoryName;
        this.fileName = fileName;
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


    private void readMailData() throws IOException {
        String path = fileDirectoryName + File.separator + "csv" + File.separator + fileName;
        File file = new File(path);
        FileReader fileRead = new FileReader(file);
        BufferedReader read = new BufferedReader(fileRead);
        String data = "";
        while ((data = read.readLine()) != null){
            String[] maildata = data.split(",");
            Receiver receiver = new Receiver(maildata[0],maildata[1],maildata[2],maildata[3]);
            Mail mail = new Mail(receiver,maildata[4],maildata[5],maildata[6],maildata[7],maildata[8]);
            acceptedmails.addAcceptedMail(mail);
        }
        read.close();
    }

    public MailList getMailData(){
        try {
            acceptedmails = new MailList();
            readMailData();
        } catch (FileNotFoundException notfound){
            System.err.println(this.fileName + " Not found!");
        } catch (IOException excep){
            System.err.println("IOException error from reading " + this.fileName);
        }
        return acceptedmails;
    }


    public void setMailsData(MailList mails){
        String path = fileDirectoryName + File.separator + "csv" + File.separator + fileName;
        File file = new File(path);
        FileWriter writefile = null;
        try {
            writefile = new FileWriter(file);
            BufferedWriter writer = new BufferedWriter(writefile);
            for (Mail mail : mails.getAcceptedMail()) {
                String text = mail.getReceiver().getName() + "," + mail.getReceiver().getSurname() + "," + mail.getReceiver().getPhone() + ","
                        + mail.getReceiver().getRoomnum() + "," + mail.getTime() + "," + mail.getStaff_receive() +","+ mail.getRoomreceive() +
                        "," + mail.getStaffmanage() + "," + mail.getImgPath();
                writer.append(text);
                writer.newLine();
            }
            writer.close();
        }
        catch (IOException ex){
            System.err.println("Can't write " + path);
        }
    }
}
