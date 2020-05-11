package Presentacio;

import Domini.Ctrl_Domini;
import Domini.Folder;

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


import java.io.File;
import java.io.IOException;
import java.text.DecimalFormat;


public class Controller {
    ObservableList<String> list = FXCollections.observableArrayList();

    @FXML
    private AnchorPane zipPanel;
    @FXML
    private AnchorPane statisticsPanel;
    @FXML
    private Label fileNameLabel;
    @FXML
    private Label textEstadistica;
    @FXML
    private Label infoExtensions;
    @FXML
    private Label velCompressLZ78;
    @FXML
    private Label ratioCompressLZ78;

    @FXML
    private Label velDiscompressLZ78;
    @FXML
    private Label ratioDiscompressLZ78;

    @FXML
    private ChoiceBox<String> algorithms;

    @FXML
    private TextArea visualitzacioArxiu;
    @FXML
    private Button compressButton;
    @FXML
    private Button disCompressButton;
    @FXML
    private Button selectFile;
    @FXML
    private Button selectFolder;
    @FXML
    private Button changeFile;
    @FXML
    private Button visualizeFile;

    @FXML
    private Line lineTop1, lineTop2;

    @FXML
    private ImageView zipImage;


    private File selectedFile;
    private Ctrl_Domini domini = new Ctrl_Domini();
    private int numStatisticCompressLZ78, numStatisticDiscompressLZ78, numStatisticCompressLZSS, numStatisticDiscompressLZSS;

    public void initialize() {
        loadAlgorithms();
        numStatisticCompressLZ78 = numStatisticDiscompressLZ78 = 0;
    }

    public void onZipImageClicked(MouseEvent event) {
        zipPanel.setVisible(true);
        statisticsPanel.setVisible(false);
    }

    public void onStatisticsImageClicked(MouseEvent event) {
        zipPanel.setVisible(false);
        statisticsPanel.setVisible(true);

        velCompressLZ78.setText(new DecimalFormat("###0.0").format(domini.getMediumStatisticsCompressLZ78Vel()) + " Kbps");
        ratioCompressLZ78.setText(new DecimalFormat("###0.0").format(domini.getMediumStatisticsCompressLZ78Ratio() * 100) + "%");
        velDiscompressLZ78.setText(new DecimalFormat("###0.0").format(domini.getMediumStatisticsDiscompressLZ78Vel()) + " Kbps");
        ratioDiscompressLZ78.setText(new DecimalFormat("###0.0").format(Math.abs(domini.getMediumStatisticsDiscompressLZ78Ratio() * 100)) + "%");


    }

    public void onCompressButtonClicked(MouseEvent event) throws IOException {
        String nom, tanPerCent;
        nom = selectedFile.getAbsolutePath();

        if (algorithms.getValue() == "LZ78") {
            domini.compressLZ78(nom);

            double num = (domini.getStatisticsCompressLZ78Ratio(numStatisticCompressLZ78));
            if (num >= 0) tanPerCent = new DecimalFormat("###0.0").format(num * 100) + "% menys que el fitxer original.";
            else tanPerCent = new DecimalFormat("###0.0").format(Math.abs(num * 100)) + "% més que el fitxer original.";

            String time = new DecimalFormat("###0.0").format(domini.getStatisticsCompressLZ78Time(numStatisticCompressLZ78));
            textEstadistica.setVisible(true);
            textEstadistica.setText("El fitxer " + selectedFile.getName() + " s'ha comprimit ocupant un\n" + tanPerCent + " El Temps\nde compressió ha estat de " + time + " ms.");

            numStatisticCompressLZ78++;
        }
        else {
            domini.compressLZSS(nom);
            double num = (domini.getStatisticsCompressLZSSRatio(numStatisticCompressLZSS));
            if (num >= 0) tanPerCent = new DecimalFormat("###0.0").format(num * 100) + "% menys que el fitxer original.";
            else tanPerCent = new DecimalFormat("###0.0").format(Math.abs(num * 100)) + "% més que el fitxer original.";

            String time = new DecimalFormat("###0.0").format(domini.getStatisticsCompressLZSSTime(numStatisticCompressLZSS));
            textEstadistica.setVisible(true);
            textEstadistica.setText("El fitxer " + selectedFile.getName() + " s'ha comprimit ocupant un\n" + tanPerCent + " El Temps\nde compressió ha estat de " + time + " ms.");

            numStatisticCompressLZSS++;
        }




    }

    public void onDiscompressButtonClicked(MouseEvent event) throws IOException {
        String nom, tanPerCent;
        if (!selectedFile.isDirectory())
            nom = selectedFile.getAbsolutePath().substring(0, selectedFile.getAbsolutePath().length() - 4);
        else nom = selectedFile.getAbsolutePath();

        if (algorithms.getValue() == "LZ78") {
            domini.disCompressLZ78(nom);

            double num = (domini.getStatisticsDiscompressLZ78Ratio(numStatisticDiscompressLZ78));
            if (num >= 0) tanPerCent = new DecimalFormat("###0.0").format(num * 100) + "% menys que el fitxer comprimit.";
            else tanPerCent = new DecimalFormat("###0.0").format(Math.abs(num * 100)) + "% més que el fitxer comprimit.";

            String time = new DecimalFormat("###0.0").format(domini.getStatisticsDiscompressLZ78Time(numStatisticDiscompressLZ78));
            textEstadistica.setVisible(true);
            textEstadistica.setText("El fitxer " + selectedFile.getName() + " s'ha descomprimit ocupant un\n" + tanPerCent + " El Temps\nde descompressió ha estat de " + time + " ms.");
            numStatisticDiscompressLZ78++;
        }
        else {
            String pathChanged = selectedFile.getAbsolutePath();
            pathChanged = pathChanged.substring(0,pathChanged.length()-14);
            domini.disCompressLZSS(pathChanged+".txt");
            double num = (domini.getStatisticsDiscompressLZSSRatio(numStatisticDiscompressLZSS));
            if (num >= 0) tanPerCent = new DecimalFormat("###0.0").format(num * 100) + "% menys que el fitxer comprimit.";
            else tanPerCent = new DecimalFormat("###0.0").format(Math.abs(num * 100)) + "% més que el fitxer comprimit.";

            String time = new DecimalFormat("###0.0").format(domini.getStatisticsDiscompressLZSSTime(numStatisticDiscompressLZSS));
            textEstadistica.setVisible(true);
            textEstadistica.setText("El fitxer " + selectedFile.getName() + " s'ha descomprimit ocupant un\n" + tanPerCent + " El Temps\nde descompressió ha estat de " + time + " ms.");
            numStatisticDiscompressLZSS++;
        }




    }

    public void onVisualizeButtonClicked(MouseEvent event) throws IOException {
        String nom = selectedFile.getAbsolutePath().substring(0, selectedFile.getAbsolutePath().length() - 4);
        String pathFile = selectedFile.getAbsolutePath();
        String pathDiscompressed = "";
        Domini.File f1 = new Domini.File(pathFile);

        if (algorithms.getValue() == "LZ78") pathDiscompressed = domini.disCompressLZ78(nom);
        else domini.disCompressLZSS(pathFile);

        File f = new File(pathDiscompressed);
        try {

            if (f.isDirectory()) {
                Domini.Folder folder = new Folder(pathDiscompressed);
                //visualitzacioArxiu.setText(f1.readFile(nom + ".txt"));
                String a = folder.readFolderFiles(pathDiscompressed, 0);
                System.out.println(folder.getAbsolutePath());
                visualitzacioArxiu.setText(a);
                folder.deleteFolder(folder);
            } else {
                if(algorithms.getValue() == "LZ78"){
                    f = new Domini.File(nom + ".txt");
                    visualitzacioArxiu.setText(f1.readFile(nom + ".txt"));
                    if (!f.delete()) throw new IOException("No se ha podido borrar " + f.getAbsolutePath());
                }
                else {
                    pathFile = pathFile.substring(0,pathFile.length()-14);
                    f = new Domini.File(pathFile + "Compressed_new.txt");
                    visualitzacioArxiu.setText(f1.readFile(pathFile + ".txt"));
                    if (!f.delete()) throw new IOException("No se ha podido borrar " + f.getAbsolutePath());

                }


            }
        }catch (Exception e){}

    }

    public void onSelectFileClicked(MouseEvent event) {

        FileChooser fileChooser = new FileChooser();
        selectedFile = fileChooser.showOpenDialog(null);

        fileNameLabel.setText(selectedFile.getName());
        algorithms.setVisible(true);
        compressButton.setVisible(true);
        selectFile.setVisible(false);
        selectFolder.setVisible(false);
        fileNameLabel.setVisible(true);
        changeFile.setVisible(true);


        String tipus = selectedFile.getAbsolutePath().substring(selectedFile.getAbsolutePath().length() - 3, selectedFile.getAbsolutePath().length());
        if (tipus.equals("bin") || selectedFile.getAbsolutePath().endsWith("Compressed.txt")) {
            lineTop1.setStroke(Color.WHITE);
            lineTop2.setStroke(Color.WHITE);
            visualizeFile.setVisible(true);
            disCompressButton.setVisible(true);
            compressButton.setVisible(false);
            infoExtensions.setVisible(false);
            zipImage.setImage(new Image("Presentacio/Images/imatgeFitxerADescomprimir.png"));
            algorithms.setValue(tipus.equals("bin") ? "LZ78" : "LZSS");
        }else if (tipus.equals("txt") || selectedFile.isDirectory()) {
            lineTop1.setStroke(Color.WHITE);
            lineTop2.setStroke(Color.WHITE);
            disCompressButton.setVisible(false);
            visualizeFile.setVisible(false);
            compressButton.setVisible(true);
            infoExtensions.setVisible(false);
            zipImage.setImage(new Image("Presentacio/Images/imatgeFitxerAComprimir.png"));
            algorithms.setValue("LZ78");
        }  else {
            lineTop1.setStroke(Color.GRAY);
            lineTop2.setStroke(Color.GRAY);
            disCompressButton.setVisible(false);
            visualizeFile.setVisible(false);
            compressButton.setVisible(false);
            algorithms.setVisible(false);
            infoExtensions.setVisible(true);
            fileNameLabel.setText("Fitxer Invalid");
        }
    }

    public void onSelectFolderClicked(MouseEvent event) {

        DirectoryChooser fileChooser = new DirectoryChooser();
        selectedFile = fileChooser.showDialog(null);

        fileNameLabel.setText(selectedFile.getName());
        algorithms.setVisible(true);
        compressButton.setVisible(true);
        selectFile.setVisible(false);
        selectFolder.setVisible(false);
        fileNameLabel.setVisible(true);
        changeFile.setVisible(true);
        algorithms.setValue("LZ78");

        lineTop1.setStroke(Color.WHITE);
        lineTop2.setStroke(Color.WHITE);
        disCompressButton.setVisible(false);
        visualizeFile.setVisible(false);
        compressButton.setVisible(true);
        infoExtensions.setVisible(false);
        zipImage.setImage(new Image("Presentacio/Images/imatgeFitxerAComprimir.png"));
    }

    public void onChangeFileClicked(MouseEvent event) {
        lineTop1.setStroke(Color.GRAY);
        lineTop2.setStroke(Color.GRAY);
        disCompressButton.setVisible(false);
        compressButton.setVisible(false);
        visualizeFile.setVisible(false);
        algorithms.setVisible(false);
        selectFile.setVisible(true);
        selectFolder.setVisible(true);
        fileNameLabel.setVisible(false);
        textEstadistica.setVisible(false);
        infoExtensions.setVisible(true);
    }

    private void loadAlgorithms() {
        list.removeAll();
        String a = "LZ78";
        String b = "LZSS";
        list.addAll(a, b);
        algorithms.getItems().addAll(list);
    }
}
