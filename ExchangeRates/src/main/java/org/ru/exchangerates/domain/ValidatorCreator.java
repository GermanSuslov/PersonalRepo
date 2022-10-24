package org.ru.exchangerates.domain;

import lombok.Getter;
import org.apache.log4j.Logger;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class ValidatorCreator {
    private Logger logger = Logger.getLogger(ValidatorCreator.class);
    @Getter
    private boolean telegramValidator = false;
    public String instruction = "Введите запрос по типу : \n" +
            "rate EURO/TRY/USD/AMD/BGN -date tomorrow/dd.MM.yyyy -alg mist/moon/linereg \n" +
            "или \n" +
            "rate EURO/TRY/USD/AMD/BGN -period week/month -alg mist/moon/linereg -output list/graph";

    public RequestValidator createConsoleValidator() {
        RequestValidator validator = null;
        try {
            System.out.println(instruction);
            do {
                BufferedReader reader = new BufferedReader(new InputStreamReader((System.in)));
                String request = reader.readLine();
                validator = new RequestValidator(request, this);
            } while (!validator.validated());
        } catch (IOException e) {
            logger.error("Ошибка чтения запроса с консоли" + e);
        }
        return validator;
    }

    public RequestValidator createTelegramValidator(String messageText) {
        telegramValidator = true;
        RequestValidator validator = null;
        String request = messageText;
        validator = new RequestValidator(request, this);
        if(!validator.validated()) {
            validator = null;
        }
        return validator;
    }
}
