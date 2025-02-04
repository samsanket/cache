package com.test.cache;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class CustomCache<K, V> {
    private final Map<K, V> cache;

    public CustomCache() {
        this.cache = new ConcurrentHashMap<>();
    }

    public void put(K key, V value) {
        cache.put(key, value);
    }

    public V get(K key) {
        return cache.get(key);
    }

    public void remove(K key) {
        cache.remove(key);
    }

    public void clear() {
        cache.clear();
    }

    public boolean containsKey(K key) {
        return cache.containsKey(key);
    }
}
