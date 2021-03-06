import edu.princeton.cs.algs4.Queue;

public class QuickSort {
    /**
     * Returns a new queue that contains the given queues catenated together.
     *
     * The items in q2 will be catenated after all of the items in q1.
     */
    private static <Item extends Comparable> Queue<Item> catenate(Queue<Item> q1, Queue<Item> q2) {
        Queue<Item> catenated = new Queue<Item>();
        for (Item item : q1) {
            catenated.enqueue(item);
        }
        for (Item item: q2) {
            catenated.enqueue(item);
        }
        return catenated;
    }

    /** Returns a random item from the given queue. */
    private static <Item extends Comparable> Item getRandomItem(Queue<Item> items) {
        int pivotIndex = (int) (Math.random() * items.size());
        Item pivot = null;
        // Walk through the queue to find the item at the given index.
        for (Item item : items) {
            if (pivotIndex == 0) {
                pivot = item;
                break;
            }
            pivotIndex--;
        }
        return pivot;
    }

    /**
     * Partitions the given unsorted queue by pivoting on the given item.
     *
     * @param unsorted  A Queue of unsorted items
     * @param pivot     The item to pivot on
     * @param less      An empty Queue. When the function completes, this queue will contain
     *                  all of the items in unsorted that are less than the given pivot.
     * @param equal     An empty Queue. When the function completes, this queue will contain
     *                  all of the items in unsorted that are equal to the given pivot.
     * @param greater   An empty Queue. When the function completes, this queue will contain
     *                  all of the items in unsorted that are greater than the given pivot.
     */
    private static <Item extends Comparable> void partition(
            Queue<Item> unsorted, Item pivot,
            Queue<Item> less, Queue<Item> equal, Queue<Item> greater) {
        // Your code here!
        for (Item elem : unsorted) {
            int sign = elem.compareTo(pivot);
            if (sign == 0) {
                equal.enqueue(elem);
            } else if (sign < 0) {
                less.enqueue(elem);
            } else {
                greater.enqueue(elem);
            }
        }
    }

    /** Returns a Queue that contains the given items sorted from least to greatest. */
    public static <Item extends Comparable> Queue<Item> quickSort(
            Queue<Item> items) {
        // Your code here!
        Queue<Item> itemQueue = new Queue<>();
        for (Item elem : items) {
            itemQueue.enqueue(elem);
        }
        if (items.size() <= 1) {
            return itemQueue;
        }
        Item pivot = getRandomItem(itemQueue);
        Queue<Item> less = new Queue<>(), equal = new Queue<>(), greater = new Queue<>();
        partition(itemQueue, pivot, less, equal, greater);
        less = quickSort(less);
        greater = quickSort(greater);
        Queue<Item> returnQueue = catenate(less, equal);
        returnQueue = catenate(returnQueue, greater);
        return returnQueue;
    }

    public static void main(String[] args) {
        Queue<String> students = new Queue<String>();
        students.enqueue("Alice");
        students.enqueue("Vanessa");
        students.enqueue("Ethan");
        students.enqueue("Jo");
        System.out.println(students);

        Queue<String> quickSortedStudents = quickSort(students);
        System.out.println(students);
        System.out.println(quickSortedStudents);
        Queue<Integer> queue = new Queue<>();
        int[] ints = new int[] {0, 1, 5, 4, 5, 2, 8, 6, 6, 5};
        for (int i : ints) {
            queue.enqueue(i);
        }
        System.out.println(queue);
        Queue<Integer> quickSortedQueue = quickSort(queue);
        System.out.println(quickSortedQueue);
    }
}
