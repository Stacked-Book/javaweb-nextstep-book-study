package core.ref;

import next.model.Question;
import next.model.User;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.*;

public class ReflectionTest {
    private static final Logger logger = LoggerFactory.getLogger(ReflectionTest.class);

    @Test
    public void showClass() {
        Class<Question> clazz = Question.class;
        logger.debug(clazz.getName());

        Field[] fields = clazz.getDeclaredFields();
        for(Field f : fields) {
            logger.debug("필드 접근제어자 : {}", Modifier.toString(f.getModifiers()));
            logger.debug("필드 타입 : {}", f.getType());
            logger.debug("필드 이름 : {}", f.getName());
        }

        Method[] methods = clazz.getDeclaredMethods();
        for(Method m : methods) {
            logger.debug("메소드 이름 : {}", m.getName());
            logger.debug("메소드 리턴 타입 : {}", m.getReturnType());
            logger.debug("메소드 매개변수 : {}", (Object) m.getParameterTypes());
        }

        Constructor[] constructors = clazz.getConstructors();
        for(Constructor c : constructors) {
            logger.debug("생성자 이름 : {}", c.getName());
            logger.debug("생성자 접근제어자 : {}", Modifier.toString(c.getModifiers()));
            logger.debug("생성자 매개변수 : {}", c.getParameterTypes());
        }
    }
    
    @Test
    public void newInstanceWithConstructorArgs() {
        try {
            Class<User> clazz = User.class;
            Class[] parameterType = {String.class, String.class, String.class, String.class};
            Constructor<User> constructor = clazz.getConstructor(parameterType);
            User user = constructor.newInstance("kim", "1234", "김영하", "haha@gmail.com");
            logger.debug(clazz.getName());
            logger.debug(user.toString());

        } catch (NoSuchMethodException | InvocationTargetException | InstantiationException | IllegalAccessException e) {
            logger.error(e.getMessage());
        }

    }
    
    @Test
    public void privateFieldAccess() {
        try {
            Class<Student> clazz = Student.class;
            Constructor<Student> constructor = clazz.getConstructor();
            Student student = constructor.newInstance(null);

            Field field1 = clazz.getDeclaredField("name");
            field1.setAccessible(true);
            field1.set(student, "영하");

            logger.debug("수정한 이름 : {}", field1.get(student));

            Field field2 = clazz.getDeclaredField("age");
            field2.setAccessible(true);
            field2.setInt(student, 20);

            logger.debug("수정한 나이 : {}", field2.get(student));

        } catch (NoSuchMethodException | InvocationTargetException | InstantiationException
                 | NoSuchFieldException | IllegalAccessException e) {
            logger.error(e.getMessage());
        }
    }
}
