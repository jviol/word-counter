package io.github.jviol.wordcount.io;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.Scanner;

public class ExcludeFileReader {
    public List<String> readWordsToExclude() throws IOException {
        try(InputStream inputStream = getClass().getResourceAsStream("/exclude.txt")) {
            if (inputStream == null) {
                throw new RuntimeException("exclude.txt not found");
            }
            return new Scanner(inputStream).tokens().toList();
        }
    }
}
