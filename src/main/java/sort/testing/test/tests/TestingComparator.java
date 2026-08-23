package src.main.java.sort.testing.test.tests;

import java.util.Comparator;
import java.util.List;

import model.Student;
import src.main.java.sort.comparators.AverageScoreComparator;
import src.main.java.sort.comparators.GroupNumberComparator;
import src.main.java.sort.comparators.RecordBookNumberComparator;
import src.main.java.sort.testing.test.toolsForTest.ListOfStudentForTests;
import src.main.java.sort.testing.test.toolsForTest.Assertion;
import src.main.java.sort.testing.test.toolsForTest.TestRunner;


public class TestingComparator {

    ListOfStudentForTests listOfStudentForTests = new ListOfStudentForTests();
    List<Student> students = listOfStudentForTests.getStudentsList();
    AverageScoreComparator asc = new AverageScoreComparator();
    public TestingComparator() {
    }

    private final List<Student> students1 =
            new ListOfStudentForTests().getStudentsList();

    private final Comparator<Student> byGroup =
            new GroupNumberComparator();

    private final Comparator<Student> byAverageScore =
            new AverageScoreComparator();

    private final Comparator<Student> byRecordBook =
            new RecordBookNumberComparator();

    public void runAll(TestRunner runner) {
        runner.run(
                "Компаратор по группе",
                this::testGroupNumberComparator
        );

        runner.run(
                "Компаратор по среднему баллу",
                this::testAverageScoreComparator
        );

        runner.run(
                "Компаратор по номеру зачётной книжки",
                this::testRecordBookComparator
        );
    }

    private void testGroupNumberComparator() {
        Student student1 = students.get(0); // группа 1
        Student student2 = students.get(1); // группа 2
        Student student3 = students.get(2); // группа 1

        Assertion.assertTrue(
                byGroup.compare(student1, student2) < 0,
                "Группа 1 < 2"
        );

        Assertion.assertEquals(
                0,
                byGroup.compare(student1, student3),
                "Одинаковые группы должны считаться равными"
        );
    }

    private void testAverageScoreComparator() {
        Student student1 = students.get(0);
        Student student2 = students.get(1);
        Student student3 = students.get(2);

        Assertion.assertTrue(
                byAverageScore.compare(student1, student3) < 0,
                " 12 < 13"
        );

        Assertion.assertEquals(
                0,
                byAverageScore.compare(student1, student2),
                "Одинаковые баллы должны считаться равными"
        );
    }

    private void testRecordBookComparator() {
        Student student1 = students.get(0);
        Student student2 = students.get(1);
        Student student3 = students.get(2);
        Student student4 = students.get(3);

        Assertion.assertTrue(
                byRecordBook.compare(student1, student2) < 0,
                "A-1 должна идти раньше A-2"
        );

        Assertion.assertEquals(
                0,
                byRecordBook.compare(student3, student4),
                "Одинаковые номера книжек должны считаться равными"
        );
    }
}
