package src.main.java.sort.TypeOfSort;

import java.util.Comparator;
import java.util.List;

public interface SortStrategy<T> {

    void sort(List<T> list, Comparator<? super T> comparator);
}