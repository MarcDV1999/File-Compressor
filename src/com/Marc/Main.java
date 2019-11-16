package com.Marc;
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


import java.util.List;

public class Main {

    public static void main(String[] args) throws IOException {
	// write your code here
        String name = "FitxerDeText";
        //String s = "ABABCBABABAAA";

        LZ78 lz78 = new LZ78(name);
        //lz78.print();
        lz78.compress(name);
        lz78.discompress(name);


    }
}
