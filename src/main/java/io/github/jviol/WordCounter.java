package io.github.jviol;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.*;
import java.util.stream.Stream;

public class WordCounter {

    private final List<String> excludeWords = getExcludeWords();

    private Map<String, Integer> readFiles(String dir) throws IOException {
        // read all files in dir
        try (Stream<Path> files = Files.walk(Path.of(dir))) {
            return files
                    .filter(Files::isRegularFile)
                    .map(this::readFile)
                    .reduce((m1, m2) -> {
                        m2.forEach((word, count) -> m1.merge(word, count, Integer::sum));
                        return m1;
                    }).orElse(Collections.emptyMap());
        }
    }

    private Map<String, Integer> readFile(Path filePath) {
        try (var scanner = new Scanner(filePath)) {
            Map<String, Integer> wordCount = new HashMap<>();
            while (scanner.hasNext()) {
                String nextToken = scanner.next()
                        .replaceAll("[^a-zA-Z0-9]", "") // remove punctuation
                        .toLowerCase(); // case insensitive
                wordCount.merge(nextToken, 1, Integer::sum);
            }
            return wordCount;
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private List<String> getExcludeWords() {
        // read from exclude.txt
        InputStream inputStream = getClass().getResourceAsStream("/exclude.txt");
        if (inputStream == null) {
            throw new RuntimeException("exclude.txt not found");
        }
        return new Scanner(inputStream).tokens().toList();
    }

    public static void main(String[] args) throws IOException {
        if (args.length < 1) {
            System.out.println("Usage: java WordCounter <dir>");
            System.exit(1);
        }
        Map<String, Integer> wordCounts = new WordCounter().readFiles(args[0]);
        wordCounts.forEach((word, count) -> System.out.println(word + ": " + count));
        List<String> excludeWords = new WordCounter().getExcludeWords();
    }
}
