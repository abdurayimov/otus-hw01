package name.lenmar;

import java.lang.management.ManagementFactory;

//VM options -Xmx1024m -Xms1024m
public class Main {

    private static final int COUNT = 5;
    private static final int SIZE  = 5 * 1024 * 1024;
    private static final int DELAY = 5 * 20;

    public static void main(String[] args) throws InterruptedException {
        System.out.println("Starting pid: " + ManagementFactory.getRuntimeMXBean().getName());

        double sum = 0.0d;
        for (int i = 0; i < COUNT; i++) {
            // Get amount of free memory within the heap in bytes. This size will increase
            // after garbage collection and decrease as new objects are created.
            long memoryFreeSize1 = Runtime.getRuntime().freeMemory();

            Object[] array = new Object[SIZE];
            for (int j = 0; j < SIZE; j++) {
                array[j] = new String("");
                //array[j] = new int[0];
                //array[j] = new Object();
                //array[j] = 4L;
            }

            long memoryFreeSize2 = Runtime.getRuntime().freeMemory();

            //If used a reference data type, we should subtract a reference size
            //else if udes a primitive dtata type, then we need delete 4!!!
            sum += (double)(memoryFreeSize1 - memoryFreeSize2) / SIZE - 4; // - 4;

            System.gc();
            Thread.sleep(DELAY);
        }

        System.out.printf("%-20s \t %5d", "Size of String:", (int)(sum / COUNT));
        //System.out.printf("%-20s \t %5d", "Size of int array:", (int)(sum / COUNT));
        //System.out.printf("%-20s \t %5d", "Size of Object:", (int)(sum / COUNT));
        //System.out.printf("%-20s \t %5d", "Size of long:", (int)(sum / COUNT));
    }
}