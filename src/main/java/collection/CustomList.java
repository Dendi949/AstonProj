package collection;

import java.util.*;

/**
 * Временная реализация кастомной коллекции на основе массива.
 * Реализует интерфейс List<T>, но большинство методов оставлены
 * как заглушки. Полноценная реализация будет выполнена участником 5.
 * В текущей версии реализованы только методы, необходимые для тестов:
 * add, get, remove, size, clear, iterator.
 */
public class CustomList<T> implements List<T> {
    private Object[] elements = new Object[10];
    private int size = 0;

    @Override public int size() { return size; }
    @Override public boolean isEmpty() { return size == 0; }
    @Override public boolean contains(Object o) { return false; }

    @Override
    public Iterator<T> iterator() {
        return new Iterator<T>() {
            private int index = 0;
            @Override public boolean hasNext() { return index < size; }
            @Override @SuppressWarnings("unchecked")
            public T next() {
                if (!hasNext()) throw new NoSuchElementException();
                return (T) elements[index++];
            }
        };
    }

    @Override public Object[] toArray() { return Arrays.copyOf(elements, size); }
    @Override public <T1> T1[] toArray(T1[] a) { return null; }

    /**
     * Добавляет элемент в конец списка с автоматическим расширением массива.
     */
    @Override
    public boolean add(T t) {
        ensureCapacity(size + 1);
        elements[size++] = t;
        return true;
    }

    private void ensureCapacity(int minCap) {
        if (minCap > elements.length) {
            elements = Arrays.copyOf(elements, elements.length * 2);
        }
    }

    @Override
    public boolean remove(Object o) {
        int idx = indexOf(o);
        if (idx < 0) return false;
        remove(idx);
        return true;
    }

    @Override public boolean containsAll(Collection<?> c) { return false; }
    @Override public boolean addAll(Collection<? extends T> c) { return false; }
    @Override public boolean addAll(int index, Collection<? extends T> c) { return false; }
    @Override public boolean removeAll(Collection<?> c) { return false; }
    @Override public boolean retainAll(Collection<?> c) { return false; }

    @Override
    public void clear() {
        elements = new Object[10];
        size = 0;
    }

    @Override
    @SuppressWarnings("unchecked")
    public T get(int index) {
        if (index < 0 || index >= size) throw new IndexOutOfBoundsException();
        return (T) elements[index];
    }

    @Override
    public T set(int index, T element) {
        if (index < 0 || index >= size) throw new IndexOutOfBoundsException();
        T old = (T) elements[index];
        elements[index] = element;
        return old;
    }

    @Override
    public void add(int index, T element) {
        if (index < 0 || index > size) throw new IndexOutOfBoundsException();
        ensureCapacity(size + 1);
        System.arraycopy(elements, index, elements, index + 1, size - index);
        elements[index] = element;
        size++;
    }

    @Override
    @SuppressWarnings("unchecked")
    public T remove(int index) {
        if (index < 0 || index >= size) throw new IndexOutOfBoundsException();
        T old = (T) elements[index];
        int moved = size - index - 1;
        if (moved > 0) {
            System.arraycopy(elements, index + 1, elements, index, moved);
        }
        elements[--size] = null;
        return old;
    }

    @Override
    public int indexOf(Object o) {
        for (int i = 0; i < size; i++) {
            if (Objects.equals(elements[i], o)) return i;
        }
        return -1;
    }

    @Override public int lastIndexOf(Object o) { return -1; }
    @Override public ListIterator<T> listIterator() { return null; }
    @Override public ListIterator<T> listIterator(int index) { return null; }
    @Override public List<T> subList(int fromIndex, int toIndex) { return null; }
}