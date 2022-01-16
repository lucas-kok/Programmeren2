package com.codecademy.informationhandling.databaseconnection;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

public class DatabaseConnection {

    private final String connectionUrl = "jdbc:sqlserver://localhost\\MSSQLSERVER;databaseName=CodeCademyDB;user=sa;password=<Password Here>";
    private Connection con;
    private Statement stmt;
    private ResultSet rs;

    public DatabaseConnection() {
    }

    // Function that closes the ResultSet
    public void CloseResultSet() {
        if (rs != null) try {
            rs.close();
        } catch (Exception ignored) {
        }
        if (stmt != null) try {
            stmt.close();
        } catch (Exception ignored) {
        }
        if (con != null) try {
            con.close();
        } catch (Exception ignored) {
        }
    }

    // Function that will execute a given query and returns the ResultSet
    public ResultSet getQuery(String query) {
        try {
            Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
            con = DriverManager.getConnection(connectionUrl);
            stmt = con.createStatement();
            rs = stmt.executeQuery(query);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return rs;
    }

    // Function that will execute a given query
    public void setQuery(String query) {
        try {
            Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
            con = DriverManager.getConnection(connectionUrl);
            stmt = con.createStatement();
            stmt.execute(query);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (rs != null) {
                try {
                    rs.close();
                } catch (Exception ignored) {
                }
            }
            if (stmt != null) {
                try {
                    stmt.close();
                } catch (Exception ignored) {
                }
            }
            if (con != null) try {
                con.close();
            } catch (Exception ignored) {
            }
        }
    }
}
