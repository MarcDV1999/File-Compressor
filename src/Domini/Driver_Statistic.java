package Domini;
//
//  Driver_File.java
//  PROP Compressor Descompressor
//
//  Created by Marc Domènech.
//  Copyright © 2019 Marc Domènech. All rights reserved.
//

import java.util.Scanner;

public class Driver_Statistic {

    public static void main (String [] args) {
        int estado = 0, fin = 1000;
        String nomFitxer, nom;
        Scanner sc = new Scanner(System.in);
        Statistic s = new Statistic(2,3,4);

        while (estado != fin) {
            System.out.println("Driver File:");
            System.out.println("\t1. Consultar Velocitat");
            System.out.println("\t2. Consultar Temps");
            System.out.println("\t3. Consultar Ratio");
            System.out.println("\t4. Exit");
            System.out.println("Introdueixi un numero: ");
            estado = sc.nextInt();

            switch (estado) {
                case 1:
                    try {
                        System.out.println("----> Inicialitzem una estadistica a 2,3,4: ");
                        System.out.println("Velocitat: " + s.getVel());
                    }
                    catch (Exception e){
                        System.out.println("Nom de fitxer erroni, torni a intentar-ho");
                    }
                    break;
                case 2:
                    try {
                        System.out.println("----> Inicialitzem una estadistica a 2,3,4: ");
                        System.out.println("Temps: " + s.getTime());
                    }
                    catch (Exception e){
                        System.out.println("Nom de fitxer erroni, torni a intentar-ho");
                    }
                    break;
                case 3:
                    try {
                        System.out.println("----> Inicialitzem una estadistica a 2,3,4: ");
                        System.out.println("Ratio: " + s.getRatio());

                    }
                    catch (Exception e){
                        System.out.println("Nom de fitxer erroni, torni a intentar-ho");
                    }
                    break;
                case 4:
                    fin = 4;
                    System.out.println("Gràcies. Passi-ho bé.");
                    break;
            }
        }
    }
}
