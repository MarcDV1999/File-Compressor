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
import javafx.stage.FileChooser;

import java.io.File;
import java.io.IOException;
import java.util.Observable;

public class Controller {
    ObservableList<String> list = FXCollections.observableArrayList();
    @FXML private ImageView arrowZip;
    @FXML private ImageView arrowStatistics;

    @FXML private AnchorPane zipPanel;
    @FXML private AnchorPane statisticsPanel;
    @FXML private Label fileNameLabel;
    @FXML private ChoiceBox<String> algorithms;

    @FXML private TextArea visualitzacioArxiu;
    @FXML private Button compressButton;
    @FXML private Button disCompressButton;
    @FXML private Button selectFile;
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
        Ctrl_Domini domini = new Ctrl_Domini();
        String nom = selectedFile.getAbsolutePath().substring(0, selectedFile.getAbsolutePath().length() - 4);
        if(algorithms.getValue() == "LZ78") domini.compressLZ78(nom);
        else domini.compressLZSS(nom);
    }

    public void onDiscompressButtonClicked(MouseEvent event) throws IOException {
        //Ctrl_Domini domini = new Ctrl_Domini();
        String nom = selectedFile.getAbsolutePath().substring(0, selectedFile.getAbsolutePath().length() - 4);
        if(algorithms.getValue() == "LZ78") domini.disCompressLZ78(nom);
        else domini.disCompressLZSS(nom);
    }

    public void onVisualizeButtonClicked(MouseEvent event) throws IOException{
        //Ctrl_Domini domini = new Ctrl_Domini();
        String nom = selectedFile.getAbsolutePath().substring(0, selectedFile.getAbsolutePath().length() - 4);
        String nomNouFitxer = "";
        TextFile textFile = new TextFile(nom);
        System.out.println(nomNouFitxer);
        if(algorithms.getValue() == "LZ78") nomNouFitxer = domini.disCompressLZ78(nom);
        else domini.disCompressLZSS(nom);
        visualitzacioArxiu.setText(textFile.readFile(nomNouFitxer));
    }

    public void onSelectFileClicked(MouseEvent event){
        FileChooser fileChooser = new FileChooser();
        selectedFile = fileChooser.showOpenDialog(null);
        fileNameLabel.setText(selectedFile.getName());
        algorithms.setVisible(true);
        compressButton.setVisible(true);
        selectFile.setVisible(false);
        fileNameLabel.setVisible(true);
        changeFile.setVisible(true);
        loadAlgorithms();
        algorithms.setValue("LZ78");
        String tipus = selectedFile.getAbsolutePath().substring(selectedFile.getAbsolutePath().length() - 3, selectedFile.getAbsolutePath().length());
        if(tipus.equals("txt")) {
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
