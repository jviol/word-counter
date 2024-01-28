package io.github.jviol.wordcount;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class WordCounter {

    private final List<String> wordsToExclude;
    private final Map<String, Integer> excludedWordCounts = new HashMap<>();
    private final Map<String, Integer> wordCounts = new HashMap<>();

    public WordCounter(List<String> wordsToExclude) {
        this.wordsToExclude = wordsToExclude;
    }

    public Map<String, Integer> getExcludedWordCounts() {
        return excludedWordCounts;
    }

    public Map<String, Integer> getWordCounts() {
        return wordCounts;
    }

    public void updateWordCounts(Iterable<String> tokens) {
        for(String token : tokens) {
            String word = token
                    .replaceAll("[^a-zA-Z0-9]", "") // remove punctuation
                    .toLowerCase(); // case insensitive
            if (wordsToExclude.contains(word)) {
                excludedWordCounts.merge(word, 1, Integer::sum);
            } else {
                wordCounts.merge(word, 1, Integer::sum);
            }
        }
    }


}
