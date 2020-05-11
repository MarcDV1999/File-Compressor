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
    private  LZSS lzss = new LZSS();

    // Comprimeix el fitxer ubicat a path.
    public void compressLZ78(String path) throws IOException {
        File file = new File(path);
        lz78.compress(file);
    }

    // Descomprimeix el fitxer ubicat a path i retorna path
    public String disCompressLZ78(String path) throws IOException {
        Ctrl_BinFile binFile = new Ctrl_BinFile(path);
        lz78.discompress(binFile);
        return path;
    }

    // Comprimeix el fitxer ubicat a path.
    public void compressLZSS(String nom) throws IOException {
        File file = new File(nom);
        lzss.compress(file);
    }

    // Descomprimeix el fitxer ubicat a path.
    public void disCompressLZSS(String path) throws IOException {
        File file = new File(path);
        CompressedFile c = lzss.compress(file);
        System.out.println("c:"+ c.getAbsolutePath() + " file: " + file.getAbsolutePath());
        lzss.decodify(c);
        File fileToDelete = new File(c.getAbsName()+ "Compressed.txt");
        System.out.println("filerodelete:"+ fileToDelete.getAbsolutePath());
        fileToDelete.delete();

    }


    // Retorna el ratio de Compressio(LZ78) de la estadistica que trobem a la posicio index de la llista de estadistiques.
    public double getStatisticsCompressLZ78Ratio(int index){
        Set_Statistics s = lz78.getStatistics();
        return s.getStatisticCompressLZ78(index).getRatio();
    }

    // Retorna el temps de Compressio(LZ78) de la estadistica que trobem a la posicio index de la llista de estadistiques.
    public double getStatisticsCompressLZ78Time(int index){
        Set_Statistics s = lz78.getStatistics();

        return s.getStatisticCompressLZ78(index).getTime();
    }

    // Retorna el ratio de Descompressio(LZ78) de la estadistica que trobem a la posicio index de la llista de estadistiques.
    public double getStatisticsDiscompressLZ78Ratio(int index){
        Set_Statistics s = lz78.getStatistics();

        return s.getStatisticDiscompressLZ78(index).getRatio();
    }

    // Retorna el temps de Descompressio(LZ78) de la estadistica que trobem a la posicio index de la llista de estadistiques.
    public double getStatisticsDiscompressLZ78Time(int index){
        Set_Statistics s = lz78.getStatistics();
        return s.getStatisticDiscompressLZ78(index).getTime();
    }

    // Retorna la Velocitat mitja de Descompressio(LZ78).
    public double getMediumStatisticsDiscompressLZ78Vel(){
        Set_Statistics s = lz78.getStatistics();
        return s.getDiscompressLZ78MediumVel();
    }

    // Retorna el ratio mitja de Descompressio(LZ78).
    public double getMediumStatisticsDiscompressLZ78Ratio(){
        Set_Statistics s = lz78.getStatistics();
        return s.getDiscompressLZ78MediumRatio();
    }

    // Retorna la Velocitat mitja de Compressio(LZ78).
    public double getMediumStatisticsCompressLZ78Vel(){
        Set_Statistics s = lz78.getStatistics();
        return s.getCompressLZ78MediumVel();
    }

    // Retorna el ratio mitja de Compressio(LZ78).
    public double getMediumStatisticsCompressLZ78Ratio(){
        Set_Statistics s = lz78.getStatistics();
        return s.getCompressLZ78MediumRatio();
    }





    // Retorna el ratio de Compressio(LZSS) de la estadistica que trobem a la posicio index de la llista de estadistiques.
    public double getStatisticsCompressLZSSRatio(int index){
        Set_Statistics s = lzss.getStatistics();
        return s.getStatisticCompressLZSS(index).getRatio();
    }

    // Retorna el temps de Compressio(LZSS) de la estadistica que trobem a la posicio index de la llista de estadistiques.
    public double getStatisticsCompressLZSSTime(int index){
        Set_Statistics s = lzss.getStatistics();

        return s.getStatisticCompressLZSS(index).getTime();
    }

    // Retorna el ratio de Descompressio(LZSS) de la estadistica que trobem a la posicio index de la llista de estadistiques.
    public double getStatisticsDiscompressLZSSRatio(int index){
        Set_Statistics s = lzss.getStatistics();

        return s.getStatisticDiscompressLZSS(index).getRatio();
    }

    // Retorna el temps de Descompressio(LZSS) de la estadistica que trobem a la posicio index de la llista de estadistiques.
    public double getStatisticsDiscompressLZSSTime(int index){
        Set_Statistics s = lzss.getStatistics();
        return s.getStatisticDiscompressLZSS(index).getTime();
    }

    // Retorna la Velocitat mitja de Descompressio(LZSS).
    public double getMediumStatisticsDiscompressLZSSVel(){
        Set_Statistics s = lzss.getStatistics();
        return s.getDiscompressLZSSMediumVel();
    }

    // Retorna el ratio mitja de Descompressio(LZSS).
    public double getMediumStatisticsDiscompressLZSSRatio(){
        Set_Statistics s = lzss.getStatistics();
        return s.getDiscompressLZSSMediumRatio();
    }

    // Retorna la Velocitat mitja de Compressio(LZSS).
    public double getMediumStatisticsCompressLZSSVel(){
        Set_Statistics s = lzss.getStatistics();
        return s.getCompressLZSSMediumVel();
    }

    // Retorna el ratio mitja de Compressio(LZSS).
    public double getMediumStatisticsCompressLZSSRatio(){
        Set_Statistics s = lzss.getStatistics();
        return s.getCompressLZSSMediumRatio();
    }


}
