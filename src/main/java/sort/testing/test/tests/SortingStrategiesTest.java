package sort.testing.test.tests;


import model.Student;
import sort.typeOfSort.*;
import sort.comparators.AverageScoreComparator;
import sort.testing.test.toolsForTest.Assertion;
import sort.testing.test.toolsForTest.ListOfStudentForTests;
import sort.testing.test.toolsForTest.TestRunner;

import java.util.ArrayList;
import java.util.List;

public class SortingStrategiesTest {


    public SortingStrategiesTest() {

    }

        public static void runAll(TestRunner runner) {
            runTest(runner, "BubbleSort", new BubbleSort<Student>());
            runTest(runner, "EvenOddSort", new EvenOddSort<Student>());
            runTest(runner, "InsertSort", new InsertSort<Student>());
            runTest(runner, "SelectionSort", new SelectionSort<Student>());
        }

        private static void runTest(TestRunner runner,String testName, SortStrategy<Student> strategy) {
            ListOfStudentForTests listOfStudentForTests = new ListOfStudentForTests();
            List<Student> students = new ArrayList<>(listOfStudentForTests.getStudentsList());
            runner.run(testName, () -> {


                strategy.sort(
                        students,
                        new AverageScoreComparator()
                );

                List<Double> actualScores = students.stream()
                        .map(Student::getAverageScore)
                        .toList();

                Assertion.assertEquals(
                        List.of(12.0, 12.0, 12.0, 13.0),
                        actualScores,
                        testName + ": неправильная сортировка по среднему баллу"
                );
            });
    }
}
