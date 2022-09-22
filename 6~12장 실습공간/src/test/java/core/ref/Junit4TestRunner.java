package core.ref;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Constructor;
import java.lang.reflect.Method;

public class Junit4TestRunner {

    private static final Logger logger = LoggerFactory.getLogger(Junit4TestRunner.class);

    @Test
    public void run() throws Exception {
        Class<Junit4Test> clazz = Junit4Test.class;
        Method[] methods = clazz.getMethods();

        Constructor<Junit4Test> constructor = clazz.getConstructor();
        Junit4Test test = constructor.newInstance(null);

        for(Method m : methods) {
            if(m.isAnnotationPresent(MyTest.class)) {
                m.invoke(test);
                logger.debug("메소드 이름 : {}", m.getName());
            }
        }
    }
}
