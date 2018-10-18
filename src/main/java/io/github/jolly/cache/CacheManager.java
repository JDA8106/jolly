package io.github.jolly.cache;

import javax.cache.Caching;

/**
 * Singleton CacheManager for CachePolicy
 *
 * @author Anish Visaria
 *
 */
public class CacheManager {

    private static javax.cache.CacheManager cacheManager;

    /**
     * Gets instance of a CacheManager.
     * @return CacheManager instance
     */
    public static javax.cache.CacheManager getInstance() {
        if (cacheManager == null) {
            cacheManager = Caching.getCachingProvider().getCacheManager();
        }
        return cacheManager;
    }

}
