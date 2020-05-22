package com.thread.sync;

import java.util.HashMap;

public class HashMapDeadLock {
    private final HashMap<String, String> map = new HashMap<>();

    public void add(String key, String value) {
        this.map.put(key, value);
    }

    public static void main(String[] args) {
        final HashMapDeadLock hashMapDeadLock = new HashMapDeadLock();
        for (int i = 0; i < 2; i++) {
            new Thread(() -> {
                for (int j = 1; j < Integer.MAX_VALUE; j++) {
                    hashMapDeadLock.add(String.valueOf(j), String.valueOf(j));
                }
            }).start();
        }
    }
}
