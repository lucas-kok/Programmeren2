package com.codecademy.informationhandling.statistics;

import com.codecademy.informationhandling.databaseconnection.DatabaseConnection;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class StatisticsRepository {

    private final DatabaseConnection dbCon;

    public StatisticsRepository() {
        dbCon = new DatabaseConnection();
    }


}
