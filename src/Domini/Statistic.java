package Domini;


public class Statistic {
    private double vel; // Mbps
    private double ratio; // %
    private double time; // ms

    public Statistic(double executionTime, double fileSizeIni, double fileSizeEnd){
        this.vel = (fileSizeIni*8)/executionTime;
        this.time = executionTime;
        this.ratio = (fileSizeIni/fileSizeEnd)-1;
    }

    // Retorna el valor de Vel
    public double getVel(){ return vel; }

    //Retorna el valor de Ratio
    public double getRatio(){ return  ratio;}

    // Retorna el valor de Ratio
    public double getTime() {return time; }
}
