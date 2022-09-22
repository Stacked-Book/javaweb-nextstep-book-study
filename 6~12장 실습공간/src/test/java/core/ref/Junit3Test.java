package core.ref;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Junit3Test {

    private static final Logger logger = LoggerFactory.getLogger(Junit3Test.class);

    public void test1() throws Exception {
        // System.out.println("Running Test1");
        logger.debug("Running Test1");
    }

    public void test2() throws Exception {
        // System.out.println("Running Test2");
        logger.debug("Running Test2");
    }

    public void three() throws Exception {
        // System.out.println("Running Test3");
        logger.debug("Running Test3");
    }
}
