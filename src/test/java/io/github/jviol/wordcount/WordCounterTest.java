package io.github.jviol.wordcount;

import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

class WordCounterTest {

    @Test
    void testWordCount() {
        WordCounter wordCounter = new WordCounter(List.of("the", "a", "an"));
        wordCounter.updateWordCounts(List.of("The", "quick", "brown", "Dog", "jumps", "over", "the", "lazy", "dog."));
        assertEquals(Map.of("quick", 1, "brown", 1, "jumps", 1, "over", 1, "lazy", 1, "dog", 2),
                wordCounter.getWordCounts());
        assertEquals(Map.of("the", 2), wordCounter.getExcludedWordCounts());
    }

}