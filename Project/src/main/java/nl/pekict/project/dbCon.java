package nl.pekict.project;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

public class dbCon {

    private final String connectionUrl = "jdbc:sqlserver://localhost\\MSSQLSERVER;databaseName=CodeAcademyDB;user=sa;password=TurboSjaantjeFTW!;";
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
                String birthday = rs.getString("Birthday");
                String gender = rs.getString("Gender");
                String address = rs.getString("Address");
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

    public void updateStudent(Student oldStudent, Student newStudent) {
        try {
            Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
            con = DriverManager.getConnection(connectionUrl);
            String SQL = "UPDATE STUDENT SET email = '" + newStudent.getEmail() + "', name = '" + newStudent.getName() + "', gender = '" + newStudent.getGender() + "', address = '" + newStudent.getAdress() +
                    "', city = '" + newStudent.getCity() + "', country = '" + newStudent.getCountry() + "', birthday = '" + newStudent.getBirthday() + "' WHERE email = '" + oldStudent.getEmail() + "';";
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
