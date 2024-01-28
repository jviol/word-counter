package io.github.jviol.wordcount;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.*;
import java.util.stream.Stream;

public class WordCounter {

    private final List<String> wordsToExclude;
    private final Map<String, Integer> excludedWords = new HashMap<>();

    private Map<String, Integer> wordCounts;

    public WordCounter(List<String> wordsToExclude) {
        this.wordsToExclude = wordsToExclude;
    }

    public Map<String, Integer> getExcludedWords() {
        return excludedWords;
    }

    /**
     * @return null if no files have been read
     */
    public Map<String, Integer> getWordCounts() {
        return wordCounts;
    }

    public void readFiles(String dir) throws IOException {
        // read all files in dir
        try (Stream<Path> files = Files.walk(Path.of(dir))) {
            wordCounts = files
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
                if (wordsToExclude.contains(nextToken)) {
                    excludedWords.merge(nextToken, 1, Integer::sum);
                } else {
                    wordCount.merge(nextToken, 1, Integer::sum);
                }
            }
            return wordCount;
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }


}
