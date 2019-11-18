package Domini;
//
//  Driver_BinFile.java
//  PROP Compressor Descompressor
//
//  Created by Marc Domènech.
//  Copyright © 2018 Marc Domènech. All rights reserved.
//

import java.util.Scanner;

public class Driver_BinFile{
    public static void main (String [] args) {
        int estado = 0, fin = 1000;
        String nomFitxer, nom;
        Scanner sc = new Scanner(System.in);
        BinFile binFile;

        while (estado != fin) {
            System.out.println("Driver BinFile:");
            System.out.println("\t1. Consultar nom del fitxer amb extensió");
            System.out.println("\t2. Exit");
            System.out.println("Introdueixi un numero: ");
            estado = sc.nextInt();

            switch (estado) {
                case 1:
                    System.out.println("----> Introdueixi el nom del Fitxer (Exemple: Fitxer.bin): ");
                    try {
                        nomFitxer = sc.next();
                        nom = nomFitxer.substring(0, nomFitxer.length() - 4);
                        binFile = new BinFile(nom);
                        System.out.println("Nom del fitxer amb extensió: " + binFile.getBinFileName());
                    }
                    catch (Exception e){
                        System.out.println("Nom de fitxer erroni, torni a intentar-ho");
                    }
                    break;
                case 2:
                    fin = 2;
                    System.out.println("Gràcies. Passi-ho bé.");
                    break;

            }
        }
    }
}
