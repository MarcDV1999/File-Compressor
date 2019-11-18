package Domini;
//
//  Driver_BinFile.java
//  PROP Compressor Descompressor
//
//  Created by Marc Domènech.
//  Copyright © 2018 Marc Domènech. All rights reserved.
//

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

public class Driver_BinFile{
    public static void main (String [] args) {
        int estado = 0, fin = 1000;
        String nomFitxer, nom;
        Scanner sc = new Scanner(System.in);
        BinFile binFile;

        while (estado != fin) {
            System.out.println("Driver BinFile:");
            System.out.println("\t3. Exit");
            System.out.println("Introdueixi un numero: ");
            estado = sc.nextInt();

            switch (estado) {
                case 1:

                case 2:

                case 3:
                    fin = 3;
                    System.out.println("Gràcies. Passi-ho bé.");
                    break;
            }
        }
    }
}
