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

    public Folder(String route) {
        super(route);
        files = Arrays.asList(this.listFiles());
        size = files.size();
    }

    public int getSize(){return size;}
    public List<File> getFiles(){return files;}
}
