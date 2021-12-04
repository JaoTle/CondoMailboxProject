package Condo.controller;

import Condo.manage.AdminSource;
import Condo.manage.Compare;
import Condo.manage.StaffSource;
import Condo.models.Account;
import Condo.models.Staff;
import Condo.models.StaffData;
import animatefx.animation.*;
import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.embed.swing.SwingFXUtils;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.stage.Window;
import javafx.util.Duration;

import javax.imageio.ImageIO;
import java.io.File;
import java.io.IOException;;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;


public class AdminController {

    @FXML private Button signOut,saveBut;
    @FXML private Pane chPass, createStaff;
    @FXML private AnchorPane stList,adminPage;
    @FXML private Button informBut, createBut, chPassBut, okChangeBt;
    @FXML private PasswordField oldFieldpass, newFieldPass, confirmField,passField,firmField;
    @FXML private TextField nameField,surnameField,ageField,userField;
    @FXML private Label dateTime;
    @FXML private ImageView imageView;
    @FXML private Button chooseFile;
    @FXML private TableView<Staff> Stafflist;
    @FXML private TableColumn<Staff,String> Staffname;
    @FXML private TableColumn<Staff,String> StaffUser;
    @FXML private TableColumn<Staff,String> StaffSur;
    @FXML private TableColumn<Staff,String> StaffTime;
    @FXML private Pane staffInform;
    @FXML private Label name;
    @FXML private Label surname;
    @FXML private ImageView showImg;
    @FXML private Label age;
    @FXML private Button backBtn;
    @FXML private Label lastesttime;


    private Compare compare;
    private Account admin;
    private AdminSource adminSource;
    private StaffSource staffSource;
    private Staff staff;
    private StaffData stafflist;
    private String tabhover = "-fx-background-color: #6EF3D6;\n" +
            "-fx-background-radius : 0;\n" +
            "-fx-text-fill :#48466D;";
    private String tabdefault = "-fx-background-color: #48466D;\n" +
            "-fx-background-radius : 0;\n" +
            "-fx-text-fill : white;";
    FileChooser chooser = new FileChooser();
    File file;


    public void initialize(){
        new SlideInDown(adminPage).setCycleCount(1).play();
        compare = new Compare();
        staffSource = new StaffSource("data","staffData.csv"); //เรียกไฟล์ source ของ staff
        stafflist = staffSource.getStaffData();
        //Show date and time.
        Timeline clock = new Timeline(new KeyFrame(Duration.ZERO, e -> {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("E d/MM/yyyy HH:mm:ss");
            dateTime.setText(LocalDateTime.now().format(formatter));
        }),
                new KeyFrame(Duration.seconds(1))
        );
        clock.setCycleCount(Animation.INDEFINITE);
        clock.play();
        showStaffList();
        informBut.setStyle(tabhover);
    }

    //Show information in tableview function
    private void showStaffList(){
        compare.staffLoginCompare(stafflist);
        ObservableList<Staff> staffObservableList = FXCollections.observableList(stafflist.toList());
        Staffname.setCellValueFactory(new PropertyValueFactory<>("name"));
        StaffSur.setCellValueFactory(new PropertyValueFactory<>("surName"));
        StaffUser.setCellValueFactory(new PropertyValueFactory<>("user"));
        StaffTime.setCellValueFactory(new PropertyValueFactory<>("time"));
        Stafflist.setItems(staffObservableList);

    }
    public void setAccount(Account admin) { this.admin = admin; }

    public void setSource(AdminSource adminSource){ this.adminSource = adminSource;}

    //AdminHome
    @FXML
    private void handleAdminClick(ActionEvent click) throws IOException {
        if (click.getSource() == signOut) {
            Stage stage = (Stage) signOut.getScene().getWindow();
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/home.fxml"));
            stage.setScene(new Scene(loader.load(), 1280, 768));
            HomeController home = loader.getController();
            home.initialize();
            stage.show();
        } else {
            if (click.getSource() == informBut) {
                setStyleBt(informBut,createBut,chPassBut);
                stList.toFront();
            }
            else if (click.getSource() == createBut) {
                setStyleBt(createBut,informBut,chPassBut);
                createStaff.toFront();
                new BounceIn(createStaff).setCycleCount(1).play();
            }
            else if (click.getSource() == chPassBut) {
                setStyleBt(chPassBut,informBut,createBut);
                chPass.toFront();
                new BounceIn(chPass).setCycleCount(1).play();
            }
            else if(click.getSource() == backBtn){
                stList.toFront();
            }
        }
    }

    //AdminChangePassword
    @FXML
    private void handleChangePass() {
        if (admin.changePassword(oldFieldpass.getText(), newFieldPass.getText(), confirmField.getText())) {
            adminSource.setAdminNewPassword(admin);
            stList.toFront();
            informBut.setStyle(tabhover);
            setStyleBt(informBut,chPassBut,createBut);
            oldFieldpass.clear();
            newFieldPass.clear();
            confirmField.clear();
        }
        else {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Can't change password.");
            alert.setHeaderText("Invalid data!");
            alert.setContentText("Old password wrong or \nNew password not match confirm password");

            alert.showAndWait();
        }
    }

    //Upload Image
    @FXML private void handleChoose(){
        Window stage = createStaff.getScene().getWindow();
        chooser.setTitle("Upload file");
        chooser.getExtensionFilters().addAll(new FileChooser.ExtensionFilter("image file","*.jpg","*.png","*.gif"));
        file = chooser.showOpenDialog(stage);
        Image img = new Image(file.toURI().toString());
        imageView.setImage(img);
        imageView.setPreserveRatio(true);
        imageView.setFitHeight(150);
        imageView.setFitWidth(150);
    }

    //AdminCreateStaff
    @FXML
    private void handleSaveBt() {
        Window stage = createStaff.getScene().getWindow();
        chooser.setTitle("Save file");
        chooser.setInitialDirectory(new File("data/staffimg"));
        file = chooser.showSaveDialog(stage);
        if (file != null) {
            try {
                ImageIO.write(SwingFXUtils.fromFXImage(imageView.getImage(), null), "png", file);
            } catch (IOException ex) {
                System.err.println("Don't Save" + file.getPath());
            }catch (NullPointerException ex){
                System.err.println("Don't have image");
            }
        }
        String filename = "data/staffimg/" + file.getName();
        if(!ageField.getText().isEmpty()){
            staff = new Staff(userField.getText(),passField.getText(),nameField.getText(),surnameField.getText(),Integer.parseInt(ageField.getText()),"Not yet login",filename);
            if(!staff.checkData()) alertEmpty();
            else {
                if (staff.checkField(firmField.getText()) == 1){
                    if(stafflist.checkUser(userField.getText())){
                        stafflist.addStaffAccount(staff);
                        staffSource.setStaffNewData(stafflist);
                        stList.toFront();
                        showStaffList();
                        informBut.setStyle(tabhover);
                        setStyleBt(informBut,chPassBut,createBut);
                        userField.clear();
                        passField.clear();
                        nameField.clear();
                        surnameField.clear();
                        ageField.clear();
                        firmField.clear();
                        imageView.setImage(null);
                    }
                    else {
                        Alert alert = new Alert(Alert.AlertType.ERROR);
                        alert.setTitle("Can't create account");
                        alert.setHeaderText("This username already use.");
                        alert.setContentText("Please Change username.\nAnd try again.");

                        alert.showAndWait();
                    }
                }
                else if(staff.checkField(firmField.getText()) == 2){
                    Alert alert = new Alert(Alert.AlertType.WARNING);
                    alert.setTitle("Create Staff Account Failed");
                    alert.setHeaderText("Invalid! data");
                    alert.setContentText("Confirm password doesn't match with Password \n Please try again.");

                    alert.showAndWait();
                }
            }
        }
        else alertEmpty();
    }

    //set Style
    private void setStyleBt(Button bt1,Button bt2,Button bt3){
        bt1.setOnMouseExited(event -> bt1.setStyle(tabhover));
        bt2.setStyle(tabdefault);
        bt3.setStyle(tabdefault);
        bt2.setOnMouseEntered(event -> bt2.setStyle(tabhover));
        bt2.setOnMouseExited(event -> bt2.setStyle(tabdefault));
        bt3.setOnMouseEntered(event -> bt3.setStyle(tabhover));
        bt3.setOnMouseExited(event -> bt3.setStyle(tabdefault));
    }

    //Select to show staff information
    @FXML
    private void selectedStaff(){
        Staff staff = Stafflist.getSelectionModel().getSelectedItem();
        if(staff != null){
            staffInform.toFront();
            name.setText(staff.getName());
            surname.setText(staff.getSurName());
            age.setText(String.valueOf(staff.getAge()));
            lastesttime.setText("Lastest login : " + staff.getTime());
            staffSource.showImage(staff.getImagePath(),showImg);
        }
    }
    private void alertEmpty(){
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setTitle("Create Staff Account Failed");
        alert.setHeaderText("Invalid! data");
        alert.setContentText("Some field is empty. \n Please try again.");

        alert.showAndWait();
    }
}
