package Domini;
//
//  Driver_File.java
//  PROP Compressor Descompressor
//
//  Created by Marc Domènech.
//  Copyright © 2019 Marc Domènech. All rights reserved.
//

import java.util.Scanner;

public class Driver_Folder {

    public static void main (String [] args) {
        int estado = 0, fin = 1000;
        String nomFitxer, nom;
        Scanner sc = new Scanner(System.in);
        Folder folder;

        while (estado != fin) {
            System.out.println("Driver File:");
            System.out.println("\t1. Llegir fitxers de la carpeta");
            System.out.println("\t2. Borrar Carpeta i el seu contingut");
            System.out.println("\t3. Consultar mida de la carpeta");
            System.out.println("\t4. Consultar Llistat de fitxers que conte la carpeta");
            System.out.println("\t5. Exit");
            System.out.println("Introdueixi un numero: ");
            estado = sc.nextInt();

            switch (estado) {
                case 1:

                    try {
                        System.out.println("----> Introdueixi el nom de la carpeta (Exemple: carpeta): ");
                        nomFitxer = sc.next();
                        folder = new Folder(nomFitxer);
                        System.out.println("Contingut: " + folder.readFolderFiles(folder.getAbsolutePath(),0));
                    }
                    catch (Exception e){
                        System.out.println("Nom de fitxer erroni, torni a intentar-ho");
                    }
                    break;
                case 2:
                    try {
                        System.out.println("----> Introdueixi el nom de la carpeta (Exemple: Carpeta): ");
                        nomFitxer = sc.next();
                        folder = new Folder(nomFitxer);
                        folder.deleteFolder(folder);
                        System.out.println("carpeta borrada: ");
                    }
                    catch (Exception e){
                        System.out.println("error al borra, torni a intentar-ho");
                    }
                    break;
                case 3:
                    try {
                        System.out.println("----> Introdueixi el nom de la carpeta (Exemple: carpeta): ");
                        String text = sc.next();
                        folder = new Folder(text);
                        System.out.println("Mida de la carpeta: " + folder.getSize());
                    }
                    catch (Exception e){
                        System.out.println("Nom de fitxer erroni, torni a intentar-ho");
                    }
                    break;
                case 4:
                    try {
                        System.out.println("----> Introdueixi el nom de la carpeta (Exemple: carpeta): ");
                        String text = sc.next();
                        folder = new Folder(text);
                        System.out.println("Fitxers: " + folder.getFiles());
                    }
                    catch (Exception e){
                        System.out.println("Nom de fitxer erroni, torni a intentar-ho");
                    }
                    break;
                case 5:
                    fin = 5;
                    System.out.println("Gràcies. Passi-ho bé.");
                    break;
            }
        }
    }
}
