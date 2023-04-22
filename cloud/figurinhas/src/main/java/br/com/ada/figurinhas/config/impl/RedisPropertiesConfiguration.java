package br.com.ada.stickers.config.impl;

import br.com.ada.stickers.config.RedisProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableConfigurationProperties
public class RedisPropertiesConfiguration {
    @Bean
    public RedisProperties redisPropertiesConfig() {
        return new RedisPropertiesFromApplicationYml();
    }
}
