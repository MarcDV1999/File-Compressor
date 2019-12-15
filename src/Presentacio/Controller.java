package Presentacio;

import Domini.Ctrl_Domini;
import Domini.TextFile;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;

import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;
import javafx.stage.DirectoryChooser;
import javafx.stage.FileChooser;

import javax.swing.*;
import javax.swing.filechooser.FileSystemView;
import java.io.File;
import java.io.IOException;
import java.text.DecimalFormat;

public class Controller {
    ObservableList<String> list = FXCollections.observableArrayList();
    @FXML private ImageView arrowZip;
    @FXML private ImageView arrowStatistics;

    @FXML private AnchorPane zipPanel;
    @FXML private AnchorPane statisticsPanel;
    @FXML private Label fileNameLabel;
    @FXML private Label textEstadistica;
    @FXML private ChoiceBox<String> algorithms;

    @FXML private TextArea visualitzacioArxiu;
    @FXML private Button compressButton;
    @FXML private Button disCompressButton;
    @FXML private Button selectFile;
    @FXML private Button selectFolder;
    @FXML private Button changeFile;
    @FXML private Button visualizeFile;

    @FXML private Line lineTop1, lineTop2;

    @FXML private ImageView zipImage;


    private File selectedFile;
    private Ctrl_Domini domini = new Ctrl_Domini();
    public void onZipImageClicked(MouseEvent event){

        zipPanel.setVisible(true);
        statisticsPanel.setVisible(false);


    }

    public void onStatisticsImageClicked(MouseEvent event){

        zipPanel.setVisible(false);
        statisticsPanel.setVisible(true);


    }

    public void onCompressButtonClicked(MouseEvent event) throws IOException{
        String nom;
        //if(!selectedFile.isDirectory()) nom = selectedFile.getAbsolutePath().substring(0, selectedFile.getAbsolutePath().length() - 4);
        //else nom = selectedFile.getAbsolutePath();
        nom = selectedFile.getAbsolutePath();

        if(algorithms.getValue() == "LZ78") domini.compressLZ78(nom);
        else domini.compressLZSS(nom);
        //String tanPerCent = new DecimalFormat("#").format(domini.getStatisticsCompressLZ78Ratio());
        //String vel = new DecimalFormat("#.0").format(domini.getStatisticsCompressLZ78Vel());
        //textEstadistica.setText("El fitxer " + selectedFile.getName() + " s'ha comprimit reduint\nel fitxer en un " + tanPerCent + "% i la velocitat\nde compressió ha estat de " + vel + " Kbps.");
        textEstadistica.setText("Descomentar tema estadistiques");
    }

    public void onDiscompressButtonClicked(MouseEvent event) throws IOException {
        //Ctrl_Domini domini = new Ctrl_Domini();
        String nom;
        if(!selectedFile.isDirectory()) nom = selectedFile.getAbsolutePath().substring(0, selectedFile.getAbsolutePath().length() - 4);
        else nom = selectedFile.getAbsolutePath();
        if(algorithms.getValue() == "LZ78") domini.disCompressLZ78(nom);
        else domini.disCompressLZSS(nom);
        //String tanPerCent = new DecimalFormat("#").format(domini.getStatisticsDiscompressLZ78Ratio());
        //String vel = new DecimalFormat("#.0").format(domini.getStatisticsDiscompressLZ78Vel());
        //textEstadistica.setText("El fitxer " + selectedFile.getName() + " s'ha descomprimit ocupant\nun " + tanPerCent + "% més i la velocitat\nde descompressió ha estat de " + vel + " Kbps.");
        textEstadistica.setText("Falta descomentar tema estadistiques");
    }

    public void onVisualizeButtonClicked(MouseEvent event) throws IOException{
        //Ctrl_Domini domini = new Ctrl_Domini();
        String nom = selectedFile.getAbsolutePath().substring(0, selectedFile.getAbsolutePath().length() - 4);
        String nomNouFitxer = "";
        TextFile textFile = new TextFile(nom);
        if(algorithms.getValue() == "LZ78") nomNouFitxer = domini.disCompressLZ78(nom);
        else domini.disCompressLZSS(nom);
        visualitzacioArxiu.setText(textFile.readFile(nomNouFitxer));
        //System.out.println(nomNouFitxer);
        try {
            File f = new File(nomNouFitxer);
            f.delete();
        }
        catch (Exception e){
            System .out.println("No s'ha pogut borrar el arxiu " + nomNouFitxer);
        }
        //ScaleTransition st = new ScaleTransition(Duration.millis(2000), rect);
    }

    public void onSelectFileClicked(MouseEvent event){

         FileChooser fileChooser = new FileChooser();
         selectedFile = fileChooser.showOpenDialog(null);
        //DirectoryChooser fileChooser = new DirectoryChooser();
        //selectedFile = fileChooser.showDialog(null);

        fileNameLabel.setText(selectedFile.getName());
        algorithms.setVisible(true);
        compressButton.setVisible(true);
        selectFile.setVisible(false);
        fileNameLabel.setVisible(true);
        changeFile.setVisible(true);
        loadAlgorithms();
        algorithms.setValue("LZ78");
        String tipus = selectedFile.getAbsolutePath().substring(selectedFile.getAbsolutePath().length() - 3, selectedFile.getAbsolutePath().length());
        if(tipus.equals("txt") || selectedFile.isDirectory()) {
            lineTop1.setStroke(Color.WHITE);
            lineTop2.setStroke(Color.WHITE);
            disCompressButton.setVisible(false);
            visualizeFile.setVisible(false);
            compressButton.setVisible(true);
            zipImage.setImage(new Image("Presentacio/Images/imatgeFitxerAComprimir.png"));
        }
        else if(tipus.equals("bin")){
            lineTop1.setStroke(Color.WHITE);
            lineTop2.setStroke(Color.WHITE);
            visualizeFile.setVisible(true);
            disCompressButton.setVisible(true);
            compressButton.setVisible(false);
            zipImage.setImage(new Image("Presentacio/Images/imatgeFitxerADescomprimir.png"));
        }
        else {
            lineTop1.setStroke(Color.GRAY);
            lineTop1.setStroke(Color.GRAY);
            disCompressButton.setVisible(false);
            visualizeFile.setVisible(false);
            compressButton.setVisible(false);
            algorithms.setVisible(false);
            fileNameLabel.setText("Fitxer Invalid");
        }
    }

    public void onSelectFolderClicked(MouseEvent event){

        //FileChooser fileChooser = new FileChooser();
        //selectedFile = fileChooser.showOpenDialog(null);
        DirectoryChooser fileChooser = new DirectoryChooser();
        selectedFile = fileChooser.showDialog(null);

        fileNameLabel.setText(selectedFile.getName());
        algorithms.setVisible(true);
        compressButton.setVisible(true);
        selectFile.setVisible(false);
        fileNameLabel.setVisible(true);
        changeFile.setVisible(true);
        loadAlgorithms();
        algorithms.setValue("LZ78");
        String tipus = selectedFile.getAbsolutePath().substring(selectedFile.getAbsolutePath().length() - 3, selectedFile.getAbsolutePath().length());
        if(tipus.equals("txt") || selectedFile.isDirectory()) {
            lineTop1.setStroke(Color.WHITE);
            lineTop2.setStroke(Color.WHITE);
            disCompressButton.setVisible(false);
            visualizeFile.setVisible(false);
            compressButton.setVisible(true);
            zipImage.setImage(new Image("Presentacio/Images/imatgeFitxerAComprimir.png"));
        }
        else if(tipus.equals("bin")){
            lineTop1.setStroke(Color.WHITE);
            lineTop2.setStroke(Color.WHITE);
            visualizeFile.setVisible(true);
            disCompressButton.setVisible(true);
            compressButton.setVisible(false);
            zipImage.setImage(new Image("Presentacio/Images/imatgeFitxerADescomprimir.png"));
        }
        else {
            lineTop1.setStroke(Color.GRAY);
            lineTop1.setStroke(Color.GRAY);
            disCompressButton.setVisible(false);
            visualizeFile.setVisible(false);
            compressButton.setVisible(false);
            algorithms.setVisible(false);
            fileNameLabel.setText("Fitxer Invalid");
        }
    }

    private void loadAlgorithms(){
        list.removeAll();
        String a = "LZ78";
        String b = "LZSS";
        list.addAll(a,b);
        algorithms.getItems().addAll(list);
    }
}
