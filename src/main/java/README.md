# SortingApp

Консольное приложение для сортировки объектов класса Student с использованием паттернов Стратегия, Builder, стримов и многопоточности.

## Структура проекта
- `model` – класс Student + Builder
- `src.main.java.sort` – стратегии сортировки и компараторы
- `input` – заполнение данных из файла/рандом/вручную
- `service` – главный цикл и меню
- `collection` – кастомная коллекция
- `thread` – многопоточный подсчёт
- `tests` – ручные тесты

## Инструкция для разработчиков
1. Клонировать репозиторий.
2. Создать свою ветку от `main`.
3. Разрабатывать свой модуль, не изменяя интерфейсы без согласования.
4. Регулярно делать `pull` из `main`.
5. После завершения создать Pull Request.


## CustomList

`CustomList<E>` — собственная обобщённая коллекция, которая хранит элементы во внутреннем массиве.

Пример создания:
```java
CustomList&lt;Integer&gt; numbers = new CustomList&lt;&gt;();
```

### Добавление элементов
```java
numbers.add(10);
numbers.add(20);
numbers.add(30);
```

Добавление по индексу:
```java
numbers.add(1, 15);
```

Результат:
```text
[10, 15, 20, 30]
```

### Получение элемента
```java
Integer value = numbers.get(0);
System.out.println(value);
```

### Замена элемента
```java
numbers.set(0, 100);
```

### Удаление по индексу
```java
numbers.remove(1);
```

### Удаление по значению
```java
numbers.remove(Integer.valueOf(20));
```

Для `Integer` используется `Integer.valueOf(...)`, чтобы вызвать удаление по значению, а не по индексу.

### Получение размера
```java
int size = numbers.size();
```

### Проверка на пустоту
```java
boolean empty = numbers.isEmpty();
```

### Проверка наличия элемента
```java
boolean contains = numbers.contains(100);
```

### Поиск индекса
```java
int firstIndex = numbers.indexOf(100);
int lastIndex = numbers.lastIndexOf(100);
```

Если элемент отсутствует, возвращается:
```text
-1
```

### Очистка коллекции
```java
numbers.clear();
```

### Обход коллекции
```java
for (Integer number : numbers) {
    System.out.println(number);
}
```

### Использование Stream API
```java
numbers.stream()
        .filter(number -&gt; number &gt; 10)
        .forEach(System.out::println);
```
### Сортировка
```java
numbers.sort(Integer::compareTo);
```

Для студентов можно использовать компараторы:
```java
students.sort(StudentComparators.byGroupNumber());
students.sort(StudentComparators.byAverageScore());
students.sort(StudentComparators.byRecordBookNumber());
```
## DataFiller

Класс `DataFiller` поддерживает три способа заполнения коллекции:
```java
public enum FillType {
    RANDOM,
    FILE,
    MANUAL
}
```

Метод `fill` выбирает способ заполнения:
```java
CustomList&lt;Student&gt; students = DataFiller.fill(
        DataFiller.FillType.RANDOM,
        10,
        null
);
```

Параметры метода:

- `type` — способ заполнения;
- `size` — количество создаваемых студентов;
- `filePath` — путь к CSV-файлу.

Для режима `FILE` параметр `size` не используется. Для режимов `RANDOM` и `MANUAL` путь к файлу не требуется.

## Случайное заполнение

Прямой вызов:
```java
CustomList&lt;Student&gt; students =
        DataFiller.fillRandom(10);
```

Вызов через общий метод:
```java
CustomList&lt;Student&gt; students = DataFiller.fill(
        DataFiller.FillType.RANDOM,
        10,
        null
);
```

Будет создано `10` студентов со случайными допустимыми значениями.

## Ручное заполнение

Прямой вызов:
```java
CustomList&lt;Student&gt; students =
        DataFiller.fillManually(3);
```

Вызов через общий метод:
```java
CustomList&lt;Student&gt; students = DataFiller.fill(
        DataFiller.FillType.MANUAL,
        3,
        null
);
```

Для каждого студента программа запросит:
```text
Введите номер группы:
Введите средний балл:
Введите номер зачётной книжки:
```

Если пользователь вводит неправильное значение, программа выводит сообщение об ошибке и повторяет запрос.

Пример корректных значений:
```text
Номер группы: 101
Средний балл: 4.5
Номер зачётной книжки: RB-001
```

## Заполнение из файла

Прямой вызов:
```java
CustomList&lt;Student&gt; students =
        DataFiller.fillFromFile(
                "src/main/resources/students.csv"
        );
```

Вызов через общий метод:
```java
CustomList&lt;Student&gt; students = DataFiller.fill(
        DataFiller.FillType.FILE,
        0,
        "src/main/resources/students.csv"
);
```

Файл читается с помощью Stream API:
```java
Files.lines(Path.of(filePath))
```

Для накопления результатов используется `CustomList<Student>`, а не `ArrayList<Student>`.

## Формат students.csv

Пример файла `students.csv`:
```csv
groupNumber,averageScore,recordBookNumber
101,4.75,RB-001
203,3.90,RB-002
102,5.00,RB-003
301,2.85,RB-004
205,4.20,RB-005
```
Каждая строка содержит:
```text
номер группы,средний балл,номер зачётной книжки
```

Файл должен быть сохранён в кодировке UTF-8.

Поддерживаются:

- заголовок CSV;
- пустые строки;
- пробелы вокруг значений;
- BOM-символ в начале файла;
- строки-комментарии, если они начинаются с `#`.

Пример комментария:
```csv
# Список студентов
groupNumber,averageScore,recordBookNumber
101,4.75,RB-001
```
## Тестирование CustomList

Тестами проверяются:

- создание пустой коллекции;
- добавление элементов;
- добавление элемента по индексу;
- получение элемента;
- замена элемента;
- удаление по индексу;
- удаление по значению;
- автоматическое увеличение внутреннего массива;
- работа `contains`;
- работа `indexOf`;
- работа `lastIndexOf`;
- очистка коллекции;
- корректная работа `size`;
- проверка выхода индекса за допустимые границы.

Пример проверки:
```java
CustomList&lt;String&gt; list = new CustomList&lt;&gt;();

list.add("A");
list.add("B");
list.add("C");

if (list.size() != 3) {
    throw new AssertionError(
            "Ожидался размер 3, получен " + list.size()
    );
}

if (!"B".equals(list.get(1))) {
    throw new AssertionError(
            "По индексу 1 должен находиться элемент B"
    );
}

System.out.println("Тест успешно пройден");
```

Для запуска тестов откройте класс `CustomListTest` в IntelliJ IDEA и запустите метод `main`.

При успешном прохождении тестов выводится сообщение:
```text
Все тесты CustomList успешно пройдены
```
## Компараторы студентов

Все компараторы реализуют интерфейс `Comparator<Student>` и задают сортировку студентов **по возрастанию** выбранного параметра.

> Метод `compare(first, second)` возвращает:
>
> - отрицательное число, если `first` должен находиться раньше `second`;
> - `0`, если значения равны;
> - положительное число, если `first` должен находиться после `second`.

### `AverageScoreComparator`

Сравнивает студентов по **среднему баллу** с помощью метода `Double.compare()`.
```java
Comparator&lt;Student&gt; comparator = new AverageScoreComparator();
```

Пример порядка:
```text
12.0 → 12.0 → 13.0
```

Студенты с одинаковым средним баллом считаются равными в рамках данного критерия.

---

### `GroupNumberComparator`

Сравнивает студентов по **номеру группы** с помощью метода `Integer.compare()`.
```java
Comparator&lt;Student&gt; comparator = new GroupNumberComparator();
```

Пример порядка:
```text
Группа 1 → Группа 2 → Группа 3
```

Другие поля студента при сравнении не учитываются.

---

### `RecordBookNumberComparator`

Сравнивает студентов по **номеру зачётной книжки** с помощью метода `String.compareTo()`.
```java
Comparator&lt;Student&gt; comparator =
        new RecordBookNumberComparator();
```

Сравнение выполняется в естественном лексикографическом порядке:
```text
A-1 → A-2 → A-3
```

Если номера зачётных книжек совпадают, компаратор возвращает `0`.

---

## Стратегии сортировки

Все алгоритмы реализуют общий интерфейс `SortStrategy<T>`. Благодаря использованию обобщённого типа `T` стратегии могут сортировать списки любых объектов.

### `SortStrategy<T>`

Интерфейс определяет единый контракт для всех алгоритмов сортировки:
```java
public interface SortStrategy&lt;T&gt; {

    void sort(
            List&lt;T&gt; list,
            Comparator&lt;? super T&gt; comparator
    );
}
```

Порядок элементов определяется переданным компаратором:
```java
SortStrategy&lt;Student&gt; strategy = new BubbleSort&lt;&gt;();

strategy.sort(
        students,
        new AverageScoreComparator()
);
```

Все представленные стратегии:

- изменяют исходный список;
- работают с пользовательским компаратором;
- проверяют список и компаратор на `null`;
- выбрасывают `NullPointerException`, если один из аргументов равен `null`.

> Список должен быть изменяемым. Например, список, созданный через `List.of(...)`, нельзя передавать напрямую, поскольку алгоритмы используют операции `set()` и перестановку элементов.

---

### `BubbleSort<T>`

Реализует алгоритм **пузырьковой сортировки**.

Алгоритм последовательно сравнивает соседние элементы. Если левый элемент больше правого, они меняются местами.
```java
if (comparator.compare(
        list.get(j),
        list.get(j + 1)
) &gt; 0) {
    Swap.swap(list, j, j + 1);
}
```

Переменная `swaped` позволяет завершить сортировку досрочно, если за очередной проход не произошло ни одной перестановки.

**Особенности:**

- сортировка выполняется на месте;
- алгоритм является стабильным;
- поддерживается досрочное завершение;
- лучше всего подходит для небольших списков.

---

### `EvenOddSort<T>`

Реализует **чётно-нечётную сортировку**.

Каждая итерация состоит из двух фаз:

1. Сравниваются пары с нечётными индексами: `(1, 2)`, `(3, 4)` и далее.
2. Сравниваются пары с чётными индексами: `(0, 1)`, `(2, 3)` и далее.

Работа продолжается до тех пор, пока обе фазы не пройдут без перестановок.
```java
while (!sorted) {
    sorted = true;

    // Нечётная фаза
    // Чётная фаза
}
```

**Особенности:**

- сортировка выполняется на месте;
- алгоритм является стабильным;
- соседние элементы обрабатываются поочерёдно в двух фазах.

---

### `InsertSort<T>`

Реализует **сортировку вставками**.

На каждой итерации выбирается очередной элемент, после чего он вставляется в правильную позицию среди уже обработанных элементов.
```java
while (
        j &gt;= 0
        &amp;&amp; comparator.compare(
                list.get(j),
                currentElement
        ) &gt; 0
) {
    list.set(j + 1, list.get(j));
    j--;
}
```

**Особенности:**

- сортировка выполняется на месте;
- алгоритм является стабильным;
- эффективно работает с небольшими и почти отсортированными списками;
- для перемещения элементов используется метод `List.set()`.

---

### `SelectionSort<T>`

Реализует **сортировку выбором**.

На каждой итерации алгоритм ищет минимальный элемент в неотсортированной части списка и перемещает его в текущую позицию.
```java
if (comparator.compare(
        list.get(j),
        list.get(minInd)
) &lt; 0) {
    minInd = j;
}
```

После нахождения минимального элемента выполняется перестановка:
```java
if (minInd != i) {
    Swap.swap(list, i, minInd);
}
```

**Особенности:**

- сортировка выполняется на месте;
- количество сравнений не зависит от исходного порядка элементов;
- алгоритм в общем случае не является стабильным.

---

### Сравнение алгоритмов

| Алгоритм | Лучший случай | Средний случай | Худший случай | Дополнительная память | Стабильность |
|---|---:|---:|---:|---:|:---:|
| `BubbleSort` | \(O(n)\) | \(O(n^2)\) | \(O(n^2)\) | \(O(1)\) | Да |
| `EvenOddSort` | \(O(n)\) | \(O(n^2)\) | \(O(n^2)\) | \(O(1)\) | Да |
| `InsertSort` | \(O(n)\) | \(O(n^2)\) | \(O(n^2)\) | \(O(1)\) | Да |
| `SelectionSort` | \(O(n^2)\) | \(O(n^2)\) | \(O(n^2)\) | \(O(1)\) | Нет |

> Стабильный алгоритм сохраняет исходный взаимный порядок элементов, которые компаратор считает равными.

---

## Вспомогательный класс `Swap`

Класс `Swap` предоставляет статический метод для перестановки двух элементов списка.
```java
Swap.swap(list, firstIndex, secondIndex);
```

Метод временно сохраняет первый элемент, после чего меняет указанные элементы местами:
```java
T temporary = elements.get(first);
elements.set(first, elements.get(second));
elements.set(second, temporary);
```

Конструктор класса закрыт, поскольку создавать экземпляры `Swap` не требуется.

---

## Тестирование

Тестирование реализовано без использования **JUnit**, **Mockito** и других сторонних тестовых фреймворков.

Для запуска проверок используются собственные классы:

- `Assertion` — методы проверки результатов;
- `TestRunner` — запуск тестов и подсчёт результатов;
- `ListOfStudentForTests` — подготовка тестовых данных;
- `TestingComparator` — проверка компараторов;
- `SortingStrategiesTest` — проверка алгоритмов сортировки;
- `MainForTest` — точка запуска всех тестов.

---

### `ListOfStudentForTests`

Класс создаёт фиксированный набор студентов, используемый в тестах.

| Студент | Номер группы | Средний балл | Номер зачётной книжки |
|---|---:|---:|---|
| `student1` | 1 | 12.0 | `A-1` |
| `student2` | 2 | 12.0 | `A-2` |
| `student3` | 1 | 13.0 | `A-3` |
| `student4` | 1 | 12.0 | `A-3` |

Наличие одинаковых значений позволяет проверить корректную обработку равных элементов:

- `student1` и `student3` находятся в одной группе;
- `student1`, `student2` и `student4` имеют одинаковый средний балл;
- `student3` и `student4` имеют одинаковый номер зачётной книжки.

Метод `getStudentsList()` возвращает список, созданный через `List.of(...)`.
```java
public List&lt;Student&gt; getStudentsList() {
    return list;
}
```

> Такой список является неизменяемым. Поэтому перед сортировкой создаётся его изменяемая копия через `new ArrayList<>(...)`.

---

### `Assertion`

Класс содержит собственные методы проверки результатов тестирования.

#### `assertTrue`

Проверяет, что переданное условие истинно:
```java
Assertion.assertTrue(
        actualValue &lt; expectedValue,
        "Описание ошибки"
);
```

Если условие ложно, выбрасывается `AssertionError`.

#### `assertEquals`

Сравнивает ожидаемый и фактический результаты с помощью `Objects.equals()`:
```java
Assertion.assertEquals(
        expected,
        actual,
        "Описание ошибки"
);
```

При несовпадении сообщение содержит оба значения:
```text
Ожидалось: expected, получено: actual
```

#### `assertThrows`

Проверяет, что выполнение действия приводит к ожидаемому исключению:
```java
Assertion.assertThrows(
        NullPointerException.class,
        () -&gt; action(),
        "Ожидалось исключение"
);
```

Проверка завершается ошибкой, если:

- исключение не возникло;
- возникло исключение другого типа.

---

### `TestingComparator`

Класс проверяет работу трёх компараторов студентов.

#### Проверка `GroupNumberComparator`

Тест подтверждает, что:

- группа `1` должна располагаться раньше группы `2`;
- одинаковые номера групп считаются равными.
```java
Assertion.assertTrue(
        byGroup.compare(student1, student2) &lt; 0,
        "Группа 1 &lt; 2"
);
```

#### Проверка `AverageScoreComparator`

Тест подтверждает, что:

- средний балл `12.0` меньше `13.0`;
- одинаковые средние баллы считаются равными.
```java
Assertion.assertEquals(
        0,
        byAverageScore.compare(student1, student2),
        "Одинаковые баллы должны считаться равными"
);
```

#### Проверка `RecordBookNumberComparator`

Тест подтверждает, что:

- номер `A-1` располагается раньше `A-2`;
- одинаковые номера зачётных книжек считаются равными.

Метод `runAll()` регистрирует все проверки в объекте `TestRunner`.

---

### `SortingStrategiesTest`

Класс проверяет работу всех реализованных стратегий:
```java
runTest(runner, "BubbleSort", new BubbleSort&lt;Student&gt;());
runTest(runner, "EvenOddSort", new EvenOddSort&lt;Student&gt;());
runTest(runner, "InsertSort", new InsertSort&lt;Student&gt;());
runTest(runner, "SelectionSort", new SelectionSort&lt;Student&gt;());
```

Для каждой стратегии создаётся отдельная изменяемая копия тестового списка:
```java
List&lt;Student&gt; students = new ArrayList&lt;&gt;(
        listOfStudentForTests.getStudentsList()
);
```

Сортировка выполняется по среднему баллу:
```java
strategy.sort(
        students,
        new AverageScoreComparator()
);
```

После сортировки из объектов `Student` извлекаются фактические значения среднего балла:
```java
List&lt;Double&gt; actualScores = students.stream()
        .map(Student::getAverageScore)
        .toList();
```

Ожидаемый результат:
```text
[12.0, 12.0, 12.0, 13.0]
```

Если порядок отличается, метод `Assertion.assertEquals()` выбрасывает `AssertionError`.

---

### `TestRunner`

Класс отвечает за выполнение тестов и формирование итоговой статистики.

Метод `run()` принимает:

- название теста;
- объект `Runnable`, содержащий тестовый код.

При успешном выполнении увеличивается счётчик завершённых тестов:
```text
Тест успешно завершен BubbleSort
```

Если во время выполнения возникло исключение или `AssertionError`, увеличивается счётчик проваленных тестов:
```text
Тест не завершился SelectionSort: описание ошибки
```

Метод `end()` выводит итоговую статистику:
```text
Завершенных= 7
Проваленных= 0
```

---

### `MainForTest`

Класс является точкой входа для запуска всех тестов.
```java
public static void main(String[] args) {
    TestRunner testRunner = new TestRunner();
    TestingComparator testingComparator =
            new TestingComparator();

    testingComparator.runAll(testRunner);
    SortingStrategiesTest.runAll(testRunner);

    testRunner.end();
}
```

Сначала запускаются тесты компараторов, затем проверяются стратегии сортировки, после чего выводится итоговое количество успешных и проваленных тестов.

При корректной работе всех классов результат выглядит следующим образом:
```text
Тест успешно завершен Компаратор по группе
Тест успешно завершен Компаратор по среднему баллу
Тест успешно завершен Компаратор по номеру зачётной книжки
Тест успешно завершен BubbleSort
Тест успешно завершен EvenOddSort
Тест успешно завершен InsertSort
Тест успешно завершен SelectionSort
Завершенных= 7
Проваленных= 0
```

## Сборка и запуск
```bash
javac -d out src/main/java/**/*.java
java -cp out Main