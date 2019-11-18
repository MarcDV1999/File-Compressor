package Domini;
//
//  Ctrl_BinFile.java
//  PROP Compressor Descompressor
//
//  Created by Marc Domènech.
//  Copyright © 2019 Marc Domènech. All rights reserved.
//

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Ctrl_BinFile extends BinFile {
    private int bitsPointer;
    private int bitsUltimoChar = 8;
    private int bitsHeader = 16;

    public Ctrl_BinFile(String name) {
        super(name);
    }


    // Lee el Fichero Comprimido binFile y retorna el texto decodificado en una lista de Strings
    public List<String> readBinFile() throws IOException {
        //boolean[] ArrayHeader = {false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false};
        List<String> text = new ArrayList<>();
        //System.out.println("bitspointer " + bitsPointer);
        //String text = "";
        FileInputStream in = new FileInputStream(binFileName);

        ArrayList<Boolean> b = new ArrayList<>();
        ArrayList<Boolean> ArrayUltimoChar = new ArrayList<>(bitsUltimoChar);
        ArrayList<Boolean> ArrayPointer = new ArrayList<>(bitsPointer);
        ArrayList<Boolean> ArrayHeader = new ArrayList<>(bitsHeader);
        int ultimPointer = 0, ultimChar = 0;

        for (int i = 0; i < bitsHeader; i++)ArrayHeader.add(false);
        readBooleans(in,ArrayHeader);

        bitsPointer = getDecimalNumber(ArrayHeader);
        //System.out.println("Bitspointer: " + ArrayHeader);

        // Inicializamos los Arrays
        for (int i = 0; i < bitsPointer; i++)ArrayPointer.add(false);
        for (int i = 0; i < bitsUltimoChar; i++)ArrayUltimoChar.add(false);
        for (int i = 0; i < 8; i++)b.add(false);



        //System.out.println("Array Pointer: " + ArrayPointer);
        //printBoolean(ArrayHeader);

        //System.out.println("BIts: " + bitsPointer);

        //System.out.println("hey3");
        // Mientras haya texto por leer vamos cogiendo bits, vamos decodificando y guardando en text
        while (!readBooleans(in, b)){
            //System.out.println("hey4");
            for (Boolean aBoolean : b) {
                //System.out.println("hey5");
                try {
                    //System.out.println("\tProceso: " + aBoolean);
                    //System.out.println("\tProceso: ");
                    //System.out.println("hey10");
                    if (ultimPointer == bitsPointer && ultimChar == bitsUltimoChar) {
                        //System.out.println("Guardamos en Text");
                        //System.out.println("hey7");
                        int d1 = getDecimalNumber(ArrayPointer), d2 = getDecimalNumber(ArrayUltimoChar);
                        //char s = (char)d2;
                        //text += d1; text += s;
                        Character s = (char) d2;
                        //text.add(new Pair<Integer, Character>(d1,s));
                        text.add(String.valueOf(d1));
                        text.add(String.valueOf(s));
                        //System.out.println("Pointer: " + ArrayPointer + " --> " + obtenerDecimal(ArrayPointer));
                        //System.out.println("UltimoChar: " + ArrayUltimoChar + " --> " + obtenerDecimal(ArrayUltimoChar).toString());
                        //System.out.println("-------------------------");
                        ultimPointer = ultimChar = 0;
                    }
                    if (ultimPointer < bitsPointer) {
                        //System.out.println("hey8");
                        //System.out.println("\t\tPointer: " + ArrayPointer);
                        ArrayPointer.set(ultimPointer, aBoolean);
                        //System.out.println("hey9");
                        ultimPointer++;
                        //System.out.println("\t\t\tArrayPointer: " + ArrayPointer);
                    } else if (ultimChar < bitsUltimoChar) {
                        //System.out.println("hey11");
                        //System.out.println("\t\tUltimChar: " + b.get(i));
                        ArrayUltimoChar.set(ultimChar, aBoolean);
                        //System.out.println("hey12");
                        ultimChar++;
                        // System.out.println("\t\t\tArrayChar: " + ArrayUltimoChar);
                    }
                } catch (Exception e) {
                    System.out.println("Algo ha ido mal: " + e);
                }
            }
        }
        // Vuelvo a poner el if por si los arrays no estan vacios, si no lo pongo podria desaparecer el ultimo caracter
        //System.out.println("UltimPointer: " + ultimPointer + " ultimChar: " + ultimChar);
        if(ultimPointer > 0 && ultimChar > 0){
            readBooleans(in, b);readBooleans(in, b);
            int d1 = getDecimalNumber(ArrayPointer) ,d2 = getDecimalNumber(ArrayUltimoChar);
            Character s = (char)d2;
            //System.out.println("d1: " + d1 + "," + s);
            text.add(String.valueOf(d1));text.add(String.valueOf(s));
            //System.out.println("Pointer: " + ArrayPointer + " --> " + obtenerDecimal(ArrayPointer));
            //System.out.println("UltimoChar: " + ArrayUltimoChar + " --> " + obtenerDecimal(ArrayUltimoChar).toString());
        }

        //System.out.println("Pointer:::::: " + ArrayPointer + " --> " + obtenerDecimal(ArrayPointer));
        //System.out.println("UltimoChar::::::: " + ArrayUltimoChar + " --> " + obtenerDecimal(ArrayUltimoChar).toString());
        //System.out.println("Texto Decodificado: " + text);
        //System.out.println("The file size was " + new File(writeFileName).length()+" bytes.");
        in.close();
        return text;
    }

    // Codifica el texto eficientemente en binario y lo escribe en el Fichero Binario writeFileName
    public void writeBinFile(List textCodifiedASCII, int numFrases) throws IOException{ // Se adapta a los bits que necesita cada cosa
        FileOutputStream out = new FileOutputStream(binFileName);
        bitsPointer = Math.max((int)Math.ceil((Math.log(numFrases)/Math.log(2))),2);
        //System.out.println("Bits necesaris " + bitsPointer);
        ArrayList<Boolean> arrayToWrite = new ArrayList<Boolean>();
        int bitsToWrite, bitsQueSobran; //Cuantos bits usaremos para el pointer
        String binarioObtenido;
        String s;
        boolean fin = false;
        // Header
        binarioObtenido = getBinaryNumber(bitsPointer);
        //System.out.println("Binario Obtenido  " + binarioObtenido);
        bitsQueSobran = bitsHeader-binarioObtenido.length();
        for (int i = 0; i < bitsQueSobran; i++) arrayToWrite.add(false);
        //System.out.println("Array:  " + arrayToWrite);
        //System.out.println("binsize:  " + binarioObtenido.length());


        for (int i = 0; i < bitsHeader - bitsQueSobran; i++) {
            if(arrayToWrite.size() == 8){
                //System.out.println(arrayToWrite);
                writeBooleans(out, arrayToWrite);
                arrayToWrite.clear();
            }
            if (binarioObtenido.charAt(i) == '1') arrayToWrite.add(true);
            else if (binarioObtenido.charAt(i) == '0') arrayToWrite.add(false);
        }

        // Fi Header


        // Empezamos a Escribir el texto
        for (int c = 0; c < textCodifiedASCII.size() && !fin;c++) {         // Leemos todo el texto codificado en ascii
            //System.out.println("Frase: " + textCodifiedASCII.get(c));
            //for (int c = 0; c < 2 && !fin;c++) {
            s = textCodifiedASCII.get(c).toString();
            if (c % 2 == 0) { // Codificamos el numero
                binarioObtenido = getBinaryNumber(Integer.valueOf(s)); // Numero en Binari
                bitsQueSobran = bitsPointer - binarioObtenido.length();      // bits que tendremos que rellenar con 0
                //System.out.println("Bits binario1 --> " + binarioObtenido + " - " + s);
                bitsToWrite = bitsPointer;
            }
            else{ // Codificamos Char
                binarioObtenido = getBinaryNumber((int)s.charAt(0));
                bitsQueSobran = bitsUltimoChar - binarioObtenido.length();
                //System.out.println("Bits binario2 --> "+ binarioObtenido);
                bitsToWrite = bitsUltimoChar;
            }
            if (bitsQueSobran < 0) {
                System.out.println("----> Este Texto: " + s + " no se puede codificar con " + bitsPointer + " bits!! Faltan " + bitsQueSobran + " bits");
                fin=true;
            }

            else {
                //System.out.println("Sobran " + bitsQueSobran + " he de escriure el num " + binarioObtenido + " amb " + bitsToWrite);
                for (int i = 0; i < bitsQueSobran; i++) { // Rellenar Huecos
                    if(arrayToWrite.size() == 8){
                        //System.out.println(arrayToWrite);
                        writeBooleans(out, arrayToWrite);
                        arrayToWrite.clear();
                    }
                    arrayToWrite.add(false);
                    //System.out.println("Mes");
                }
                //System.out.println("------");
                for (int i = 0; i < bitsToWrite - bitsQueSobran; i++) {
                    if(arrayToWrite.size() == 8){
                        //System.out.println(arrayToWrite);
                        writeBooleans(out, arrayToWrite);
                        arrayToWrite.clear();
                    }
                    if (binarioObtenido.charAt(i) == '1') arrayToWrite.add(true);
                    else if (binarioObtenido.charAt(i) == '0') arrayToWrite.add(false);
                }
            }
        }
        writeBooleans(out, arrayToWrite);
    }

}
