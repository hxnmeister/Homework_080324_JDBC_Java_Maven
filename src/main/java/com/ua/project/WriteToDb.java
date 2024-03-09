package com.ua.project;

import com.ua.project.models.DBQueries;

import java.io.IOException;
import java.sql.SQLException;

public class WriteToDb {
    public static void main(String[] args) {
        DBQueries dbQueries = DBQueries.builder().build();

        while(true) {
            try {
                dbQueries.writeRandomQuery();
                Thread.sleep(1000);
            }
            catch(SQLException e) {
                System.out.println(" An error occurred while executing a database query!");
            }
            catch(InterruptedException e) {
                System.out.println(" The program \"Write To DB\" execution was interrupted!");
            }
            catch(IOException e) {
                System.out.println(e.getMessage());
            }
        }
    }
}
