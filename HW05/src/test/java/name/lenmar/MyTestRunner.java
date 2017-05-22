package name.lenmar;

/**
 * Created by Lenmar Abdurayimov on 5/5/2017.
 */
public class MyTestRunner {

    private static Executor executor;

    public static void main(String... args) {
        Class[] classes = { TestClass.class };

        for (Class<?> clazz : classes) {
            executor = new Executor(clazz);
            executor.run();
        }
    }
}
