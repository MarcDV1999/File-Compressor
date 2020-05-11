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
    private int numFrases = 0;
    private long executionTimeIni,executionTimeEnd, executionTime= System.currentTimeMillis();
    private Set_Statistics statistics;

    public LZ78(){statistics = new Set_Statistics();}

    //Comprime en formato LZ78 en el Archivo con Nombre: name (sin el formato).
    public void compress(File file) throws IOException { // Toca intentar comprimir en un fitxer tots els fitxer. Pero un ja el fa be
        String nameToSave = "";
        Ctrl_BinFile bin;
        executionTimeIni = System.currentTimeMillis();

        List textCodifiedASCII = new ArrayList();
        nameToSave = file.getAbsName();
        bin = new Ctrl_BinFile(nameToSave);
        if(!file.isDirectory()) {
            textCodifiedASCII.addAll(codify(file, true)); // Afegim el Text amb el header(Path + text + \0)
            nameToSave = file.getAbsName();
            binFile = new Ctrl_BinFile(nameToSave);
            binFile.writeBinFile(textCodifiedASCII, numFrases);

            executionTimeEnd = System.currentTimeMillis();
            executionTime = executionTimeEnd - executionTimeIni; // ms
            statistics.addCompressLZ78(new Statistic(executionTime, file.length(), binFile.getSize()));
        }
        else {
            textCodifiedASCII.addAll(codify(file, false));
            compressFolder(new Folder(file.getAbsolutePath()),textCodifiedASCII);
            nameToSave = file.getAbsolutePath();
            binFile = new Ctrl_BinFile(nameToSave);

            binFile.writeBinFile(textCodifiedASCII, numFrases);
            Folder folder = new Folder(file.getAbsolutePath());
            executionTimeEnd = System.currentTimeMillis();
            executionTime = executionTimeEnd - executionTimeIni; // ms
            statistics.addCompressLZ78(new Statistic(executionTime, folder.getSize(), binFile.getSize()));
        }

    }


    //Descomprime en formato LZ78 en el Archivo con Nombre: name (sin el formato).
    public void discompress(BinFile bin) throws IOException{
        String fileContentAndPath,pathNewFile, newFileContent, root;
        Integer endOfNextFile;
        executionTimeIni = System.currentTimeMillis();
        binFile = new Ctrl_BinFile(bin.getAbsolutePath());
        File f = new File(bin.getAbsolutePath()), parent;
        Folder folder;
        pathNewFile = f.getAbsolutePath()+".txt";
        root = f.getAbsolutePath();
        List allInfo = binFile.readBinFile();
        Pair<String, Integer> p;
        try {
            while (allInfo.size()>0) {
                p = deCodify(allInfo);
                fileContentAndPath = p.getValue0();
                endOfNextFile = p.getValue1();

                String result = tractarHeader(fileContentAndPath);
                if(!result.equals("")) pathNewFile = result;
                allInfo = allInfo.subList(endOfNextFile+1, allInfo.size());
                f = new File(pathNewFile);
                parent = new File(f.getParent());

                if(pathNewFile.endsWith(".txt")){
                    try {
                        newFileContent = fileContentAndPath.substring(pathNewFile.length() + 1, fileContentAndPath.length() - 1);
                        if (!parent.exists()) createFoldersNonExistent(parent);
                        f.writeFile(pathNewFile, newFileContent);
                    }catch (Exception e){System.out.println("Error a discompress LZ78");}
                }
                else {
                    f = new File(pathNewFile);
                    f.mkdir();
                }
            }

            executionTimeEnd = System.currentTimeMillis();
            executionTime = executionTimeEnd - executionTimeIni; // ms
            f = new File(root);
            //System.out.println("root: " + root);
            if(f.isDirectory()) {
                folder = new Folder(f.getAbsolutePath());
                statistics.addDiscompressLZ78(new Statistic(executionTime, bin.getSize(), folder.getSize()));
            }
            else {
                f = new File(root+".txt");
                //System.out.println("bin: " + bin.getSize() + "f: " + f.length());
                statistics.addDiscompressLZ78(new Statistic(executionTime, bin.getSize(), f.length()));
            }




        }catch (Exception e){
            System.out.println("Discompress LZ78 diu: Error al intentar decodificar el seguent fitxer\n" + e);
        }
    }

    // Retorna el conjunto de estadisticas del algoritmo.
    public Set_Statistics getStatistics(){return statistics;}


    //Comprime en formato LZ78 una carpeta.
    private List compressFolder(Folder folder, List textCodifiedASCII) { // Toca intentar comprimir en un fitxer tots els fitxer. Pero un ja el fa be
        java.io.File file;
        if(folder.getSize() == 0) {
            textCodifiedASCII.addAll(codify(new File(folder.getAbsolutePath()), false)); // Afegim el Text amb el header(Path + text + \0)
        }else {
            for (int i = 0; i < folder.getSize(); i++) {
                try {
                    file = folder.getFiles().get(i);
                    String actualAbsPath = file.getAbsolutePath();
                    if (file.isDirectory()) {
                        compressFolder(new Folder(actualAbsPath), textCodifiedASCII);
                    } else {
                        if (actualAbsPath.substring(actualAbsPath.length() - 4).equals(".txt"))
                            textCodifiedASCII.addAll(codify(new File(actualAbsPath), false));
                        else System.out.println("\t\t\t\t5. No es un arxiu valid" + actualAbsPath);
                    }
                }catch (Exception e){}
            }
        }
        return textCodifiedASCII;
    }


    // Genera todas las carpetas necessarias para descomprimir el archivo parent.
    private void createFoldersNonExistent(File parent){
        File grandpa = new File(parent.getParent());
        while (!parent.exists()){
            if(!grandpa.exists()) createFoldersNonExistent(grandpa);
            else parent.mkdir();
        }
    }

    // Coge del fichero y encuentra si lo tiene un header con el nombre del fichero
    private String tractarHeader(String textDecodified){
        String newText = "";
        for (int i = 0; i < textDecodified.length(); i++){
            if(textDecodified.charAt(i) == '\1') { return newText; }
            else newText += textDecodified.charAt(i);
        }
        System.out.println("Error al detectar el path al Header del arxiu");
        return "";
    }


    // Retorna una Lista de Tuplas(Pointer, UltimoChar) en ASCII.
    private List codify(File file, boolean compressingOnlyThisFile) {
        /*  Pre: text no ha de ser vacio, sino salta una Excepcion
            Post: Llena la HashTable con las frases frequentes en formato LZ78 con la estructura siguiente:

                    {Frase , ID , Pointer , UltimoChar}   Ej ---> {A=[1,0,A]}
                  Guarda en textCodified el texto codificado en formato:
                    [ID , Pointer , UltimoChar]   Ej ---> [1, 0, A][2, 0, B][3, 1, B]

         */
        List textCodifiedASCII = new ArrayList();
        String text = "", textWithoutHeader = "";
        if(file.isDirectory()){
            text = file.getAbsolutePath() + "\1\0";
        }
        else{
            try {
                textWithoutHeader = file.readFile(file.getAbsolutePath());
                text = file.getAbsolutePath() + '\1' + textWithoutHeader + '\0';
            }catch (IOException e){ System.out.println("No he pogut Codificar el arxiu: " + file.getAbsolutePath());}
        }
       /*
        else if(!compressingOnlyThisFile){
            try {
                textWithoutHeader = file.readFile(file.getAbsolutePath());
                text = file.getAbsolutePath() + '\1' + textWithoutHeader + '\0';
            }catch (IOException e){ System.out.println("No he pogut Codificar el arxiu: " + file.getAbsolutePath());}
        }
        else{
            try {
                textWithoutHeader = file.readFile(file.getAbsolutePath());
                text = '\1' + textWithoutHeader + '\0';
            }catch (IOException e){ System.out.println("No he pogut Codificar el arxiu: " + file.getAbsolutePath());}
        }
*/

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
                    else frase += Character.toString(text.charAt(i)); // Inicialitzem la primera frase amb el primer char del text

                }
                // Ejecutamos esto por si la ultima frase de text era una frase a guardar
                if (!code.containsKey(frase)) {
                    if (frase.length() > 0) {
                        Integer v1 = code.get(frase.substring(0, frase.length() - 1)).getValue0(); // Quitamos el ultimo char a la frase para buscar en la HashTable
                        tripleta = new Triplet(contadorID++, v1, frase.charAt(frase.length() - 1));
                    } else tripleta = new Triplet(contadorID++, 0, frase.charAt(0));
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
                numFrases = --contadorID;

            } catch (Exception e) { System.out.println("Ha habido un Error al intentar Codificar el texto. Revise que el documento no este vacio.");
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
        executionTimeIni = System.currentTimeMillis();
        for (int i = 0; i < textCompressedASCII.size(); i+=2){

            pointer = Integer.valueOf(textCompressedASCII.get(i).toString());
            ultimoChar = textCompressedASCII.get(i+1).toString();
            if(ultimoChar.charAt(0) == '\0') {
                return new Pair<>(textDescompressed,i+1);
            }
            if(pointer == 0) {
                decode.put(contadorID++, new Triplet<String, Integer, Character>(ultimoChar, pointer, ultimoChar.charAt(0)));
                textDescompressed += ultimoChar.charAt(0);
            }
            else{
                frase = decode.get(pointer).getValue0();
                decode.put(contadorID++, new Triplet<String, Integer, Character>(frase + ultimoChar, pointer, ultimoChar.charAt(0)));
                textDescompressed += frase + ultimoChar;
            }
            if(i%100 == 0){
                executionTimeEnd = System.currentTimeMillis() - executionTimeIni;
                executionTimeIni = System.currentTimeMillis();
            }
        }
        return new Pair(textDescompressed,-2);
    }



}


