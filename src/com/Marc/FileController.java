package com.Marc;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class FileController extends File{
    BitWritter bitWritter;
    private int bitsPointer;
    private int bitsHeader;
    private int bitsUltimoChar;

    public FileController(String name){
        super(name);

    }



/*
    // Lee el Fichero Comprimido y retorna el texto decodificado
    public List<String> readBinFile(String binFile) throws IOException {
        //boolean[] ArrayHeader = {false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false};
        List<String> text = new ArrayList<>();
        //System.out.println("bitspointer " + bitsPointer);
        //String text = "";
        FileInputStream in = new FileInputStream(binFile);

        ArrayList<Boolean> b = new ArrayList<>();
        ArrayList<Boolean> ArrayUltimoChar = new ArrayList<>(bitsUltimoChar);
        ArrayList<Boolean> ArrayPointer = new ArrayList<>(bitsPointer);
        ArrayList<Boolean> ArrayHeader = new ArrayList<>(bitsHeader);
        int ultimPointer = 0, ultimChar = 0;

        // Inicializamos los Arrays
        for (int i = 0; i < bitsPointer; i++)ArrayPointer.add(false);
        for (int i = 0; i < bitsUltimoChar; i++)ArrayUltimoChar.add(false);
        for (int i = 0; i < bitsHeader; i++)ArrayHeader.add(false);
        for (int i = 0; i < 8; i++)b.add(false);
        readBooleans(in,ArrayHeader);
        //System.out.println("Array Header: " + ArrayHeader);
        //printBoolean(ArrayHeader);
        bitsPointer = getDecimal(ArrayHeader);
        //System.out.println("BIts: " + bitsPointer);


        // Mientras haya texto por leer vamos cogiendo bits, vamos decodificando y guardando en text
        while (!readBooleans(in, b)){
            for (Boolean aBoolean : b) {
                try {
                    //System.out.println("\tProceso: " + b.get(i));
                    //System.out.println("\tProceso: ");
                    if (ultimPointer == bitsPointer && ultimChar == bitsUltimoChar) {
                        //System.out.println("Guardamos en Text");
                        int d1 = getDecimal(ArrayPointer), d2 = getDecimal(ArrayUltimoChar);
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
                        //System.out.println("\t\tPointer: " + b.get(i));
                        ArrayPointer.set(ultimPointer, aBoolean);
                        ultimPointer++;
                        //System.out.println("\t\t\tArrayPointer: " + ArrayPointer);
                    } else if (ultimChar < bitsUltimoChar) {
                        //System.out.println("\t\tUltimChar: " + b.get(i));
                        ArrayUltimoChar.set(ultimChar, aBoolean);
                        ultimChar++;
                        // System.out.println("\t\t\tArrayChar: " + ArrayUltimoChar);
                    }
                } catch (Exception e) {
                    System.out.println("Algo ha ido mal");
                }
            }
        }
        // Vuelvo a poner el if por si los arrays no estan vacios, si no lo pongo podria desaparecer el ultimo caracter
        //System.out.println("UltimPointer: " + ultimPointer + " ultimChar: " + ultimChar);
        if(ultimPointer > 0 && ultimChar > 0){
            readBooleans(in, b);readBooleans(in, b);
            int d1 = getDecimal(ArrayPointer) ,d2 = getDecimal(ArrayUltimoChar);
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


 */

}
