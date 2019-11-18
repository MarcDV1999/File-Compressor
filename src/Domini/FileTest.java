package Domini;
//
//  FileTest.java
//  PROP Compressor Descompressor
//
//  Created by Marc Domènech.
//  Copyright © 2019 Marc Domènech. All rights reserved.
//


import static org.junit.jupiter.api.Assertions.*;

class FileTest {
    File file;
    String nom = "Tomas";

    @org.junit.jupiter.api.BeforeEach
    void setUp() {
        //file = new File(nom);
    }


    @org.junit.jupiter.api.AfterEach
    void tearDown() {
    }

    @org.junit.jupiter.api.Test
    void getName() {
        String expected = nom;
        String actual = file.getName();
        assertEquals(expected, actual);
    }
}