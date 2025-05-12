package com.example;

import java.util.Properties;
import java.io.InputStream;
import java.io.IOException;

public class DBConfig {
    public static String url;
    public static String user;
    public static String password;

    static {
        try (InputStream input = DBConfig.class.getClassLoader().getResourceAsStream("config.properties")) {
            Properties prop = new Properties();
            prop.load(input);

            url = prop.getProperty("db.url");
            user = prop.getProperty("db.user");
            password = prop.getProperty("db.password");

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}