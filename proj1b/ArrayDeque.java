public class ArrayDeque<T> implements Deque<T> {

    private T[] items;
    private int size;
    private int front;
    private int end;

    public ArrayDeque() {
        items = (T[]) new Object[8];
        size = front = end = 0;
    }

    /*public ArrayDeque(T item) {
        items = (T[]) new Object[8];
        front = end = 0;
        items[front] = item;
        size = 1;
    }*/

    private void resize(int capacity) {
        T[] a = (T[]) new Object[capacity];
        if (front < end) {
            System.arraycopy(items, front, a, 0, size);
        } else {
            System.arraycopy(items, front, a, 0, items.length - front);
            System.arraycopy(items, 0, a, items.length - front, end + 1);
        }
        items = a;
        front = 0;
        end = size - 1;
    }

    @Override
    public void addFirst(T item) {
        if (size == 0) {
            items[front] = item;
            size += 1;
            return;
        } else if (size == items.length) {
            resize(size * 2);
        }
        front = (items.length + front - 1) % items.length;
        items[front] = item;
        size += 1;
    }

    @Override
    public T removeFirst() {
        if (size == 0) {
            return null;
        }
        if ((size < items.length / 4) && (items.length > 16)) {
            resize(items.length / 4);
        }
        T first = getFirst();
        items[front] = null;
        size -= 1;
        if (size != 0) {
            front = (front + 1) % items.length;
        }
        return first;
    }

    private T getFirst() {
        return items[front];
    }

    @Override
    public void addLast(T item) {
        if (size == 0) {
            items[end] = item;
            size += 1;
            return;
        } else if (size == items.length) {
            resize(size * 2);
        }
        end = (end + 1) % items.length;
        items[end] = item;
        size += 1;
    }

    @Override
    public T removeLast() {
        if (size == 0) {
            return null;
        }
        if ((size < items.length / 4) && (items.length > 16)) {
            resize(items.length / 4);
        }
        T last = getLast();
        items[end] = null;
        size -= 1;
        if (size != 0) {
            end = (items.length + end - 1) % items.length;
        }
        return last;
    }

    private T getLast() {
        return items[end];
    }

    @Override
    public boolean isEmpty() {
        return size == 0;
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public T get(int index) {
        if (size == 0 || index >= size) {
            return null;
        }
        return items[(front + index) % items.length];
    }

    @Override
    public void printDeque() {
        for (int i = 0; i < size; i += 1) {
            System.out.print(get(i) + " ");
        }
        System.out.println();
    }
}
