package com.efimchick.ifmo;

public enum ScoreAndMarks {
    SCORE_A(90.0, "A"),
    SCORE_B(83.0, "B"),
    SCORE_C(75.0, "C"),
    SCORE_D(68.0, "D"),
    SCORE_E(60.0, "E"),
    SCORE_F(0.0, "F");

    private Double from;
    private String mark;

    ScoreAndMarks(double from, String mark) {
        this.from = from;
        this.mark = mark;
    }

    public double getFrom() {
        return from;
    }

    public String getMark() {
        return mark;
    }
}
