package Condo.manage;

import Condo.MainCondo;
import Condo.models.*;
import Condo.models.Package;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.io.*;
import java.net.URISyntaxException;

public class MailboxSource {
    private String fileDirectoryName;
    private String fileName;
    private MailList mails;

    public MailboxSource(String fileDirectoryName , String fileName){  //รับ Constructor เป็นชื่อdirectory กับ ชื่อfile
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
    private void readMailData() throws IOException {
        String path = fileDirectoryName + File.separator + "csv" + File.separator + fileName;
        File file = new File(path);
        FileReader fileRead = new FileReader(file);
        BufferedReader read = new BufferedReader(fileRead);
        String data = "";
        while ((data = read.readLine()) != null){
            String[] maildata = data.split(",");
            Person sender = new Person(maildata[0],maildata[1],maildata[2]);
            Receiver receiver = new Receiver(maildata[3],maildata[4],maildata[5],maildata[6]);
            if(maildata.length == 13){
                Mail letter = new Mail(sender,receiver,maildata[7],maildata[8],maildata[9],maildata[11],maildata[12]);
                mails.addMailbox(letter);
            }
            else if(maildata.length == 14){
                Mail document = new Document(sender,receiver,maildata[7],maildata[8],maildata[9],maildata[10],maildata[12],maildata[13]);
                mails.addMailbox(document);
            }
            else if(maildata.length == 16){
                Mail pack = new Package(sender,receiver,maildata[7],maildata[8],maildata[9],maildata[10],maildata[11],maildata[12],maildata[14],maildata[15]);
                mails.addMailbox(pack);
            }
        }
        read.close();
    }

    public MailList getMailData(){
        try {
            mails = new MailList();
            readMailData();
        } catch (FileNotFoundException notfound){
            System.err.println(this.fileName + " Not found!");
        } catch (IOException excep){
            System.err.println("IOException error from reading " + this.fileName);
        }
        return mails;
    }
    public void setMailsData(MailList mails){
        String path = fileDirectoryName + File.separator + "csv" + File.separator + fileName;
        File file = new File(path);
        FileWriter writefile = null;
        try {
            writefile = new FileWriter(file);
            BufferedWriter writer = new BufferedWriter(writefile);
            for (Mail mail : mails.toList()) {
                //System.out.println(mail.toString());
                if(mail.getMailtype().equals("Letter")){
                    String letter = mail.getSender().getName() + "," + mail.getSender().getSurname() + "," + mail.getSender().getPhone()
                            + "," + mail.getReceiver().getName() + "," + mail.getReceiver().getSurname() + "," + mail.getReceiver().getPhone()
                            + "," + mail.getReceiver().getRoomnum() + "," + mail.getWidth() + "," + mail.getLength() + ","
                            + mail.getTime() + "," + mail.getStatus() + "," + mail.getStaff_receive() + "," + mail.getImgPath() +"\n";
                    writefile.append(letter);
                }
                else if(mail.getMailtype().equals("Document")){
                    String doc = mail.getSender().getName() + "," + mail.getSender().getSurname() + "," + mail.getSender().getPhone()
                            + "," + mail.getReceiver().getName() + "," + mail.getReceiver().getSurname() + "," + mail.getReceiver().getPhone()
                            + "," + mail.getReceiver().getRoomnum() + "," + mail.getWidth() + "," + mail.getLength() + "," + ((Document)mail).getType()
                            + "," + mail.getTime() + "," + mail.getStatus() + "," + mail.getStaff_receive() + "," + mail.getImgPath() +"\n";
                    writefile.append(doc);
                }
                else if(mail.getMailtype().equals("Package")){
                    String pack = mail.getSender().getName() + "," + mail.getSender().getSurname() + "," + mail.getSender().getPhone()
                            + "," + mail.getReceiver().getName() + "," + mail.getReceiver().getSurname() + "," + mail.getReceiver().getPhone()
                            + "," + mail.getReceiver().getRoomnum() + "," + mail.getWidth() + "," + mail.getLength() + "," + ((Package)mail).getHeight()
                            + "," + ((Package)mail).getTransport() + "," + ((Package)mail).getTrack() + "," +  mail.getTime() + "," + mail.getStatus()
                            + "," + mail.getStaff_receive() + "," + mail.getImgPath() +"\n";
                    writefile.append(pack);
                }
            }
            writer.close();
        }
        catch (IOException ex){
            System.err.println("Can't write " + path);
        }
    }

    public void showImage(String uploadPath, ImageView showImg) {
        File jarDir = null;
        File codeDir = null;
        try {
            jarDir = new File(MainCondo.class.getProtectionDomain()
                    .getCodeSource().getLocation()
                    .toURI().getPath());
            codeDir = jarDir.getParentFile();

            String path = codeDir.toString() + File.separator  + File.separator + uploadPath;
            File uploadFile = new File(path);
            showImg.setImage(new Image(uploadFile.toURI().toString()));
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }
    }
}
