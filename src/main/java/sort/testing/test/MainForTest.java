package sort.testing.test;

import sort.testing.test.tests.SortingStrategiesTest;
import sort.testing.test.tests.TestingComparator;
import sort.testing.test.toolsForTest.TestRunner;

public class MainForTest {

    public static void main(String[] args) {
        TestRunner testRunner = new TestRunner();
        TestingComparator testingComparator = new TestingComparator();

        testingComparator.runAll(testRunner);
        SortingStrategiesTest.runAll(testRunner);
        testRunner.end();
    }
}