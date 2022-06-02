package com.efimchick.ifmo.collections.countwords;

import java.util.ArrayList;
import java.util.List;
import java.util.Comparator;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Arrays;
import java.util.Locale;
import java.util.TreeMap;

public class Words {
    public static final int MIN_FREQUENCY_WORDS = 10;
    public static final int MIN_LENGTH_WORD = 4;
    private static final String REGEX = "[^а-яёa-z]";

    private Map<String, Integer> getMapWords(List<String> lines) {
        Map<String, Integer> words = new TreeMap<>();
        String text = String.join(",", lines);
        String textToLowerCase = text.toLowerCase(Locale.ROOT);
        String[] splitText = textToLowerCase.split(REGEX);
        List<String> list = Arrays.asList(splitText);
        for (String word : list) {
            if ((word.length() >= MIN_LENGTH_WORD)) {
                int result = 0;
                if (words.containsKey(word)) {
                    result = words.get(word) + 1;
                } else {
                    result = 1;
                }
                words.put(word, result);
            }
        }
        return words;
    }

    public static Map<String, Integer> sortByValue(Map<String, Integer> map) {
        List<Map.Entry<String, Integer>> list = new ArrayList<>(map.entrySet());
        list.sort(comparingByValue());
        LinkedHashMap<String, Integer> result = new LinkedHashMap<>();
        for (Map.Entry<String, Integer> entry : list) {
            Integer value = entry.getValue();
            if (value >= MIN_FREQUENCY_WORDS) {
                result.put(entry.getKey(), entry.getValue());
            }
        }
        return result;
    }

    public static <K, V extends Comparable<? super V>> Comparator<Map.Entry<K, V>> comparingByValue() {
        return new Comparator<Map.Entry<K, V>>() {
            @Override
            public int compare(Map.Entry<K, V> o1, Map.Entry<K, V> o2) {
                int i = o2.getValue().compareTo(o1.getValue());
                if (i == 0) {
                    int checkKey = compareMyString((String) o2.getKey(), (String) o1.getKey());
                    if (checkKey > 0) {
                        checkKey = -1;
                    } else {
                        checkKey = 1;
                    }
                    return checkKey;
                }
                return i;
            }
        };
    }

    static int compareMyString(String s1, String s2) {
        return s1.compareTo(s2);
    }

    public String toString(Map<String, Integer> map) {
        StringBuilder text = new StringBuilder();
        int counter = 0;
        for (Map.Entry<String, Integer> entry : map.entrySet()) {
            text.append(entry.getKey() + " - " + entry.getValue());
            counter++;
            if (counter < map.size()) {
                text.append("\n");
            }
        }
        return text.toString();
    }

    public String countWords(List<String> lines) {
        Map<String, Integer> words = getMapWords(lines);
        Map<String, Integer> result = sortByValue(words);
        return toString(result);
    }
}
