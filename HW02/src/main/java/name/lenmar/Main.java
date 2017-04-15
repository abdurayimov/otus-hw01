package name.lenmar;

import java.lang.management.ManagementFactory;

/*
 * Created by Lenmar Abdurayimov 2017/04/14
 */
//VM options -Xmx1024m -Xms1024m
public class Main {

    public static void main(String[] args) throws InterruptedException {
        System.out.println("Starting pid: " + ManagementFactory.getRuntimeMXBean().getName());

        ObjectFactory of = new ObjectFactory(10 * 1024 * 1024, 5, 2000);

        printResult("Size of empty string 1:", of.getObjectSize(() -> ""));                      //  4
        printResult("Size of empty string 2:", of.getObjectSize(() -> new String("")));  // 28
        printResult("Size of string \"a\":",   of.getObjectSize(() -> new String("a"))); // 28
        printResult("Size of Object:",         of.getObjectSize(() -> new Object()));            // 20
        printResult("Size of char[0]:",        of.getObjectSize(() -> new char[0]));             // 20
        printResult("Size of int[0]:",         of.getObjectSize(() -> new int[0]));              // 20
        printResult("Size of int[1]:",         of.getObjectSize(() -> new int[1]));              // 28
    }

    private static void printResult(String comment, int result) {
        System.out.printf("%-30s \t %5d %n", comment, result);
    }
}