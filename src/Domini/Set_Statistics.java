package Domini;

import java.util.ArrayList;

public class Set_Statistics {
    private ArrayList<Statistic> statisticsCompressLZ78;
    private ArrayList<Statistic> statisticsDiscompressLZ78;

    public Set_Statistics(){
        statisticsCompressLZ78 = new ArrayList<Statistic>();
        statisticsDiscompressLZ78 = new ArrayList<Statistic>();
    }

    public void addCompressLZ78(Statistic s){ statisticsCompressLZ78.add(s);}
    public void addDiscompressLZ78(Statistic s){ statisticsDiscompressLZ78.add(s);}

    public Statistic getStatisticCompressLZ78(int a){ return statisticsCompressLZ78.get(a); }
    public Statistic getStatisticDiscompressLZ78(int a){ return statisticsDiscompressLZ78.get(a); }
}
