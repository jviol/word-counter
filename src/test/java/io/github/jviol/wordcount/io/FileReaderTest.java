package io.github.jviol.wordcount.io;

import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.Iterator;
import java.util.NoSuchElementException;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;

class FileReaderTest {

    @Test
    void testEmptyDirectory() throws Exception {
        Iterator<String> iterator = new FileReader("src/test/resources/emptyDir").iterator();
        assertFalse(iterator.hasNext());
        assertThrows(NoSuchElementException.class, () -> iterator.next());
    }

    @Test
    void testEmptyFiles() throws IOException {
        Iterator<String> iterator = new FileReader("src/test/resources/dirWithEmptyFiles").iterator();
        assertFalse(iterator.hasNext());
        assertThrows(NoSuchElementException.class, () -> iterator.next());
    }
}