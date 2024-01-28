package io.github.jviol.wordcount;

import io.github.jviol.wordcount.io.ExcludeFileReader;
import io.github.jviol.wordcount.io.FileReader;
import io.github.jviol.wordcount.io.FileWriter;

import java.io.IOException;
import java.util.List;

public class Main {

    public static void main(String[] args) throws IOException {
        if (args.length < 1) {
            System.out.println("Usage: java Main <input_dir> [<output_dir>]");
            System.exit(1);
        }
        String outputDir = args.length > 1 ? args[1] : "output";
        List<String> wordsToExclude = new ExcludeFileReader().readWordsToExclude();
        WordCounter wordCounter = new WordCounter(wordsToExclude);
        wordCounter.updateWordCounts(new FileReader(args[0]));

        FileWriter fileWriter = new FileWriter(outputDir);
        fileWriter.writeExcludedWordCounts(wordCounter.getExcludedWordCounts());
        fileWriter.writeToOutputFiles(wordCounter.getWordCounts());
    }
}
