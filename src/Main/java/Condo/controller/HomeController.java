package Condo.controller;

import Condo.manage.AdminSource;
import Condo.manage.RoomSource;
import Condo.manage.RoomerAccountSource;
import Condo.manage.StaffSource;
import Condo.models.*;
import animatefx.animation.FadeIn;
import animatefx.animation.FadeInUp;
import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;


public class HomeController {
    @FXML private AnchorPane HomePage;
    @FXML private Button informBt,loginBut , instrucBt, homeBt,registerBt,RegisBtn;
    @FXML private Pane informP,loginP,instructP,regisP;
    @FXML private TextField user,Name,room,regisUser;
    @FXML private PasswordField pass,regisPass,confirmRegis;
    @FXML private Label name, nickname, codeId,dateTime;

    private RoomSource roomSource;
    private RoomData rooms;
    private RoomersAccountData roomerAc;
    private RoomerAccountSource roomerAccountSource;
    private StaffSource stDataSource;
    private StaffData staffAcc;
    private AdminSource adminSource;
    private Account admin;
    private String tabdefault = "-fx-background-color: #48466D;\n" + "-fx-background-radius : 0;\n" + "-fx-text-fill : white;";
    private String tabhover = "    -fx-background-color: #ABEDD8;\n" +
            "    -fx-background-radius : 0;\n" +
            "    -fx-text-fill :#48466D;";
    @FXML public void initialize(){
        new FadeIn(HomePage).play();
        //Show date and time.
        Timeline clock = new Timeline(new KeyFrame(Duration.ZERO, e -> {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("E d/MM/yyyy HH:mm:ss");
            dateTime.setText(LocalDateTime.now().format(formatter));
        }),
                new KeyFrame(Duration.seconds(1))
        );
        clock.setCycleCount(Animation.INDEFINITE);
        clock.play();
        roomerAccountSource = new RoomerAccountSource("data","RoomerAccount.csv");
        roomSource = new RoomSource("data","Roominform.csv");
        stDataSource = new StaffSource("data","staffData.csv"); //ดึงข้อมูล staff จากไฟล์ source
        adminSource = new AdminSource("data","Admin.csv"); // ดึงข้อมูล admin จากไฟล์ source
        roomerAc = roomerAccountSource.getRoomersData();
        rooms = roomSource.getRoomData();
        staffAcc = stDataSource.getStaffData();
        admin = adminSource.getAdminAccount();
        name.setText("ชุติกาญจน์   มงคลธนโชค");
        nickname.setText("เติ้ล");
        codeId.setText("6210401244");
        homeBt.setStyle(tabhover);
    }

    //select tab
   @FXML
    public void handleClick(ActionEvent event) {
       if(event.getSource() == homeBt){
           setStyleBt(homeBt,informBt,instrucBt,registerBt);
           loginP.toFront();
           new FadeInUp(loginP).play();
       }
       else if(event.getSource() == informBt){
           setStyleBt(informBt,homeBt,instrucBt,registerBt);
           informP.toFront();
           new FadeInUp(informP).play();
       }
       else if(event.getSource() == instrucBt){
           setStyleBt(instrucBt,informBt,homeBt,registerBt);
           instructP.toFront();
           new FadeInUp(instructP).play();
       }
       else if(event.getSource() == registerBt){
            setStyleBt(registerBt,instrucBt,informBt,homeBt);
            regisP.toFront();
           new FadeInUp(regisP).play();
       }
   }

   //login process
   @FXML public void handleLogin(ActionEvent event) throws IOException {
        if(user.getText().equals(admin.getUser()) && pass.getText().equals(admin.getPassword())){
           loginBut = (Button) event.getSource();
           Stage stage = (Stage) loginBut.getScene().getWindow();
           FXMLLoader loader = new FXMLLoader(getClass().getResource("/admin_home.fxml"));
           stage.setScene(new Scene(loader.load(),1280,768));
           AdminController ad = loader.getController();
           ad.setAccount(admin);
           ad.setSource(adminSource);
           stage.show();
           new FadeIn(HomePage).stop();
        }
        else if(staffAcc.checkAccount(user.getText(),pass.getText())){
            String timeStamp = new SimpleDateFormat("d/MM/yyyy HH:mm:ss").format(new Date());
           loginBut = (Button) event.getSource();
           Stage stage = (Stage) loginBut.getScene().getWindow();
           FXMLLoader loader = new FXMLLoader(getClass().getResource("/staff.fxml"));
           stage.setScene(new Scene(loader.load(),1280,768));
           StaffController st = loader.getController();
           staffAcc.getStaffAccount().setTime(timeStamp);
           st.setAccount(staffAcc);
           stDataSource.setStaffNewData(staffAcc);
           st.setSource(stDataSource);
           stage.show();
           new FadeIn(HomePage).stop();
        }
        else if(roomerAc.checkAccount(user.getText(),pass.getText())){
            loginBut = (Button) event.getSource();
            Stage stage = (Stage) loginBut.getScene().getWindow();
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/roomer.fxml"));
            stage.setScene(new Scene(loader.load(),1280,768));
            RoomerController rm = loader.getController();
            rm.setRoomers(roomerAc);
            stage.show();
            new FadeIn(HomePage).stop();

       }
        else{
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Login Failed");
            alert.setHeaderText("Invalid! Username or Password.");
            alert.setContentText("Please! check your Username and Password.\nAnd try again.");
            alert.showAndWait();
        }
   }

   //register process
    @FXML
    public void handleRegister(){
        if(rooms.checkRoomnum(room.getText())){
            Room selectedroom = rooms.getRoomInform();
            if(selectedroom.checkRoomers(Name.getText())){
                if(regisPass.getText().equals(confirmRegis.getText())){
                    RoomerAccount roomac = new RoomerAccount(regisUser.getText(),regisPass.getText(),Name.getText(),room.getText());
                    roomerAc.addRoomerAccount(roomac);
                    roomerAccountSource.setRoomersData(roomerAc);
                    loginP.toFront();
                    new FadeInUp(loginP).play();
                    homeBt.setStyle(tabhover);
                    setStyleBt(homeBt,instrucBt,informBt,registerBt);
                }
                else {
                    Alert alert = new Alert(Alert.AlertType.WARNING);
                    alert.setTitle("Register Failed!");
                    alert.setHeaderText("Password and Confirm password not match");
                    alert.setContentText("Please Check Confirm password \n And try again");

                    alert.showAndWait();
                }
            }
            else{
                Alert alert = new Alert(Alert.AlertType.WARNING);
                alert.setTitle("Register Failed!");
                alert.setHeaderText("Not found roomer name");

                alert.showAndWait();
            }
        }
        else {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Register Failed!");
            alert.setHeaderText("Not found this room.");
            alert.setContentText("Please Check room number\n And try again");

            alert.showAndWait();
        }
    }

   //set style
    private void setStyleBt(Button bt1,Button bt2,Button bt3,Button bt4){
        bt1.setOnMouseExited(event -> bt1.setStyle(tabhover));
        bt2.setStyle(tabdefault);
        bt2.setOnMouseEntered(event -> bt2.setStyle(tabhover));
        bt2.setOnMouseExited(event -> bt2.setStyle(tabdefault));
        bt3.setStyle(tabdefault);
        bt3.setOnMouseEntered(event -> bt3.setStyle(tabhover));
        bt3.setOnMouseExited(event -> bt3.setStyle(tabdefault));
        bt4.setStyle(tabdefault);
        bt4.setOnMouseEntered(event -> bt4.setStyle(tabhover));
        bt4.setOnMouseExited(event -> bt4.setStyle(tabdefault));
    }
}
