package src.main.java.sort.TypeOfSort;

import java.util.Comparator;
import java.util.List;
import java.util.Objects;

public class InsertSort<T> implements SortStrategy<T>{
    @Override
    public void sort(List<T> list, Comparator<? super T> comparator) {
        Objects.requireNonNull(list, "Список не может быть null");
        Objects.requireNonNull(comparator, "Компаратор не может быть null");

        for (int i = 1; i < list.size(); i++) {
            T currentElement = list.get(i);
            int j = i - 1;

            while (j >= 0 && comparator.compare(list.get(j), currentElement) > 0) {
                list.set(j + 1, list.get(j));
                j--;
            }

            list.set(j + 1, currentElement);
        }
    }
}
