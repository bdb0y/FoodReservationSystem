package com.dev.foodreservation.database.utilities;

import java.util.HashMap;
import java.util.Map;

public class SharedPreferences {

    private static Map<String, Object> preferences
            = new HashMap<>();

    public static void add(String key, Object value) {
        preferences.put(key, value);
    }

    public static Object get(String key) {
        return preferences.get(key);
    }

    public static void clear() {
        preferences.clear();
    }

    public static void remove(String key) {
        preferences.remove(key);
    }
}