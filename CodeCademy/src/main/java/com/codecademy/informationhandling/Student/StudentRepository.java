package Student;

import DatabaseConnection.DatabaseConnection;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;

public class StudentRepository {

    private DatabaseConnection dbCon = new DatabaseConnection();

    public StudentRepository() {
    }

    public void createStudent(Student student) {
        String query = "INSERT INTO Student VALUES ('" + student.getEmail() + "', '" + student.getName() + "', convert(datetime, '" + student.getBirthday().toString().replaceAll("-", "/") + "', 103)" +
                ", '" + student.getGender() + "', '" + student.getAddress() + "', '" + student.getCity() + "', '" + student.getCountry() + "')";
        dbCon.setQuery(query);
    }

    public HashMap<String, Student> getAllStudents() throws SQLException {
        HashMap<String, Student> studentList = new HashMap<>();
        String query = "SELECT * FROM Student";
        ResultSet rs = dbCon.getQuery(query);
        while (rs.next()) {
            System.out.println(rs.getString("Name"));
        }
        dbCon.CloseResultSet();
        return studentList;
    }

    public void updateStudent() {

    }

    public void deleteStudent() {

    }

}