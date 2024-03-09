package com.ua.project;

import com.ua.project.models.DBQueries;

import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;

public class ReadInfoFromDB {
    public static void main(String[] args) {
        DBQueries dbQueries = DBQueries.builder().build();

        while(true) {
            try(ResultSet resultSet = dbQueries.searchQuery("INFO")) {
                dbQueries.displayQueryResultAndDelete(resultSet);
            }
            catch (SQLException e) {
                System.out.println(" An error occurred while executing a database query!");
            }
            catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }
}
