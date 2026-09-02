package sort.comparators;

import model.Student;
import java.util.Comparator;

public class StudentComparators {

    public static Comparator<Student> byGroupNumber() {
        return new GroupNumberComparator();
    }

    public static Comparator<Student> byAverageScore() {
        return new AverageScoreComparator();
    }

    public static Comparator<Student> byRecordBookNumber() {
        return new RecordBookNumberComparator();
    }
}