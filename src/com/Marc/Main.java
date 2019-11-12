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

 */

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

public class Main {

    public static void main(String[] args) throws IOException {
	// write your code here
        String fileName = "FitxerDeText.txt";
        String s = "ABABCBABABAAA";
        String a = "Hola me llamo Marc";

        LZ78 lz78 = new LZ78(fileName);
        lz78.codify();
        //lz78.print();
        lz78.compress();
        //lz78.writeFile2();
        //BitWritter b = new BitWritter();

    }
}
