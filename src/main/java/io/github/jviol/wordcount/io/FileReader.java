package io.github.jviol.wordcount.io;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.Scanner;
import java.util.stream.Stream;

/**
 * An {@link Iterable} that iterates over all the lines in all the files in a directory.
 */
public class FileReader implements Iterable<String> {

    private final Iterator<Path> files;

    public FileReader(String dir) throws IOException {
        try (Stream<Path> files = Files.walk(Path.of(dir))) {
            this.files = files.filter(Files::isRegularFile).toList().iterator(); // Collecting to a list so we can close the stream
        }
    }

    @Override
    public Iterator<String> iterator() {
        return new Iterator<>() {
            private Scanner scanner;

            @Override
            public boolean hasNext() {
                if (scanner == null) {
                    scanner = nextScanner();
                    if (scanner == null) {
                        return false;
                    }
                }
                while (!scanner.hasNext()) {
                    scanner = nextScanner();
                    if (scanner == null) {
                        return false;
                    }
                }
                return true;
            }

            @Override
            public String next() {
                if (!hasNext()) {
                    throw new NoSuchElementException();
                }
                return scanner.next();
            }

            private Scanner nextScanner() {
                if (files.hasNext()) {
                    Path filePath = files.next();
                    try {
                        return new Scanner(filePath);
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                } else {
                    return null;
                }
            }
        };
    }
}
