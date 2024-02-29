package ru.netology.page;

import com.codeborne.selenide.SelenideElement;

import static com.codeborne.selenide.Condition.*;
import static com.codeborne.selenide.Selenide.*;

public class VerifyPage {
    private SelenideElement code = $("[data-test-id=code] input");
    private SelenideElement verifyButton = $("[data-test-id=action-verify]");
    private SelenideElement error = $(".notification__content");

    public DashboardPage validVerify(String VerifyCode) {
        verify(VerifyCode);
        return page(DashboardPage.class);
    }

    public void verifyPageVisible() {
        code.shouldBe(visible);
    }

    public void invalidCode() {
        error.shouldBe(visible);
        error.shouldHave(text("Неверно указан код! Попробуйте ещё раз"));
    }

    public void verify(String codeVerify) {
        code.setValue(codeVerify);
        verifyButton.click();
    }
}
