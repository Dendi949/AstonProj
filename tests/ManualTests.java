import model.Student;
import thread.CountOccurrences;
import collection.CustomList;

import java.util.ArrayList;
import java.util.List;

/**
 * Класс с ручными тестами для проверки всех модулей проекта.
 * Тесты используют assert и не требуют внешних библиотек.
 * Разработал: Участник 6
 *
 * ВНИМАНИЕ: тесты для сортировок и DataFiller временно закомментированы,
 * так как их реализации ещё не готовы. После завершения работы участников 2 и 3
 * эти тесты нужно будет раскомментировать и адаптировать.
 */
public class ManualTests {

    public static void main(String[] args) {
        System.out.println("=== Запуск ручных тестов ===");
        testBuilder();          // проверяем Builder и валидацию
        // testSorting();       // TODO: раскомментировать после реализации сортировок
        // testDataFiller();    // TODO: раскомментировать после реализации DataFiller
        testCustomList();       // проверяем кастомную коллекцию
        testMultithreading();   // проверяем многопоточный подсчёт
        System.out.println("✅ Все доступные тесты пройдены!");
    }

    /**
     * Тест Builder и валидации класса Student.
     * Проверяет корректное создание объекта и выбрасывание исключений при неверных данных.
     */
    private static void testBuilder() {
        System.out.print("Тест Builder... ");

        // 1. Создание студента с корректными данными
        try {
            Student s = new Student.Builder()
                    .groupNumber(5)           // было .setGroupNumber
                    .averageScore(4.5)        // было .setAverageScore
                    .recordNumber("BK12345")  // было .setRecordBookNumber
                    .build();
            // Проверяем, что поля установлены правильно
            assert s.getGroupNumber() == 5;
            assert s.getAverageScore() == 4.5;
            assert s.getRecordBookNumber().equals("BK12345");
        } catch (Exception e) {
            throw new AssertionError("Builder failed: " + e.getMessage());
        }

        // 2. Валидация: отрицательный номер группы -> ожидаем IllegalArgumentException
        try {
            new Student.Builder().groupNumber(-1);
            throw new AssertionError("Validation failed: negative group");
        } catch (IllegalArgumentException e) {
            // Ожидаемое исключение – тест пройден
        }

        // 3. Валидация: средний балл > 5 -> ожидаем исключение
        try {
            new Student.Builder().averageScore(6.0);
            throw new AssertionError("Validation failed: score > 5");
        } catch (IllegalArgumentException e) {
            // Ожидаемо
        }

        // 4. Валидация: пустой номер зачётки -> ожидаем исключение
        try {
            new Student.Builder().recordNumber("");
            throw new AssertionError("Validation failed: empty book number");
        } catch (IllegalArgumentException e) {
            // Ожидаемо
        }

        System.out.println("OK");
    }

    /**
     * Тест кастомной коллекции CustomList.
     * Проверяет основные методы: добавление, получение, удаление, вставку, очистку.
     */
    private static void testCustomList() {
        System.out.print("Тест CustomList... ");
        CustomList<String> list = new CustomList<>();

        // Добавляем элементы
        list.add("A");
        list.add("B");
        list.add("C");
        assert list.size() == 3;
        assert list.get(1).equals("B");

        // Удаляем по индексу (удаляем элемент с индексом 1 – "B")
        list.remove(1);
        assert list.size() == 2;
        assert list.get(1).equals("C");

        // Вставляем элемент по индексу
        list.add(1, "X");
        assert list.get(1).equals("X");
        assert list.size() == 3;

        // Очищаем коллекцию
        list.clear();
        assert list.isEmpty();

        System.out.println("OK");
    }

    /**
     * Тест многопоточного подсчёта вхождений.
     * Создаёт список, содержащий 100 раз "A" и 50 раз "B",
     * затем параллельно подсчитывает их количество и проверяет результаты.
     */
    private static void testMultithreading() {
        System.out.print("Тест многопоточности... ");
        try {
            List<String> list = new ArrayList<>();
            for (int i = 0; i < 100; i++) list.add("A");
            for (int i = 0; i < 50; i++) list.add("B");

            // Подсчитываем вхождения "A" с использованием 4 потоков
            int countA = CountOccurrences.countParallel(list, "A", 4);
            assert countA == 100;

            // Подсчитываем вхождения "B" с использованием 4 потоков
            int countB = CountOccurrences.countParallel(list, "B", 4);
            assert countB == 50;

            System.out.println("OK");
        } catch (Exception e) {
            throw new AssertionError("Multithreading test failed: " + e.getMessage());
        }
    }

    /*
    // ------ Тесты, которые будут активированы после реализации соответствующих модулей ------

    private static void testSorting() {
        // Проверка BubbleSort, InsertionSort, SelectionSort и EvenOddSort
        // ...
    }

    private static void testDataFiller() {
        // Проверка заполнения из файла, рандом и вручную
        // ...
    }
    */
}