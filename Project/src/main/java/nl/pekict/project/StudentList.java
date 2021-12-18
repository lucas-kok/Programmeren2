package nl.pekict.project;

import java.util.ArrayList;

public class StudentList {

    private ArrayList<Student> studentList = new ArrayList<>();

    public StudentList() {
    }

    public void addStudent (Student student) {
        if (!studentList.contains(student)) {
            studentList.add(student);
        }

    }
    public void addStudent (Student student, boolean toDatbase) {
        if (!studentList.contains(student)) {
            studentList.add(student);
        }
        if (toDatbase) new dbCon().addStudent(student);
    }

    public void deleteStudent(Student student) {
        studentList.remove(studentList.indexOf(student));
        new dbCon().deleteStudent(student);
    }

    public void deleteStudent(String email) {
        Student tempStudent = studentList.get(0);
        for (Student student : studentList) {
            if (student.getEmail() == email) tempStudent = student;
        }
        studentList.remove(studentList.indexOf(tempStudent));
        new dbCon().deleteStudent(tempStudent);
    }

    public void updateUser(Student oldStudent, Student newStudent) {
        for (Student student : studentList) {
            if (student.getEmail().equals(oldStudent)) {
                studentList.set(studentList.indexOf(student), newStudent);
                new dbCon().updateStudent(oldStudent, newStudent);
            }
        }
    }

    public ArrayList<Student> getAllStudents() {
        return new ArrayList<>(this.studentList);
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        for (Student student : studentList) {
           builder.append("\n-" + student.toString());
        }
        return "Students in list:" + builder.toString();
    }
}
