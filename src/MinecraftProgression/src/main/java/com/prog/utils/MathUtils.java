package com.prog.utils;

public class MathUtils {
    public static int log2(int N) {
        return logX(2, N);
    }

    public static int logX(int X, int N) {
        return (int) (Math.log(N) / Math.log(X));
    }
}
