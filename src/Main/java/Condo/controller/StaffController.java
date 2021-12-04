package Condo.controller;

import Condo.manage.Compare;
import Condo.manage.RoomSource;
import Condo.manage.StaffSource;
import Condo.models.Room;
import Condo.models.RoomData;
import Condo.models.StaffData;
import animatefx.animation.BounceIn;
import animatefx.animation.FadeIn;
import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import javafx.util.Duration;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class StaffController {
    private Compare compare;
    private StaffData account;
    private StaffSource staffSource;
    private RoomData rooms;
    private RoomSource roomSource;
    public void setAccount(StaffData staff) { this.account = staff;}
    public void setSource(StaffSource staffSource){ this.staffSource = staffSource;}
    private String tabhover = "-fx-background-color: #6EF3D6;\n" +
            "-fx-background-radius : 0;\n" +
            "-fx-text-fill :#48466D;";
    private String tabdefault = "-fx-background-color: #48466D;\n" +
            "-fx-background-radius : 0;\n" +
            "-fx-text-fill : white;";
    @FXML private AnchorPane StaffPage;
    //MainMail FXML
    @FXML private Pane chPass;
    @FXML private AnchorPane roomInform;
    @FXML private Button signOut,rmInformBt,manageRmBt,mngMailBt,chPassBt,okChangeBt;
    @FXML private Label staffUser,staffName,dateTime;
    @FXML private PasswordField oldPass,newPass,confirmField;
    @FXML public BorderPane border;
    @FXML private ImageView staffImg;

    //List
    @FXML private TableView<Room> roomList;
    @FXML private TableColumn<Room,String> RoomNum;
    @FXML private TableColumn<Room,String> Building;
    @FXML private TableColumn<Room,String> Floor;
    @FXML private TableColumn<Room,String> Roomtype;
    @FXML private TableColumn<Room,String> Roomers;


    @FXML
    public void initialize() {
        new FadeIn(StaffPage).setCycleCount(1).play();
        Platform.runLater(new Runnable() {
            public void run() {
                compare = new Compare();
                roomSource = new RoomSource("data","Roominform.csv"); //ดึงข้อมูล room จากไฟล์ source
                rooms = roomSource.getRoomData();
                staffUser.setText(account.getStaffAccount().getUser());
                staffName.setText(account.getStaffAccount().getName() + " " + account.getStaffAccount().getSurName());
                staffSource.showImage(account.getStaffAccount().getImagePath(),staffImg);
                //Show date and time
                Timeline clock = new Timeline(new KeyFrame(Duration.ZERO, e -> {
                    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("E d/MM/yyyy HH:mm:ss");
                    dateTime.setText(LocalDateTime.now().format(formatter));
                }),
                        new KeyFrame(Duration.seconds(1))
                );
                clock.setCycleCount(Animation.INDEFINITE);
                clock.play();
                //Set present data and show data on tableview
                staffSource.showImage(account.getStaffAccount().getImagePath(),staffImg);
                rooms.setStatus();
                rmInformBt.setStyle(tabhover);
                showRoomList();
            }
        });
    }

    //Show data on tableview function
    private void showRoomList(){
        compare.roomNumCompare(rooms);
        ObservableList<Room> roomObservableList = FXCollections.observableList(rooms.toList());
        RoomNum.setCellValueFactory(new PropertyValueFactory<>("roomnum"));
        Building.setCellValueFactory(new PropertyValueFactory<>("building"));
        Floor.setCellValueFactory(new PropertyValueFactory<>("floor"));
        Roomtype.setCellValueFactory(new PropertyValueFactory<>("roomtype"));
        Roomers.setCellValueFactory(new PropertyValueFactory<>("status"));
        roomList.setItems(roomObservableList);
    }

    //StaffHome
    @FXML private void handleStaffClick(ActionEvent click) throws IOException {
        if(click.getSource() == signOut){
            Stage stage = (Stage) signOut.getScene().getWindow();
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/home.fxml"));
            stage.setScene(new Scene( loader.load(),1280,768));
            HomeController home = loader.getController();
            home.initialize();
            stage.show();
        }
        else {
            if(click.getSource() == rmInformBt) {
                initialize();
                setStyleBt(rmInformBt,mngMailBt,manageRmBt,chPassBt);
                roomInform.toFront();
            }
            else if(click.getSource() == chPassBt) {
                setStyleBt(chPassBt,rmInformBt,manageRmBt,mngMailBt);
                chPass.toFront();
                new BounceIn(chPass).setCycleCount(1).play();
            }
            else if (click.getSource() == manageRmBt) {
                setStyleBt(manageRmBt,mngMailBt,rmInformBt,chPassBt);
                loadUI("room_inform");
                new FadeIn(StaffPage).stop();
            }
            else if(click.getSource() == mngMailBt){
                setStyleBt(mngMailBt,manageRmBt,chPassBt,rmInformBt);
                loadUI("mailbox_inform");
                new FadeIn(StaffPage).stop();
            }
        }
    }

    //StaffChangePassword
    @FXML private void handleChangePassword(){
        if (account.getStaffAccount().changePassword(oldPass.getText(),newPass.getText(),confirmField.getText())) {
            staffSource.setStaffNewData(account);
            roomInform.toFront();
            chPassBt.setStyle(tabdefault);
            rmInformBt.setStyle(tabhover);
            chPassBt.setOnMouseEntered(event -> chPassBt.setStyle(tabhover));
            chPassBt.setOnMouseExited(event -> chPassBt.setStyle(tabdefault));
        }
        else if(!account.getStaffAccount().changePassword(oldPass.getText(),newPass.getText(),confirmField.getText())) {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Change password failed!.");
            alert.setHeaderText("Wrong password");
            alert.setContentText("Old password is wrong \nor New password not match confirm password.");

            alert.showAndWait();
        }
        else {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Change password failed!.");
            alert.setHeaderText("Some field is empty.");
            alert.setContentText("Please add all data. And try again.");

            alert.showAndWait();
        }
    }

    //Loader others scene function
    private void loadUI(String ui){
        Parent root = null;
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/"+ui+".fxml"));
            root = loader.load();
            if(ui.equals("mailbox_inform")) {
                ManageMailController mailCtrl = loader.getController();
                mailCtrl.setAccount(account);
                mailCtrl.setStaffSource(staffSource);
                mailCtrl.setRooms(rooms);
                mailCtrl.setRoomSource(roomSource);
            }
            else if(ui.equals("room_inform")){
                ManageRoomController roomCtrl = loader.getController();
                roomCtrl.setRoomData(rooms);
                roomCtrl.setRoomSource(roomSource);
            }
        }
        catch (IOException ex){
            System.err.println("Can't load" + ui + " scene.");
        }
        border.setCenter(root);
        border.toFront();
    }

    //set Style
    private void setStyleBt(Button bt1,Button bt2,Button bt3,Button bt4){
        bt1.setOnMouseExited(event -> bt1.setStyle(tabhover));
        bt2.setStyle(tabdefault);
        bt3.setStyle(tabdefault);
        bt4.setStyle(tabdefault);
        bt2.setOnMouseEntered(event1 -> bt2.setStyle(tabhover));
        bt2.setOnMouseExited(event1 -> bt2.setStyle(tabdefault));
        bt3.setOnMouseEntered(event1 -> bt3.setStyle(tabhover));
        bt3.setOnMouseExited(event1 -> bt3.setStyle(tabdefault));
        bt4.setOnMouseEntered(event1 -> bt4.setStyle(tabhover));
        bt4.setOnMouseExited(event1 -> bt4.setStyle(tabdefault));
    }
}
