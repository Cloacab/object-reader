package manager;

import lombok.SneakyThrows;
import rules.Rule;
import rules.Rules;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.util.*;

public class ValidatorImpl implements Validator {
    private final HashMap<Field, List<Rule>> fieldToRules = new HashMap<>();
    @Override
    public boolean validate(String value, Field field) throws ClassNotFoundException, InvocationTargetException, NoSuchMethodException, InstantiationException, IllegalAccessException {
        List<Rule> rules = getRules(field);
        boolean flag = false;
        for (Rule rule : rules) {
            if (!rule.validateRule(value)) {
                flag = true;
                break;
            }
        }
        return flag;
    }

    private List<Rule> getRules(Field field) throws ClassNotFoundException, NoSuchMethodException, InvocationTargetException, InstantiationException, IllegalAccessException {
        if (field.isAnnotationPresent(Rules.class)) {
            if (fieldToRules.containsKey(field)) {
                return fieldToRules.get(field);
            } else {
                List<Rule> result = new ArrayList<>();

                String[] rules = field.getAnnotation(Rules.class).value();
                for (String rule : rules) {
                    Rule ruleObject = (Rule) Class.forName(rule).getDeclaredConstructor().newInstance();
                    result.add(ruleObject);
                }
                fieldToRules.put(field, result);
                return result;
            }
        }
        return new ArrayList<>();
    }
}
