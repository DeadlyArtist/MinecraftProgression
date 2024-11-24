package com.prog.utils;

public class MathUtils {
    public static int log2(int N) {
        return logX(2, N);
    }

    public static double log2(double N) {
        return logX(2, N);
    }

    public static int logX(int X, int N) {
        return (int) (Math.log(N) / Math.log(X));
    }

    public static double logX(double X, double N) {
        return Math.log(N) / Math.log(X);
    }
}
