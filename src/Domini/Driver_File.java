package Domini;
//
//  Driver_File.java
//  PROP Compressor Descompressor
//
//  Created by Marc Domènech.
//  Copyright © 2019 Marc Domènech. All rights reserved.
//

import java.util.Scanner;

public class Driver_File {

    public static void main (String [] args) {
        int estado = 0, fin = 1000;
        String nomFitxer, nom;
        Scanner sc = new Scanner(System.in);
        TextFile file;

        while (estado != fin) {
            System.out.println("Driver File:");
            System.out.println("\t1. Consultar Nom del Fitxer sense extensió");
            System.out.println("\t2. Exit");
            System.out.println("Introdueixi un numero: ");
            estado = sc.nextInt();

            switch (estado) {
                case 1:
                    try {
                        System.out.println("----> Introdueixi el nom del Fitxer (Exemple: Fitxer.txt): ");
                        nomFitxer = sc.next();
                        nom = nomFitxer.substring(0, nomFitxer.length() - 4);
                        file = new TextFile(nom);
                        System.out.println("Nom del fitxer sense extensió: " + file.getName());
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
