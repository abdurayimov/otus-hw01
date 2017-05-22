package name.lenmar;

/**
 * Created by Lenmar Abdurayimov on 5/5/2017.
 */
public class Assert {

    protected Assert() {}

    public static void fail(String message) {
        if (message == null) {
            throw new AssertionError();
        }
        throw new AssertionError(message);
    }

    public static void assertTrue(String message, boolean expression) {
        if (!expression) {
            fail(message);
        }
    }

    public static void assertFalse(String message, boolean expression) {
        assertTrue(message, !expression);
    }

    public static void assertNull(String message, Object object) {
        assertTrue(message, object != null);
    }
}
