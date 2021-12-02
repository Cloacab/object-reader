package annotations;

import rules.Rule;
import rules.Rules;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;

public class RulesAnnotationProcessor implements AnnotationProcessor {
    @Override
    public boolean process(Object t, Object value) {
        Field field = (Field) t;
        if (field.isAnnotationPresent(Rules.class)) {
            Rules annotation = field.getAnnotation(Rules.class);
            for (String s : annotation.value()) {
                try {
                    Class<?> aClass = Class.forName(s);
                    Object o = aClass.getDeclaredConstructor().newInstance();
                    Rule rule = (Rule) o;
                    field.setAccessible(true);
                    return rule.validateRule(value.toString());
                } catch (ClassNotFoundException | NoSuchMethodException e) {
                    System.err.println("No class was found.\n" + e.getMessage());
                    e.printStackTrace();
                } catch (InvocationTargetException e) {
                    e.printStackTrace();
                } catch (InstantiationException e) {
                    e.printStackTrace();
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                }
            }
        }
        return false;
    }
}
