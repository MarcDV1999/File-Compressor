package Domini;

import java.io.*;
import java.util.*;

public class LZSS{

//Utilitzarem 1 flag per a marcar si trobem el següent símbol al nostre HashMap o no.
//If no match found, submit/store only next symbol.
//Otherwise submit/store the length and offset but not the next symbol

    //  private String writeFileName = "FitxerComprimit.bin";
    // private BufferedWriter writer;

    public LZSS(String name) {

        //  this.writer = new BufferedWriter(new FileWriter(name));
    }



    public CompressedFile compress(TextFile file) throws IOException{
        CompressedFile text = codify(file);
        String resultat = text.getResultat().toString();
        String nameCompressedFile = file.getName() + "Compressed.txt";
        file.writeFile(nameCompressedFile, resultat);
        //   BinFile binFile = new BinFile(file.getName());
        // binFile.writeBinFile(text,binFile.getBinFileName());
        return text;
    }

    public CompressedFile codify(TextFile file) throws IOException{
        String text = file.readFile(file.getTextFileName());
        HashMap<Character, List<Integer>> code = new HashMap <Character, List<Integer>>();
        int n = text.length();
        BitSet match = new BitSet();
        List out = new ArrayList();

        int size = 0;
        try {
            for (int i = 0; i < n; ++i) {
                Integer matchLen = 0;
                Integer inici = 0;
                char symbol = text.charAt(i); //Mirem el símbol actual.
                boolean trobat = false;
                List<Integer> poss = code.get(symbol);
                if (poss == null) {
                    poss = new LinkedList<Integer>();
                    poss.add(i);
                    code.put(symbol, poss);
                } else {
                    Iterator<Integer> it = poss.iterator();
                    while (it.hasNext()){
                        int s = it.next();
                        int consecutius = vegadesconsecutives(text, s + 1, i + 1, n); //miro quin es el length de caracters repetits
                        if (consecutius > matchLen) {
                            inici =  s;
                            matchLen = consecutius;
                        }
                        trobat = true;
                    }
                    poss.add(i);

                    int jn = Math.min(i + matchLen, n); //Ara mateix me la suda, ya ho mirarem pk en el cas _ dona igual.
                    for (int j = i + 1; j < jn; j++) {
                        List<Integer> p = code.get(text.charAt(j));
                        if (p == null) {
                            p = new LinkedList<Integer>();
                            code.put(text.charAt(j), p);
                        }
                        p.add(j);
                    }

                }
                if (trobat && matchLen > 2) {
                    match.set(size);
                    out.add(inici);
                    out.add(matchLen); //SUPERCLAVE, ES EL COUT.
                    i += matchLen - 1;
                } else {
                    match.set(size, false);
                    out.add(symbol);
                }
                size++;
            }

        }
        catch (Exception e) {
        }

        return new CompressedFile(file.getName(),out,size,match);




    }

    public void decodify(CompressedFile text ) throws IOException{

        TextFile textdescomprimit = new TextFile("textdescomprimit");
        int index = 0;
        StringBuffer output = new StringBuffer();
        int n = text.getBytesfinals();
        List textocodificado = text.getResultat();
        int iteradortexto = 0;
        //boolean inici = false; //Es un bool que nomes utilitzem si estem observant un enter. Si inici es true voldra dir que l'enter que estem tractant fa referencia a la posicio d'inici de la paraula que estem reescrivint. Si inici es false voldra dir que estem tractant el byte d'offset.
        for(int i = 0; i < n; i++){

            if(text.getCharscodificats().get(i)){

                //   if(inici) {
                int start = (int)textocodificado.get(iteradortexto);
                int offset = (int)textocodificado.get(iteradortexto+1);
                for(int j = 0; j < offset; ++j){
                    output.append(output.charAt(start++));
                }
                ++iteradortexto;

                // }
            } else{
                output.append(textocodificado.get(iteradortexto));
            }

            ++iteradortexto;
        }
        String descompressedFileName = text.getName() + "Descompressed.txt";
        textdescomprimit.writeFile(descompressedFileName,output.toString());
    }


    private int vegadesconsecutives(String textsencer, int a, int b, int final_text){
        int n = Math.min(b-a, final_text-b); //Mínim dels caràcters a l'esquerra de l'actual, i a la dreta de l'actual.
        for(int i = 0; i < n+1; i++){
            if(textsencer.charAt(a++) != textsencer.charAt(b++)) {
                return i+1; // Miro quantes lletres següents a l'actual són iguals a les lletres següents a la primera instància de la lletra
            }
        }
        return 0;
    }




}