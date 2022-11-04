package ru.prerev.tinderclient.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;


@ConfigurationProperties(prefix = "bot")
@Data
public class BotProperty {
    String botName;
    String botToken;
}
