package nl.pekict.project;

import java.sql.*;

public class dbCon {

    private String connectionUrl = "jdbc:sqlserver://localhost\\MSSQLSERVER;databaseName=ProjectCodeAcademy;user=sa;password=TurboSjaantjeFTW!;";
    private Connection con = null;
    private Statement stmt = null;
    private ResultSet rs = null;

    public dbCon() {
    }

    public StudentList getAllStudents() {
        StudentList list = new StudentList();
        try {
            Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
            con = DriverManager.getConnection(connectionUrl);
            String SQL = "SELECT * FROM Student";
            stmt = con.createStatement();
            rs = stmt.executeQuery(SQL);

            while (rs.next()) {
                // Vraag per row de kolommen in die row op.
                String email = rs.getString("Email");
                String name = rs.getString("Name");
                Date birthday = rs.getDate("Birthday");
                String gender = rs.getString("Gender");
                String address = rs.getString("Adress");
                String city = rs.getString("City");
                String country = rs.getString("Country");

                list.addStudent(new Student(name, gender, email, address, city, country, birthday));

                //System.out.println(email + ", " + name + ", " + birthday + ", " + gender + ", " + address + ", " + city + ", " + country);
            }

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

        return list;
    }

    public void deleteStudent(Student student) {
        try {
            Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
            con = DriverManager.getConnection(connectionUrl);
            String SQL = "DELETE FROM Student WHERE Email = '" + student.getEmail() + "'";
            stmt = con.createStatement();
            stmt.execute(SQL);
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

    public void addStudent(Student student) {
        try {
            Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
            con = DriverManager.getConnection(connectionUrl);
            String SQL = "INSERT INTO Student VALUES ('" + student.getEmail() + "', '" + student.getName() + "', " + student.getBirthday() + ", '" + student.getGender() + "', '" + student.getAdress() + "', '" + student.getCity() + "', '" + student.getCountry() + "')";
            stmt = con.createStatement();
            stmt.execute(SQL);
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
