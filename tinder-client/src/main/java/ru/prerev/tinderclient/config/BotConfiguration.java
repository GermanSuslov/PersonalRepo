package ru.prerev.tinderclient.config;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.telegram.telegrambots.updatesreceivers.DefaultBotSession;
import ru.prerev.tinderclient.telegrambot.Bot;

@Component
@RequiredArgsConstructor
public class BotConfiguration {

    @Bean
    Bot bot(BotProperty property, TelegramBotsApi botsApi) throws TelegramApiException {
        System.out.println("bot");
        return new Bot(property, botsApi);
    }
    @Bean
    TelegramBotsApi botsApi() throws TelegramApiException {
        System.out.println("TelegramBotsApi");
        return new TelegramBotsApi(DefaultBotSession.class);
    }
    @Bean
    BotProperty property() {
        return new BotProperty();
    }
}
