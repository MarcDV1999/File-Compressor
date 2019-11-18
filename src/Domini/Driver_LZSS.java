package Domini;

import java.util.Scanner;

public class Driver_LZSS {
    public static void main (String [] args) throws Exception {
        int estado = 0;
        int fin = 1000; //por poner algo...
        String nomFitxer, nom;
        LZSS lzss;
        TextFile textfile;
        CompressedFile cf;
        Scanner sc = new Scanner(System.in);

        while (estado != fin) {
            System.out.println("Driver LZSS:");
            System.out.println("\t1. Comprimir Arxiu");
            System.out.println("\t2. Decodificar Arxiu");
            System.out.println("\t3. Codificar Arxiu");
            System.out.println("\t4. Exit");

            System.out.println("Introdueixi un numero: ");
            estado = sc.nextInt();
            switch (estado) {
                case 1:
                    System.out.println("----> Introdueixi el nom del Fitxer a comprimir (Exemple: Fitxer.txt): ");
                    try {
                        nomFitxer = sc.next();
                        nom = nomFitxer.substring(0, nomFitxer.length() - 4);
                        textfile = new TextFile(nom);
                        lzss = new LZSS(nom);
                        lzss.compress(textfile);
                        System.out.println("Obre el Fitxer " + textfile.getTextFileName() + " per veure els canvis.");
                    }catch (Exception e){
                        System.out.println("Nom de fitxer erroni, torni a intentar-ho");
                    }
                    break;
                case 2:
                    System.out.println("----> Introdueixi el nom del Fitxer a decodificar (Exemple: Fitxer.txt): ");
                    try {
                        nomFitxer = sc.next();
                        nom = nomFitxer.substring(0, nomFitxer.length() - 4);
                        textfile = new TextFile(nom);
                        lzss = new LZSS(nom);
                        CompressedFile c = lzss.compress(textfile);
                        lzss.decodify(c);
                        System.out.println("Resultat: " + c.getResultat());

                    }catch (Exception e){
                        System.out.println("Nom de fitxer erroni, torni a intentar-ho");
                    }
                    break;
                case 3:
                    System.out.println("----> Introdueixi el nom del Fitxer a Codificar (Exemple: Fitxer.txt): ");
                    try {
                        nomFitxer = sc.next();
                        nom = nomFitxer.substring(0, nomFitxer.length() - 4);
                        textfile = new TextFile(nom);
                        lzss = new LZSS(nom);
                        CompressedFile c = lzss.compress(textfile);
                        lzss.decodify(c);

                        System.out.println("Resultat: " + c.getResultat());
                    }catch (Exception e){
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
