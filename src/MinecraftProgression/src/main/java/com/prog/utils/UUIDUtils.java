package com.prog.utils;

import java.util.UUID;

public class UUIDUtils {
    public static UUID of(String string) {
        return UUID.nameUUIDFromBytes(string.getBytes());
    }
}
