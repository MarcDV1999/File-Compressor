package Domini;
//
//  Main.java
//  PROP Compressor Descompressor
//
//  Created by Marc Domènech.
//  Copyright © 2019 Marc Domènech. All rights reserved.
//
/*
    Llibreries a utilitzar pel Profe:

    import BufferedReader
    import InputStreamReader
    import java.io.File
    java.nio.File

                           Diccionari Implementat Eficientment
    Algorisme LZ78      ---------------------------------------->      LZW

        LZ78:
            -> Pointers i Caracters s'alternen
                -> Apunten a Substring previament parsejat

        LZW:
            -> Només hi ha pointers
                -> Apunten a substring previament parsejats

    UML:Controlador -> On poso les funcionalitats mes especifiques de la clase
    UML:CLase -> Poso les funcionalitats que necessitare sempre
    Driver -> Per testejar la classe, fem un menu interactiu pel terminal on puguis provar tots els
                metodes i que es vegi com queda la cosa


 */

import java.io.IOException;
import java.io.File;


public class Main {

    public static void main(String[] args) throws IOException {

        String name = "fitxer";
        //String s = "ABABCBABABAAA";
        String route = "/Users/marcdomenech/Desktop/PROP_LZ78/src/c";


        Folder f = new Folder(route);
        //System.out.println("Parent: " + f.getParent());
        String s = "Hola MArc" + '\0' + " soy yo";
        for(int i = 0; i< s.length() ;i++){
            if(s.charAt(i) == '\0') System.out.println("Aqui");
        }

    }
}
