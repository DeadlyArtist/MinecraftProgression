package com.prog.utils;

import java.util.List;

public class PathUtils {
    public static String create(String... paths) {
        return String.join("/", paths);
    }
}
