package service;

import input.DataFiller;
import model.Student;
import sort.typeOfSort.SortStrategy;
import sort.comparators.StudentComparators;
import sort.typeOfSort.BubbleSort;
import sort.typeOfSort.InsertSort;
import sort.typeOfSort.SelectionSort;
import thread.CountOccurrences;

import java.util.Comparator;
import java.util.List;
import java.util.Scanner;
import java.io.FileWriter;
import java.io.IOException;

public class MainLoop {

    private Comparator<Student> selectedComparator;
    private SortStrategy<Student> selectedStrategy;
    private List<Student> students = List.of();
    private final Scanner scanner = new Scanner(System.in);
    private final FileWriterService fileWriterService = new FileWriterService();

    public void run() {
        boolean running = true;

        while (running) {
            printMenu();

            int choice = readInt();

            switch (choice) {
                case 1:
                    fillCollection();
                    break;
                case 2:
                    selectStrategy();
                    break;
                case 3:
                    sortAndPrint();
                    break;
                case 4:
                    writeToFile();
                    break;
                case 5:
                    saveSearchResultToFile();
                    break;
                case 0:
                    running = false;
                    System.out.println("Программа завершена.");
                    break;
                default:
                    System.out.println("Такого пункта меню нет.");
            }
        }
    }

    private int readInt() {
        while (true) {
            String input = scanner.nextLine();

            try {
                return Integer.parseInt(input);
            } catch (NumberFormatException e) {
                System.out.println("Ошибка: введите число.");
                System.out.print("Ваш выбор: ");
            }
        }
    }

    private int readPositiveInt() {
        while (true) {
            int number = readInt();

            if (number > 0) {
                return number;
            }

            System.out.println("Ошибка: число должно быть больше 0.");
            System.out.print("Введите количество: ");
        }
    }

    private void printMenu() {
        System.out.println("\nАлгоритм сортировки. Класс: СТУДЕНТЫ ");
        System.out.println("1. Заполнить коллекцию");
        System.out.println("2. Выбрать стратегию сортировки");
        System.out.println("3. Сортировать и вывести");
        System.out.println("4. Записать результат в файл");
        System.out.println("5. Записать результат поиска (количество вхождений) в файл");
        System.out.println("0. Выход");
        System.out.print("Ваш выбор: ");
    }

    private void fillCollection() {
        System.out.println("\nЗАПОЛНЕНИЕ КОЛЛЕКЦИИ");
        System.out.println("1. Случайные данные");
        System.out.println("2. Из файла");
        System.out.println("3. Вручную");
        System.out.println("0. Назад");
        System.out.print("Ваш выбор: ");

        int choice = readInt();

        switch (choice) {
            case 1:
                fillRandom();
                break;
            case 2:
                fillFromFile();
                break;
            case 3:
                fillManual();
                break;
            case 0:
                return;
            default:
                System.out.println("Такого пункта нет.");
        }
    }

    private void fillRandom() {
        System.out.print("Введите количество студентов: ");

        int size = readPositiveInt();

        students = DataFiller.fill(
                DataFiller.FillType.RANDOM,
                size,
                null);
        if (students.isEmpty()) {
            System.out.println("Не удалось заполнить коллекцию.");
        } else {
            System.out.println("Коллекция заполнена.");
        }
    }

    private void fillFromFile() {
        System.out.print("Введите путь к файлу: ");

        String path = scanner.nextLine();

        students = DataFiller.fill(
                DataFiller.FillType.FILE,
                0,
                path);
        if (students.isEmpty()) {
            System.out.println("Не удалось загрузить данные.");
        } else {
            System.out.println("Данные загружены.");
        }
    }

    private void fillManual() {
        System.out.print("Введите количество студентов: ");

        int size = readPositiveInt();

        students = DataFiller.fill(
                DataFiller.FillType.MANUAL,
                size,
                null);
        if (students.isEmpty()) {
            System.out.println("Не удалось заполнить коллекцию.");
        } else {
            System.out.println("Коллекция заполнена.");
        }
    }

    private void selectStrategy() {
        System.out.println("\nСТРАТЕГИЯ СОРТИРОВКИ");
        System.out.println("1. Пузырьковая сортировка");
        System.out.println("2. Сортировка вставками");
        System.out.println("3. Сортировка выбором");
        System.out.println("0. Назад");
        System.out.print("Ваш выбор: ");

        int choice = readInt();

        switch (choice) {
            case 1:
                selectedStrategy = new BubbleSort<>();
                System.out.println("Выбрана пузырьковая сортировка.");
                break;

            case 2:
                selectedStrategy = new InsertSort<>();
                System.out.println("Выбрана сортировка вставками.");
                break;

            case 3:
                selectedStrategy = new SelectionSort<>();
                System.out.println("Выбрана сортировка выбором.");
                break;

            case 0:
                return;

            default:
                System.out.println("Такого пункта нет.");
        }
    }

    private void selectComparator() {
        System.out.println("\nПОЛЕ ДЛЯ СОРТИРОВКИ");
        System.out.println("1. Номер группы");
        System.out.println("2. Средний балл");
        System.out.println("3. Номер зачетной книжки");
        System.out.println("0. Назад");
        System.out.print("Ваш выбор: ");

        int choice = readInt();

        switch (choice) {
            case 1:
                selectedComparator = StudentComparators.byGroupNumber();
                System.out.println("Выбрано поле: номер группы.");
                break;

            case 2:
                selectedComparator = StudentComparators.byAverageScore();
                System.out.println("Выбрано поле: средний балл.");
                break;

            case 3:
                selectedComparator = StudentComparators.byRecordBookNumber();
                System.out.println("Выбрано поле: номер зачетной книжки.");
                break;

            case 0:
                return;

            default:
                System.out.println("Такого пункта нет.");
        }
    }

    private void sortAndPrint() {
        if (students.isEmpty()) {
            System.out.println("Коллекция пуста. Сначала заполните её.");
            return;
        }

        if (selectedStrategy == null) {
            System.out.println("Сначала выберите стратегию сортировки.");
            return;
        }

        selectedComparator = null;
        selectComparator();

        if (selectedComparator == null) {
            return;
        }

        selectedStrategy.sort(students, selectedComparator);

        System.out.println("\nОтсортированные студенты:");

        for (Student student : students) {
            System.out.println(student);
        }
    }

    private void writeToFile() {
        if (students.isEmpty()) {
            System.out.println("Коллекция пуста. Сначала заполните её.");
            return;
        }

        System.out.print("Введите путь к файлу: ");
        String filePath = scanner.nextLine();

        fileWriterService.writeStudents(students, filePath);
    }

    /**
     * Запрашивает номер зачётной книжки, подсчитывает количество студентов с таким номером
     * и записывает результат в файл в режиме добавления.
     */
    private void saveSearchResultToFile() {
        if (students.isEmpty()) {
            System.out.println("Коллекция пуста. Сначала заполните её.");
            return;
        }

        System.out.print("Введите номер зачётной книжки для поиска: ");
        String target = scanner.nextLine().trim();
        if (target.isEmpty()) {
            System.out.println("Номер не может быть пустым. Операция отменена.");
            return;
        }

        // Подсчёт количества студентов с таким номером
        long count = students.stream()
                .filter(s -> s.getRecordBookNumber().equals(target))
                .count();

        System.out.println("Найдено студентов с номером '" + target + "': " + count);

        System.out.print("Введите путь к файлу для записи результата: ");
        String path = scanner.nextLine().trim();
        if (path.isEmpty()) {
            System.out.println("Путь не может быть пустым. Операция отменена.");
            return;
        }

        // Запись в файл в режиме добавления
        try (FileWriter fw = new FileWriter(path, true)) {
            fw.write("Результат поиска по номеру зачётки '" + target + "': " + count + System.lineSeparator());
            System.out.println("Результат записан в файл: " + path);
        } catch (IOException e) {
            System.err.println("Ошибка при записи в файл: " + e.getMessage());
        }
    }
}