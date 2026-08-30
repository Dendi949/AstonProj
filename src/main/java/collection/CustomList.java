package collection;

import java.util.*;


public class CustomList<T> extends AbstractList<T>{

    private static final int DEFAULT_CAPACITY = 10;

    private Object[] elements;
    private int size;

    public CustomList() {
        this(DEFAULT_CAPACITY);
    }

    public CustomList(int initialCapacity) {
        if (initialCapacity < 0) {
            throw new IllegalArgumentException(
                    "Вместимость не может быть отрицательной"
            );
        }

        elements = new Object[initialCapacity];
    }

    public CustomList(Collection<? extends T> source) {
        this();
        addAll(Objects.requireNonNull(
                source,
                "Коллекция не должна быть null"
        ));
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public boolean isEmpty() {
        return size == 0;
    }

    @SuppressWarnings("unchecked")
    @Override
    public T get(int index) {
        checkElementIndex(index);
        return (T) elements[index];
    }

    @SuppressWarnings("unchecked")
    @Override
    public T set(int index, T element) {
        checkElementIndex(index);

        T previousElement = (T) elements[index];
        elements[index] = element;

        return previousElement;
    }

    @Override
    public boolean add(T element) {
        add(size, element);
        return true;
    }

    @Override
    public void add(int index, T element) {
        checkPositionIndex(index);
        ensureCapacity(size + 1);

        int elementsToMove = size - index;

        if (elementsToMove > 0) {
            System.arraycopy(
                    elements,
                    index,
                    elements,
                    index + 1,
                    elementsToMove
            );
        }

        elements[index] = element;
        size++;
        modCount++;
    }

    @Override
    public boolean addAll(Collection<? extends T> source) {
        return addAll(size, source);
    }

    @Override
    public boolean addAll(
            int index,
            Collection<? extends T> source
    ) {
        checkPositionIndex(index);
        Objects.requireNonNull(
                source,
                "Добавляемая коллекция не должна быть null"
        );

        Object[] newElements = source.toArray();

        if (newElements.length == 0) {
            return false;
        }

        ensureCapacity(size + newElements.length);

        int elementsToMove = size - index;

        if (elementsToMove > 0) {
            System.arraycopy(
                    elements,
                    index,
                    elements,
                    index + newElements.length,
                    elementsToMove
            );
        }

        System.arraycopy(
                newElements,
                0,
                elements,
                index,
                newElements.length
        );

        size += newElements.length;
        modCount++;

        return true;
    }

    @SuppressWarnings("unchecked")
    @Override
    public T remove(int index) {
        checkElementIndex(index);

        T removedElement = (T) elements[index];
        int elementsToMove = size - index - 1;

        if (elementsToMove > 0) {
            System.arraycopy(
                    elements,
                    index + 1,
                    elements,
                    index,
                    elementsToMove
            );
        }

        elements[--size] = null;
        modCount++;

        return removedElement;
    }

    @Override
    public boolean remove(Object target) {
        int index = indexOf(target);

        if (index < 0) {
            return false;
        }

        remove(index);
        return true;
    }

    @Override
    public boolean contains(Object target) {
        return indexOf(target) >= 0;
    }

    @Override
    public int indexOf(Object target) {
        for (int i = 0; i < size; i++) {
            if (Objects.equals(elements[i], target)) {
                return i;
            }
        }

        return -1;
    }

    @Override
    public int lastIndexOf(Object target) {
        for (int i = size - 1; i >= 0; i--) {
            if (Objects.equals(elements[i], target)) {
                return i;
            }
        }

        return -1;
    }

    @Override
    public void clear() {
        if (size == 0) {
            return;
        }

        Arrays.fill(elements, 0, size, null);
        size = 0;
        modCount++;
    }

    private void ensureCapacity(int requiredCapacity) {
        if (requiredCapacity <= elements.length) {
            return;
        }

        int currentCapacity = elements.length;
        int increasedCapacity = currentCapacity + currentCapacity / 2;

        int newCapacity = Math.max(DEFAULT_CAPACITY, increasedCapacity);

        if (newCapacity < requiredCapacity) {
            newCapacity = requiredCapacity;
        }

        elements = Arrays.copyOf(elements, newCapacity);
    }

    private void checkElementIndex(int index) {
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException("Индекс: " + index + ", размер: " + size);
        }
    }

    private void checkPositionIndex(int index) {
        if (index < 0 || index > size) {
            throw new IndexOutOfBoundsException("Индекс: " + index + ", размер: " + size);
        }
    }
}