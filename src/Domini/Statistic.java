package Domini;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

public class Statistic {
    private double vel; // Mbps
    private double ratio; // %

    public Statistic(double executionTime, double fileSizeIni, double fileSizeEnd){
        this.vel = (fileSizeIni*8)/executionTime;
        System.out.println("FIle size: " + fileSizeIni + " - executionTime: " + executionTime);
        if(fileSizeIni >= fileSizeEnd) this.ratio = (fileSizeEnd/fileSizeIni) * 100;
        else this.ratio = (fileSizeIni/fileSizeEnd) * 100;

    }

    public double getVel(){return vel; }
    public double getRatio(){return  ratio;}


}
