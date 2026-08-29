package input;

// TODO: Заменить на кастомную коллекцию CustomArrayList из пакета collection
// Сейчас используется стандартный ArrayList для возможности компиляции и тестирования.
import java.util.ArrayList;

import model.Student;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.Random;
import java.util.Scanner;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

/**
 * Класс для заполнения коллекции студентов тремя способами:
 *  - случайная генерация (RANDOM)
 *  - чтение из CSV-файла через Stream API (FILE)
 *  - ручной ввод с консоли (MANUAL)
 *
 * Все способы используют паттерн Builder для создания объектов Student
 * с обязательной валидацией полей.
 */
public class DataFiller {

    /** Перечисление доступных способов заполнения данных. */
    public enum FillType { RANDOM, FILE, MANUAL }

    /**
     * Универсальный метод-фасад, который делегирует заполнение
     * в соответствующий приватный метод в зависимости от типа.
     *
     * @param type     способ заполнения (RANDOM / FILE / MANUAL)
     * @param size     количество студентов (для RANDOM и MANUAL) или максимум (для FILE)
     * @param filePath путь к CSV-файлу (только для FILE)
     * @return список созданных студентов
     */
    public static List<Student> fill(FillType type, int size, String filePath) {
        switch (type) {
            case RANDOM:
                // Генерация случайных данных
                return fillRandom(size);
            case FILE:
                // Чтение из файла с обработкой возможного IOException
                try {
                    return fillFromFile(filePath, size);
                } catch (IOException e) {
                    throw new RuntimeException("Ошибка чтения файла: " + e.getMessage(), e);
                }
            case MANUAL:
                // Интерактивный ввод с клавиатуры
                return fillManual(size, new Scanner(System.in));
            default:
                throw new IllegalArgumentException("Неизвестный тип заполнения: " + type);
        }
    }

    /**
     * Читает студентов из CSV-файла с использованием Stream API.
     *
     * Формат CSV: groupNumber,averageScore,recordBookNumber
     * Первая строка (заголовок) пропускается через skip(1).
     * Обработка ограничивается limit(size) — не более size строк.
     *
     * При ошибке парсинга отдельной строки (неверный формат или валидация Builder)
     * строка пропускается, а ошибка пишется в System.err.
     *
     * @param filePath путь к CSV-файлу
     * @param size     максимальное количество студентов для чтения
     * @return список прочитанных и валидированных студентов
     * @throws IOException при проблемах с чтением файла
     */
    static List<Student> fillFromFile(String filePath, int size) throws IOException {
        // TODO: заменить на CustomArrayList при появлении реализации
        ArrayList<Student> list = new ArrayList<>();

        // Files.lines() открывает Stream<String> для построчного чтения файла.
        // try-with-resources гарантирует закрытие файла после обработки.
        try (Stream<String> lines = Files.lines(Paths.get(filePath))) {
            lines.skip(1)          // пропускаем заголовок CSV
                 .limit(size)      // ограничиваем количество считываемых строк
                 .forEach(line -> {
                     // Разбиваем строку по запятой на 3 поля
                     String[] parts = line.split(",");
                     if (parts.length >= 3) {
                         try {
                             // Парсим и очищаем каждое поле
                             int group = Integer.parseInt(parts[0].trim());
                             double score = Double.parseDouble(parts[1].trim());
                             String record = parts[2].trim();

                             // Создаём студента через Builder — здесь сработает валидация
                             Student s = new Student.Builder()
                                     .setGroupNumber(group)
                                     .setAverageScore(score)
                                     .setRecordBookNumber(record)
                                     .build();
                             list.add(s);
                         } catch (IllegalArgumentException | NumberFormatException e) {
                             // IllegalArgumentException — ошибка валидации Builder
                             // NumberFormatException — неверный формат числа в CSV
                             System.err.println("Ошибка в строке '" + line + "': " + e.getMessage());
                         }
                     } else {
                         System.err.println("Некорректная строка (ожидалось 3 поля): " + line);
                     }
                 });
        }
        return list;
    }

    /**
     * Генерирует список студентов со случайными, но валидными данными.
     *
     * Использует IntStream для создания size объектов.
     * Для каждого студента генерируются случайные значения,
     * которые затем проверяются в Builder.build() → validate().
     * Если случайные данные невалидны (маловероятно при текущих диапазонах),
     * происходит повторная генерация (до 1000 попыток).
     *
     * @param size количество студентов для генерации
     * @return список случайных студентов
     */
    static List<Student> fillRandom(int size) {
        Random random = new Random();
        return IntStream.range(0, size)
                .mapToObj(i -> {
                    int attempts = 0;
                    // Цикл защиты: если случайное значение не пройдёт валидацию,
                    // повторяем генерацию до 1000 раз
                    while (attempts < 1000) {
                        int group = random.nextInt(100) + 1;                       // 1..100
                        double score = Math.round(random.nextDouble() * 5.0 * 100.0) / 100.0; // 0.00..5.00
                        String record = String.valueOf(random.nextInt(900000) + 100000);       // 100000..999999
                        try {
                            // Пытаемся создать студента — здесь сработает validate()
                            return new Student.Builder()
                                    .setGroupNumber(group)
                                    .setAverageScore(score)
                                    .setRecordBookNumber(record)
                                    .build();
                        } catch (IllegalArgumentException e) {
                            // Валидация не пройдена — генерируем заново
                            attempts++;
                        }
                    }
                    throw new RuntimeException("Не удалось сгенерировать валидного студента за 1000 попыток");
                })
                // TODO: заменить на CustomArrayList при появлении реализации
                .collect(Collectors.toCollection(ArrayList::new));
    }

    /**
     * Интерактивный ввод студентов с консоли через Scanner.
     *
     * Для каждого из size студентов запрашивает 3 поля:
     * номер группы, средний балл, номер зачётной книжки.
     * Если введённые данные не проходят валидацию Builder,
     * пользователю предлагается повторить ввод для того же студента.
     *
     * Scanner передаётся как параметр для возможности тестирования
     * с подменённым источником ввода (например, из строки).
     *
     * @param size    количество студентов для ввода
     * @param scanner источник ввода (System.in для консоли, или Scanner(String) для тестов)
     * @return список введённых студентов
     */
    static List<Student> fillManual(int size, Scanner scanner) {
        // TODO: заменить на CustomArrayList при появлении реализации
        ArrayList<Student> list = new ArrayList<>();

        IntStream.range(0, size).forEach(i -> {
            boolean added = false;
            // Цикл повторного ввода: пока студент не создан валидно — спрашиваем заново
            while (!added) {
                System.out.println("\n--- Ввод студента " + (i + 1) + " из " + size + " ---");

                // Ввод номера группы с проверкой на число
                System.out.print("Номер группы (положительное число): ");
                int group;
                try {
                    group = Integer.parseInt(scanner.nextLine().trim());
                } catch (NumberFormatException e) {
                    System.out.println("Ошибка: номер группы должен быть целым числом.");
                    continue; // возвращаемся к началу while для повторного ввода
                }

                // Ввод среднего балла с проверкой на число
                System.out.print("Средний балл (0.0 - 5.0): ");
                double score;
                try {
                    score = Double.parseDouble(scanner.nextLine().trim());
                } catch (NumberFormatException e) {
                    System.out.println("Ошибка: средний балл должен быть числом.");
                    continue;
                }

                // Ввод номера зачётной книжки (строка — проверка в Builder)
                System.out.print("Номер зачётной книжки: ");
                String record = scanner.nextLine().trim();

                try {
                    // Создаём студента — здесь сработает validate() в Builder
                    Student s = new Student.Builder()
                            .setGroupNumber(group)
                            .setAverageScore(score)
                            .setRecordBookNumber(record)
                            .build();
                    list.add(s);
                    System.out.println("Студент добавлен.");
                    added = true; // выходим из while, переходим к следующему студенту
                } catch (IllegalArgumentException e) {
                    // Валидация Builder не пройдена — показываем сообщение и повторяем ввод
                    System.out.println("Ошибка валидации: " + e.getMessage() + ". Повторите ввод.");
                }
            }
        });
        return list;
    }
}
