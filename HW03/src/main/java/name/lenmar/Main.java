package name.lenmar;

import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

/**
 * Created by Lenmar Abdurayimov on 4/17/2017.
 */
public class Main {

    private static final int MIN_VALUE = 0;
    private static final int MAX_VALUE = 5;

    public static void main(String... args) {
        long startTime = System.nanoTime();

        Collection<Integer> source = new MyArrayList<>();
        Collection<Number>  dest   = new MyArrayList<>();

        for (int i = MIN_VALUE; i <= MAX_VALUE; i++) {
            source.add(i);
            dest.add(Math.random());
        }

        // Adding a few extra elements to 'dist' collection (for testing)
        dest.add(Math.random());
        dest.add(Math.random());

        System.out.println("Initial collection 'source':\n\t\t\t" + source);
        // size = 6, elements: [    0,     1,     2,     3,     4,     5]
        System.out.println("Initial collection 'dest':\n\t\t\t" + dest);
        // size = 8, elements: [ 0.41,  0.45,  0.16,  0.53,  0.40,  0.68,  0.33,  0.52]

        /*
         * Collections.addAll(Collection<? super T> c, T... elements)
         * Adds all of the specified elements to the specified collection.
         */
        Collections.addAll(dest, 5, 2, 7);
        System.out.println("Final collection 'dest' after adding elements:\n\t\t\t" + dest);
        // size = 11, elements: [ 0.41,  0.45,  0.16,  0.53,  0.40,  0.68,  0.33,  0.52,     5,     2,     7]

        /*
         * Collections.copy(List<? super T> dest, List<? extends T> src)
         * Copies all of the elements from one list into another.
         */
        Collections.copy((List) dest, (List) source);
        System.out.println("Final collection 'dest' after copying:\n\t\t\t" + dest);
        // size = 11, elements: [    0,     1,     2,     3,     4,     5,  0.33,  0.52,     5,     2,     7]

        /*
         * Collections.sort(List<T> list, Comparator<? super T> c)
         * Sorts the specified list according to the order induced by the specified comparator.
         */
        Collections.sort((List) source, Collections.reverseOrder());
        System.out.println("Final collection 'source' after sorting in reverse order:\n\t\t\t" + source);
        // size = 6, elements: [    5,     4,     3,     2,     1,     0]

        List<String> al = new MyArrayList<>();
        for (int i = MIN_VALUE; i <= MAX_VALUE; i++) {
            al.add(String.valueOf(i));
        }

        Collections.shuffle(al);
        System.out.println("Initial collection 'al' after shuffling:\n\t\t\t" + al);
        // size = 6, elements: [0, 4, 5, 2, 1, 3]
        Collections.sort(al);
        System.out.println("Final collection 'al' after sorting in direct order:\n\t\t\t" + al);
        // size = 6, elements: [0, 1, 2, 3, 4, 5]

        long finishTime = System.nanoTime();
        long timeNs = finishTime - startTime;
        System.out.println("\nTime spent: " + timeNs + "ns (" + timeNs / 1_000_000 + "ms)");
        // Time spent: 31160825ns (31ms)
    }
}