package sort.testing.test.tests;

import model.Student;
import sort.comparators.AverageScoreComparator;
import sort.comparators.GroupNumberComparator;
import sort.comparators.RecordBookNumberComparator;
import sort.testing.test.toolsForTest.Assertion;
import sort.testing.test.toolsForTest.ListOfStudentForTests;
import sort.testing.test.toolsForTest.TestRunner;

import java.util.Comparator;
import java.util.List;

public class TestingComparator {

    private final ListOfStudentForTests listOfStudentForTests = new ListOfStudentForTests();
    private final List<Student> students = listOfStudentForTests.getStudentsList();
    private final Comparator<Student> byGroup = new GroupNumberComparator();
    private final Comparator<Student> byAverageScore = new AverageScoreComparator();
    private final Comparator<Student> byRecordBook = new RecordBookNumberComparator();

    public TestingComparator() {
    }

    public void runAll(TestRunner runner) {
        runner.run("Компаратор по группе", this::testGroupNumberComparator);
        runner.run("Компаратор по среднему баллу", this::testAverageScoreComparator);
        runner.run("Компаратор по номеру зачётной книжки", this::testRecordBookComparator);
    }

    private void testGroupNumberComparator() {
        Student student1 = students.get(0);
        Student student2 = students.get(1);
        Student student3 = students.get(2);

        Assertion.assertTrue(byGroup.compare(student1, student2) < 0, "Группа 1 < 2");
        Assertion.assertEquals(0, byGroup.compare(student1, student3), "Одинаковые группы должны считаться равными");
    }

    private void testAverageScoreComparator() {
        Student student1 = students.get(0);
        Student student2 = students.get(1);
        Student student3 = students.get(2);

        Assertion.assertTrue(byAverageScore.compare(student1, student3) < 0, "12 < 13");
        Assertion.assertEquals(0, byAverageScore.compare(student1, student2), "Одинаковые баллы должны считаться равными");
    }

    private void testRecordBookComparator() {
        Student student1 = students.get(0);
        Student student2 = students.get(1);
        Student student3 = students.get(2);
        Student student4 = students.get(3);

        Assertion.assertTrue(byRecordBook.compare(student1, student2) < 0, "A-1 должна идти раньше A-2");
        Assertion.assertEquals(0, byRecordBook.compare(student3, student4), "Одинаковые номера книжек должны считаться равными");
    }
}