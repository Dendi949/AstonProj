package thread;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Класс для многопоточного подсчёта количества вхождений заданного элемента
 * в коллекцию. Разбивает список на части и обрабатывает их параллельно.
 * Разработал: Участник 6
 */
public class CountOccurrences {

    /**
     * Подсчитывает количество вхождений элемента target в список list
     * с использованием указанного числа потоков.
     *
     * @param list        коллекция для поиска (может быть null)
     * @param target      искомый элемент
     * @param numThreads  количество потоков для параллельной обработки
     * @param <T>         тип элементов списка
     * @return общее количество вхождений target в list
     * @throws InterruptedException если выполнение потоков было прервано
     */
    public static <T> int countParallel(List<T> list, T target, int numThreads) throws InterruptedException {
        // Если список пуст или null – сразу возвращаем 0
        if (list == null || list.isEmpty()) {
            return 0;
        }

        int size = list.size();
        // Вычисляем размер чанка (последний может быть меньше)
        int chunkSize = (size + numThreads - 1) / numThreads;

        // Создаём пул потоков фиксированного размера
        ExecutorService executor = Executors.newFixedThreadPool(numThreads);

        // Атомарный счётчик для потокобезопасного накопления результата
        AtomicInteger total = new AtomicInteger(0);

        // Разбиваем список на чанки и отправляем задачи в пул
        for (int i = 0; i < size; i += chunkSize) {
            int start = i;
            int end = Math.min(i + chunkSize, size);
            executor.submit(() -> {
                int count = 0;
                for (int j = start; j < end; j++) {
                    if (list.get(j).equals(target)) {
                        count++;
                    }
                }
                total.addAndGet(count); // атомарное добавление
            });
        }

        // Запрещаем отправку новых задач
        executor.shutdown();
        // Ожидаем завершения всех задач (максимум 5 секунд)
        boolean finished = executor.awaitTermination(5, TimeUnit.SECONDS);
        if (!finished) {
            // Если не успели – принудительно останавливаем
            executor.shutdownNow();
        }
        // Возвращаем итоговое количество
        return total.get();
    }
}