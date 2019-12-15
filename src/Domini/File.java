package Domini;
//
//  File.java
//  PROP Compressor Descompressor
//
//  Created by Marc Domènech.
//  Copyright © 2019 Marc Domènech. All rights reserved.
//


import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class File extends java.io.File {

    public File(String path){
        super(path);
    }
    public String getAbsPath(){return this.getAbsolutePath();}
    public String getAbsName(){return this.getAbsPath().substring(0,getAbsolutePath().length()-4);}

    public String readFile(String fileName) throws IOException {
        //InputStream is = new FileInputStream(fileName);
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

}
