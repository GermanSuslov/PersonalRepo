package org.example.ui.tests;

import io.qameta.allure.Description;
import io.qameta.allure.Feature;
import io.qameta.allure.Owner;
import io.qameta.allure.Step;
import io.qameta.allure.junit4.DisplayName;
import org.junit.Assert;
import org.junit.Test;

@Feature("Feature annotation test")
public class BaseTest {
    @Owner("German")
    @DisplayName("Hello test")
    @Description("Description annotation test1")
    @Test
    public void test1() {
        String helloString = "Hello";
        System.out.println(helloString);
        Assert.assertEquals("Hello", helloString);
    }

    @Owner("Suslov")
    @DisplayName("Bye test")
    @Description("Description annotation test2")
    @Test
    public void test2() {
        String helloString = "Bye";
        System.out.println(helloString);
        Assert.assertEquals("Hello", helloString);
    }
}
