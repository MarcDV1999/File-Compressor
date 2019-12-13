package Presentacio;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import sun.applet.Main;

import javax.swing.*;
import java.awt.*;
import java.awt.image.ImageObserver;
import java.awt.image.ImageProducer;
import java.net.URL;

public class Interfaz extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception{
        Parent root = FXMLLoader.load(getClass().getResource("sample.fxml"));
        primaryStage.setTitle("Compressor");

        primaryStage.getIcons().add(new Image("Presentacio/Images/fitxerZip.png"));

        primaryStage.initStyle(StageStyle.UNIFIED);
        Scene scene = new Scene(root);
        scene.setFill(Color.TRANSPARENT);
        primaryStage.setScene(scene);
        primaryStage.show();
    }
}
