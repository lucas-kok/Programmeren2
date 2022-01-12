package com.codecademy.informationhandling.Databaseconnection;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

public class DatabaseConnection {

    private String connectionUrl = "jdbc:sqlserver://localhost\\MSSQLSERVER;databaseName=CodeCadamyDB;user=sa;password=LucasKokSQL!";
    Connection con = null;
    Statement stmt = null;
    ResultSet rs = null;

    public DatabaseConnection() {
    }

    public void CloseResultSet() {
        if (rs != null) try {
            rs.close();
        } catch (Exception e) {
        }
        if (stmt != null) try {
            stmt.close();
        } catch (Exception e) {
        }
        if (con != null) try {
            con.close();
        } catch (Exception e) {
        }
    }

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

    public void setQuery(String query) {
        try {
            Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
            con = DriverManager.getConnection(connectionUrl);
            stmt = con.createStatement();
            stmt.execute(query);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (rs != null) try {
                rs.close();
            } catch (Exception e) {
            }
            if (stmt != null) try {
                stmt.close();
            } catch (Exception e) {
            }
            if (con != null) try {
                con.close();
            } catch (Exception e) {
            }
        }
    }

}
