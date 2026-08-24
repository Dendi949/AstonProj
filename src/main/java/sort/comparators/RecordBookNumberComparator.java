package sort.comparators;

import java.util.Comparator;
import model.Student;

public class RecordBookNumberComparator implements Comparator<Student> {
    @Override
    public int compare(Student o1, Student o2) {
        return o1.getRecordBookNumber()
                .compareTo(o2.getRecordBookNumber());
    }
}
