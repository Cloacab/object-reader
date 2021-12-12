package manager;

import java.lang.reflect.Field;
// get rules and validate input
public interface Validator {
    boolean validate(String value, Field field);
}
