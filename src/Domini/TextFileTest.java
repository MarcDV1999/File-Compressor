package Domini;
//
//  TextFile.java
//  PROP Compressor Descompressor
//
//  Created by Marc Domènech.
//  Copyright © 2019 Marc Domènech. All rights reserved.
//

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;

class TextFileTest {
    String nom = "f";
    String text = "Hola me llamo Marc Domènech.\n";
    TextFile textFile;

    @BeforeEach
    void setUp() {
        textFile = new TextFile(nom);
    }

    @Test
    void readFile() throws IOException {
        String expected = text;
        String actual = textFile.readFile(textFile.getTextFileName());
        assertEquals(expected, actual);
    }

    @Test
    void writeFile() throws IOException{
        String expected = text;
        textFile.writeFile(textFile.getTextFileName(), text);
        String actual = textFile.readFile(textFile.getTextFileName());
        assertEquals(expected, actual);
    }

    @Test
    void getTextFileName() {
        String expected = nom + ".txt";
        String actual = textFile.getTextFileName();
        assertEquals(expected, actual);
    }
}