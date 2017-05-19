package name.lenmar;

import java.util.*;

import org.apache.commons.lang3.NotImplementedException;

/**
 * Created by Lenmar Abdurayimov on 4/17/2017.
 *
 * Source: https://habrahabr.ru/post/128269/
 */
public class MyArrayList<T> implements List<T> {

    // Default (initial) capacity for new MyArrayList
    private static final int DEFAULT_CAPACITY = 10;

    // Size of MyArrayList (the number of elements)
    private int size;

    // Array of elements (data storage)
    private T[] elementData;

    // Default constructor
    public MyArrayList() {
        this(DEFAULT_CAPACITY);
    }

    // Constructor with capacity as parameter
    public MyArrayList(int capacity) {
        if (capacity < 0)
            throw new IllegalArgumentException("Illegal capacity of Collection");
        elementData = (T[]) new Object[capacity];
    }

    @Override
    public Iterator<T> iterator() {
        /*return new Iterator<T>() {

            private int index;

            @Override
            public boolean hasNext() {
                return index < size;
            }

            @Override
            public T next() {
                return elementData[index++];
            }
        };*/
        return listIterator();
    }

    @Override
    public ListIterator<T> listIterator() {
        return new MyListIterator(elementData, -1, size);
    }

    @Override
    public ListIterator<T> listIterator(int index) {
        return new MyListIterator(elementData, index, size);
    }

    // Return a number of elements in MyArrayList
    @Override
    public int size() {
        return size;
    }

    // Return if MyArrayList is empty
    @Override
    public boolean isEmpty() {
        return size == 0;
    }

    // Add an element to the end of Collection
    @Override
    public boolean add(T t) {
        if (size == elementData.length) {
            int newCapacity = (elementData.length * 3) / 2 + 1;
            if (newCapacity >= Integer.MAX_VALUE)
                throw new IndexOutOfBoundsException("Can't add the elements! Collection size limit exceeded.");
            else
                elementData = Arrays.copyOf(elementData, newCapacity);
        }
        elementData[size++] = t;
        return true;
    }

    // Get an element from Collection by the specific index
    @Override
    public T get(int index) {
        checkRange(index);
        return elementData[index];
    }

    @Override
    public T set(int index, T element) {
        checkRange(index);
        T result = elementData[index];
        elementData[index] = element;
        return result;
    }

    private void checkRange(int index) {
        if ((index < 0) || (index >= size))
            throw new IndexOutOfBoundsException("Index out of bound!");

    }

    // Return if Collection contains a specific element
    @Override
    public boolean contains(Object o) {
        return indexOf(o) >= 0;
    }

    // Return an index of specific element in Collection
    @Override
    public int indexOf(Object o) {
        if (o == null) {
            for (int i = 0; i < size; i++)
                if (elementData[i] == null)
                    return i;
        } else {
            for (int i = 0; i < size; i++)
                if (o.equals(elementData[i]))
                    return i;
        }
        return -1;
    }

    // Return an array representation of Collection
    @Override
    public Object[] toArray() {
        return Arrays.copyOf(elementData, size);
    }

    @Override
    public <T1> T1[] toArray(T1[] a) {
        throw new NotImplementedException("TODO");
    }

    @Override
    public void sort(Comparator<? super T> c) {
        T[] elementsArray = Arrays.copyOfRange(elementData, 0, size);
        Arrays.sort(elementsArray, c);
        System.arraycopy(elementsArray, 0, elementData, 0, size);
    }

    // Clear Collection from all elements
    @Override
    public void clear() {
        for (int i = 0; i < elementData.length; i++)
            elementData[i] = null;
        size = 0;
    }

    @Override
    public String toString() {
        Object o;
        String format;
        if (elementData != null) {
            StringBuilder sb = new StringBuilder();
            for (int i = 0; i < size - 1; i++) {
                o = elementData[i];
                format = (((o instanceof Float) || (o instanceof Double)) ? "%5.2f" : ((o instanceof Number) ? "%5d" : "%s"));
                sb.append(String.format(format, elementData[i]));
                sb.append(", ");
            }
            o = elementData[size-1];
            format = (((o instanceof Float) || (o instanceof Double)) ? "%5.2f" : ((o instanceof Number) ? "%5d" : "%s"));
            sb.append(String.format(format, elementData[size - 1]));
            return "size = " + size + ", elements: [" + sb.toString() + "]";
        }
        return "size = " + size + ", elements: " + Arrays.toString(elementData);
    }

    @Override
    public int lastIndexOf(Object o) {
        throw new NotImplementedException("TODO");
    }

    @Override
    public boolean containsAll(Collection<?> c) {
        throw new NotImplementedException("TODO");
    }

    @Override
    public boolean retainAll(Collection<?> c) {
        throw new NotImplementedException("TODO");
    }

    @Override
    public void add(int index, T element) {
        throw new NotImplementedException("TODO");
    }

    @Override
    public boolean addAll(Collection<? extends T> c) {
        throw new NotImplementedException("TODO");
    }

    @Override
    public boolean addAll(int index, Collection<? extends T> c) {
        throw new NotImplementedException("TODO");
    }

    @Override
    public boolean remove(Object o) {
        throw new NotImplementedException("TODO");
    }

    @Override
    public T remove(int index) {
        throw new NotImplementedException("TODO");
    }

    @Override
    public boolean removeAll(Collection<?> c) {
        throw new NotImplementedException("TODO");
    }

    @Override
    public List<T> subList(int fromIndex, int toIndex) {
        throw new NotImplementedException("TODO");
    }
}