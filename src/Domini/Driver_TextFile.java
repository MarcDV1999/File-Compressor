package Domini;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Driver_TextFile{


    public static void main (String [] args) throws Exception {
        int estado = 0, fin = 1000;
        String nomFitxer, nom;
        Scanner sc = new Scanner(System.in);
        TextFile textFile;

        while (estado != fin) {
            System.out.println("Driver BinFile:");
            System.out.println("\t1. Llegir Fitxer de text");
            System.out.println("\t2. Escriure Fitxer de text");
            System.out.println("\t3. Consultar nom del fitxer");
            System.out.println("\t4. Exit");
            System.out.println("Introdueixi un numero: ");
            estado = sc.nextInt();



            switch (estado) {
                case 1:
                    System.out.println("----> Introdueixi el nom del Fitxer de text(Exemple: Fitxer.txt): ");
                    nomFitxer = sc.next();
                    nom = nomFitxer.substring(0,nomFitxer.length()-4);
                    textFile = new TextFile(nom);

                    String s = textFile.readFile(textFile.getTextFileName());
                    System.out.println("Lectura del Text: " + s);
                    break;
                case 2:
                    System.out.println("----> Introdueixi el nom del Fitxer de text(Exemple: Fitxer.txt): ");
                    nomFitxer = sc.next();
                    nom = nomFitxer.substring(0,nomFitxer.length()-4);
                    textFile = new TextFile(nom);

                    BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
                    System.out.println("----> Introdueixi un text (exemple: Hola em dic Marc): ");
                    String name = reader.readLine();

                    textFile.writeFile(textFile.getTextFileName(), name);
                    System.out.println("Obrir el text per veure el resultat: ");
                    break;
                case 3:
                    System.out.println("----> Introdueixi el nom del Fitxer de text(Exemple: Fitxer.txt): ");
                    nomFitxer = sc.next();
                    nom = nomFitxer.substring(0,nomFitxer.length()-4);
                    textFile = new TextFile(nom);

                    System.out.println("Nom del Fitxer: " + textFile.getTextFileName());
                    break;

                case 4:
                    fin = 4;
                    System.out.println("Gràcies. Passi-ho bé.");
                    break;
            }
        }
    }
}
