package Condo.controller;

import Condo.manage.RoomSource;
import Condo.models.Room;
import Condo.models.RoomData;
import Condo.models.SuiteRoom;
import animatefx.animation.*;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;

public class ManageRoomController {
    private RoomSource roomSource;
    private RoomData roomData;
    private StaffController staffCtl;
    @FXML private Pane manageRm,addRm;
    @FXML private TextField room;
    @FXML private Button okBt,addRoom;
    @FXML private Pane roomInfor;
    @FXML private Pane deluxePn;
    @FXML private TextField room1,room2;
    @FXML private Button saveDBt;
    @FXML private Label roomNum,roomType,building,roomdata,floor;
    @FXML private Pane suitePn;
    @FXML private TextField roomer1,roomer2,roomer3,roomer4;
    @FXML private Button saveSBt,BackBt,BackBt1,SaveAdd;
    @FXML private AnchorPane manageRoom;
    @FXML private MenuButton build;
    @FXML private MenuItem buildA,buildB;
    @FXML private TextField floorText,roomnumText;
    @FXML private MenuButton type;
    @FXML private MenuItem Deluxe,Suite;



    @FXML public void initialize(){
        new BounceIn(manageRoom).play();
    }
    public void setRoomData(RoomData roomData) {
        this.roomData = roomData;
    }

    public void setRoomSource(RoomSource roomSource) {
        this.roomSource = roomSource;
    }

    //Input room number to fill room information
    @FXML private void handleRoomHome(ActionEvent event){
        if(event.getSource() == okBt){
            if(roomData.checkRoomnum(room.getText())){
                roomInfor.toFront();
                new BounceIn(manageRoom).stop();
                new BounceInRight(roomInfor).play();
                if(roomData.getRoomInform().getRoomtype().equals("Deluxe")){
                    deluxePn.toFront();
                    new Shake(deluxePn).play();
                    roomNum.setText("Room : " +roomData.getRoomInform().getRoomnum());
                    roomType.setText("Room type : " + roomData.getRoomInform().getRoomtype() + " room");
                    building.setText("Building : " + roomData.getRoomInform().getBuilding());
                    floor.setText("Floor : " + roomData.getRoomInform().getFloor());
                    roomdata.setText("1 Bedroom ,1 Bathroom");
                    //Set Textfield
                    if(!roomData.getRoomInform().getRoomer1().equals("null")) {
                        room1.setText(roomData.getRoomInform().getRoomer1());
                        room1.setDisable(true);
                    }
                    else {
                        room1.clear();
                        room1.setDisable(false);
                    }
                    if(!roomData.getRoomInform().getRoomer2().equals("null")){
                        room2.setText(roomData.getRoomInform().getRoomer2());
                        room2.setDisable(true);
                    }
                    else {
                        room2.setDisable(false);
                        room2.clear();
                    }
                }
                else if(roomData.getRoomInform().getRoomtype().equals("Suite")){
                    suitePn.toFront();
                    new Shake(suitePn).play();
                    roomNum.setText("Room : " +roomData.getRoomInform().getRoomnum());
                    roomType.setText("Room type : " + roomData.getRoomInform().getRoomtype() + " room");
                    building.setText("Building : " + roomData.getRoomInform().getBuilding());
                    floor.setText("Floor : " + roomData.getRoomInform().getFloor());
                    roomdata.setText("2 Bedrooms , 2 Bathrooms \n,1 Living room");
                    //set Textfield
                    if(!roomData.getRoomInform().getRoomer1().equals("null")) {
                        roomer1.setText(roomData.getRoomInform().getRoomer1());
                        roomer1.setDisable(true);
                    }
                    else {
                        roomer1.clear();
                        roomer1.setDisable(false);
                    }
                    if(!roomData.getRoomInform().getRoomer2().equals("null")){
                        roomer2.setText(roomData.getRoomInform().getRoomer2());
                        roomer2.setDisable(true);
                    }
                    else {
                        roomer2.clear();
                        roomer2.setDisable(false);
                    }
                    if(!((SuiteRoom)(roomData.getRoomInform())).getRoomer3().equals("null")){
                        roomer3.setText(((SuiteRoom)(roomData.getRoomInform())).getRoomer3());
                        roomer3.setDisable(true);
                    }
                    else{
                        roomer3.clear();
                        roomer3.setDisable(false);
                    }
                    if(!((SuiteRoom)(roomData.getRoomInform())).getRoomer4().equals("null")) {
                        roomer4.setText(((SuiteRoom)roomData.getRoomInform()).getRoomer4());
                        roomer4.setDisable(true);
                    }
                    else {
                        roomer4.clear();
                        roomer4.setDisable(false);
                    }
                }
            }
            else {
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Not found!");
                alert.setHeaderText(room.getText() + " not found.");
                alert.setContentText("Not found " + room.getText() +" room please check again.");

                alert.showAndWait();
            }
        }
        else if(event.getSource() == addRoom){
            addRm.toFront();
            new FadeInRightBig(addRm).play();
        }

    }
    @FXML private void handleMenu(ActionEvent item){
        MenuItem menu = (MenuItem) item.getSource();
        if(menu == buildA){
            build.setText(menu.getText());
        }
        else if(menu == buildB){
            build.setText(menu.getText());
        }
        if(menu == Deluxe){
            type.setText(menu.getText());
            roomnumText.setText(roomData.setRoom(build.getText(),floorText.getText()));
        }
        else if(menu == Suite){
            type.setText(menu.getText());
            roomnumText.setText(roomData.setRoom(build.getText(),floorText.getText()));
        }
    }
    //Add room information and Add room
    @FXML private void handleClick(ActionEvent event){
        if(event.getSource() == BackBt || event.getSource() == BackBt1){
            manageRm.toFront();
            room.clear();
            roomnumText.clear();
            floorText.clear();
            build.setText("Building");
            type.setText("Type");
        }
        else if(event.getSource() == SaveAdd){
            if(roomnumText.getText().isEmpty() || floorText.getText().isEmpty() || build.getText().equals("Buliding") || type.getText().equals("Type")) {
                Alert alert = new Alert(Alert.AlertType.WARNING);
                alert.setTitle("Failed.");
                alert.setHeaderText("Add room failed!");
                alert.setContentText("Some field data isn't complete.");

                alert.showAndWait();
            }
            else {
                String num = build.getText().toUpperCase() + roomnumText.getText();
                String roomtype = type.getText();
                if(roomData.checkRoom(num,floorText.getText())){
                    if(roomtype.equals("Deluxe")){
                        Room room = new Room(num,floorText.getText(),roomtype,build.getText().toUpperCase(),"null","null");
                        roomData.addRoomData(room);
                        roomSource.setRoomData(roomData);
                    }
                    else if(roomtype.equals("Suite")){
                        SuiteRoom room = new SuiteRoom(num,floorText.getText(),roomtype,build.getText().toUpperCase(),"null","null","null","null");
                        roomData.addRoomData(room);
                        roomSource.setRoomData(roomData);
                    }
                    manageRm.toFront();
                    new BounceIn(manageRoom).play();
                    roomnumText.clear();
                    floorText.clear();
                    build.setText("Building");
                    type.setText("Type");
                }
                else {
                    Alert alert = new Alert(Alert.AlertType.WARNING);
                    alert.setTitle("Failed.");
                    alert.setHeaderText("Add room failed!");
                    alert.setContentText("Wrong data.");

                    alert.showAndWait();
                }
            }
        }
        if(event.getSource() == saveDBt){
            if(roomData.getRoomInform().setRoomer1(room1.getText())){
                roomData.getRoomInform().setRoomer2(room2.getText());
                roomSource.setRoomData(roomData);
                room.clear();
                manageRm.toFront();
            }
            else alert();
        }
        else if(event.getSource() == saveSBt) {
            if(roomData.getRoomInform().setRoomer1(roomer1.getText())){
                if(roomData.getRoomInform().setRoomer2(roomer2.getText())){
                    if(((SuiteRoom)(roomData.getRoomInform())).setRoomer3(roomer3.getText())){
                        ((SuiteRoom)(roomData.getRoomInform())).setRoomer4(roomer4.getText());
                        roomSource.setRoomData(roomData);
                        room.clear();
                        manageRm.toFront();
                    }
                    roomSource.setRoomData(roomData);
                    room.clear();
                    manageRm.toFront();
                }
                roomSource.setRoomData(roomData);
                room.clear();
                manageRm.toFront();
            }
            else alert();
        }
    }

    private void alert(){
            Alert alert = new Alert((Alert.AlertType.ERROR));
            alert.setTitle("Manage room failed.");
            alert.setHeaderText("Field is empty");
            alert.setContentText("Please add information in field.");

            alert.showAndWait();
    }

}
