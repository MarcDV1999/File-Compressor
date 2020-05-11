package Domini;
//
//  Driver_File.java
//  PROP Compressor Descompressor
//
//  Created by Marc Domènech.
//  Copyright © 2019 Marc Domènech. All rights reserved.
//

import java.util.Scanner;

public class Driver_Set_Statistics {

    public static void main (String [] args) {
        int estado = 0, fin = 1000;
        String nomFitxer, nom;
        Scanner sc = new Scanner(System.in);
        Set_Statistics compressLZ78 = new Set_Statistics();
        Set_Statistics compressLZSS = new Set_Statistics();
        Set_Statistics discompressLZ78 = new Set_Statistics();
        Set_Statistics discompressLZSS = new Set_Statistics();

        while (estado != fin) {
            System.out.println("Driver File:");
            System.out.println("\t1. Afegir Estadistica Compressio LZ78");
            System.out.println("\t2. Afegir Estadistica Descompressio LZ78");
            System.out.println("\t3. Afegir Estadistica Compressio LZSS");
            System.out.println("\t4. Afegir Estadistica Descompressio LZSS");

            System.out.println("\t5. Consultar Velocitat Mitjana Compressio LZ78");
            System.out.println("\t6. Consultar Velocitat Mitjana Desompressio LZ78");
            System.out.println("\t7. Consultar Velocitat Mitjana Compressio LZSS");
            System.out.println("\t8. Consultar Velocitat Mitjana Descompressio LZSS");

            System.out.println("\t9. Consultar Ratio Mitja Compressio LZ78");
            System.out.println("\t10. Consultar Ratio Mitja Desompressio LZ78");
            System.out.println("\t11. Consultar Ratio Mitja Compressio LZSS");
            System.out.println("\t12. Consultar Ratio Mitja Descompressio LZSS");
            System.out.println("\t13. Consultar Ratio Mitja Descompressio LZSS");


            System.out.println("Introdueixi un numero: ");
            estado = sc.nextInt();

            switch (estado) {
                case 1:
                    try {
                        System.out.println("----> Indica el temps d'execució en ms (Exemple: 200): ");
                        String tempsExe = sc.next();
                        System.out.println("----> Indica la mida del Fitxer original en Bytes(Exemple: 23): ");
                        String midaIni = sc.next();
                        System.out.println("----> Indica la mida del Fitxer Final en Bytes(Exemple: 56): ");
                        String midaFi = sc.next();
                        compressLZ78.addCompressLZ78(new Statistic(Integer.valueOf(tempsExe), Integer.valueOf(midaIni), Integer.valueOf(midaFi)));
                        System.out.println("S'ha afegit correctament: ");
                    }
                    catch (Exception e){
                        System.out.println("Hi ha hagut un error al afegir");
                    }
                    break;
                case 2:
                    try {
                        System.out.println("----> Indica el temps d'execució en ms (Exemple: 200): ");
                        String tempsExe = sc.next();
                        System.out.println("----> Indica la mida del Fitxer original en Bytes(Exemple: 23): ");
                        String midaIni = sc.next();
                        System.out.println("----> Indica la mida del Fitxer Final en Bytes(Exemple: 56): ");
                        String midaFi = sc.next();
                        discompressLZ78.addDiscompressLZ78(new Statistic(Integer.valueOf(tempsExe), Integer.valueOf(midaIni), Integer.valueOf(midaFi)));
                        System.out.println("S'ha afegit correctament: ");
                    }
                    catch (Exception e){
                        System.out.println("Hi ha hagut un error al afegir");
                    }
                    break;
                case 3:
                    try {
                        System.out.println("----> Indica el temps d'execució en ms (Exemple: 200): ");
                        String tempsExe = sc.next();
                        System.out.println("----> Indica la mida del Fitxer original en Bytes(Exemple: 23): ");
                        String midaIni = sc.next();
                        System.out.println("----> Indica la mida del Fitxer Final en Bytes(Exemple: 56): ");
                        String midaFi = sc.next();
                        compressLZSS.addCompressLZSS(new Statistic(Integer.valueOf(tempsExe), Integer.valueOf(midaIni), Integer.valueOf(midaFi)));
                        System.out.println("S'ha afegit correctament: ");
                    }
                    catch (Exception e){
                        System.out.println("Hi ha hagut un error al afegir");
                    }
                    break;
                case 4:
                    try {
                        System.out.println("----> Indica el temps d'execució en ms (Exemple: 200): ");
                        String tempsExe = sc.next();
                        System.out.println("----> Indica la mida del Fitxer original en Bytes(Exemple: 23): ");
                        String midaIni = sc.next();
                        System.out.println("----> Indica la mida del Fitxer Final en Bytes(Exemple: 56): ");
                        String midaFi = sc.next();
                        discompressLZSS.addDiscompressLZSS(new Statistic(Integer.valueOf(tempsExe), Integer.valueOf(midaIni), Integer.valueOf(midaFi)));
                        System.out.println("S'ha afegit correctament: ");
                    }
                    catch (Exception e){
                        System.out.println("Hi ha hagut un error al afegir");
                    }
                    break;
                case 5:
                        System.out.println("Velocitat Mitjana: " + compressLZ78.getCompressLZ78MediumVel());
                    break;
                case 6:
                    System.out.println("Velocitat Mitjana: " + discompressLZ78.getDiscompressLZ78MediumVel());
                    break;
                case 7:
                    System.out.println("Velocitat Mitjana: " + compressLZSS.getCompressLZSSMediumVel());
                    break;
                case 8:
                    System.out.println("Velocitat Mitjana: " + discompressLZ78.getDiscompressLZSSMediumVel());
                    break;
                case 9:
                    System.out.println("Ratio: " + compressLZ78.getCompressLZ78MediumRatio());
                    break;
                case 10:
                    System.out.println("Ratio: " + discompressLZ78.getDiscompressLZ78MediumRatio());
                    break;
                case 11:
                    System.out.println("Ratio: " + compressLZSS.getCompressLZSSMediumRatio());
                    break;
                case 12:
                    System.out.println("Ratio: " + discompressLZSS.getDiscompressLZSSMediumRatio());
                    break;
                case 13:
                    fin = 13;
                    System.out.println("Gràcies. Passi-ho bé.");
                    break;
            }
        }
    }
}
