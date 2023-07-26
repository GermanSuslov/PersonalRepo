package org.example.ui.tests.main_page;

import org.example.ui.forms.pages.MainPage;
import org.example.ui.forms.pages.MostPlayedPage;
import org.example.ui.forms.pages.NewsPage;
import org.example.ui.forms.pages.PrivacyAgreementPage;
import org.example.ui.tests.BaseTest;
import org.example.utils.DriverUtils;
import org.example.utils.ResourcesHelper;
import jdk.jfr.Description;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.util.List;

public class SteamMainPageTests extends BaseTest {
    private PrivacyAgreementPage privacyAgreementPage;
    private MostPlayedPage mostPlayedPage;
    private MainPage mainPage;
    private NewsPage newsPage;

    @BeforeMethod
    public void openMainPage() {
        mainPage = new MainPage();
        mainPage.open();
    }

    @Test()
    @Description("Privacy policy page test")
    public void privacyPolicyTest() {
        logger.info("Privacy policy page test started");
        mainPage.footerForm.privacyLinkClick();
        privacyAgreementPage = new PrivacyAgreementPage();
        Assert.assertTrue(privacyAgreementPage.openedInNewTab(),
                "Number of opened tabs is invalid");
        DriverUtils.closeRecentWindow();

        Assert.assertTrue(privacyAgreementPage.isOpened(),
                "Languages elements list is invisible or wrong page is opened");

        Assert.assertTrue(privacyAgreementPage.languagesAreSupported(),
                "Languages are not supported");

        Integer revisionYear = ResourcesHelper.getRevisionTestData();
        Assert.assertTrue(privacyAgreementPage.policyRevisionSignIsCorrect(revisionYear),
                "Revision year is invalid");
        logger.info("Privacy policy page test finished");
    }

    @Test()
    @Description("Most played games order test")
    public void mostPlayedGamesTest() {
        logger.info("Most played games order test started");
        Assert.assertTrue(mainPage.isOpened(), "Cannot find unique element - gift cards image");

        mainPage.navigationForm
                .newAndNoteworthyDropdownNavigateMouse()
                .mostPlayedButtonClick();
        mostPlayedPage = new MostPlayedPage();
        Assert.assertTrue(mostPlayedPage.isOpened(), "Cannot find unique element - charts button");

        List<Integer> gamePositions = ResourcesHelper.getGamePositionsTestData();
        for (Integer gamePosition : gamePositions) {
            Assert.assertTrue(mostPlayedPage.hasMoreCurrentPlayersThanNextPosition(gamePosition),
                    "Next position has more players, than position №" + gamePosition);
        }
        logger.info("Most played games order test finished");
    }

    @Test()
    @Description("Empty filter checkboxes test")
    public void newsFiltersTest() {
        logger.info("Empty filter checkboxes test started");
        Assert.assertTrue(mainPage.isOpened(), "Cannot find unique element - gift cards image");

        mainPage.headerForm.newsButtonClick();
        newsPage = new NewsPage();
        Assert.assertTrue(newsPage.isOpened(), "Cannot find unique element - steam news sidebar");

        newsPage.optionsAndFiltersButtonClick();
        newsPage.unselectAllCheckBoxFilters();
        String messageActualResult = newsPage.getResultMessage();
        String messageExpectedResult = ResourcesHelper.getNewsResultMessage().toUpperCase();
        Assert.assertEquals(messageActualResult, messageExpectedResult, "Invalid news result message");
        logger.info("Empty filter checkboxes test finished");
    }
}