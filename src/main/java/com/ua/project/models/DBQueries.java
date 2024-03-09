package com.ua.project.models;

import com.ua.project.util.helpers.DBConnectionHelpers;
import lombok.Builder;
import lombok.NoArgsConstructor;

import java.io.IOException;
import java.sql.*;
import java.util.List;
import java.util.Random;

@Builder
@NoArgsConstructor
public class DBQueries {
    /**
     * This method selects one of the queries at random and sends them to the database.
     *
     * @return true if the first result is a ResultSet object; false if it is an update count or there are no results
     * @throws SQLException
     * @throws IOException
     */
    public boolean writeRandomQuery() throws SQLException, IOException {
        Random random = new Random();
        final List<String> QUERIES = List.of(
                """
                    INSERT INTO notice(message, type, processed)
                    VALUES ('New message from ' || NOW(), 'INFO', B'0')
                """,
                """
                    INSERT INTO notice(message, type, processed)
                    VALUES ('Error occurred in ' || NOW(), 'WARN', B'0')
                """);

        try(Connection connection = DBConnectionHelpers.connectToDB()) {
            Statement statement = connection.createStatement();
            return statement.execute(QUERIES.get(random.nextInt(QUERIES.size())));
        }
    }

    /**
     * This method searches for records in the database using “type” as a search parameter.
     *
     * @param type a parameter that corresponds to the desired column in the database.
     * @return resultSet
     * @throws SQLException
     * @throws IOException
     */
    public ResultSet searchQuery(final String type) throws SQLException, IOException {
        final String searchQuery = """
                    SELECT *
                    FROM notice
                    WHERE type=? AND processed=B'0'
                """;

        try(Connection connection = DBConnectionHelpers.connectToDB()) {
            PreparedStatement preparedStatement = connection.prepareStatement(searchQuery);
            preparedStatement.setString(1, type.toUpperCase());

            return preparedStatement.executeQuery();
        }
    }

    /**
     *
     * @param id primary key in database
     * @param type a parameter that corresponds to the desired column in the database.
     * @return true if the first result is a ResultSet object; false if the first result is an update count or there is no result
     * @throws SQLException
     * @throws IOException
     */
    public boolean deleteQuery(final int id, final String type) throws SQLException, IOException {
        final String deleteQuery = """
                    DELETE FROM notice
                    WHERE id=? AND type=?
                """;

        try(Connection connection = DBConnectionHelpers.connectToDB()) {
            PreparedStatement preparedStatement = connection.prepareStatement(deleteQuery);
            preparedStatement.setInt(1, id);
            preparedStatement.setString(2, type.toUpperCase());

            return preparedStatement.execute();
        }
    }

    /**
     * This method displays received data in the console and then deletes records gradually by id.
     *
     * @param resultSet  resulting data set from DB
     *
     * @throws SQLException
     * @throws IOException
     */
    public void displayQueryResultAndDelete(ResultSet resultSet) throws SQLException, IOException {
        while(resultSet.next()) {
            int id = resultSet.getInt("id");
            boolean processed = resultSet.getBoolean("processed");
            String message = resultSet.getString("message");
            String type = resultSet.getString("type");

            System.out.println("| " + id + " | " + message + " | " + type + " | " + processed + " |");
            deleteQuery(id, type);
        }
    }
}
