package service;

import input.DataFiller;
import model.Student;
import sort.typeOfSort.SortStrategy;
import sort.comparators.StudentComparators;

import java.util.Comparator;
import java.util.List;
import java.util.Scanner;

public class MainLoop {

    private Comparator<Student> selectedComparator;
    private SortStrategy selectedStrategy;
    private List<Student> students = List.of();
    private final Scanner scanner = new Scanner(System.in);

    public void run() {
        boolean running = true;

        while (running) {
            printMenu();

            int choice = readInt();

            switch (choice) {
                case 1:
                    fillCollection(); // заполнение коллекции
                    break;
                case 2:
                    selectStrategy(); // выбор стратегии
                    break;
                case 3:
                    sortAndPrint(); // сортировка и вывод
                    break;
                case 4:
                    System.out.println("Запись в файл");
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
                null
        );

        System.out.println("Коллекция заполнена.");
    }

    private void fillFromFile() {
        System.out.print("Введите путь к файлу: ");

        String path = scanner.nextLine();

        students = DataFiller.fill(
                DataFiller.FillType.FILE,
                0,
                path
        );

        System.out.println("Данные загружены.");
    }

    private void fillManual() {
        System.out.print("Введите количество студентов: ");

        int size = readPositiveInt();

        students = DataFiller.fill(
                DataFiller.FillType.MANUAL,
                size,
                null
        );

        System.out.println("Коллекция заполнена.");
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
                System.out.println("Пузырьковая сортировка будет доступна после реализации.");
                break;

            case 2:
                System.out.println("Сортировка вставками будет доступна после реализации.");
                break;

            case 3:
                System.out.println("Сортировка выбором будет доступна после реализации.");
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
}