package manager;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;

// get rules and validate input
public interface Validator {
    boolean validate(String value, Field field) throws ClassNotFoundException, InvocationTargetException, NoSuchMethodException, InstantiationException, IllegalAccessException;
}
