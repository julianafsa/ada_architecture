package br.com.ada.stickers.config.impl;

import br.com.ada.stickers.config.RedisProperties;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.validation.annotation.Validated;

@Getter
@Setter
@Validated
@ConfigurationProperties(prefix = "redis")
public class RedisPropertiesFromApplicationYml implements RedisProperties {
    @NotBlank(message = "Redis host could not be loaded into the application")
    private String host;

    @NotBlank(message = "Redis port could not be loaded into the application")
    private String port;

    @NotBlank(message = "Redis password could not be loaded into the application")
    private String password;
}
