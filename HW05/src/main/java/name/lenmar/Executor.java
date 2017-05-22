package name.lenmar;

import name.lenmar.annotations.After;
import name.lenmar.annotations.Before;
import name.lenmar.annotations.Test;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Lenmar Abdurayimov on 5/5/2017.
 */
public class Executor {

    private Class<?> clazz;

    private List<Method> beforeMethods = new ArrayList<>();
    private List<Method> afterMethods = new ArrayList<>();
    private List<Method> testMethods = new ArrayList<>();

    public Executor(Class<?> clazz) {
        this.clazz = clazz;
        initialise();
    }

    private void initialise() {
        beforeMethods = ReflectionHelper.getMethodsAnnotatedWith(clazz, Before.class);
        afterMethods = ReflectionHelper.getMethodsAnnotatedWith(clazz, After.class);
        testMethods = ReflectionHelper.getMethodsAnnotatedWith(clazz, Test.class);
    }

    public void run() {
        System.out.println("\nRunning " + clazz.getName());

        annotatedMethodRunTest();
    }

    private void annotatedMethodRunTest() {
        for (Method beforeMethod : beforeMethods) {
            ReflectionHelper.callMethod(ReflectionHelper.instantiate(clazz), beforeMethod.getName());
        }
        for (Method afterMethod : afterMethods) {
            ReflectionHelper.callMethod(ReflectionHelper.instantiate(clazz), afterMethod.getName());
        }
        for (Method testMethod : testMethods) {
            System.out.println("\t- running method annotated by \"@Test\" (" + testMethod.getName() + ")");
            ReflectionHelper.callMethod(ReflectionHelper.instantiate(clazz), testMethod.getName());
        }
    }
}
