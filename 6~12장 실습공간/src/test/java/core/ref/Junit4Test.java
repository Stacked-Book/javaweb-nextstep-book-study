package core.ref;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Junit4Test {

    private static final Logger logger = LoggerFactory.getLogger(Junit4Test.class);

    @MyTest
    public void one() throws Exception {
        // System.out.println("Running Test1");
        logger.debug("Running Test1");
    }

    @MyTest
    public void two() throws Exception {
        // System.out.println("Running Test2");
        logger.debug("Running Test2");
    }

    public void testThree() throws Exception {
        // System.out.println("Running Test3");
        logger.debug("Running Test3");
    }
}
