package com.epam.rd.autotasks;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import static java.lang.Math.sqrt;

public class QuadraticEquation {
    public static final int FORMULA_CONSTANT_FOUR = 4;
    public static final int FORMULA_CONSTANT_TWO = 2;
    public static final int NO_ROOTS = 0;
    public static final int ONE_ROOT = 1;
    public static final int FIRST_ROOT = 0;
    public static final int SECOND_ROOT = 1;

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        double a = scanner.nextDouble();
        double b = scanner.nextDouble();
        double c = scanner.nextDouble();
        List<Double> roots = getQuadraticRoots(a,b,c);
        System.out.println(getQuadraticRootsAsString(roots));
    }

    public static List<Double> getQuadraticRoots (double a, double b, double c){
        List<Double> roots = new ArrayList<>();
        double d = b * b - FORMULA_CONSTANT_FOUR * a * c;
        if (d > 0) {
            double x1;
            double x2;
            x1 = (-b - Math.sqrt(d)) / (FORMULA_CONSTANT_TWO * a);
            x2 = (-b + Math.sqrt(d)) / (FORMULA_CONSTANT_TWO * a);
            roots.add(x1);
            roots.add(x2);
        } else if (d == 0) {
            double x;
            x = -b / (FORMULA_CONSTANT_TWO * a);
            roots.add(x);
        }
        return roots;
    }

    public static String getQuadraticRootsAsString (List<Double> roots){
        if (roots.size() == NO_ROOTS) {
            return "no roots";
        } else if (roots.size() == ONE_ROOT){
            return roots.get(FIRST_ROOT).toString();
        } else {
            return roots.get(FIRST_ROOT) + " " + roots.get(SECOND_ROOT);
        }
    }
}
