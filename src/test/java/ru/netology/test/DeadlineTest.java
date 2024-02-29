package ru.netology.test;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.Test;
import ru.netology.data.DataGenerator;
import ru.netology.data.SQLGenerator;
import ru.netology.page.LoginPage;

import static com.codeborne.selenide.Selenide.*;
import static ru.netology.data.SQLGenerator.dbClean;

public class DeadlineTest {
    @AfterAll
    static void cleaner() {
        dbClean();
    }

    @Test
    void shouldSuccessLogin() {
        var loginPage = open("http://localhost:9999", LoginPage.class);
        var authUser = DataGenerator.getAuthUser();
        var verifyPage = loginPage.validAuth(authUser);
        verifyPage.verifyPageVisible();
        var verifyCode = SQLGenerator.getCodeVerify();
        verifyPage.validVerify(verifyCode.getCode());
    }

    @Test
    void shouldBlockUserAfterThreeAttemptsOfAuthWithInvalidData() {
        var loginPage = open("http://localhost:9999", LoginPage.class);
        var oneAuth = new DataGenerator.AuthUser(DataGenerator.getAuthUser().getLogin(),
                DataGenerator.getRandomUser().getPassword());
        loginPage.validAuth(oneAuth);
        loginPage.errorAuth();
        loginPage.clean();
        clearBrowserCookies();
        var twoAuth = new DataGenerator.AuthUser(DataGenerator.getAuthUser().getLogin(),
                DataGenerator.getRandomUser().getPassword());
        loginPage.validAuth(twoAuth);
        loginPage.errorAuth();
        loginPage.clean();
        clearBrowserCookies();
        var threeAuth = new DataGenerator.AuthUser(DataGenerator.getAuthUser().getLogin(),
                DataGenerator.getRandomUser().getPassword());
        loginPage.validAuth(threeAuth);
        loginPage.blockUser();
    }

    @Test
    void shouldRandomLogin() {
        var loginPage = open("http://localhost:9999", LoginPage.class);
        var authUser = new DataGenerator.AuthUser(DataGenerator.getRandomUser().getLogin(),
                DataGenerator.getAuthUser().getPassword());
        loginPage.validAuth(authUser);
        loginPage.errorAuth();
    }

    @Test
    void shouldRandomPass() {
        var loginPage = open("http://localhost:9999", LoginPage.class);
        var authUser = new DataGenerator.AuthUser(DataGenerator.getAuthUser().getLogin(),
                DataGenerator.getRandomUser().getPassword());
        loginPage.validAuth(authUser);
        loginPage.errorAuth();
    }

    @Test
    void shouldRandomVerifyCode() {
        var loginPage = open("http://localhost:9999", LoginPage.class);
        var authUser = DataGenerator.getAuthUser();
        var verifyPage = loginPage.validAuth(authUser);
        verifyPage.verifyPageVisible();
        var verifyCode = DataGenerator.getRandomCode().getCode();
        verifyPage.verify(verifyCode);
        verifyPage.invalidCode();
    }
}
