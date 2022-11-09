package ru.prerev.tinderclient.config;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.telegram.telegrambots.updatesreceivers.DefaultBotSession;
import ru.prerev.tinderclient.domain.Authorizer;
import ru.prerev.tinderclient.domain.Menu;
import ru.prerev.tinderclient.rest.DeleteService;
import ru.prerev.tinderclient.rest.GetService;
import ru.prerev.tinderclient.rest.PostService;
import ru.prerev.tinderclient.telegrambot.Bot;
import ru.prerev.tinderclient.telegrambot.keyboard.InlineKeyboardMaker;

@Component
@RequiredArgsConstructor
public class BotConfiguration {

    @Bean
    Bot bot(TelegramBotsApi botsApi){
        System.out.println("bot");
        return new Bot(botsApi, authorizer(), menu());
    }
    @Bean
    Menu menu() {
        return new Menu();
    }
    @Bean
    TelegramBotsApi botsApi() throws TelegramApiException {
        System.out.println("TelegramBotsApi");
        return new TelegramBotsApi(DefaultBotSession.class);
    }
    @Bean
    RestTemplate restTemplate() {
        return new RestTemplate();
    }
    @Bean
    GetService getService() {
        return new GetService(restTemplate());
    }
    @Bean
    Authorizer authorizer() {
        return new Authorizer(postService(), restTemplate(),deleteService(), getService(), keyboardMaker());
    }
    @Bean
    PostService postService() {
        return new PostService(restTemplate());
    }
    @Bean
    InlineKeyboardMaker keyboardMaker() {
        return new InlineKeyboardMaker();
    }
    @Bean
    DeleteService deleteService() {
        return new DeleteService(restTemplate());
    }
}
