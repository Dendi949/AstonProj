package sort.typeOfSort;

import java.util.List;

public final class Swap<T> {

    private Swap() {
    }

    public static <T> void swap(List<T> elements, int first, int second) {
        T temporary = elements.get(first);
        elements.set(first, elements.get(second));
        elements.set(second, temporary);
    }
}