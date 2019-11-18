package Domini;
//
//  Ctrl_Domini.java
//  PROP Compressor Descompressor
//
//  Created by Marc Domènech.
//  Copyright © 2019 Marc Domènech. All rights reserved.
//

import java.io.IOException;

public class Ctrl_Domini {
    public void compressLZ78(String nom) throws IOException {
        TextFile file = new TextFile(nom);
        LZ78 lz78 = new LZ78();
        lz78.compress(file);
    }

    public void disCompressLZ78(String nom) throws IOException {
        BinFile file = new BinFile(nom);
        LZ78 lz78 = new LZ78();
        lz78.discompress(file);
    }
    public void compressLZSS(String nom) throws IOException {
        TextFile file = new TextFile(nom);
        LZSS lzss = new LZSS(nom);
        lzss.compress(file);
    }
    public void disCompressLZSS(String nom) throws IOException {
        TextFile file = new TextFile(nom);
        LZSS lzss = new LZSS(nom);
        CompressedFile c = lzss.compress(file);
        lzss.decodify(c);

    }
}
