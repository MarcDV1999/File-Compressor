package com.Marc;

import java.io.*;

public class File {
    String textFileName, binFileName;

    public File(String name){
        this.textFileName = name + ".txt";
        this.binFileName = name + ".txt";
    }
/*
    public String readFile(String fileName) throws IOException {
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

    public void writeFile(String filename, String textDecodified) throws IOException {
        BufferedWriter writer = new BufferedWriter(new FileWriter(filename));
        writer.write(textDecodified);

        writer.close();
    }

    public  String getTextFileName(){
        return textFileName;
    }

    public  String getBinFileName(){
        return binFileName;
    }
    */


}
