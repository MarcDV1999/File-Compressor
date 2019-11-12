package com.Marc;

import javafx.util.Pair;
import org.javatuples.Triplet;

import java.io.*;
import java.lang.Math;
import java.nio.file.Path;

import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Set;

public class LZ78 {

    private HashMap<String, Triplet<Integer,Integer,Character>> code = new HashMap<String, Triplet <Integer,Integer,Character> > ();
    //private List<String> textCodifiedASCII;
    //private String textCodifiedASCII = "";
    private List textCodifiedASCII = new ArrayList();
    private String textCodifiedBIN = "";
    private int numFrases = 0;
    private String readFileName = "";
    private String writeFileName = "FitxerComprimit.bin";
    private String writeFileName1B = "FitxerComprimit1B.bin";
    private String writeFileName2B = "FitxerComprimit2B.bin";
    private String writeFileNameE = "FitxerComprimitE.bin";
    private BufferedWriter writer;
    //private DataOutputStream writer;

    public LZ78(String fileName) throws IOException{
        this.readFileName = fileName;
        this.writer = new BufferedWriter(new FileWriter(writeFileName));
        //FileOutputStream fos = new FileOutputStream(writeFileName);
        //this.writer = new DataOutputStream(fos);
    }


    public void codify() {
        /*  Pre: text no ha de ser vacio, sino salta un error
            Post: Llena la HashTable con las frases frequentes en formato LZ78 con la estructura siguiente:

                    {Frase , ID , Pointer , UltimoChar}   Ej ---> {A=[1,0,A]}
                  Guarda en textCodified el texto codificado en formato:
                    [ID , Pointer , UltimoChar]   Ej ---> [1, 0, A][2, 0, B][3, 1, B]

         */
        try {
            String text = readFile(readFileName);
            Integer contadorID = 1; //Se usa para ir asignando ID a las distintas frases en la HashTable
            String frase = Character.toString(text.charAt(0)); // Inicialitzem la primera frase amb el primer char del text
            Triplet tripleta = new Triplet(0, 0, frase.charAt(0)); // Realment no m'importa el valor inicial

            code.put("", new Triplet(0, 0, '?')); // Agregamos el elemento 0 en la HashTable

            // Recorremos text i vamos almacenando todas las frases
            for (int i = 1; i < text.length(); i++) {

                if (!code.containsKey(frase)) {
                    // Si no tengo la frase, lo agrego y reinicio la variable local frase
                    if (frase.length() > 1) { //Si es compuesta, tengo que buscar la frase a la que referencia
                        Integer v1 = code.get(frase.substring(0, frase.length() - 1)).getValue0(); // Quitamos el ultimo char a la frase para buscar en la HashTable
                        tripleta = new Triplet(contadorID, v1, frase.charAt(frase.length() - 1));

                    } else tripleta = new Triplet(contadorID, 0, frase.charAt(0));

                    code.put(frase, tripleta);
                    contadorID++;

                    //textCodifiedASCII += tripleta.getValue1().toString() + tripleta.getValue2().toString();
                    //Pair p = new Pair(tripleta.getValue1().toString(),tripleta.getValue2().toString());
                    //System.out.println(tripleta.getValue2().toString().getClass());
                    //textCodifiedASCII.add("h");
                    textCodifiedASCII.add(tripleta.getValue1().toString());
                    textCodifiedASCII.add(tripleta.getValue2().toString());

                    //writeFile(tripleta);
                    frase = Character.toString(text.charAt(i)); // Inicialitzem la primera frase amb el primer char del text
                }
                // Si ya lo tenia sigo buscando
                else frase += Character.toString(text.charAt(i)); // Inicialitzem la primera frase amb el primer char del text

            }
            // Ejecutamos esto por si la ultima frase de text era una frase a guardar
            if (!code.containsKey(frase)) {
                if (frase.length() > 0) {
                    Integer v1 = code.get(frase.substring(0, frase.length() - 1)).getValue0(); // Quitamos el ultimo char a la frase para buscar en la HashTable
                    tripleta = new Triplet(contadorID++, v1, frase.charAt(frase.length() - 1));
                } else tripleta = new Triplet(contadorID++, 0, frase.charAt(0));

                code.put(frase, tripleta);
                //textCodifiedASCII += tripleta.getValue1().toString() + tripleta.getValue2().toString();
                //Pair p = new Pair(tripleta.getValue1().toString(),tripleta.getValue2().toString());
                textCodifiedASCII.add(tripleta.getValue1().toString());
                textCodifiedASCII.add(tripleta.getValue2().toString());
                //writeFile(tripleta);
            }
            numFrases = --contadorID;
            writer.close();
        }
        catch (Exception e) {
            System.out.println("Ha habido un Error al intentar codeificar el texto. Revise que el documento no este vacio.");
        }

    }



    public void compress() throws IOException{
        BitWritter b = new BitWritter(textCodifiedASCII, writeFileName, numFrases);
        //b.writeWith1B();
        b.writeWith2B(writeFileName2B);
        b.writeWith1B(writeFileName1B);
        b.readBinFile(8,8);
        //b.writeEfficient(writeFileNameE);
    }


    public void print(){
        //System.out.println(code);
        System.out.println(textCodifiedASCII);
        System.out.println("Numero de Frases guardadas = " + numFrases);
    }
    private String readFile(String fileName) throws IOException {
        InputStream is = new FileInputStream(fileName);
        BufferedReader buf = new BufferedReader(new InputStreamReader(is));
        String line = buf.readLine();
        StringBuilder sb = new StringBuilder();
        while (line != null){
            sb.append(line).append("\n");
            line = buf.readLine();
        }

        String fileAsString = sb.toString();
        //System.out.println("Contenido del fichero:\n" + fileAsString);
        return fileAsString;
    }

}


