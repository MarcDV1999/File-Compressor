package Domini;
//
//  File.java
//  PROP Compressor Descompressor
//
//  Created by Marc Domènech.
//  Copyright © 2019 Marc Domènech. All rights reserved.
//


import java.io.*;

public class File extends java.io.File {

    // Genera un nou fitxer amb path = path.
    public File(String path){
        super(path);
    }

    // Retorna el Absolute Path del fitxer sense la extensio.
    public String getAbsName(){return this.getAbsolutePath().substring(0,getAbsolutePath().length()-4);}

    //Lee del fichero con nombre filename i retorna su contenido.
    public String readFile(String fileName) throws IOException {
        BufferedReader buf = new BufferedReader(new FileReader(fileName));
        String line = buf.readLine();
        StringBuilder sb = new StringBuilder();
        while (line != null){
            sb.append(line).append("\n");
            line = buf.readLine();
        }
        String fileAsString = sb.toString();
        return fileAsString;
    }

    //Escribe en filename el String textoDecodified.
    public void writeFile(String filename, String textDecodified) throws IOException {
        BufferedWriter writer = new BufferedWriter(new FileWriter(filename));
        //System.out.println(textDecodified + " hola");
        writer.write(textDecodified);
        writer.close();
    }

}
