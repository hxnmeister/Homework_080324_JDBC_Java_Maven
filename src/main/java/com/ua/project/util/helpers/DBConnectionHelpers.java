package com.ua.project.util.helpers;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class DBConnectionHelpers {
    public static Connection connectToDB() throws IOException, SQLException {
        Properties properties = new Properties();

        try(InputStream inputStream = Files.newInputStream(Paths.get("./src/main/resources/application.properties"))) {
            properties.load(inputStream);
        }

        String url = properties.getProperty("db.url");
        String user = properties.getProperty("db.user");
        String password = properties.getProperty("db.password");

        return DriverManager.getConnection(url, user, password);
    }
}
