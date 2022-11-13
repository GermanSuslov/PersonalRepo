package ru.prerev.tinderclient.config;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.telegram.telegrambots.updatesreceivers.DefaultBotSession;
import ru.prerev.tinderclient.domain.*;
import ru.prerev.tinderclient.rest.MatchService;
import ru.prerev.tinderclient.search.MatchSearcher;
import ru.prerev.tinderclient.search.GenderSearcher;
import ru.prerev.tinderclient.rest.DeleteService;
import ru.prerev.tinderclient.rest.GetService;
import ru.prerev.tinderclient.rest.PostService;
import ru.prerev.tinderclient.telegrambot.Bot;
import ru.prerev.tinderclient.telegrambot.keyboard.InlineKeyboardMaker;
import ru.prerev.tinderclient.telegrambot.keyboard.ReplyKeyboardMaker;

@Slf4j
@Component
@RequiredArgsConstructor
public class BotConfiguration {

    @Bean
    Bot bot(TelegramBotsApi botsApi) {
        log.info("bot");
        return new Bot(botsApi, authorizer(), menu());
    }

    @Bean
    Menu menu() {
        return new Menu(getService(), replyKeyboardMaker(), inlineKeyboardMaker(), lovers(), genderSearcher(), profile());
    }
    @Bean
    MatchService matchService() {
        return new MatchService(restTemplate());
    }

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
    GetService getService() {
        return new GetService(restTemplate());
    }

    @Bean
    Authorizer authorizer() {
        return new Authorizer(profile(), postService(), deleteService(), getService(), inlineKeyboardMaker());
    }

    @Bean
    PostService postService() {
        return new PostService(restTemplate());
    }

    @Bean
    InlineKeyboardMaker inlineKeyboardMaker() {
        return new InlineKeyboardMaker();
    }

    @Bean
    ReplyKeyboardMaker replyKeyboardMaker() {
        return new ReplyKeyboardMaker();
    }

    @Bean
    DeleteService deleteService() {
        return new DeleteService(restTemplate());
    }

    @Bean
    GenderSearcher genderSearcher() {
        return new GenderSearcher(getService(), matchService(), replyKeyboardMaker(), profile());
    }

    @Bean
    MatchSearcher matchSearcher() {
        return new MatchSearcher(getService());
    }

    @Bean
    Lovers lovers() {
        return new Lovers(profile(), replyKeyboardMaker(), matchSearcher());
    }

    Profile profile() {
        return new Profile(getService());
    }
}
