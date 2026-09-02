package sort.typeOfSort;

import java.util.Comparator;
import java.util.List;
import java.util.Objects;

public class EvenOddSort<T> implements SortStrategy<T> {

    @Override
    public void sort(List<T> list, Comparator<? super T> comparator) {
        Objects.requireNonNull(list, "Список не может быть null");
        Objects.requireNonNull(comparator, "Компаратор не может быть null");

        boolean sorted = false;

        while (!sorted) {
            sorted = true;
            // нечётные индексы
            for (int i = 1; i < list.size() - 1; i += 2) {
                if (comparator.compare(list.get(i), list.get(i + 1)) > 0) {
                    Swap.swap(list, i, i + 1);
                    sorted = false;
                }
            }
            // чётные индексы
            for (int i = 0; i < list.size() - 1; i += 2) {
                if (comparator.compare(list.get(i), list.get(i + 1)) > 0) {
                    Swap.swap(list, i, i + 1);
                    sorted = false;
                }
            }
        }
    }
}