package ru.prerev.tinderclient.config;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.telegram.telegrambots.updatesreceivers.DefaultBotSession;
import ru.prerev.tinderclient.telegrambot.keyboard.InlineKeyboardMaker;
import ru.prerev.tinderclient.telegrambot.keyboard.ReplyKeyboardMaker;

@Slf4j
@Component
@RequiredArgsConstructor
public class BotConfiguration {

    @Bean
    TelegramBotsApi botsApi() throws TelegramApiException {
        log.info("TelegramBotsApi");
        return new TelegramBotsApi(DefaultBotSession.class);
    }

    @Bean
    RestTemplate restTemplate() {
        return new RestTemplate();
    }

    @Bean
    InlineKeyboardMaker inlineKeyboardMaker() {
        return new InlineKeyboardMaker();
    }

    @Bean
    ReplyKeyboardMaker replyKeyboardMaker() {
        return new ReplyKeyboardMaker();
    }

}
