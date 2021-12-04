package Condo.controller;

import Condo.models.RoomersAccountData;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.stage.Stage;

import java.io.IOException;

public class RoomerController {
    RoomersAccountData roomers;
    public void setRoomers(RoomersAccountData roomers) {
        this.roomers = roomers;
    }
    @FXML Button backBtn;
    @FXML Label text;

    @FXML public void initialize(){
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                text.setText("Welcome " + roomers.getRoomersAccount().getName());
            }
        });
    }

    @FXML private void handleBack() throws IOException {
        Stage stage = (Stage) backBtn.getScene().getWindow();
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/home.fxml"));
        stage.setScene(new Scene(loader.load(),1280,768));
        HomeController hm = loader.getController();
        stage.show();
    }
}
