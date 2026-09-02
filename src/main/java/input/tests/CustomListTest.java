package input.tests;

import collection.CustomList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.Objects;

public class CustomListTest {

    private static int passed;
    private static int failed;

    public CustomListTest() {
    }

    public static void runAllTests() {
        passed = 0;
        failed = 0;

        runTest("Добавление и получение", CustomListTest::shouldAddAndGet);
        runTest("Вставка и замена", CustomListTest::shouldInsertAndSet);
        runTest("Удаление", CustomListTest::shouldRemove);
        runTest("Поиск элементов", CustomListTest::shouldFindElements);
        runTest("Итератор", CustomListTest::shouldIterate);
        runTest("Добавление коллекции", CustomListTest::shouldAddAll);
        runTest("Массив и Stream API", CustomListTest::shouldSupportArrayAndStream);
        runTest("Очистка", CustomListTest::shouldClear);
        runTest("Проверка индексов", CustomListTest::shouldCheckIndexes);

        System.out.println();
        System.out.println("Успешно: " + passed);
        System.out.println("Ошибок: " + failed);

        if (failed > 0) {
            throw new AssertionError("Тесты завершились с ошибками");
        }
    }

    private static void shouldAddAndGet() {
        CustomList<Integer> list = new CustomList<>(1);
        for (int i = 0; i < 50; i++) {
            list.add(i);
        }
        checkEquals(50, list.size(), "Неверный размер");
        for (int i = 0; i < 50; i++) {
            checkEquals(i, list.get(i), "Неверный элемент");
        }
    }

    private static void shouldInsertAndSet() {
        CustomList<String> list = new CustomList<>();
        list.add("A");
        list.add("C");
        list.add(1, "B");
        checkEquals(List.of("A", "B", "C"), list, "Неверный порядок после вставки");
        String oldValue = list.set(1, "X");
        checkEquals("B", oldValue, "set вернул неверное старое значение");
        checkEquals("X", list.get(1), "Элемент не заменился");
    }

    private static void shouldRemove() {
        CustomList<String> list = new CustomList<>(List.of("A", "B", "C", "D"));
        checkEquals("B", list.remove(1), "Удалён неверный элемент");
        checkEquals(List.of("A", "C", "D"), list, "Неверный список после удаления");
        check(list.remove("C"), "Элемент C должен удалиться");
        check(!list.remove("Z"), "Несуществующий элемент не удаляется");
    }

    private static void shouldFindElements() {
        CustomList<String> list = new CustomList<>(List.of("A", "B", "A"));
        check(list.contains("B"), "B должен находиться");
        check(!list.contains("X"), "X не должен находиться");
        checkEquals(0, list.indexOf("A"), "Неверный indexOf");
        checkEquals(2, list.lastIndexOf("A"), "Неверный lastIndexOf");
    }

    private static void shouldIterate() {
        CustomList<Integer> list = new CustomList<>(List.of(1, 2, 3));
        int sum = 0;
        for (Integer value : list) {
            sum += value;
        }
        checkEquals(6, sum, "Итератор работает неправильно");
    }

    private static void shouldAddAll() {
        CustomList<String> list = new CustomList<>();
        list.addAll(List.of("A", "D"));
        list.addAll(1, List.of("B", "C"));
        checkEquals(List.of("A", "B", "C", "D"), list, "addAll работает неправильно");
    }

    private static void shouldSupportArrayAndStream() {
        CustomList<Integer> list = new CustomList<>(List.of(5, 1, 4, 2, 3));
        Integer[] array = list.toArray(new Integer[0]);
        check(Arrays.equals(new Integer[]{5, 1, 4, 2, 3}, array), "toArray работает неправильно");
        int sum = list.stream().mapToInt(Integer::intValue).sum();
        checkEquals(15, sum, "Stream работает неправильно");
        list.sort(Comparator.naturalOrder());
        checkEquals(List.of(1, 2, 3, 4, 5), list, "sort работает неправильно");
    }

    private static void shouldClear() {
        CustomList<Integer> list = new CustomList<>(List.of(1, 2, 3));
        list.clear();
        check(list.isEmpty(), "Список должен быть пустым");
        checkEquals(0, list.size(), "Размер должен быть равен нулю");
    }

    private static void shouldCheckIndexes() {
        CustomList<Integer> list = new CustomList<>();
        checkThrows(IndexOutOfBoundsException.class, () -> list.get(0));
        checkThrows(IndexOutOfBoundsException.class, () -> list.add(1, 10));
        checkThrows(IllegalArgumentException.class, () -> new CustomList<Integer>(-1));
    }

    private static void runTest(String testName, Runnable test) {
        try {
            test.run();
            passed++;
            System.out.println("[OK] " + testName);
        } catch (Throwable exception) {
            failed++;
            System.out.println("[ERROR] " + testName);
            exception.printStackTrace();
        }
    }

    private static void check(boolean condition, String message) {
        if (!condition) {
            throw new AssertionError(message);
        }
    }

    private static void checkEquals(Object expected, Object actual, String message) {
        if (!Objects.equals(expected, actual)) {
            throw new AssertionError(message + ". Ожидалось: " + expected + ", получено: " + actual);
        }
    }

    private static void checkThrows(Class<? extends Throwable> expected, Runnable action) {
        try {
            action.run();
        } catch (Throwable exception) {
            if (expected.isInstance(exception)) {
                return;
            }
            throw new AssertionError("Ожидалось исключение " + expected.getSimpleName());
        }
        throw new AssertionError("Исключение " + expected.getSimpleName() + " не возникло");
    }
}