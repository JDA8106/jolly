package io.github.jolly.cache;

import io.github.resilience4j.cache.Cache;

import java.util.function.Function;
import java.util.function.Supplier;

/**
 * Cache Policy for Jolly
 *
 * @author Anish Visaria
 *
 * @param <K> Input of Cached Function
 * @param <V> Output of Cached Function
 *
 */
public class CachePolicy<K, V> {

    private Cache<K, V> cacheContext;
    private Function<K, V> cachedFunction;

    /**
     * Instantiates Cache Policy with function to cache.
     * @param function function to cache
     */
    public CachePolicy(Supplier<V> function) {
        javax.cache.Cache<K, V> cacheInstance = CacheManager.getInstance().getCache("policy");
        cacheContext = Cache.of(cacheInstance);
        cachedFunction = Cache.decorateSupplier(cacheContext, function);
    }


    /**
     * Fetches result from cache or from the function, if unavailable.
     * @param cacheKey input of function
     * @return Output of function for particular input
     */
    public V getResult(K cacheKey) {
        return cachedFunction.apply(cacheKey);
    }


}
