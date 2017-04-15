package name.lenmar;

import java.util.function.Supplier;

/*
 * Created by Lenmar Abdurayimov 2017/04/14
 */
public final class ObjectFactory {

    private int size;
    private int count;
    private int delay;

    public ObjectFactory(int size, int count, int delay) {
        this.size  = size;
        this.count = count;
        this.delay = delay;
    }

    public <T> int getObjectSize(Supplier<T> supplier) throws InterruptedException {
        Runtime runtime = Runtime.getRuntime();

        double sum = 0.0d;
        for (int i = 0; i < count; i++) {
            Object[] array = new Object[size];

            for (int j = 0; j < size; j++) {
                array[j] = supplier.get();
            }
            long before = runtime.freeMemory();

            System.gc();
            Thread.sleep(delay);

            long after = runtime.freeMemory();

            if (after <= before)
                System.out.println("Memory size didn't changed after GC!");

            sum += (double)Math.abs((before - after)) / size;
        }
        return (int)(sum / count);
    }

}