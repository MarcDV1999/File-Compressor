package Domini;

import com.sun.codemodel.internal.JFieldRef;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.Scanner;

public class Driver_File {

    public static void main (String [] args) {
        int estado = 0, fin = 1000;
        String nomFitxer, nom;
        Scanner sc = new Scanner(System.in);
        File file;

        while (estado != fin) {
            System.out.println("Driver BinFile:");
            System.out.println("\t1. Consultar Nom del Fitxer");
            System.out.println("\t2. Exit");
            System.out.println("Introdueixi un numero: ");
            estado = sc.nextInt();

            switch (estado) {
                case 1:
                    System.out.println("----> Introdueixi el nom del Fitxer (Exemple: Fitxer.txt): ");
                    nomFitxer = sc.next();
                    nom = nomFitxer.substring(0,nomFitxer.length()-4);
                    file = new File(nom);

                    System.out.println("Nom del fitxer sense extensió: " + file.getName());
                    break;
                case 2:
                    fin = 2;
                    System.out.println("Gràcies. Passi-ho bé.");
                    break;
            }
        }
    }
}
