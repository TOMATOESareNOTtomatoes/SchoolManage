package com.fanqie.commonutils.utils;

import java.util.HashSet;
import java.util.Random;
import java.util.Set;

public class RandomNumberGenerator {
    private static final int MAX_NUMBER = 999999999;
    private static final int MIN_NUMBER = 100000000;

    private static Set<Integer> generatedNumbers = new HashSet<>();
    private static Random random = new Random();

    public static int generate() {
        int number = random.nextInt(MAX_NUMBER - MIN_NUMBER + 1) + MIN_NUMBER;
        while (generatedNumbers.contains(number)) {
            number = random.nextInt(MAX_NUMBER - MIN_NUMBER + 1) + MIN_NUMBER;
        }
        generatedNumbers.add(number);
        return number;
    }
}