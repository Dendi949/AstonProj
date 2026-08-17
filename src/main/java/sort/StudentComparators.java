package src.main.java.sort;

import model.Student;
import java.util.Comparator;

public class StudentComparators {
    public static Comparator<Student> byGroupNumber() {
        return Comparator.comparingInt(Student::getGroupNumber);
    }
    public static Comparator<Student> byAverageScore() {
        return Comparator.comparingDouble(Student::getAverageScore);
    }
    public static Comparator<Student> byRecordBookNumber() {
        return Comparator.comparing(Student::getRecordBookNumber);
    }
}