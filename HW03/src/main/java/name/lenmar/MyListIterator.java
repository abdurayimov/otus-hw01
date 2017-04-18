package name.lenmar;

import org.apache.commons.lang3.NotImplementedException;

import java.util.ListIterator;

/**
 * Created by Lenmar Abdurayimov on 4/18/2017.
 */
public class MyListIterator<T> implements ListIterator<T> {

    private T[] elementData;
    private int cursor;
    private int size;

    public MyListIterator(T[] elementData, int cursor, int size) {
        this.elementData = elementData;
        this.cursor = cursor;
        this.size = size;
    }

    @Override
    public boolean hasNext() {
        return cursor + 1 < size;
    }

    @Override
    public T next() {
        return elementData[++cursor];
    }

    @Override
    public boolean hasPrevious() {
        return cursor > 0;
    }

    @Override
    public T previous() {
        if (hasPrevious()) {
            return elementData[--cursor];
        } else {
            throw new IllegalArgumentException();
        }
    }

    @Override
    public int nextIndex() {
        return cursor++;
    }

    @Override
    public int previousIndex() {
        return cursor--;
    }

    @Override
    public void remove() {
        throw new NotImplementedException("TODO");
    }

    @Override
    public void set(T t) {
        elementData[cursor] = t;
    }

    @Override
    public void add(T t) {
        throw new NotImplementedException("TODO");
    }
}
