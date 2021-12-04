package Condo.controller;



import Condo.manage.*;
import Condo.models.*;
import Condo.models.Package;
import animatefx.animation.*;
import javafx.application.Platform;
import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.embed.swing.SwingFXUtils;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.stage.FileChooser;
import javafx.stage.Window;

import javax.imageio.ImageIO;
import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;

public class ManageMailController {
    private Compare compare;
    private StaffSource staffSource;
    private StaffData account;
    private RoomSource roomSource;
    private RoomData rooms;
    private MailboxSource mailboxSource;
    private MailList mails,acceptedMails;
    private String text;
    private String staffName;
    private AcceptedMailSource acceptedMailSource;
    private String tabhover = "-fx-background-color: #C6FCE5;\n" +
            "-fx-background-radius : 0;\n" +
            "-fx-text-fill : #3D84A8;";
    private String tabdefault = "-fx-background-color: #3D84A8;\n" +
            "-fx-background-radius : 0;\n" +
            "-fx-text-fill : #C6FCE5;";
    private String tabclick = "-fx-background-color :#6EF3D6 ; -fx-background-radius : 0 ; -fx-text-fill : #48466D";
    public void setAccount(StaffData staff) { account = staff; }
    public void setStaffSource(StaffSource source){ staffSource = source;}
    public void setRoomSource(RoomSource roomSource) { this.roomSource = roomSource; }
    public void setRooms(RoomData rooms) { this.rooms = rooms; }

    //all
    @FXML private AnchorPane MailPage;
    @FXML private Pane addInform,mailInform,mailStatus;
    @FXML private Button backBt,packBt,docBt,letterBt;
    @FXML private TextField searchText;
    @FXML private Button addMail;
    @FXML private TableView<Mail> mailList;
    @FXML private TableColumn<Mail,Mail> viewBt;
    @FXML private TableColumn<Mail,String> receiver;
    @FXML private TableColumn<Mail,String> roomNum;
    @FXML private TableColumn<Mail,String> staffReceive;
    @FXML private TableColumn<Mail,String> dateTime;
    @FXML private Label dateReceive,roomLabel,nameLabel,surnameLabel,StatusLabel,InformLabel,staffLabel;
    @FXML private ImageView showImg;
    @FXML private TextField getby;
    @FXML private CheckBox checkBox;
    @FXML private Button saveBt,backBtn;
    //Accepted Mail
    @FXML private Pane AcceptedMail;
    @FXML private TableView<Mail> acceptedList;
    @FXML private TableColumn<Mail,String> roomCol;
    @FXML private TableColumn<Mail,String> NameCol;
    @FXML private TableColumn<Mail,String> getbyCol;
    @FXML private TableColumn<Mail,String> statusCol;
    @FXML private TableColumn<Mail,String> acceptCol;
    @FXML private TableColumn<Mail,String> staffCol;
    @FXML private TableColumn<Mail,String> getFrom;
    @FXML private Button Accepted,backToList;

    //Document
    @FXML private Pane documentPn;
    @FXML private TextField docHeight,docWidth;
    @FXML private ImageView imgDoc;
    @FXML private Button docChoose;
    @FXML private MenuButton typeBt;
    @FXML private MenuItem normal,secret,quick;
    @FXML private TextField docRcName,docRcSur,docRcPhone,docRm;
    @FXML private TextField docSdPhone,docSdName,docSdSur;
    @FXML private Button docSaveBt;

    //Package
    @FXML private Pane packagePn;
    @FXML private TextField pacRcName,pacRcSur,pacRcPhone,pacRm,pacSdPhone;
    @FXML private TextField pacSdName;
    @FXML private TextField pacSdSur;
    @FXML private Button pacSaveBt;
    @FXML private TextField pacHeight,pacWidth;
    @FXML private ImageView imgPack;
    @FXML private Button pacChoose;
    @FXML private MenuButton transBt;
    @FXML private MenuItem flash,kerry,thaipost;
    @FXML private TextField pacLength,pacTrack;

    //Letter
    @FXML private Pane letterPn;
    @FXML private TextField letLength,letWidth;
    @FXML private ImageView imgLetter;
    @FXML private Button letChoose;
    @FXML private TextField letRcName,letRcSur,letRcPhone,letRm,letSdPhone,letSdName,letSdSur;
    @FXML private Button letSaveBt;


    FileChooser chooser = new FileChooser();
    File file;

    @FXML public void initialize(){
        new FadeInDown(MailPage).play();
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                compare = new Compare();
                acceptedMailSource = new AcceptedMailSource("data","Accepted.csv");
                mailboxSource = new MailboxSource("data","Mailbox.csv");
                mails = mailboxSource.getMailData();
                acceptedMails = acceptedMailSource.getMailData();
                mails.setMails();
                showMailList();
                letterBt.setStyle(tabclick);
            }
        });
    }


    //Show accepted mail
    private void showAcceptedMail(){
        acceptedMails.setAcceptedMail();
        compare.mailAcceptedCompare(acceptedMails);
        ObservableList<Mail>  acceptedmailObservableList = FXCollections.observableList(acceptedMails.getAcceptedMail());
        NameCol.setCellValueFactory(new PropertyValueFactory<>("name"));
        roomCol.setCellValueFactory(new PropertyValueFactory<>("room"));
        acceptCol.setCellValueFactory(new PropertyValueFactory<>("time"));
        statusCol.setCellValueFactory(new PropertyValueFactory<>("status"));
        getbyCol.setCellValueFactory(new PropertyValueFactory<>("roomreceive"));
        staffCol.setCellValueFactory(new PropertyValueFactory<>("staff_receive"));
        getFrom.setCellValueFactory(new PropertyValueFactory<>("staffmanage"));
        acceptedList.setItems(acceptedmailObservableList);
    }
    //Show data on tableView
    private void showMailList(){
        compare.mailReceiveCompare(mails);
        ObservableList<Mail> mailObservableList = FXCollections.observableList(mails.toList());
        receiver.setCellValueFactory(new PropertyValueFactory<>("name"));
        roomNum.setCellValueFactory(new PropertyValueFactory<>("room"));
        dateTime.setCellValueFactory(new PropertyValueFactory<>("time"));
        staffReceive.setCellValueFactory(new PropertyValueFactory<>("staff_receive"));

        viewBt.setCellValueFactory(param -> new ReadOnlyObjectWrapper<>(param.getValue()));
        viewBt.setCellFactory(param -> new TableCell<Mail,Mail>(){
            Button viewBtn = new Button("View");
            @Override
            protected void updateItem(Mail item, boolean empty) {
                viewBtn.setStyle("-fx-background-color: #ffffff; -fx-border-width : 2;" +
                        "-fx-border-color : #3D84A8 ; -fx-background-radius : 20 ; " +
                        "-fx-border-radius: 20;-fx-tex-fill :#3D84A8;");
                viewBtn.setOnMouseEntered(event -> viewBtn.setStyle("-fx-background-color : #3D84A8 ; -fx-border-color :#3D84A8 ;" +
                        "-fx-text-fill : #C6FCE5; -fx-background-radius : 20 ; " +
                        "-fx-border-radius:20; -fx-border-width : 2;"));
                viewBtn.setOnMouseExited(event -> viewBtn.setStyle("-fx-background-color: #ffffff; -fx-border-width : 2;" +
                        "-fx-border-color : #3D84A8 ; -fx-background-radius : 20 ; " +
                        "-fx-border-radius: 20;-fx-tex-fill :#3D84A8;"));
                super.updateItem(item, empty);
                if (item == null){
                    setGraphic(null);
                    return;
                }
                setGraphic(viewBtn);
                viewBtn.setOnAction(event -> {
                    mailStatus.toFront();
                    new FadeInDown(mailStatus).play();
                    Mail selectMail = getItem();
                    setTextStatus(selectMail);
                });
            }
        });
        FilteredList<Mail> searchFilter = new FilteredList<>(mailObservableList,b -> true);
        searchText.textProperty().addListener((observable, oldValue, newValue) -> {
            searchFilter.setPredicate(mail -> {
                if(newValue == null || newValue.isEmpty()) return true;
                if(mail.getRoom().indexOf(newValue) != -1) return true;
                else return false;
            });
        });
        SortedList<Mail> sortedMail = new SortedList<>(searchFilter);
        sortedMail.comparatorProperty().bind(mailList.comparatorProperty());
        mailList.setItems(sortedMail);
    }





    //setTextMailStatus
    private void setTextStatus(Mail selectMail){
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("d/MM/yyyy HH:mm:ss");
        selectMail.setInform();
        InformLabel.setText(selectMail.getInform());
        staffLabel.setText(selectMail.getStaff_receive());
        dateReceive.setText("Received date : " + selectMail.getTime());
        roomLabel.setText(selectMail.getRoom());
        nameLabel.setText(selectMail.getReceiver().getName());
        surnameLabel.setText(selectMail.getReceiver().getSurname());
        StatusLabel.setText(selectMail.getStatus());
        mailboxSource.showImage(selectMail.getImgPath(),showImg);
        saveBt.setOnAction(event -> {
            if(checkBox.isSelected()){
                if(!getby.getText().isEmpty()){
                    staffName = account.getStaffAccount().getName() + " " + account.getStaffAccount().getSurName();
                    selectMail.setStaffmanage(staffName);
                    selectMail.setRoomreceive(getby.getText());
                    selectMail.setTime(LocalDateTime.now().format(formatter));
                    selectMail.setStatus("Accepted");
                    acceptedMails.addAcceptedMail(selectMail);
                    mails.deleteMail(selectMail);
                    mailboxSource.setMailsData(mails);
                    acceptedMailSource.setMailsData(acceptedMails);
                    mailInform.toFront();
                    showMailList();
                }
               else {
                   Alert alert = new Alert(Alert.AlertType.WARNING);
                   alert.setTitle("Can't accepted");
                   alert.setHeaderText("Not have receiver");
                   alert.setContentText("Please fill get by field");

                   alert.showAndWait();
                }
            }
        });
    }

    @FXML private void handleBack(){
        mailInform.toFront();
        showMailList();
    }

    //MailTypeTab
    @FXML private void handleTabMail(ActionEvent event){
        if(event.getSource() == letterBt){
            letterPn.toFront();
            letterBt.toFront();
            setStyleBt(letterBt,docBt,packBt);
        }
        else if(event.getSource() == docBt){

            setStyleBt(docBt,letterBt,packBt);
            documentPn.toFront();
            docBt.toFront();
        }
        else if(event.getSource() == packBt){
            setStyleBt(packBt,letterBt,docBt);
            packagePn.toFront();
            packBt.toFront();
        }
        else if(event.getSource() == backBt){
            clearPackField();
            clearDocField();
            clearLetterField();
            mailInform.toFront();
        }
    }
    //Add mail
    @FXML private void handleClick(ActionEvent event){
        if(event.getSource() == addMail) {
            addInform.toFront();
            letterPn.toFront();
            new BounceInRight(addInform).play();
        }
        else if(event.getSource() == Accepted) {
            AcceptedMail.toFront();
            showAcceptedMail();
        }
    }

    //Set action with menuItem
    @FXML private void handleMenuItem(ActionEvent item){
        MenuItem menu = (MenuItem) item.getSource();
        if(menu == normal){
            text = menu.getText();
            typeBt.setText(text);
            text = "normal";
        }
        else if(menu == quick){
            text = menu.getText();
            typeBt.setText(text);
            text = "quick";
        }
        else if(menu == secret){
            text = menu.getText();
            typeBt.setText(text);
            text = "secret";
        }
        else if(menu == kerry){
            text = menu.getText();
            transBt.setText(text);
        }
        else if(menu == thaipost){
            text = menu.getText();
            transBt.setText(text);
        }
        else if(menu == flash){
            text = menu.getText();
            transBt.setText(text);
        }
    }



    //Upload Image
    @FXML private void handleChoose(ActionEvent event){
        Window stage = addInform.getScene().getWindow();
        chooser.setTitle("Upload file");
        chooser.getExtensionFilters().addAll(new FileChooser.ExtensionFilter("image file","*.jpg","*.png","*.gif"));
        file = chooser.showOpenDialog(stage);
        Image img = new Image(file.toURI().toString());
        if(event.getSource() == letChoose){
            imgLetter.setImage(img);
            imgLetter.setPreserveRatio(true);
            imgLetter.setFitWidth(175);
            imgLetter.setFitHeight(150);
        }
        else if(event.getSource() == docChoose){
            imgDoc.setImage(img);
            imgDoc.setPreserveRatio(true);
            imgDoc.setFitWidth(175);
            imgDoc.setFitHeight(150);
        }
        else if(event.getSource() == pacChoose){
            imgPack.setImage(img);
            imgPack.setPreserveRatio(true);
            imgPack.setFitWidth(175);
            imgPack.setFitHeight(150);
        }
    }

    //Save and write mail information
    @FXML private void handleSave(ActionEvent event){
        staffName = account.getStaffAccount().getName() + " " + account.getStaffAccount().getSurName();
        Window stage = addInform.getScene().getWindow();
        chooser.setTitle("Save file");
        chooser.setInitialDirectory(new File("data/mailimg"));
        String timeStamp = new SimpleDateFormat("d/MM/yyyy HH:mm:ss").format(new Date());
        if(event.getSource() == letSaveBt){
            file = chooser.showSaveDialog(stage);
            String filepath = "/data/mailimg/" + file.getName();
            Person sender = new Person(letSdName.getText(),letSdSur.getText(),letSdPhone.getText());
            Receiver receiver = new Receiver(letRcName.getText(),letRcSur.getText(),letRcPhone.getText(),letRm.getText().toUpperCase());
            Mail letter = new Mail(sender,receiver,letWidth.getText(),letLength.getText(),timeStamp,staffName,filepath);
            if(sender.checkData() && receiver.checkData(rooms) && letter.checkData()){
                mails.addMailbox(letter);
                mailboxSource.setMailsData(mails);
                if (file != null) {
                    try {
                        ImageIO.write(SwingFXUtils.fromFXImage(imgLetter.getImage(), null), "png", file);
                    } catch (IOException ex) {
                        System.err.println("Don't Save" + file.getPath());
                    }
                }
                else alertImageNull();
                mailInform.toFront();
                clearLetterField();
                initialize();
            }
            else alert();

        }
        else if(event.getSource() == docSaveBt){
            file = chooser.showSaveDialog(stage);
            String filepath = "/data/mailimg/" + file.getName();
            Person sender = new Person(docSdName.getText(),docSdSur.getText(),docSdPhone.getText());
            Receiver receiver = new Receiver(docRcName.getText(),docRcSur.getText(),docRcPhone.getText(),docRm.getText().toUpperCase());
            Mail doc = new Document(sender,receiver,docHeight.getText(),docWidth.getText(),text,timeStamp,staffName,filepath);
            if(sender.checkData() && receiver.checkData(rooms) && doc.checkData()){
                mails.addMailbox(doc);
                mailboxSource.setMailsData(mails);
                if (file != null) {
                    try {
                        ImageIO.write(SwingFXUtils.fromFXImage(imgDoc.getImage(), null), "png", file);
                    } catch (IOException ex) {
                        System.err.println("Don't Save" + file.getPath());
                    }
                }
                else alertImageNull();
                mailInform.toFront();
                clearDocField();
                initialize();
            }
            else alert();

        }
        else if(event.getSource() == pacSaveBt){
            file = chooser.showSaveDialog(stage);
            String filepath = "/data/mailimg/" + file.getName();
            Person sender = new Person(pacSdName.getText(),pacSdSur.getText(),pacSdPhone.getText());
            Receiver receiver = new Receiver(pacRcName.getText(),pacRcSur.getText(),pacRcPhone.getText(),pacRm.getText().toUpperCase());
            Mail pack = new Package(sender,receiver,pacWidth.getText(),pacLength.getText(),pacHeight.getText(),text,pacTrack.getText(),timeStamp,staffName,filepath);
            if(sender.checkData() && receiver.checkData(rooms) && pack.checkData()){
                mails.addMailbox(pack);
                mailboxSource.setMailsData(mails);
                if (file != null) {
                    try {
                        ImageIO.write(SwingFXUtils.fromFXImage(imgPack.getImage(), null), "png", file);
                    } catch (IOException ex) {
                        System.err.println("Don't Save" + file.getPath());
                    }
                }
                else alertImageNull();
                mailInform.toFront();
                clearPackField();
                initialize();
            }
            else alert();
        }
    }

    //clear package field method
    private void clearPackField(){
        pacHeight.clear();
        pacLength.clear();
        pacRcName.clear();
        pacRcPhone.clear();
        pacRcSur.clear();
        pacRm.clear();
        pacSdName.clear();
        pacSdPhone.clear();
        pacSdSur.clear();
        pacTrack.clear();
        pacWidth.clear();
        imgPack.setImage(null);
        transBt.setText("Transport");
        packBt.setStyle(tabdefault);
        letterBt.toFront();
        letterBt.setStyle(tabclick);
        packBt.setOnMouseEntered(event -> packBt.setStyle(tabhover));
        packBt.setOnMousePressed(event -> packBt.setStyle(tabclick));
        packBt.setOnMouseExited(event -> packBt.setStyle(tabdefault));

    }
    //clear document field method
    private void clearDocField(){
        docHeight.clear();
        docRcName.clear();
        docRcPhone.clear();
        docRcSur.clear();
        docRm.clear();
        docSdName.clear();
        docSdPhone.clear();
        docSdSur.clear();
        docWidth.clear();
        imgDoc.setImage(null);
        typeBt.setText("Priority");
        letterBt.setStyle(tabclick);
        letterBt.toFront();
        docBt.setStyle(tabdefault);
        docBt.setOnMouseEntered(event -> docBt.setStyle(tabhover));
        docBt.setOnMousePressed(event -> docBt.setStyle(tabclick));
        docBt.setOnMouseExited(event -> docBt.setStyle(tabdefault));
    }
    //clear letter field method
    private void clearLetterField(){
        letLength.clear();
        letRcName.clear();
        letRcPhone.clear();
        letRcSur.clear();
        letRm.clear();
        letSdName.clear();
        letSdPhone.clear();
        letSdSur.clear();
        letWidth.clear();
        imgLetter.setImage(null);

    }

    // alert image
    private void alertImageNull(){
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Error!");
        alert.setHeaderText("Data not complete.");
        alert.setContentText("Don't have image. Please upload image");

        alert.showAndWait();

    }
    // alert method
    private void alert(){
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Error!");
        alert.setHeaderText("Data not complete.");
        alert.setContentText("Please complete all information.\n " +
                ",check this room has roomer. or not found this room.");

        alert.showAndWait();
    }

    //set Style
    private void setStyleBt(Button bt1,Button bt2,Button bt3){
        bt1.setOnMouseExited(event -> bt1.setStyle(tabclick));
        bt2.setStyle(tabdefault);
        bt3.setStyle(tabdefault);
        bt2.setOnMouseEntered(event -> bt2.setStyle(tabhover));
        bt2.setOnMousePressed(event ->bt2.setStyle(tabclick));
        bt2.setOnMouseExited(event -> bt2.setStyle(tabdefault));
        bt3.setOnMouseEntered(event -> bt3.setStyle(tabhover));
        bt3.setOnMousePressed(event -> bt3.setStyle(tabclick));
        bt3.setOnMouseExited(event -> bt3.setStyle(tabdefault));
    }

}
