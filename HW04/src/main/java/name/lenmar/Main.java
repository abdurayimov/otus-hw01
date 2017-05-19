package name.lenmar;

import java.lang.management.GarbageCollectorMXBean;
import java.lang.management.ManagementFactory;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Created by Lenmar Abdurayimov on 5/2/2017.
 */

/*
    -agentlib:jdwp=transport=dt_socket,address=14000,server=y,suspend=n
    -Xms512m
    -Xmx512m
    -XX:MaxMetaspaceSize=256m
    -XX:+UseConcMarkSweepGC
    -XX:+CMSParallelRemarkEnabled
    -XX:+UseCMSInitiatingOccupancyOnly
    -XX:CMSInitiatingOccupancyFraction=70
    -XX:+ScavengeBeforeFullGC
    -XX:+CMSScavengeBeforeRemark
    -XX:+UseParNewGC
    -verbose:gc
    -Xloggc:./logs/gc_pid_%p.log
    -XX:+PrintGCDateStamps
    -XX:+PrintGCDetails
    -XX:+UseGCLogFileRotation
    -XX:NumberOfGCLogFiles=10
    -XX:GCLogFileSize=1M
    -Dcom.sun.management.jmxremote.port=15000
    -Dcom.sun.management.jmxremote.authenticate=false
    -Dcom.sun.management.jmxremote.ssl=false
    -XX:+HeapDumpOnOutOfMemoryError
    -XX:HeapDumpPath=./dumps/
    -XX:OnOutOfMemoryError="kill -3 %p"

    jps -- list VMs
    jstack <pid> >> threaddumps.log -- get dump from pid
    jinfo -- list VM parameters
    jhat / jvisualvm -- analyze heap dump
*/

public class Main {

    private static final int BOUND = 10_000_000;
    private static final int DELAY = 5_000;

    public static void main(String[] args) throws InterruptedException {
        System.out.println("Starting pid: " + ManagementFactory.getRuntimeMXBean().getName());

        GCMonitor.registerGCMonitor();

        List<Integer> list = new ArrayList<>();
        Random rand = new Random();

        try {
            for (int i = 1; i < Integer.MAX_VALUE; i++) {
                list.add(rand.nextInt(BOUND));

                if ((i % 2) == 0) {
                    list.remove(list.size() - 1);
                }

                if ((i % BOUND) == 0) {
                    Thread.sleep(DELAY);

                    System.out.println("\n" + i + " objects are added to list");
                    System.out.println(list.size() + " objects left after removing");
                }
            }
        }
        finally {
            //printGCStats();
        }
    }

    private static void printGCStats() {
        long totalGarbageCollections = 0L;
        long garbageCollectionTime   = 0L;

        List<GarbageCollectorMXBean> gcMXBeans = ManagementFactory.getGarbageCollectorMXBeans();
        for(GarbageCollectorMXBean gc : gcMXBeans) {
            long count = gc.getCollectionCount();
            totalGarbageCollections += (count >= 0) ? count : 0L;

            long time = gc.getCollectionTime();
            garbageCollectionTime += (time >= 0) ? time : 0L;
        }

        System.out.println("Total Garbage Collections: " + totalGarbageCollections);
        System.out.println("Total Garbage Collection Time (ms): " + garbageCollectionTime);
        System.out.println();
        System.out.println("Minor GC count: " + gcMXBeans.get(0).getCollectionCount());
        System.out.println("Minor GC Time (ms): " + gcMXBeans.get(0).getCollectionTime());
        System.out.println("Major GC count: " + gcMXBeans.get(1).getCollectionCount());
        System.out.println("Major GC Time (ms): " + gcMXBeans.get(1).getCollectionTime());
    }
}