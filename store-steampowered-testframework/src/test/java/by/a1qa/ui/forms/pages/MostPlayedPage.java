package by.a1qa.ui.forms.pages;

import by.a1qa.ui.elements.Table;
import by.a1qa.ui.forms.SteamForm;
import org.openqa.selenium.By;

import java.util.List;
import java.util.stream.Collectors;

public class MostPlayedPage extends SteamForm {
    private final Table currentPlayersTable = new Table("Values of current players",
            By.xpath("//td[contains(@class, 'weeklytopsellers_ConcurrentCell')]"));

    public MostPlayedPage() {
        super(new Table("Current players column title",
                By.xpath("//th[contains(@class, 'weeklytopsellers_Concurrent')]")));
    }

    public boolean hasMoreCurrentPlayersThanNextPosition(int gameChartPosition) {
        int gameListIndex = gameChartPosition - 1;
        waiter.waitForElementsToBeVisible(currentPlayersTable);
        List<Long> currentPlayersValues = currentPlayersTable
                .getElementsText()
                .stream()
                .limit(gameChartPosition + 1)
                .map(number -> number.replaceAll("[\\s,.]", ""))
                .map(Long::parseLong)
                .collect(Collectors.toList());
        return currentPlayersValues.get(gameListIndex) > currentPlayersValues.get(gameListIndex + 1);
    }
}