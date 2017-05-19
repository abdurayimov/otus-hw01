package name.lenmar;

import com.sun.management.GarbageCollectionNotificationInfo;
import com.sun.management.GcInfo;

import javax.management.Notification;
import javax.management.NotificationEmitter;
import javax.management.NotificationListener;
import javax.management.openmbean.CompositeData;
import java.lang.management.GarbageCollectorMXBean;
import java.lang.management.ManagementFactory;
import java.lang.management.MemoryUsage;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * Created by Lenmar Abdurayimov on 5/2/2017.
 */
public class GCMonitor {

    private static final long JVM_START_TIME = ManagementFactory.getRuntimeMXBean().getStartTime();
    private static DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");
    private static final long ONE_BYTE = 1 << 10;

    private static GcType gcType = GcType.UNKNOWN;
    private static int majorGCcount = 0;

    public static void registerGCMonitor() {
        List<GarbageCollectorMXBean> gcMXBeans = ManagementFactory.getGarbageCollectorMXBeans();
        for (final GarbageCollectorMXBean gcbean : gcMXBeans) {
            System.out.println("register " + gcbean.getName() + " for " + Arrays.deepToString(gcbean.getMemoryPoolNames()));

            NotificationEmitter emitter = (NotificationEmitter) gcbean;
            NotificationListener listener = new NotificationListener() {
                long totalGcDuration = 0;

                @Override
                public void handleNotification(Notification notification, Object handback) {
                    HandBack hb = (HandBack) handback;
                    GarbageCollectionNotificationInfo info = GarbageCollectionNotificationInfo.from((CompositeData) notification.getUserData());
                    GcInfo gcInfo = info.getGcInfo();
                    if ("end of minor GC".equals(info.getGcAction())) {
                        gcType = GcType.YOUNG;
                    } else if ("end of major GC".equals(info.getGcAction())) {
                        gcType = GcType.OLD;
                        majorGCcount++;
                    }
                    StringBuilder gcGlance = new StringBuilder();
                    gcGlance.append(gcType.name()).append(" (").append(gcType.getGcType()).append(")").append(": - ").append(gcInfo.getId());
                    gcGlance.append(" (").append(info.getGcCause()).append(") ");
                    gcGlance.append("start: ")
                            .append(dateFormat.format(new Date(JVM_START_TIME + gcInfo.getStartTime())));
                    gcGlance.append(", end: ")
                            .append(dateFormat.format(new Date(JVM_START_TIME + gcInfo.getEndTime())));
                    System.out.println(gcGlance.toString());

                    //memory info
                    Map<String, MemoryUsage> beforeUsageMap = gcInfo.getMemoryUsageBeforeGc();
                    Map<String, MemoryUsage> afterUsageMap = gcInfo.getMemoryUsageAfterGc();
                    for (Map.Entry<String, MemoryUsage> entry : afterUsageMap.entrySet()) {
                        String name = entry.getKey();
                        MemoryUsage afterUsage = entry.getValue();
                        MemoryUsage beforeUsage = beforeUsageMap.get(name);

                        StringBuilder usage = new StringBuilder();
                        usage.append("\t[").append(name).append("] ");
                        usage.append("init:").append(afterUsage.getInit() / ONE_BYTE).append("K; ");
                        usage.append("used:").append(hb.handUsage(beforeUsage.getUsed(), afterUsage.getUsed(), beforeUsage.getMax())).append("; ");
                        usage.append("committed: ").append(hb.handUsage(beforeUsage.getCommitted(), afterUsage.getCommitted(), beforeUsage.getMax()));

                        System.out.println(usage.toString());
                    }
                    totalGcDuration += gcInfo.getDuration();
                    //summary
                    long percent = (gcInfo.getEndTime() - totalGcDuration) * 1000L / gcInfo.getEndTime();
                    StringBuilder summary = new StringBuilder();
                    summary.append("duration:").append(gcInfo.getDuration()).append("ms");
                    summary.append(", throughput:").append((percent / 10)).append(".").append(percent % 10).append('%');
                    summary.append(", collection count:").append(gcbean.getCollectionCount());
                    summary.append(", collection time:").append(gcbean.getCollectionTime());
                    System.out.println(summary.toString());
                    System.out.println();
                }
            };
            emitter.addNotificationListener(listener, notification -> {
                //filter GC notification
                return notification.getType().equals(GarbageCollectionNotificationInfo.GARBAGE_COLLECTION_NOTIFICATION);
            }, HandBack.getInstance());
        }
    }

    private static class HandBack {
        private static HandBack instance = new HandBack();

        private HandBack() {}

        public static HandBack getInstance() {
            return instance;
        }

        public String handUsage(long before, long after, long max) {
            StringBuilder usage = new StringBuilder();

            if (max == -1) {
                usage.append("").append(before / ONE_BYTE).append("K -> ").append(after / ONE_BYTE).append("K)");
                return usage.toString();
            }

            long beforePercent = ((before * 1000L) / max);
            long afterPercent = ((after * 1000L) / max);

            usage.append(beforePercent / 10).append('.').append(beforePercent % 10).append("%(")
                    .append(before / ONE_BYTE).append("K) -> ").append(afterPercent / 10).append('.')
                    .append(afterPercent % 10).append("%(").append(after / ONE_BYTE).append("K)");
            return usage.toString();
        }
    }
}