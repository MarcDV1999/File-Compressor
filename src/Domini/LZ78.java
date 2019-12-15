package Domini;
//
//  LZ78.java
//  PROP Compressor Descompressor
//
//  Created by Marc Domènech.
//  Copyright © 2019 Marc Domènech. All rights reserved.
//


import org.javatuples.Pair;
import org.javatuples.Triplet;

import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;


class LZ78 {

    private HashMap<String, Triplet<Integer,Integer,Character>> code = new HashMap<> ();
    private HashMap<Integer, Triplet<String,Integer,Character>> decode = new HashMap<> ();
    private static Ctrl_BinFile binFile;
    private static TextFile textFile;
    private int numFrases = 0;
    private long executionTimeIni,executionTimeEnd, executionTime= System.currentTimeMillis();
    private String new_extension = "_new";
    private Set_Statistics statistics;
    private Folder folder;
    private String rootPath;

    public LZ78(){statistics = new Set_Statistics();}

    //Comprime en formato LZ78 en el Archivo con Nombre: name (sin el formato).
    public void compress(File file) throws IOException { // Toca intentar comprimir en un fitxer tots els fitxer. Pero un ja el fa be
        String nameToSave = "";
        executionTimeIni = System.currentTimeMillis();

        List textCodifiedASCII = new ArrayList();
        //textCodifiedASCII.addAll(codify(file));
        if(!file.isDirectory()) {
            textCodifiedASCII.addAll(codify(file)); // Afegim el Text amb el header(Path + text + \0)
            nameToSave = file.getAbsName();
        }
        else {
            textCodifiedASCII.addAll(codify(file));
            //System.out.println("Detecto la Carpeta: " + file.getName() + " y Voy a compressFolder");
            compressFolder(new Folder(file.getAbsolutePath()),textCodifiedASCII);
            nameToSave = file.getAbsolutePath();
        }
        System.out.println("Nombre con el que guardo el bin: " + file.getAbsName());
        binFile = new Ctrl_BinFile(nameToSave);

        binFile.writeBinFile(textCodifiedASCII, numFrases);

        executionTimeEnd = System.currentTimeMillis();
        executionTime = executionTimeEnd - executionTimeIni; // ms
        Statistic s = new Statistic(executionTime,file.length(), binFile.getSize());
        statistics.addCompressLZ78(s);
        //System.out.println("EIni: " + executionTimeIni + " - ExecutionTimeEnd: " + executionTimeEnd);
        //System.out.println("Texto Codificado:\t" + textCodifiedASCII);
    }

    public List compressFolder(Folder folder, List textCodifiedASCII) throws IOException { // Toca intentar comprimir en un fitxer tots els fitxer. Pero un ja el fa be
       java.io.File file = new File(folder.getAbsolutePath());
        System.out.println("1.Carpeta: " + folder.getAbsolutePath());
        if(folder.getSize() == 0) {
            System.out.println("\t2. Carpeta Vacia:");
            textCodifiedASCII.addAll(codify(new File(folder.getAbsolutePath()))); // Afegim el Text amb el header(Path + text + \0)
        }else {
            System.out.println("\t2. Mirem Elements Carpeta:");
            for (int i = 0; i < folder.getSize(); i++) {
                file = folder.getFiles().get(i);
                String actualAbsPath = file.getAbsolutePath();
                System.out.println("\t\t3. Mirem: " + actualAbsPath);
                //System.out.println("Archivo: " + file.getAbsolutePath());
                if (file.isDirectory()) {
                    System.out.println("\t\t\t4. Es una carpeta");
                    textCodifiedASCII.addAll(compressFolder(new Folder(actualAbsPath), textCodifiedASCII));
                } else {
                    //f_domini = new File(file.getAbsolutePath().substring(0, file.getAbsolutePath().length() - 4));
                    System.out.println("\t\t\t4. Es un arxiu");
                    //System.out.println("----> " + actualAbsPath.substring(actualAbsPath.length()-4));
                    if(actualAbsPath.substring(actualAbsPath.length()-4).equals(".txt")){
                        System.out.println("\t\t\t\t5. Codifiquem" + actualAbsPath);
                        textCodifiedASCII.addAll(codify(new File(actualAbsPath)));
                    }
                    else{ System.out.println("\t\t\t\t5. No es un arxiu valid" + actualAbsPath);}

                }
                //System.out.println("Text queda: " + textCodifiedASCII);
            }
        }
        return textCodifiedASCII;
    }

    //Descomprime en formato LZ78 en el Archivo con Nombre: name (sin el formato).
    public void discompress(BinFile bin) throws IOException{
        String textDecodified;
        executionTimeIni = System.currentTimeMillis();
        //String decompressedFileName = bin.getAbsolutePath() + new_extension;
        textFile = new TextFile(bin.getAbsolutePath());
        binFile = new Ctrl_BinFile(bin.getAbsolutePath());
        File f, parent;

        List textCodifiedASCII = binFile.readBinFile();
        //System.out.println("list: " + textCodifiedASCII);
        Pair<String, Integer> p = new Pair<>("",1);
        //System.out.println("Text sencer: " + textCodifiedASCII);
        try {
            while (textCodifiedASCII.size()>0) {
                p = deCodify(textCodifiedASCII);
                textDecodified = p.getValue0();
                textCodifiedASCII = textCodifiedASCII.subList(p.getValue1()+1, textCodifiedASCII.size());

                String pathToSave = tractarHeader(textDecodified);
                //System.out.println("Guardem arxiu a: " + pathToSave);
                f = new File(pathToSave);
                parent = new File(f.getParent());
                if(pathToSave.endsWith(".txt")){
                    textDecodified = textDecodified.substring(pathToSave.length() + 1, textDecodified.length() - 1);

                    //pathToSave = pathToSave.substring(0,pathToSave.length()-4);

                    if(!parent.exists()){
                        createFoldersNonExistent(parent);
                    }
                    textFile.writeFile(pathToSave, textDecodified);
                }
                else {

                    f = new File(pathToSave);
                    //System.out.println("Tinc una carpeta, la creem");
                    System.out.println("Creem la carpeta: " + pathToSave);
                    f.mkdir();
                }
                //System.out.println("textDecodified: " + textDecodified);
                //System.out.println("Save as: " + pathToSave + new_extension);

                /*
                if(pathToSave.substring(pathToSave.length()-4).equals(".txt"))textFile.writeFile(pathToSave + new_extension, textDecodified);
                else {
                    File folder = new File(pathToSave);
                    folder.mkdir();
                }
                 */


                /*
                executionTimeEnd = System.currentTimeMillis();
                executionTime = executionTimeEnd - executionTimeIni; // ms
                Statistic s = new Statistic(executionTime, bin.getSize(), textFile.length());
                statistics.addDiscompressLZ78(s);
                //statistics.addDiscompressLZ78(new Statistic(-3,-4,-5));
                 */
            }
        }catch (Exception e){
            System.out.println("Discompress LZ78 diu: Error al intentar decodificar el seguent fitxer\n" + e);
        }
    }
    private void createFoldersNonExistent(File parent){
        File grandpa = new File(parent.getParent());
        while (!parent.exists()){
            if(!grandpa.exists()) createFoldersNonExistent(grandpa);
            else parent.mkdir();
        }
    }

    private String tractarHeader(String textDecodified){
        String newText = "";
        for (int i = 0; i < textDecodified.length(); i++){
            if(textDecodified.charAt(i) == ' ') { return newText; }
            else newText += textDecodified.charAt(i);
        }

        System.out.println("Error al detectar el path al Header del arxiu");
        return "";
    }


    // Retorna una Lista de Tuplas(Pointer, UltimoChar) en ASCII.
    private List codify(File file) {
        /*  Pre: text no ha de ser vacio, sino salta una Excepcion
            Post: Llena la HashTable con las frases frequentes en formato LZ78 con la estructura siguiente:

                    {Frase , ID , Pointer , UltimoChar}   Ej ---> {A=[1,0,A]}
                  Guarda en textCodified el texto codificado en formato:
                    [ID , Pointer , UltimoChar]   Ej ---> [1, 0, A][2, 0, B][3, 1, B]

         */
        List textCodifiedASCII = new ArrayList();
        String text = "", textWithoutHeader = "";
        if(file.isDirectory()){
            text = file.getAbsolutePath() + " \0";
            System.out.println("Codifico carpeta");
        }
        else {
            System.out.println("Afegeixo: " + file.getAbsolutePath());
            try {
                textWithoutHeader = file.readFile(file.getAbsolutePath());
                text = file.getAbsolutePath() + " " + textWithoutHeader + '\0';
                //System.out.println("Afegeixo: " + textCodifiedASCII);
            }catch (IOException e){ System.out.println("No he pogut Codificar el arxiu: " + textFile.getTextFileName());}
        }

            //textFile = new TextFile(file.getAbsolutePath());
            System.out.println("Codify dice: Intento acceder al archivo (" + file.getAbsPath() + ")");
            try {
                code.clear();
                Integer contadorID = 1; //Se usa para ir asignando ID a las distintas frases en la HashTable
                String frase = Character.toString(text.charAt(0)); // Inicialitzem la primera frase amb el primer char del text
                Triplet tripleta; // Realment no m'importa el valor inicial

                code.put("", new Triplet(0, 0, '?')); // Agregamos el elemento 0 en la HashTable

                // Recorremos text i vamos almacenando todas las frases
                for (int i = 1; i < text.length(); i++) {
                    if (!code.containsKey(frase)) {
                        // Si no tengo la frase, lo agrego y reinicio la variable local frase
                        if (frase.length() > 1) { //Si es compuesta, tengo que buscar la frase a la que referencia
                            //System.out.println("\tFrase llarga: " + frase);
                            Integer v1 = code.get(frase.substring(0, frase.length() - 1)).getValue0(); // Quitamos el ultimo char a la frase para buscar en la HashTable
                            tripleta = new Triplet(contadorID, v1, frase.charAt(frase.length() - 1));

                        } else tripleta = new Triplet(contadorID, 0, frase.charAt(0));

                        code.put(frase, tripleta);
                        contadorID++;
                        textCodifiedASCII.add(tripleta.getValue1().toString());
                        textCodifiedASCII.add(tripleta.getValue2().toString());

                        frase = Character.toString(text.charAt(i)); // Inicialitzem la primera frase amb el primer char del text
                    }
                    // Si ya lo tenia sigo buscando
                    else
                        frase += Character.toString(text.charAt(i)); // Inicialitzem la primera frase amb el primer char del text

                }
                // Ejecutamos esto por si la ultima frase de text era una frase a guardar
                if (!code.containsKey(frase)) {
                    //System.out.println("AQUI ");
                    if (frase.length() > 0) {
                        //System.out.println("\tFrase llarga: " + frase);
                        Integer v1 = code.get(frase.substring(0, frase.length() - 1)).getValue0(); // Quitamos el ultimo char a la frase para buscar en la HashTable
                        tripleta = new Triplet(contadorID++, v1, frase.charAt(frase.length() - 1));
                    } else tripleta = new Triplet(contadorID++, 0, frase.charAt(0));
                    //System.out.println("\tFrase llarga: " + frase);
                    code.put(frase, tripleta);
                    textCodifiedASCII.add(tripleta.getValue1().toString());
                    textCodifiedASCII.add(tripleta.getValue2().toString());
                } else if (frase.length() > 0) {
                    Integer v1 = code.get(frase).getValue0();
                    tripleta = new Triplet(contadorID++, v1, '\u001a');
                    code.put(frase, tripleta);
                    textCodifiedASCII.add(tripleta.getValue1().toString());
                    textCodifiedASCII.add(tripleta.getValue2().toString());
                }
                //System.out.println("\tFrase llarga1: " + frase);
                numFrases = --contadorID;

            } catch (Exception e) {
                System.out.println("Ha habido un Error al intentar Codificar el texto. Revise que el documento no este vacio.");
            }

        return textCodifiedASCII;

    }

    // Retorna un texto ASCII a partir de un Lista de Tuplas(Pointer, UltimoChar).
    private Pair<String,Integer> deCodify(List textCompressedASCII){
        decode.clear();
        long executionTimeIni;
        String textDescompressed = "", ultimoChar = "", frase = "";
        int contadorID = 1, pointer;


        decode.put(0, new Triplet("", 0, '?')); // Agregamos el elemento 0 en la HashTable
        //System.out.println("textCompressedASCII: " + textCompressedASCII);

        for (int i = 0; i < textCompressedASCII.size(); i+=2){
            pointer = Integer.valueOf(textCompressedASCII.get(i).toString());
            ultimoChar = textCompressedASCII.get(i+1).toString();
            if(textCompressedASCII.get(i+1).toString().charAt(0) == '\0') {
               // System.out.println("------TextDescompressed: " + textDescompressed);
                return new Pair<>(textDescompressed,i+1);
            }
            if(pointer == 0) {
                //System.out.println("\tEs 0, Añadimos la letra " + ultimoChar);
                executionTimeIni = System.currentTimeMillis();
                decode.put(contadorID++, new Triplet<String, Integer, Character>(ultimoChar, pointer, ultimoChar.charAt(0)));
                executionTimeEnd = System.currentTimeMillis() - executionTimeIni;
                textDescompressed += ultimoChar.charAt(0);
            }
            else{

                frase = decode.get(pointer).getValue0();
                //System.out.println("\tEs FRASE, Añadimos la letra " + frase + ultimoChar);
                decode.put(contadorID++, new Triplet<String, Integer, Character>(frase + ultimoChar, pointer, ultimoChar.charAt(0)));
                textDescompressed += frase + ultimoChar;
            }

        }
        return new Pair(textDescompressed,-1);
    }

    public String getNew_extension(){return new_extension;}

    public Set_Statistics getStatistics(){return statistics;}

}


