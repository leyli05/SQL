package ru.netology.data;

import com.github.javafaker.Faker;
import lombok.Value;

import java.util.Locale;

public class DataGenerator {
    private static Faker faker = new Faker(new Locale("EN"));

    private DataGenerator() {
    }

    @Value
    public static class AuthUser {
        String login;
        String password;
    }

    public static AuthUser getAuthUser() {
        return new AuthUser("vasya", "qwerty123");
    }

    private static String getRandomLogin() {
        return faker.name().username();
    }

    private static String getRandomPassword() {
        return faker.internet().password();
    }

    public static AuthUser getRandomUser() {
        return new AuthUser(getRandomLogin(), getRandomPassword());
    }

    @Value
    public static class CodeVerify {
        String code;
    }

    public static CodeVerify getRandomCode() {
        return new CodeVerify(faker.numerify("000000"));
    }

}