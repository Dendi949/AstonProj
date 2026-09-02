package input;

import model.Student;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.List;

/**
 * Тесты для DataFiller.
 * Проверяют валидацию Student, генерацию случайных данных,
 * чтение из CSV-файла и ручной ввод (с подменой System.in).
 */
public class DataFillerTest {

    public static void main(String[] args) {
        System.out.println("=== Тесты DataFiller ===\n");

        testBuilderValidation();
        testFillRandom();
        testFillFromFile();
        testFillManual();

        System.out.println("\n=== Все тесты пройдены ===");
    }

    /**
     * Проверка валидации в Builder.
     * Ожидаем исключения для невалидных данных.
     */
    static void testBuilderValidation() {
        System.out.println("Тест: Builder валидация...");

        try {
            new Student.Builder().groupNumber(-1).averageScore(4.0).recordNumber("123456").build();
            throw new AssertionError("Должно быть исключение для отрицательной группы");
        } catch (IllegalArgumentException e) {
            System.out.println("  [OK] Отрицательная группа отклонена: " + e.getMessage());
        }

        try {
            new Student.Builder().groupNumber(1).averageScore(5.5).recordNumber("123456").build();
            throw new AssertionError("Должно быть исключение для балла > 5");
        } catch (IllegalArgumentException e) {
            System.out.println("  [OK] Балл > 5 отклонён: " + e.getMessage());
        }

        try {
            new Student.Builder().groupNumber(1).averageScore(4.0).recordNumber("").build();
            throw new AssertionError("Должно быть исключение для пустой зачётки");
        } catch (IllegalArgumentException e) {
            System.out.println("  [OK] Пустая зачётка отклонена: " + e.getMessage());
        }

        Student s = new Student.Builder()
                .groupNumber(101)
                .averageScore(4.5)
                .recordNumber("123456")
                .build();
        if (s.getGroupNumber() != 101) {
            throw new AssertionError("Группа не совпадает");
        }
        if (Double.compare(s.getAverageScore(), 4.5) != 0) {
            throw new AssertionError("Балл не совпадает");
        }
        if (!"123456".equals(s.getRecordBookNumber())) {
            throw new AssertionError("Номер зачётки не совпадает");
        }
        System.out.println("  [OK] Корректный студент создан: " + s);
    }

    /**
     * Проверка генерации случайных студентов.
     * Ожидаем, что возвращается список заданного размера,
     * и все студенты валидны.
     */
    static void testFillRandom() {
        System.out.println("\nТест: fillRandom...");
        List<Student> list = DataFiller.fill(DataFiller.FillType.RANDOM, 5, null);
        if (list.size() != 5) {
            throw new AssertionError("Ожидалось 5, получено " + list.size());
        }
        for (Student s : list) {
            if (s.getGroupNumber() <= 0) {
                throw new AssertionError("Некорректная группа: " + s.getGroupNumber());
            }
            if (s.getAverageScore() < 0.0 || s.getAverageScore() > 5.0) {
                throw new AssertionError("Некорректный балл: " + s.getAverageScore());
            }
            if (s.getRecordBookNumber() == null || s.getRecordBookNumber().isBlank()) {
                throw new AssertionError("Некорректный номер зачётки");
            }
        }
        System.out.println("  [OK] Сгенерировано " + list.size() + " студентов");
    }

    /**
     * Проверка чтения из CSV-файла с использованием Stream API.
     * Создаём временный файл с корректными и битыми строками.
     * Ожидаем, что будут загружены только валидные записи.
     * (Валидные: корректный парсинг чисел и прохождение валидации Builder)
     */
    static void testFillFromFile() {
        System.out.println("\nТест: fillFromFile (stream)...");
        try {
            Path temp = Files.createTempFile("students_test", ".csv");
            Files.write(temp, Arrays.asList(
                    "group,score,record",
                    "101,4.5,111111",
                    "102,3.2,222222",
                    "103,5.0,333333",
                    "104,6.0,444444",   // невалидный балл > 5
                    "105,2.8,555555"
            ));

            List<Student> list = DataFiller.fill(DataFiller.FillType.FILE, 10, temp.toString());
            // Ожидаем 4 корректных строки (101,102,103,105)
            if (list.size() != 4) {
                throw new AssertionError("Ожидалось 4, получено " + list.size());
            }
            if (list.get(0).getGroupNumber() != 101) {
                throw new AssertionError("Первый студент не 101");
            }
            if (Double.compare(list.get(1).getAverageScore(), 3.2) != 0) {
                throw new AssertionError("Второй студент не 3.2");
            }

            System.out.println("  [OK] Загружено " + list.size() + " студентов, невалидные строки отклонены");
            Files.deleteIfExists(temp);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Проверка ручного ввода через консоль.
     * Для этого перенаправляем System.in на тестовую строку,
     * затем вызываем публичный метод fill(FillType.MANUAL, ...).
     * После теста восстанавливаем оригинальный System.in.
     */
    static void testFillManual() {
        System.out.println("\nТест: fillManual (имитация ввода)...");
        java.io.InputStream originalIn = System.in;

        try {
            String input = "101\n4.5\n123456\n102\n3.8\n654321\n";
            System.setIn(new ByteArrayInputStream(input.getBytes()));

            List<Student> list = DataFiller.fill(DataFiller.FillType.MANUAL, 2, null);

            if (list.size() != 2) {
                throw new AssertionError("Ожидалось 2, получено " + list.size());
            }
            if (list.get(0).getGroupNumber() != 101) {
                throw new AssertionError("Первый студент не 101");
            }
            if (list.get(1).getGroupNumber() != 102) {
                throw new AssertionError("Второй студент не 102");
            }
            System.out.println("  [OK] Ручной ввод работает корректно");
        } finally {
            System.setIn(originalIn);
        }
    }
}