package com.fanqie.commonutils.utils;

import java.util.UUID;

public class UUIDStringUtils {
    public static String randomUUID() {
        UUID uuid = UUID.randomUUID();
        return uuid.toString().replace("-", "").toUpperCase();
    }
}
