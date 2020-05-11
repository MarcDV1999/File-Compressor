package Domini;

import java.io.*;
import java.util.*;

public class LZSS{
    private long executionTimeIni,executionTimeEnd, executionTime= System.currentTimeMillis();
    private Set_Statistics statistics;

//Utilitzarem 1 flag per a marcar si trobem el següent símbol al nostre HashMap o no.
// Si no trobem el següent simbol, aleshores guarden nomes el següent.
// si el trobem, guardem la mida i el offset pero no el següent simbol

    public LZSS(){statistics = new Set_Statistics();}

    public CompressedFile compress(File file) throws IOException{
        executionTimeIni = System.currentTimeMillis();

        CompressedFile text = codify(file);
        String resultat = text.getResultat().toString();
        String nameCompressedFile = file.getAbsName() + "Compressed.txt";
        file.writeFile(nameCompressedFile, resultat);

        executionTimeEnd = System.currentTimeMillis();
        executionTime = executionTimeEnd - executionTimeIni; // ms
        File newFile = new File(nameCompressedFile);
        statistics.addCompressLZSS(new Statistic(executionTime, file.length(), newFile.length()));
        return text;
    }

    public CompressedFile codify(File file) throws IOException{
        String text = file.readFile(file.getAbsolutePath());
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

        return new CompressedFile(file.getAbsolutePath(),out,size,match);
    }

    public void decodify(CompressedFile text ) throws IOException{

        File textdescomprimit = new File(text.getAbsolutePath());
        StringBuffer output = new StringBuffer();
        int n = text.getBytesfinals();
        List textocodificado = text.getResultat();
        int iteradortexto = 0;
        //boolean inici = false; //Es un bool que nomes utilitzem si estem observant un enter. Si inici es true voldra dir que l'enter que estem tractant fa referencia a la posicio d'inici de la paraula que estem reescrivint. Si inici es false voldra dir que estem tractant el byte d'offset.
        for(int i = 0; i < n; i++){

            if(text.getCharscodificats().get(i)){

                //   if(inici) {
                int start = Integer.valueOf(textocodificado.get(iteradortexto).toString());
                int offset = Integer.valueOf(textocodificado.get(iteradortexto+1).toString());
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
        String descompressedFileName = textdescomprimit.getAbsName();
        String textNou = output.toString();
        System.out.println("- " + descompressedFileName);
        textdescomprimit.writeFile(descompressedFileName + "_new.txt",textNou);

        executionTimeEnd = System.currentTimeMillis();
        executionTime = executionTimeEnd - executionTimeIni; // ms
        statistics.addDiscompressLZSS(new Statistic(executionTime, text.length(), textdescomprimit.length()));
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


    // Retorna el conjunto de estadisticas del algoritmo.
    public Set_Statistics getStatistics(){return statistics;}
}
