package Domini;

import java.util.ArrayList;
import java.util.BitSet;
import java.util.List;

public class CompressedFile extends File {
    private BitSet charscodificats = new BitSet();
    private List resultat = new ArrayList();
    private int bytesfinals;
    private String compressedFileName;

    public CompressedFile(String name, List out, int size, BitSet match) {
        super(name);
        this.resultat = out;
        this.bytesfinals = size;
        this.charscodificats = match;
        this.compressedFileName = name + ".txt";
    }

        public List getResultat(){
            return resultat;
        }

        public BitSet getCharscodificats(){
            return charscodificats;
        }

        public int getBytesfinals(){
            return bytesfinals;
        }
        public String getCompressedFileName(){
            return compressedFileName;
        }

}
