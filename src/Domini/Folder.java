package Domini;
//
//  Folder.java
//  PROP Compressor Descompressor
//
//  Created by Marc Domènech.
//  Copyright © 2019 Marc Domènech. All rights reserved.
//

import java.io.File;

public class Folder {

    public Folder(String route) {
        String actualFolder = System.getProperty(route);
        File carpeta = new File(actualFolder);

        System.out.println(carpeta.isDirectory()); // Imprimeix si es una carpeta o no
    }
}
