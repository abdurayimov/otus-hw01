package name.lenmar;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Lenmar Abdurayimov on 5/2/2017.
 */
public final class GcHelper {

    private GcHelper() {}

    private static final Map<String, GcType> KNOWN_COLLECTOR_NAMES = knownCollectors();

    private static Map<String, GcType> knownCollectors() {
        Map<String, GcType> m = new HashMap<>();
        m.put("ConcurrentMarkSweep",  GcType.OLD);
        m.put("Copy",                 GcType.YOUNG);
        m.put("G1 Old Generation",    GcType.OLD);
        m.put("G1 Young Generation",  GcType.YOUNG);
        m.put("MarkSweepCompact",     GcType.OLD);
        m.put("PS MarkSweep",         GcType.OLD);
        m.put("PS Scavenge",          GcType.YOUNG);
        m.put("ParNew",               GcType.YOUNG);
        return Collections.unmodifiableMap(m);
    }

    /** Determine the type, old or young, based on the name of the collector. */
    static GcType getGcType(String name) {
        GcType t = KNOWN_COLLECTOR_NAMES.get(name);
        return (t == null) ? GcType.UNKNOWN : t;
    }

    /** Returns true if memory pool name matches an old generation pool. */
    static boolean isOldGenPool(String name) {
        return name.endsWith("Old Gen") || name.endsWith("Tenured Gen");
    }

    /** Returns true if memory pool name matches an young generation pool. */
    static boolean isYoungGenPool(String name) {
        return name.endsWith("Eden Space") || name.endsWith("Survivor Space");
    }

}
