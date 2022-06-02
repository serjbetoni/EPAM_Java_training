package com.efimchick.ifmo.streams.countwords;


import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class Words {
    public static final int MIN_FREQUENCY_WORDS = 10;
    public static final int MIN_LENGTH_WORD = 4;
    private static final String REGEX = "[^a-zA-Zа-яА-Я]";
    private static final String SPLIT_REGEX = " ";
    private static final String STRING_FORMAT = "%s - %d\n";


    public String countWords(List<String> lines) {
        String result = lines.stream()
                .map(line -> line.replaceAll(REGEX, SPLIT_REGEX))
                .flatMap(line -> Arrays.stream(line.toLowerCase().split(SPLIT_REGEX)))
                .filter(s -> s.length() >= MIN_LENGTH_WORD)
                .collect(Collectors.groupingBy(s -> s, Collectors.counting()))
                .entrySet()
                .stream()
                .filter(e -> e.getValue() >= MIN_FREQUENCY_WORDS)
                .sorted(Map.Entry.comparingByKey())
                .sorted((e1, e2) -> (int) (e2.getValue() - e1.getValue()))
                .map(e -> String.format(STRING_FORMAT, e.getKey(), e.getValue()))
                .collect(Collectors.joining());
        return (result == null || result.length() == 0)
                ? null
                : (result.substring(0, result.length() - 1));
    }
}
