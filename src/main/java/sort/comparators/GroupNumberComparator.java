package sort.comparators;

import model.Student;
import java.util.Comparator;

public class GroupNumberComparator implements Comparator<Student> {
    @Override
    public int compare(Student o1, Student o2) {
        return Integer.compare(
                o1.getGroupNumber(),
                o2.getGroupNumber()
        );
    }
}
