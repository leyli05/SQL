package ru.netology.data;

import lombok.SneakyThrows;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.ScalarHandler;


import java.sql.Connection;
import java.sql.DriverManager;

public class SQLGenerator {
    private static QueryRunner queryRunner = new QueryRunner();

    private SQLGenerator() {
    }

    @SneakyThrows
    private static Connection getConnection() {
        return DriverManager.getConnection("jdbc:mysql://localhost:3306/app", "app", "pass");
    }

    @SneakyThrows
    public static DataGenerator.CodeVerify getCodeVerify() {
        String SQL_code = "SELECT code FROM auth_codes ORDER BY created DESC LIMIT 1";
        try (Connection connection = getConnection()) {
            String result = queryRunner.query(connection, SQL_code, new ScalarHandler<>());
            return new DataGenerator.CodeVerify(result);
        }
    }

    @SneakyThrows
    public static void dbClean() {
        queryRunner.update(getConnection(), "DELETE FROM auth_codes");
        queryRunner.update(getConnection(), "DELETE FROM card_transactions");
        queryRunner.update(getConnection(), "DELETE FROM cards");
        queryRunner.update(getConnection(), "DELETE FROM users");
    }
}