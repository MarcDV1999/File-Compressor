package com.Marc;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class BitWritter {

    private int bitsPointer;
    private int bitsUltimoChar = 8;
    private int bitsHeader = 16;


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
        bitsPointer = obtenerDecimal(ArrayHeader);
        //System.out.println("BIts: " + bitsPointer);


        // Mientras haya texto por leer vamos cogiendo bits, vamos decodificando y guardando en text
        while (!readBooleans(in, b)){
            for (Boolean aBoolean : b) {
                try {
                    //System.out.println("\tProceso: " + b.get(i));
                    //System.out.println("\tProceso: ");
                    if (ultimPointer == bitsPointer && ultimChar == bitsUltimoChar) {
                        //System.out.println("Guardamos en Text");
                        int d1 = obtenerDecimal(ArrayPointer), d2 = obtenerDecimal(ArrayUltimoChar);
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
            int d1 = obtenerDecimal(ArrayPointer) ,d2 = obtenerDecimal(ArrayUltimoChar);
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

    // Codifica el texto eficientemente en binario
    public void writeEfficient(List textCodifiedASCII, String writeFileNameE, int numFrases) throws IOException{ // Se adapta a los bits que necesita cada cosa
        FileOutputStream out = new FileOutputStream(writeFileNameE);
        bitsPointer = Math.max((int)Math.ceil((Math.log(numFrases)/Math.log(2))),2);
        //System.out.println("Bits necesaris " + bitsPointer);
        ArrayList<Boolean> arrayToWrite = new ArrayList<Boolean>();
        int bitsToWrite = bitsHeader, bitsQueSobran = bitsHeader-bitsPointer; //Cuantos bits usaremos para el pointer
        String binarioObtenido;
        String s;
        boolean fin = false;
        // Header
        binarioObtenido = obtenerBinario(bitsPointer);
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
        //System.out.println("Array:  " + arrayToWrite);
        //writeBooleans(out,arrayToWrite);

        // Fi Header


        // Empezamos a Escribir el texto
        for (int c = 0; c < textCodifiedASCII.size() && !fin;c++) {         // Leemos todo el texto codificado en ascii
            //System.out.println("Frase: " + textCodifiedASCII.get(c));
            //for (int c = 0; c < 2 && !fin;c++) {
            s = textCodifiedASCII.get(c).toString();
            if (c % 2 == 0) { // Codificamos el numero
                binarioObtenido = obtenerBinario(Integer.valueOf(s)); // Numero en Binari
                bitsQueSobran = bitsPointer - binarioObtenido.length();      // bits que tendremos que rellenar con 0
                //System.out.println("Bits binario1 --> " + binarioObtenido + " - " + s);
                bitsToWrite = bitsPointer;
            }
            else{ // Codificamos Char
                binarioObtenido = obtenerBinario((int)s.charAt(0));
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
        //arrayToWrite.add(false);arrayToWrite.add(false);
        //System.out.println("La llista queda aixi " + arrayToWrite);
        //printArrayBool(arrayToWrite);
        writeBooleans(out, arrayToWrite);

        //bitsPointerToWrite.add(true);bitsPointerToWrite.add(false);bitsPointerToWrite.add(false);
        //writeBooleans(out,bitsPointerToWrite);
        //bitsUltimoCharToWrite.add(false);bitsUltimoCharToWrite.add(false);bitsUltimoCharToWrite.add(true);
        //writeBooleans(out,bitsUltimoCharToWrite);

    }

    //Escribe un Array de Booleans en formato bits en el fichero Binario(Comprimido)
    public void writeBooleans(OutputStream out, ArrayList<Boolean> ar) throws IOException {
        /*
        for (int i = 0; i < ar.size(); i += 8) {
            int b = 0;
            boolean a = false;
            for (int j = Math.min(i + 7, ar.size()-1); j >= i; j--) {
                b = (b << 1) | (ar.get(j) ? 1 : 0);
                if(b == 0) a = false;
                else a = true;
                ar.set(j, a);
            }
            out.write(b);
        }

         */
        for (int i = 0; i < ar.size(); i += 8) {
            int b = 0;
            boolean a = false;
            for (int j = Math.min(i + 7, ar.size()-1); j >= i; j--) {
                b = (b << 1) | (ar.get(j) ? 1 : 0);
                if(b == 0) a = false;
                else a = true;
                ar.set(j, a);
            }
            out.write(b);
        }
    }

    //Escribe un Array de Booleans en formato bits en el fichero Binario(Comprimido)
    public boolean readBooleans(InputStream in, ArrayList ar) throws IOException {
        for (int i = 0; i < ar.size(); i += 8) {
            int b = in.read();
            if (b < 0) return true;
            for (int j = i; j < i + 8 && j < ar.size(); j++) {
                //ar.set(j,(b & 1) != 0);
                ar.set(j, (b & 1) != 0);
                b >>>= 1;
            }
        }
        return false;
    }

    // Retorna un String con la traduccion a binario del numero que introducimos como parametro
    private String obtenerBinario(Integer numero){
        ArrayList<String> binario = new ArrayList<>();
        int resto;
        String binarioString = "";

        do{
            resto = numero%2;
            numero = numero/2;
            binario.add(0, Integer.toString(resto));
        }while(numero >= 2); //Haremos el bucle hasta que el cociente no se pueda dividir mas

        binario.add(0, Integer.toString(numero)); //Cogeremos el ultimo cociente

        //Cogemos cada uno de los elementos del ArrayList y los juntamos en un String
        for(int i = 0; i < binario.size(); i++){
            binarioString += binario.get(i);
        }
        return binarioString;
    }

    // Retorna la traduccion a decimal del numero en binario que pasamos por parametro.
    // Se considera el bit de menor peso el que se encuentra mas a la izquierda.
    private Integer obtenerDecimal(ArrayList<Boolean> b){
        int decimal = 0;
        for (int i = 0; i < b.size(); i++){
            if(b.get(i)){
                decimal+=(int)Math.pow(2,b.size()-1-i);
                //System.out.println("Bit: " + i + " valor: " + (int)Math.pow(2,i));
            }

        }
        return decimal;
    }


}
