package sort.comparators;

import model.Student;
import java.util.Comparator;

public class AverageScoreComparator implements Comparator<Student> {

    @Override
    public int compare(Student o1, Student o2) {
        return Double.compare(o1.getAverageScore(), o2.getAverageScore());
    }
}