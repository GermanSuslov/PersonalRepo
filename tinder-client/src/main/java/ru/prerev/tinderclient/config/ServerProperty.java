package ru.prerev.tinderclient.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "server")
@Data
public class ServerProperty {
    String url;
}
