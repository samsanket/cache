package com.test.cache;

import java.util.Map;
import java.util.concurrent.*;

public class ExpiringCache<K, V> {
    private final Map<K, CacheEntry<V>> cache = new ConcurrentHashMap<>();
    private final ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(1);

    public ExpiringCache(long expiryTimeInSeconds) {
        scheduler.scheduleAtFixedRate(this::removeExpiredEntries, expiryTimeInSeconds, expiryTimeInSeconds, TimeUnit.SECONDS);
    }

    public void put(K key, V value, long ttl) {
        cache.put(key, new CacheEntry<>(value, System.currentTimeMillis() + ttl * 1000));
    }

    public V get(K key) {
        CacheEntry<V> entry = cache.get(key);
        if (entry == null || entry.isExpired()) {
            cache.remove(key);
            return null;
        }
        return entry.value;
    }

    public void remove(K key) {
        cache.remove(key);
    }

    public void clear() {
        cache.clear();
    }

    private void removeExpiredEntries() {
        long now = System.currentTimeMillis();
        cache.entrySet().removeIf(entry -> entry.getValue().expiryTime < now);
    }

    private static class CacheEntry<V> {
        V value;
        long expiryTime;

        CacheEntry(V value, long expiryTime) {
            this.value = value;
            this.expiryTime = expiryTime;
        }

        boolean isExpired() {
            return System.currentTimeMillis() > expiryTime;
        }
    }
}
