package ru.azmeev.intershop.showcase.configuration;

import org.springframework.boot.autoconfigure.cache.RedisCacheManagerBuilderCustomizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.cache.RedisCacheConfiguration;
import ru.azmeev.intershop.showcase.service.CacheItemService;
import ru.azmeev.intershop.showcase.service.CartService;

import java.time.Duration;
import java.time.temporal.ChronoUnit;

@Configuration
public class RedisConfiguration {

    @Bean
    public RedisCacheManagerBuilderCustomizer itemsCacheCustomizer() {
        return builder -> builder
                .withCacheConfiguration(
                        CacheItemService.ITEMS_CACHE,
                        RedisCacheConfiguration.defaultCacheConfig()
                                .entryTtl(Duration.of(2, ChronoUnit.MINUTES))
                ).withCacheConfiguration(
                        CacheItemService.ITEMS_LIST_CACHE,
                        RedisCacheConfiguration.defaultCacheConfig()
                                .entryTtl(Duration.of(2, ChronoUnit.MINUTES)))
                .withCacheConfiguration(
                        CacheItemService.ITEMS_PAGE_CACHE,
                        RedisCacheConfiguration.defaultCacheConfig()
                                .entryTtl(Duration.of(2, ChronoUnit.MINUTES)))
                .withCacheConfiguration(
                        CartService.CART_CACHE_NAME,
                        RedisCacheConfiguration.defaultCacheConfig()
                                .entryTtl(Duration.of(1, ChronoUnit.MINUTES))
                );
    }
}
