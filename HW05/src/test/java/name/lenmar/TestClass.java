package name.lenmar;

import name.lenmar.annotations.After;
import name.lenmar.annotations.Before;
import name.lenmar.annotations.Test;

/**
 * Created by Lenmar Abdurayimov on 5/5/2017.
 */
public class TestClass {

    @Before
    public void before() {
        System.out.println("\t- running method annotated by \"@Before\"");
    }

    @After
    public void after() {
        System.out.println("\t- running method annotated by \"@After\"");
    }

    @Test
    public void testStringEquals1() {
        Assert.assertTrue("Strings must be equals", "Hello".equals("Hello"));
    }

    @Test
    public void testStringEquals2() {
        Assert.assertFalse("Strings haven't to be equals", "Hello".equals("World"));
    }

    @Test
    public void testStringEquals3() {
        Assert.assertNull("Strings haven't to be null", new String());
    }
}
