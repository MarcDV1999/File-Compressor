package com.Marc;

import com.sun.tools.corba.se.idl.StructEntry;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class BitWritter {

    private FileOutputStream out;
    private FileInputStream in;
    private String readFileName;
    private String writeFileName;
    private String writeFileName1B;
    private String writeFileName2B;
    private List textCodifiedASCII;
    private int bitsPointer;
    private int bitsUltimoChar = 8;

    public BitWritter(List textCodifiedASCII, String writeFileName, int numFrases) throws IOException {

        this.textCodifiedASCII = textCodifiedASCII;
        this.writeFileName = writeFileName;
        this.readFileName = "FitxerComprimit1B.bin";
        bitsPointer = (int)Math.ceil((Math.log(numFrases)/Math.log(2)));
        //bitsPointer = 16;
    }

    public void readBinFile(int bitsPointer, int bitsUltimoChar) throws IOException{
        boolean[] b = {false, false, false, false, false, false, false, false};
        int decimal = 0;
        FileInputStream in = new FileInputStream(readFileName);
        /*
        for(int i = 0; i < 5; i++) {
            readBooleans(in, b);
            decimal = obtenerDecimal(b);
            printArrayBool(b);
            if(i%2 == 0)System.out.println("En Decimal: " + decimal);
            else System.out.println("Char: " + (char)decimal);
        }
         */
        ArrayList<Boolean> x = new ArrayList<Boolean>(4);
        readBooleansArray(in,x);
        System.out.println(x);

        in.close();
        System.out.println("The file size was " + new File(writeFileName).length()+" bytes.");

    }
    public void readWith2B() throws IOException{
        //boolean[] ar = {true,true,true,true,true};
        FileInputStream out = new FileInputStream(readFileName);

        out.close();
        System.out.println("The file size was " + new File(writeFileName).length()+" bytes.");
    }

    private void readWith1B() throws IOException{ // 8 bits cada cosa

    }

    public void writeWith1B(String writeFileName1B) throws IOException{ // 8 bits cada cosa
        FileOutputStream out = new FileOutputStream(writeFileName1B);
        boolean[] b = {false, false, false, false, false, false, false, false};
        int bitsPointerAux = 8; //Cuantos bits usaremos para el pointer
        String binarioObtenido;
        int bitsQueSobran, bitsToWrite = 8;
        String s;
        boolean fin = false;

        for (int c = 0; c < textCodifiedASCII.size() && !fin;c++) {         // Leemos todo el texto codificado en ascii
            s = textCodifiedASCII.get(c).toString();
            if (c % 2 == 0) { // Codificamos el numero
                //String s = "64";
                binarioObtenido = obtenerBinario(Integer.valueOf(s));
                bitsQueSobran = bitsPointerAux - binarioObtenido.length();      // bits que tendremos que rellenar con 0
                //System.out.println("int: " + s + " bin -> " + binarioObtenido);
            }
            else{ // Codificamos Char
                //String s = "B";
                binarioObtenido = obtenerBinario((int)s.charAt(0));
                bitsQueSobran = bitsUltimoChar - binarioObtenido.length();
                //System.out.println("char: " + s + " bin -> " + binarioObtenido);
            }
            if (bitsQueSobran < 0 || bitsPointerAux > 8 || bitsQueSobran < 0 || bitsUltimoChar > 8) {
                System.out.println("----> Este Texto: " + s + " no se puede codificar con 8 bits!! Faltan " + bitsQueSobran + " bits");
                fin=true;
            }
            else {
                for (int i = (bitsToWrite-1); i > (bitsToWrite-1) - bitsQueSobran; i--) b[i] = false;
                for (int i = 0; i < bitsUltimoChar - bitsQueSobran; i++) {
                    if (i < binarioObtenido.length() && binarioObtenido.charAt(i) == '1')
                        b[b.length - bitsQueSobran - 1 - i] = true;
                    else if (i < binarioObtenido.length() && binarioObtenido.charAt(i) == '0')
                        b[b.length - bitsQueSobran - 1 - i] = false;
                }
                //printArrayBool(b);
                writeBooleans(out, b);
            }


        }


    }

    public void writeWith2B(String writeFileName2B) throws IOException{ // 16 bits cada cosa
        FileOutputStream out = new FileOutputStream(writeFileName2B);
        boolean[] b = {false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false};
        int bitsPointerAux = 16; //Cuantos bits usaremos para el pointer
        String binarioObtenido;
        int bitsQueSobran, bitsToWrite;
        String s;

        boolean fin = false;

        for (int c = 0; c < textCodifiedASCII.size() && !fin;c++) {         // Leemos todo el texto codificado en ascii
            s = textCodifiedASCII.get(c).toString();
            if (c % 2 == 0) { // Codificamos el numero
                //String s = "64";
                binarioObtenido = obtenerBinario(Integer.valueOf(s));
                bitsQueSobran = bitsPointerAux - binarioObtenido.length();      // bits que tendremos que rellenar con 0
                //System.out.println("int: " + s + " bin -> " + binarioObtenido);
                bitsToWrite = 16;
            }
            else{ // Codificamos Char
                //String s = "B";
                binarioObtenido = obtenerBinario((int)s.charAt(0));
                bitsQueSobran = bitsUltimoChar - binarioObtenido.length();
                //System.out.println("char: " + s + " bin -> " + binarioObtenido);
                bitsToWrite = 8;
            }
            if (bitsQueSobran < 0 || bitsPointerAux > 16 || bitsQueSobran < 0 || bitsUltimoChar > 8) {
                System.out.println("----> Este Texto no se puede codificar con 16 bits!! Faltan " + bitsQueSobran + " bits");
                fin=true;
            }
            else {
                for (int i = (bitsToWrite-1); i > (bitsToWrite-1) - bitsQueSobran; i--) b[i] = false;
                for (int i = 0; i < bitsUltimoChar - bitsQueSobran; i++) {
                    if (i < binarioObtenido.length() && binarioObtenido.charAt(i) == '1')
                        b[b.length - bitsQueSobran - 1 - i] = true;
                    else if (i < binarioObtenido.length() && binarioObtenido.charAt(i) == '0')
                        b[b.length - bitsQueSobran - 1 - i] = false;
                }
                //printArrayBool(b);
                writeBooleans(out, b);
            }
        }


    }

    public void writeEfficient(String writeFileNameE) throws IOException{ // Se adapta a los bits que necesita cada cosa
        FileOutputStream out = new FileOutputStream(writeFileNameE);
        boolean[] b = {false, false, false, false, false, false, false, false};
        String binarioObtenido;
        int bitsQueSobran, bitsToWrite = 8;
        String s;
        boolean fin = false;
        int contador = 0;

        for (int c = 0; c < textCodifiedASCII.size() && !fin;c++) {         // Leemos todo el texto codificado en ascii
            s = textCodifiedASCII.get(c).toString();
            if (c % 2 == 0) { // Codificamos el numero
                //String s = "64";
                binarioObtenido = obtenerBinario(Integer.valueOf(s));
                bitsQueSobran = bitsPointer - binarioObtenido.length();      // bits que tendremos que rellenar con 0
                //System.out.println("int: " + s + " bin -> " + binarioObtenido);
            }
            else{ // Codificamos Char
                //String s = "B";
                binarioObtenido = obtenerBinario((int)s.charAt(0));
                bitsQueSobran = bitsUltimoChar - binarioObtenido.length();
                //System.out.println("char: " + s + " bin -> " + binarioObtenido);
            }
            if (bitsQueSobran < 0 || bitsPointer > 8 || bitsQueSobran < 0 || bitsUltimoChar > 8) {
                System.out.println("----> Este Texto no se puede codificar con 8 bits!! Faltan " + bitsQueSobran + " bits");
                fin=true;
            }
            else {
                for (int i = (bitsToWrite-1); i > (bitsToWrite-1) - bitsQueSobran; i--) {
                    b[i] = false;
                    contador++;
                    if (contador == 8){
                        writeBooleans(out, b);
                        contador = 0;
                        //System.out.println("Escric: " + b);
                        //printArrayBool(b);
                    }
                }
                for (int i = 0; i < bitsUltimoChar - bitsQueSobran; i++) {
                    if (i < binarioObtenido.length() && binarioObtenido.charAt(i) == '1')
                        b[b.length - bitsQueSobran - 1 - i] = true;
                    else if (i < binarioObtenido.length() && binarioObtenido.charAt(i) == '0')
                        b[b.length - bitsQueSobran - 1 - i] = false;
                    contador++;
                    if (contador == 8){
                        writeBooleans(out, b);
                        contador = 0;
                        //System.out.println("Escric: " + b);
                        //printArrayBool(b);
                    }
                }

                writeBooleans(out, b);
            }


        }


    }








    private static void writeBooleans(OutputStream out, boolean[] ar) throws IOException {
        for (int i = 0; i < ar.length; i += 8) {
            int b = 0;
            for (int j = Math.min(i + 7, ar.length-1); j >= i; j--) {
                b = (b << 1) | (ar[j] ? 1 : 0);
            }
            out.write(b);
        }
    }

    private static void readBooleans(InputStream in, boolean[] ar) throws IOException {
        for (int i = 0; i < ar.length; i += 8) {
            int b = in.read();
            if (b < 0) throw new EOFException();
            for (int j = i; j < i + 8 && j < ar.length; j++) {
                ar[j] = (b & 1) != 0;
                b >>>= 1;
            }
        }
    }

    private static void readBooleansArray(InputStream in, ArrayList ar) throws IOException {
        for (int i = 0; i < ar.size(); i += 8) {
            int b = in.read();
            if (b < 0) throw new EOFException();
            for (int j = i; j < i + 8 && j < ar.size(); j++) {
                ar.set(j,(b & 1) != 0);
                b >>>= 1;
            }

        }
    }

    private void printArrayBool(boolean[] b){
        for (int i = 0; i < b.length; i++){
            System.out.println("Bit "+ i + ": " + b[i]);
        }
    }


    public String obtenerBinario(Integer numero){
        ArrayList<String> binario = new ArrayList<String>();
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

    public Integer obtenerDecimal(boolean b[]){
        int decimal = 0;
        for (int i = 0; i < b.length; i++){
            if(b[i]){
                decimal+=(int)Math.pow(2,i);
                //System.out.println("Bit: " + i + " valor: " + (int)Math.pow(2,i));
            }

        }
        return decimal;
    }
}
