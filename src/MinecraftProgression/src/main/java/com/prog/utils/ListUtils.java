package com.prog.utils;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class ListUtils {
    @SafeVarargs
    public static <T> List<T> ofMany(List<T>... lists) {
        List<T> combinedList = new ArrayList<>();
        for (List<T> list : lists) {
            combinedList.addAll(list);
        }
        return Collections.unmodifiableList(combinedList); // Return an immutable list if desired
    }
}
