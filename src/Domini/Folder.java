package Domini;
//
//  Folder.java
//  PROP Compressor Descompressor
//
//  Created by Marc Domènech.
//  Copyright © 2019 Marc Domènech. All rights reserved.
//

import java.io.File;
import java.util.Arrays;
import java.util.List;

public class Folder extends Domini.File {
    private List<File> files;
    private int size;

    // Crea una nova carpeta amb path = route i guarda la llista de fitxers que conté a files i la mida de la carpeta a size
    public Folder(String route) {
        super(route);
        files = Arrays.asList(this.listFiles());
        size = files.size();
    }

    // Llegeix i retorna el contingut dels fitxers que conte la carpeta.
    public String readFolderFiles(String folder, int recursionLevel) {
        String resultado = "";
        File folderFile = new File(folder);
        String fileText = "", folderText = "";
        if (folderFile.exists()) {
            File[] files = folderFile.listFiles();
            for (File file : files) {
                fileText = "";
                boolean isFolder = file.isDirectory();
                if(isFolder) {
                    for(int i = 0; i < recursionLevel;i++){
                        folderText += "\t";
                    }
                    resultado += folderText + "FOLDER: " + file.getName() + "\n";
                    resultado += readFolderFiles(file.getAbsolutePath(), recursionLevel+1);
                }
                else {
                    for(int i = 0; i < recursionLevel;i++){
                        fileText += "\t";
                        //
                    }
                    fileText += "FILE: ";
                    resultado += (isFolder ? folderText : fileText) + file.getName() + "\n";
                }
            }
        }
        System.out.println(resultado);
        return resultado;
    }

    //Elimina una carpeta i tot el seu contingut.
    public void deleteFolder(File folder) {
        File[] files = folder.listFiles();
        if(files!=null) { //some JVMs return null for empty dirs
            for(File f: files) {
                if(f.isDirectory()) {
                    this.deleteFolder(f);
                } else {
                    f.delete();
                }
            }
        }
        folder.delete();
    }

    // Retorna la mida de la carpeta.
    public long getSize(){
            long length = 0;
            for (File file : this.listFiles()) {
                if (file.isFile())
                    length += file.length();
                else{
                    Folder folder = new Folder(file.getAbsolutePath());
                    length += folder.getSize();
                }
            }
            return length;
    }

    // Retorna la llista de fitxers que es troben dins la carpeta.
    public List<File> getFiles(){return files;}
}
