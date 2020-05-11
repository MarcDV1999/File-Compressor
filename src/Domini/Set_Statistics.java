package Domini;

import java.util.ArrayList;

public class Set_Statistics {
    private ArrayList<Statistic> statisticsCompressLZ78;
    private ArrayList<Statistic> statisticsDiscompressLZ78;

    private ArrayList<Statistic> statisticsCompressLZSS;
    private ArrayList<Statistic> statisticsDiscompressLZSS;

    public Set_Statistics(){
        statisticsCompressLZ78 = new ArrayList<Statistic>();
        statisticsDiscompressLZ78 = new ArrayList<Statistic>();
        statisticsCompressLZSS = new ArrayList<Statistic>();
        statisticsDiscompressLZSS = new ArrayList<Statistic>();
    }

    //Afegeix una estadistica a la llista de estadistiques de compressio amb el algorisme LZ78
    public void addCompressLZ78(Statistic s){ statisticsCompressLZ78.add(s);}

    //Afegeix una estadistica a la llista de estadistiques de descompressio amb el algorisme LZ78
    public void addDiscompressLZ78(Statistic s){ statisticsDiscompressLZ78.add(s);}


    //Afegeix una estadistica a la llista de estadistiques de compressio amb el algorisme LZ78
    public void addCompressLZSS(Statistic s){ statisticsCompressLZSS.add(s);}

    //Afegeix una estadistica a la llista de estadistiques de descompressio amb el algorisme LZ78
    public void addDiscompressLZSS(Statistic s){ statisticsDiscompressLZSS.add(s);}



    //Retorna la llista estadistiques al comprimir amb el algorisme LZ78
    public Statistic getStatisticCompressLZ78(int a){ return statisticsCompressLZ78.get(a); }

    //Retorna la llista estadistiques al descomprimir amb el algorisme LZ78
    public Statistic getStatisticDiscompressLZ78(int a){ return statisticsDiscompressLZ78.get(a); }



    //Retorna la llista estadistiques al comprimir amb el algorisme LZ78
    public Statistic getStatisticCompressLZSS(int a){ return statisticsCompressLZSS.get(a); }

    //Retorna la llista estadistiques al descomprimir amb el algorisme LZ78
    public Statistic getStatisticDiscompressLZSS(int a){ return statisticsDiscompressLZSS.get(a); }




    //Retorna Valor Mitja de Vel al comprimir amb el algorisme LZ78
    public double getCompressLZ78MediumVel(){
        double medium = 0;
        for (Statistic s : statisticsCompressLZ78){
            medium += s.getVel();
        }
        if(statisticsCompressLZ78.size() == 0) return 0;
        else return medium/statisticsCompressLZ78.size();
    }

    //Retorna Valor Mitja de Vel al descomprimir amb el algorisme LZ78
    public double getDiscompressLZ78MediumVel(){
        double medium = 0;
        for (Statistic s : statisticsDiscompressLZ78){
            medium += s.getVel();
        }
        if(statisticsDiscompressLZ78.size() == 0) return 0;
        else return medium/statisticsDiscompressLZ78.size();
    }

    //Retorna Valor Mitja de Ratio al comprimir amb el algorisme LZ78
    public double getCompressLZ78MediumRatio(){
        double medium = 0;
        for (Statistic s : statisticsCompressLZ78){
            medium += s.getRatio();
        }
        if(statisticsCompressLZ78.size() == 0) return 0;
        else return medium/statisticsCompressLZ78.size();
    }

    //Retorna Valor Mitja de Ratio al descomprimir amb el algorisme LZ78
    public double getDiscompressLZ78MediumRatio(){
        double medium = 0;
        for (Statistic s : statisticsDiscompressLZ78){
            medium += s.getRatio();
        }
        if(statisticsDiscompressLZ78.size() == 0) return 0;
        else return medium/statisticsDiscompressLZ78.size();
    }


    //Retorna Valor Mitja de Vel al comprimir amb el algorisme LZ78
    public double getCompressLZSSMediumVel(){
        double medium = 0;
        for (Statistic s : statisticsCompressLZSS){
            medium += s.getVel();
        }
        if(statisticsCompressLZSS.size() == 0) return 0;
        else return medium/statisticsCompressLZSS.size();
    }

    //Retorna Valor Mitja de Vel al descomprimir amb el algorisme LZ78
    public double getDiscompressLZSSMediumVel(){
        double medium = 0;
        for (Statistic s : statisticsDiscompressLZSS){
            medium += s.getVel();
        }
        if(statisticsDiscompressLZSS.size() == 0) return 0;
        else return medium/statisticsDiscompressLZSS.size();
    }

    //Retorna Valor Mitja de Ratio al comprimir amb el algorisme LZ78
    public double getCompressLZSSMediumRatio(){
        double medium = 0;
        for (Statistic s : statisticsCompressLZSS){
            medium += s.getRatio();
        }
        if(statisticsCompressLZSS.size() == 0) return 0;
        else return medium/statisticsCompressLZSS.size();
    }

    //Retorna Valor Mitja de Ratio al descomprimir amb el algorisme LZ78
    public double getDiscompressLZSSMediumRatio(){
        double medium = 0;
        for (Statistic s : statisticsDiscompressLZSS){
            medium += s.getRatio();
        }
        if(statisticsDiscompressLZSS.size() == 0) return 0;
        else return medium/statisticsDiscompressLZSS.size();
    }

}
