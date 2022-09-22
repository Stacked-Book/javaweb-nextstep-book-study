package core.ref;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Constructor;
import java.lang.reflect.Method;

public class Junit3TestRunner {

    private static final Logger logger = LoggerFactory.getLogger(Junit3TestRunner.class);
    @Test
    public void run() throws Exception {
        Class<Junit3Test> clazz = Junit3Test.class;

        /**
         * method.invoke(class.newInstance())는 deprecated기 때문에
         * 아래와 같이 직접 생성자를 생성해줘야 한다.
         */
        Constructor<Junit3Test> constructor = clazz.getConstructor();
        Junit3Test test = constructor.newInstance(null);

        Method[] methods = clazz.getMethods();

        for(Method m : methods) {
            if(m.getName().startsWith("test")) {
                m.invoke(test);
                logger.debug("메소드 이름 : {}", m.getName());
            }
        }

    }
}
