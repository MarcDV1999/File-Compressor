package Domini;
//
//  Ctrl_Domini.java
//  PROP Compressor Descompressor
//
//  Created by Marc Domènech.
//  Copyright © 2019 Marc Domènech. All rights reserved.
//

import java.io.IOException;

public class Ctrl_Domini {
    private LZ78 lz78 = new LZ78();


    public void compressLZ78(String nom) throws IOException {
        File file = new File(nom);
        lz78.compress(file);
    }
    public String disCompressLZ78(String nom) throws IOException {
        BinFile file = new BinFile(nom);
        lz78.discompress(file);
        return file.getName() + lz78.getNew_extension();
    }









    public void compressLZSS(String nom) throws IOException {
        TextFile file = new TextFile(nom);
        LZSS lzss = new LZSS(nom);
        lzss.compress(file);
    }
    public void disCompressLZSS(String nom) throws IOException {
        TextFile file = new TextFile(nom);
        LZSS lzss = new LZSS(nom);
        CompressedFile c = lzss.compress(file);
        lzss.decodify(c);

    }






    public double getStatisticsCompressLZ78Vel(){
        Set_Statistics s = lz78.getStatistics();
        //System.out.println(s.getStatistic(0).getVel());
        return s.getStatisticCompressLZ78(0).getVel();
    }
    public double getStatisticsCompressLZ78Ratio(){
        Set_Statistics s = lz78.getStatistics();
        return s.getStatisticCompressLZ78(0).getRatio();
    }

    public double getStatisticsDiscompressLZ78Vel(){
        Set_Statistics s = lz78.getStatistics();
        return s.getStatisticDiscompressLZ78(0).getRatio();
    }
    public double getStatisticsDiscompressLZ78Ratio(){
        Set_Statistics s = lz78.getStatistics();
        return s.getStatisticDiscompressLZ78(0).getRatio();
    }
}
