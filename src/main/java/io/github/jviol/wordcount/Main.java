package io.github.jviol.wordcount;

import java.io.IOException;
import java.util.List;

public class Main {

    public static void main(String[] args) throws IOException {
        if (args.length < 1) {
            System.out.println("Usage: java Main <input_dir> [<output_dir>]");
            System.exit(1);
        }
        String outputDir = args.length > 1 ? args[1] : ".";
        List<String> wordsToExclude = new FileReader().readWordsToExclude();
        WordCounter wordCounter = new WordCounter(wordsToExclude);
        wordCounter.readFiles(args[0]);

        FileWriter fileWriter = new FileWriter(outputDir);
        fileWriter.writeExcludedWordCounts(wordCounter.getExcludedWords());
        fileWriter.writeToOutputFiles(wordCounter.getWordCounts());
    }
}
