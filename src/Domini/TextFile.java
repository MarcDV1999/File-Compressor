package Domini;

import java.io.*;

public class TextFile extends File {
    private String textFileName;

    public TextFile(String name) {
        super(name);
        this.textFileName = name + ".txt";
    }

    //Retorna el texto de filename en un String.
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

    //Escribe en filename el String textoDecodified.
    public void writeFile(String filename, String textDecodified) throws IOException {
        BufferedWriter writer = new BufferedWriter(new FileWriter(filename));
        writer.write(textDecodified);
        writer.close();
    }

    //Consulta el Nombre del fichero de texto.
    public  String getTextFileName(){
        return textFileName;
    }
}
