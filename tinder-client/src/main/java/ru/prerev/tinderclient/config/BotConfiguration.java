package ru.prerev.tinderclient.config;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.telegram.telegrambots.updatesreceivers.DefaultBotSession;
import ru.prerev.tinderclient.domain.Authorizer;
import ru.prerev.tinderclient.rest.PostService;
import ru.prerev.tinderclient.telegrambot.Bot;
import ru.prerev.tinderclient.telegrambot.keyboard.InlineKeyboardMaker;

@Component
@RequiredArgsConstructor
public class BotConfiguration {

    @Bean
    Bot bot(BotProperty property, TelegramBotsApi botsApi){
        System.out.println("bot");
        return new Bot(property, botsApi, authorizer());
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
    @Bean
    RestTemplate restTemplate() {
        return new RestTemplate();
    }
    @Bean
    ServerProperty serverProperty() {
        return new ServerProperty();
    }
    @Bean
    Authorizer authorizer() {
        return new Authorizer(postService(), keyboardMaker());
    }
    @Bean
    PostService postService() {
        return new PostService(restTemplate(), serverProperty());
    }
    @Bean
    InlineKeyboardMaker keyboardMaker() {
        return new InlineKeyboardMaker();
    }
    /*
    @Bean
    PostService postService(ServerProperty serverProperty) {
        return new PostService(serverProperty);
    }*/
}
