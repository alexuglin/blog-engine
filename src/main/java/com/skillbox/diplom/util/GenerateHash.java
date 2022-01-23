package com.skillbox.diplom.util;

import java.util.UUID;

public class GenerateHash {

    private static final String DASH = "-";

    public static String getHash(int len) {
        return getHash().substring(0, len);
    }

    public static String getHash() {
        return UUID.randomUUID().toString().replace(DASH, "");
    }
}
