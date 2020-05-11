package Domini;

import java.io.IOException;
import java.util.Scanner;

public class Driver_CompressedFile {
    public static void main (String [] args)throws IOException {
        int estado = 0, fin = 1000;
        String nomFitxer, nom;
        Scanner sc = new Scanner(System.in);
        CompressedFile file;
        LZSS lzss;

        while (estado != fin) {
            System.out.println("Driver CompressedFile:");
            System.out.println("\t1. Consultar Resultat");
            System.out.println("\t2. Consultar Chars Codificats");
            System.out.println("\t3. Consultar Bytes Finals");
            System.out.println("\t4. Exit");
            System.out.println("Introdueixi un numero: ");
            estado = sc.nextInt();

            switch (estado) {
                case 1:
                    try {
                        System.out.println("----> Introdueixi el nom del Fitxer (Exemple: Fitxer.txt): ");
                        nomFitxer = sc.next();
                        File t = new File(nomFitxer);
                        lzss = new LZSS();
                        file = lzss.codify(t);
                        System.out.println("Resultat: " + file.getResultat());
                    }
                    catch (Exception e){
                        System.out.println("Nom de fitxer erroni, torni a intentar-ho");
                    }
                    break;
                case 2:
                    try {
                        System.out.println("----> Introdueixi el nom del Fitxer (Exemple: Fitxer.txt): ");
                        nomFitxer = sc.next();
                        File t = new File(nomFitxer);
                        lzss = new LZSS();
                        file = lzss.codify(t);
                        System.out.println("CharsCodificats: " + file.getCharscodificats());
                    }
                    catch (Exception e){
                        System.out.println("Nom de fitxer erroni, torni a intentar-ho");
                    }
                    break;
                case 3:
                    try {
                        System.out.println("----> Introdueixi el nom del Fitxer (Exemple: Fitxer.txt): ");
                        nomFitxer = sc.next();
                        File t = new File(nomFitxer);
                        lzss = new LZSS();
                        file = lzss.codify(t);
                        System.out.println("Bytes Finals: " + file.getBytesfinals());
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
