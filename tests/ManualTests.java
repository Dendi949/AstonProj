import model.Student;
import thread.CountOccurrences;
import collection.CustomList;
import sort.typeOfSort.BubbleSort;
import sort.typeOfSort.InsertSort;
import sort.typeOfSort.SelectionSort;
import sort.typeOfSort.EvenOddSort;
import sort.comparators.GroupNumberComparator;
import sort.comparators.AverageScoreComparator;
import sort.comparators.RecordBookNumberComparator;
import input.DataFiller;

import java.util.ArrayList;
import java.util.List;
import java.util.Comparator;

/**
 * Класс с ручными тестами для проверки всех модулей проекта.
 * Тесты используют assert и не требуют внешних библиотек.
 */
public class ManualTests {

    public static void main(String[] args) {
        System.out.println("=== Запуск ручных тестов ===");
        testBuilder();
        testSorting();
        testDataFiller();
        testCustomList();
        testMultithreading();
        System.out.println("✅ Все тесты пройдены!");
    }

    // ---------- Тест Builder и валидации ----------
    private static void testBuilder() {
        System.out.print("Тест Builder... ");
        try {
            Student s = new Student.Builder()
                    .groupNumber(5)
                    .averageScore(4.5)
                    .recordNumber("BK12345")
                    .build();
            assert s.getGroupNumber() == 5;
            assert s.getAverageScore() == 4.5;
            assert s.getRecordBookNumber().equals("BK12345");
        } catch (Exception e) {
            throw new AssertionError("Builder failed: " + e.getMessage());
        }

        try {
            new Student.Builder().groupNumber(-1);
            throw new AssertionError("Validation failed: negative group");
        } catch (IllegalArgumentException ignored) {
            // expected
        }

        try {
            new Student.Builder().averageScore(6.0);
            throw new AssertionError("Validation failed: score > 5");
        } catch (IllegalArgumentException ignored) {
            // expected
        }

        try {
            new Student.Builder().recordNumber("");
            throw new AssertionError("Validation failed: empty book number");
        } catch (IllegalArgumentException ignored) {
            // expected
        }

        System.out.println("OK");
    }

    // ---------- Тест стратегий сортировки ----------
    private static void testSorting() {
        System.out.print("Тест сортировок... ");

        List<Student> original = List.of(
                new Student.Builder().groupNumber(3).averageScore(4.0).recordNumber("C").build(),
                new Student.Builder().groupNumber(1).averageScore(3.5).recordNumber("A").build(),
                new Student.Builder().groupNumber(2).averageScore(4.5).recordNumber("B").build()
        );

        // 1. BubbleSort по группе
        List<Student> list = new ArrayList<>(original);
        new BubbleSort<Student>().sort(list, new GroupNumberComparator());
        assert list.get(0).getGroupNumber() == 1;
        assert list.get(1).getGroupNumber() == 2;
        assert list.get(2).getGroupNumber() == 3;

        // 2. InsertSort по среднему баллу
        list = new ArrayList<>(original);
        new InsertSort<Student>().sort(list, new AverageScoreComparator());
        assert list.get(0).getAverageScore() == 3.5;
        assert list.get(1).getAverageScore() == 4.0;
        assert list.get(2).getAverageScore() == 4.5;

        // 3. SelectionSort по номеру зачётки
        list = new ArrayList<>(original);
        new SelectionSort<Student>().sort(list, new RecordBookNumberComparator());
        assert list.get(0).getRecordBookNumber().equals("A");
        assert list.get(1).getRecordBookNumber().equals("B");
        assert list.get(2).getRecordBookNumber().equals("C");

        // 4. EvenOddSort
        List<Student> evenOddList = List.of(
                new Student.Builder().groupNumber(2).averageScore(1.0).recordNumber("X").build(),
                new Student.Builder().groupNumber(1).averageScore(5.0).recordNumber("Y").build(),
                new Student.Builder().groupNumber(4).averageScore(2.0).recordNumber("Z").build(),
                new Student.Builder().groupNumber(3).averageScore(3.0).recordNumber("W").build()
        );
        List<Student> copy = new ArrayList<>(evenOddList);
        new EvenOddSort<Student>().sort(copy, Comparator.comparingInt(Student::getGroupNumber));
        assert copy.get(0).getGroupNumber() == 2;
        assert copy.get(1).getGroupNumber() == 1;
        assert copy.get(2).getGroupNumber() == 4;
        assert copy.get(3).getGroupNumber() == 3;

        System.out.println("OK");
    }

    // ---------- Тест DataFiller ----------
    private static void testDataFiller() {
        System.out.print("Тест DataFiller... ");

        // 1. Рандом
        List<Student> random = DataFiller.fill(DataFiller.FillType.RANDOM, 5, null);
        assert random.size() == 5;
        for (Student s : random) {
            assert s.getGroupNumber() > 0;
            assert s.getAverageScore() >= 0 && s.getAverageScore() <= 5;
            assert s.getRecordBookNumber() != null && !s.getRecordBookNumber().isEmpty();
        }

        // 2. Из файла (создаём временный файл)
        try {
            java.nio.file.Path temp = java.nio.file.Files.createTempFile("test", ".csv");
            java.nio.file.Files.write(temp, List.of(
                    "101,4.5,A1",
                    "102,3.2,A2",
                    "103,5.0,A3"
            ));
            List<Student> fromFile = DataFiller.fill(DataFiller.FillType.FILE, 0, temp.toString());
            assert fromFile.size() == 3;
            assert fromFile.get(0).getGroupNumber() == 101;
            assert fromFile.get(1).getAverageScore() == 3.2;
            assert fromFile.get(2).getRecordBookNumber().equals("A3");
            java.nio.file.Files.deleteIfExists(temp);
        } catch (Exception e) {
            throw new AssertionError("File test failed: " + e.getMessage());
        }

        // 3. Ручной ввод (симулируем через System.in)
        java.io.InputStream originalIn = System.in;
        try {
            String input = "2\n5\n4.5\nB1\n3\n3.8\nB2\n";
            System.setIn(new java.io.ByteArrayInputStream(input.getBytes()));
            List<Student> manual = DataFiller.fill(DataFiller.FillType.MANUAL, 2, null);
            assert manual.size() == 2;
            assert manual.get(0).getGroupNumber() == 5;
            assert manual.get(1).getAverageScore() == 3.8;
        } finally {
            System.setIn(originalIn);
        }

        System.out.println("OK");
    }

    // ---------- Тест CustomList ----------
    private static void testCustomList() {
        System.out.print("Тест CustomList... ");
        CustomList<String> list = new CustomList<>();

        list.add("A");
        list.add("B");
        list.add("C");
        assert list.size() == 3;
        assert list.get(1).equals("B");

        list.remove(1);
        assert list.size() == 2;
        assert list.get(1).equals("C");

        list.add(1, "X");
        assert list.get(1).equals("X");
        assert list.size() == 3;

        list.clear();
        assert list.isEmpty();

        System.out.println("OK");
    }

    // ---------- Тест многопоточного подсчёта ----------
    private static void testMultithreading() {
        System.out.print("Тест многопоточности... ");
        try {
            List<String> list = new ArrayList<>();
            for (int i = 0; i < 100; i++) {
                list.add("A");
            }
            for (int i = 0; i < 50; i++) {
                list.add("B");
            }

            int countA = CountOccurrences.countParallel(list, "A", 4);
            assert countA == 100;

            int countB = CountOccurrences.countParallel(list, "B", 4);
            assert countB == 50;

            System.out.println("OK");
        } catch (Exception e) {
            throw new AssertionError("Multithreading test failed: " + e.getMessage());
        }
    }
}