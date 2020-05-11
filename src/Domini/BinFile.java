package Domini;
//
//  BinFile.java
//  PROP Compressor Descompressor
//
//  Created by Marc Domènech.
//  Copyright © 2018 Marc Domènech. All rights reserved.
//

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class BinFile extends File {
    protected String binFileName;


    public BinFile(String name){
        super(name);
        this.binFileName = name + ".bin";
    }

    //Escribe un Array de Booleans en formato bits en el fichero Binario(Comprimido)
    protected void writeBooleans(OutputStream out, ArrayList<Boolean> ar) throws IOException {
        for (int i = 0; i < ar.size(); i += 8) {
            int b = 0;
            boolean a;
            for (int j = Math.min(i + 7, ar.size()-1); j >= i; j--) {
                b = (b << 1) | (ar.get(j) ? 1 : 0);
                a = b != 0;
                ar.set(j, a);
            }
            out.write(b);
        }
    }

    //Lee un Array de Booleans en formato bits, guarda en ar el Array y retorna true o false si quedan bits a leer o no respectivamente.
    protected boolean readBooleans(InputStream in, ArrayList ar) throws IOException {
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
    protected static String getBinaryNumber(Integer numero){
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
        for (String s : binario) {
            binarioString += s;
        }
        return binarioString;
    }

    // Retorna la traduccion a decimal del numero en binario que pasamos por parametro.
    // Se considera el bit de menor peso el que se encuentra mas a la izquierda.
    protected static Integer getDecimalNumber(ArrayList<Boolean> b){
        int decimal = 0;
        for (int i = 0; i < b.size(); i++){
            if(b.get(i)){
                decimal+=(int)Math.pow(2,b.size()-1-i);
                //System.out.println("Bit: " + i + " valor: " + (int)Math.pow(2,i));
            }

        }
        return decimal;
    }

    // Consulta el nombre del Fichero Binario.
    public String getBinFileName(){return binFileName;}

    // Retorna la mida del fitxer.
    public double getSize(){
        java.io.File f = new java.io.File(binFileName);
        return f.length(); // bytes
    }

}
