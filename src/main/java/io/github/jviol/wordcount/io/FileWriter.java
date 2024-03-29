package io.github.jviol.wordcount.io;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class FileWriter {

    private final String outputDir;

    public FileWriter(String outputDir) {
        this.outputDir = outputDir;
    }

    public void writeExcludedWordCounts(Map<String, Integer> excludedWordCounts) throws IOException {
        List<String> lines = excludedWordCounts.entrySet().stream()
                .map(e -> e.getKey().toUpperCase() + " " + e.getValue())
                .toList();
        String fileName = "excluded.txt";

        Files.createDirectories(Path.of(outputDir)); // create output directory if it doesn't exist
        Files.write(Path.of(outputDir, fileName), lines);
        System.out.printf("Wrote %d lines to %s.%n", lines.size(), fileName);
    }

    public void writeToOutputFiles(Map<String, Integer> wordCounts) throws IOException {
        Map<Character, List<String>> linesByFirstLetter = wordCounts.entrySet().stream()
                .map(entry -> entry.getKey().toUpperCase() + " " + entry.getValue())
                .collect(Collectors.groupingBy(line -> line.charAt(0)));
        for (char c = 'A'; c <= 'Z'; c++) {
            List<String> lines = linesByFirstLetter.getOrDefault(c, List.of());
            // Write to FILE_<C>.txt
            String fileName = String.format("FILE_%c.txt", c);
            Path path = Path.of(outputDir, fileName);
            Files.write(path, lines);
            System.out.printf("Wrote %d lines to %s.%n", lines.size(), path);
        }
    }
}
