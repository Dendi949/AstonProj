package input;

import collection.CustomList;
import model.Student;

import java.io.IOException;
import java.io.UncheckedIOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.*;
import java.util.stream.Stream;

public class DataFiller {
    public enum FillType { RANDOM, FILE, MANUAL }

    private static final Scanner CONSOLE = new Scanner(System.in);

    private DataFiller() {
    }

    public static List<Student> fill(
            FillType type,
            int size,
            String filePath
    ) {
        Objects.requireNonNull(
                type,
                "Тип заполнения не должен быть null"
        );

        return switch (type) {
            case RANDOM -> fillRandom(size);

            case MANUAL -> fillManually(size);

            case FILE -> {
                try {
                    yield fillFromFile(filePath);
                } catch (IOException exception) {
                    throw new UncheckedIOException(
                            "Не удалось прочитать файл: " + filePath,
                            exception
                    );
                }
            }
        };
    }
    public static CustomList<Student> fillRandom(int size) {
        return fillRandom(size, new Random());
    }


    public static CustomList<Student> fillRandom(int size, Random random) {
        checkSize(size);

        Objects.requireNonNull(random, "Random не должен быть null");

        CustomList<Student> students = new CustomList<>(size);

        for (int i = 0; i < size; i++) {
            int groupNumber = random.nextInt(10) + 1;
            double averageScore = random.nextInt(501) / 100.0;

            String recordBookNumber = String.format(
                    Locale.ROOT,
                    "RB-%06d",
                    i + 1
            );

            Student student = new Student.Builder()
                    .groupNumber(groupNumber)
                    .averageScore(averageScore)
                    .recordNumber(recordBookNumber)
                    .build();

            students.add(student);
        }

        return students;
    }


    public static CustomList<Student> fillFromFile(
            String filePath
    ) throws IOException {
        if (filePath == null || filePath.isBlank()) {
            throw new IllegalArgumentException(
                    "Путь к файлу не должен быть пустым"
            );
        }

        return fillFromFile(Path.of(filePath));
    }


    public static CustomList<Student> fillFromFile(
            Path path
    ) throws IOException {
        Objects.requireNonNull(
                path,
                "Путь к файлу не должен быть null"
        );

        try (Stream<String> lines = Files.lines(
                path,
                StandardCharsets.UTF_8
        )) {
            return lines
                    .map(DataFiller::cleanValue)
                    .filter(line -> !line.isEmpty())
                    .filter(line -> !line.startsWith("#"))
                    .filter(line -> !isHeader(line))
                    .map(DataFiller::parseStudent)
                    .collect(
                            CustomList<Student>::new,

                            (students, student) -> {
                                students.add(student);
                            },

                            (first, second) -> {
                                first.addAll(second);
                            }
                    );
        }
    }

    public static CustomList<Student> fillManually(int size) {
        return fillManually(size, CONSOLE);
    }

    public static CustomList<Student> fillManually(
            int size,
            Scanner scanner
    ) {
        checkSize(size);

        Objects.requireNonNull(
                scanner,
                "Scanner не должен быть null"
        );

        CustomList<Student> students = new CustomList<>(size);

        for (int i = 0; i < size; i++) {
            System.out.println();
            System.out.println("Студент №" + (i + 1));

            int groupNumber = readGroupNumber(scanner);
            double averageScore = readAverageScore(scanner);

            String recordBookNumber = readNotBlank(
                    scanner,
                    "Введите номер зачётной книжки: "
            );

            Student student = new Student.Builder()
                    .groupNumber(groupNumber)
                    .averageScore(averageScore)
                    .recordNumber(recordBookNumber)
                    .build();

            students.add(student);
        }

        return students;
    }

    private static Student parseStudent(String line) {
        String[] values = line.split(",", -1);

        if (values.length != 3) {
            throw new IllegalArgumentException("В CSV-строке должно быть три значения: " + line);
        }

        int groupNumber;
        double averageScore;

        try {
            groupNumber = Integer.parseInt(
                    cleanValue(values[0])
            );

            averageScore = Double.parseDouble(
                    cleanValue(values[1])
            );
        } catch (NumberFormatException exception) {
            throw new IllegalArgumentException("Некорректные числа в CSV-строке: " + line, exception);
        }

        String recordBookNumber = cleanValue(values[2]);

        try {

            return new Student.Builder()
                    .groupNumber(groupNumber)
                    .averageScore(averageScore)
                    .recordNumber(recordBookNumber)
                    .build();
        } catch (IllegalArgumentException exception) {
            throw new IllegalArgumentException("Студент не прошёл валидацию в строке: " + line + ". " + exception.getMessage(), exception);
        }
    }

    private static boolean isHeader(String line) {
        String[] values = line.split(",", -1);

        if (values.length != 3) {
            return false;
        }

        return cleanValue(values[0])
                .equalsIgnoreCase("groupNumber")
                && cleanValue(values[1])
                .equalsIgnoreCase("averageScore")
                && cleanValue(values[2])
                .equalsIgnoreCase("recordBookNumber");
    }

    private static int readGroupNumber(Scanner scanner) {
        while (true) {
            String value = readLine(
                    scanner,
                    "Введите номер группы: "
            );

            try {
                int groupNumber = Integer.parseInt(value);

                if (groupNumber > 0) {
                    return groupNumber;
                }
            } catch (NumberFormatException ignored) {
            }

            System.out.println(
                    "Ошибка: номер группы должен быть целым числом больше 0."
            );
        }
    }

    private static double readAverageScore(Scanner scanner) {
        while (true) {
            String value = readLine(
                    scanner,
                    "Введите средний балл от 0 до 5: "
            );

            try {
                double averageScore = Double.parseDouble(
                        value.replace(',', '.')
                );

                if (averageScore >= 0
                        && averageScore <= 5
                        && Double.isFinite(averageScore)) {
                    return averageScore;
                }
            } catch (NumberFormatException ignored) {
            }

            System.out.println(
                    "Ошибка: средний балл должен находиться от 0 до 5."
            );
        }
    }

    private static String readNotBlank(
            Scanner scanner,
            String message
    ) {
        while (true) {
            String value = readLine(scanner, message);

            if (!value.isBlank()) {
                return value;
            }

            System.out.println(
                    "Ошибка: значение не должно быть пустым."
            );
        }
    }

    private static String readLine(
            Scanner scanner,
            String message
    ) {
        System.out.print(message);

        if (!scanner.hasNextLine()) {
            throw new IllegalStateException(
                    "Ввод неожиданно завершился"
            );
        }

        return scanner.nextLine().trim();
    }

    private static String cleanValue(String value) {
        return value
                .replace("\uFEFF", "")
                .trim();
    }

    private static void checkSize(int size) {
        if (size < 0) {
            throw new IllegalArgumentException("Количество студентов не может быть отрицательным");
        }
    }
}
