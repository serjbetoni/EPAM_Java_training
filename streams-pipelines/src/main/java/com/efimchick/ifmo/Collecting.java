package com.efimchick.ifmo;


import com.efimchick.ifmo.util.CourseResult;
import com.efimchick.ifmo.util.Person;

import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

public class Collecting {
    public static final int ODD_CHECK = 2;
    private static Map<Double, String> scoreAndMarks;

    static {
        scoreAndMarks = new LinkedHashMap<>();
        scoreAndMarks.put(ScoreAndMarks.SCORE_A.getFrom(), ScoreAndMarks.SCORE_A.getMark());
        scoreAndMarks.put(ScoreAndMarks.SCORE_B.getFrom(), ScoreAndMarks.SCORE_B.getMark());
        scoreAndMarks.put(ScoreAndMarks.SCORE_C.getFrom(), ScoreAndMarks.SCORE_C.getMark());
        scoreAndMarks.put(ScoreAndMarks.SCORE_D.getFrom(), ScoreAndMarks.SCORE_D.getMark());
        scoreAndMarks.put(ScoreAndMarks.SCORE_E.getFrom(), ScoreAndMarks.SCORE_E.getMark());
        scoreAndMarks.put(ScoreAndMarks.SCORE_F.getFrom(), ScoreAndMarks.SCORE_F.getMark());
    }

    public int sum(IntStream stream) {
        return stream.sum();
    }

    public int production(IntStream stream) {
        return stream.reduce(1, (a, b) -> a * b);
    }

    public int oddSum(IntStream stream) {
        return stream
                .filter(x -> x % ODD_CHECK != 0)
                .sum();
    }

    public Map<Integer, Integer> sumByRemainder(int divisor, IntStream stream) {
        return stream
                .boxed()
                .collect(Collectors.groupingBy(s -> s % divisor,
                        Collectors.summingInt(x -> x)));
    }

    public Map<Person, Double> totalScores(Stream<CourseResult> stream) {
        List<CourseResult> courseResults = stream.collect(Collectors.toList());
        return courseResults.stream()
                .collect(Collectors.toMap(CourseResult::getPerson, r -> r.getTaskResults()
                        .values().stream()
                        .mapToInt(v -> v)
                        .sum() / (double) getNumOfTasks(courseResults)));
    }

    private long getNumOfTasks(List<CourseResult> courseResults) {
        return courseResults.stream()
                .flatMap(r -> r.getTaskResults()
                        .keySet().stream())
                .distinct().count();
    }

    private long getNumOfPeople(List<CourseResult> courseResults) {
        return courseResults.stream()
                .map(CourseResult::getPerson)
                .distinct().count();
    }

    public Double averageTotalScore(Stream<CourseResult> stream) {
        List<CourseResult> courseResults = stream.collect(Collectors.toList());
        return courseResults.stream()
                .map(CourseResult::getTaskResults)
                .flatMapToDouble(r -> r.values().stream().mapToDouble(s -> s))
                .sum() / (getNumOfPeople(courseResults) * getNumOfTasks(courseResults));
    }

    public Map<String, Double> averageScoresPerTask(Stream<CourseResult> stream) {
        List<CourseResult> courseResults = stream.collect(Collectors.toList());
        return courseResults.stream()
                .flatMap(r -> r.getTaskResults().entrySet().stream())
                .collect(Collectors.groupingBy(Map.Entry::getKey,
                        Collectors.summingDouble(e -> e.getValue() / (double) getNumOfPeople(courseResults))));
    }

    public Map<Person, String> defineMarks(Stream<CourseResult> stream) {
        List<CourseResult> courseResults = stream.collect(Collectors.toList());
        return courseResults.stream()
                .collect(Collectors.toMap(CourseResult::getPerson,
                        s -> {
                            double score = s.getTaskResults().values().stream()
                                    .mapToDouble(v -> v)
                                    .sum() / getNumOfTasks(courseResults);
                            return scoreAndMarks.entrySet().stream()
                                    .filter(v -> v.getKey() <= score)
                                    .map(Map.Entry::getValue)
                                    .findFirst().orElse(ScoreAndMarks.SCORE_F.getMark());
                        }));
    }

    public String easiestTask(Stream<CourseResult> stream) {
        Map<String, Double> highestAverage = averageScoresPerTask(stream);
        return Collections.max(highestAverage.entrySet(), Map.Entry.comparingByValue()).getKey();
    }
}
