package name.lenmar;

/**
 * Created by Lenmar Abdurayimov on 5/2/2017.
 */
public enum GcType {
    /** Minor collection. */
    YOUNG("Minor GC"),

    /** Major collection. */
    OLD("Major GC"),

    /** Could not determine the collection type. */
    UNKNOWN("Unknown GC");

    private String gcType;

    GcType(String gcType) {
        this.gcType = gcType;
    }

    String getGcType() {
        return gcType;
    }
}
