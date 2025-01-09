package com.sadds.ConcurrentCollection;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

public class ConcurrentHashmapDemo {
    private static final Map<String, String> cache = new ConcurrentHashMap<String, String>();

    /*
       > Adding an element
            first key is hashed to find out which segment it belongs to
            then the lock is acquired for that particular segment
            value is inserted
            and the lock is released
       > fetching an element
            first key is hashes to find out which segment it belongs to
            then the lock is acquires for that particular segment
            then key is searches in the segment ( if not found then null returns)
            and the lock is releases
    */

    public static void main(String[] args) {
        for (int i = 0; i < 10; i++) {
            final int threadNum = i;

            new Thread(() -> {
                String key = "Key " + threadNum;
                for (int j = 0; j < 3; j++) {
                    String value = getCachedValue(key);
                    System.out.println("Thread " + Thread.currentThread().getName() +
                            " : Key =  " + key + " Value = " + value);
                }
            }).start();
        }
    }

    private static String getCachedValue(String key) {
        String value = cache.get(key);

        if (value == null) {
            value = compute(key);
            cache.put(key, value);
        }

        return value;
    }

    private static String compute(String key) {
        System.out.println(key + " not present in cache, so computing");
        try {
            Thread.sleep(500);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

        return "Value for " + key + " : " + Thread.currentThread().getName();
    }
}












