package manager;

import annotations.UserInput;
import context.InjectByType;
import context.Singleton;
import convertors.Convertor;
import convertors.ConvertorFactory;
import lombok.Setter;
import lombok.SneakyThrows;
import model.CustomClass;
import rules.Rule;
import rules.Rules;

import java.io.InputStream;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.util.*;

@Singleton
public class ObjectReaderImpl implements ObjectReader {

    public static boolean INTERACTIVE_MODE = true;

    @InjectByType
    private ConvertorFactory factory;
    @InjectByType
    private ObjectCreator creator;

    @Setter
    private Scanner scanner = new Scanner(System.in);
    private final HashMap<Class<?>, Set<Field>> typeToArgTypesMap = new HashMap<>();

    @SneakyThrows
    @Override
    public Object readObject(Class<?> type) {
        if (!type.isAnnotationPresent(CustomClass.class))
            throw new RuntimeException("Class is not annotated by CustomClass annotation.");
        // scan object structure and all of its subclasses marked with CustomClass annotation.
        scanObject(type);

        ArrayList<Object> args = new ArrayList<>();
        for (Field field : type.getDeclaredFields()) {
            if (field.isAnnotationPresent(UserInput.class)) {
                args.add(readField(field, field.getType()));
            }
        }
        List<Class<?>> arguments = new ArrayList<>();
        typeToArgTypesMap.get(type).forEach(f -> arguments.add(f.getType()));
        Constructor<?> constructor = type.getDeclaredConstructor(arguments.toArray(new Class[0]));
        return creator.create(constructor, args.toArray());
    }

    @Override
    public void setInputStream(InputStream inputStream) {
        this.scanner = new Scanner(inputStream);
    }

    public void scanObject(Class<?> type) {
        if (!type.isAnnotationPresent(CustomClass.class)) return;

        for (Field field : type.getDeclaredFields()) {
            if (field.getType().isAnnotationPresent(CustomClass.class)) {
                scanObject(field.getType());
            }
            if (field.isAnnotationPresent(UserInput.class)) {
                typeToArgTypesMap.computeIfAbsent(type, k -> new LinkedHashSet<>()).add(field);
            }
        }

        try {
            List<Class<?>> arguments = new ArrayList<>();
            typeToArgTypesMap.get(type).forEach(f -> arguments.add(f.getType()));
            type.getDeclaredConstructor(arguments.toArray(new Class[0]));
        } catch (NoSuchMethodException e) {
            throw new RuntimeException("Your custom class should have constructor, that contains all 'UserInput' annotated fields as parameters. Problem occurs in class: " + type.getName());
        }
    }

//    @SneakyThrows
    private <T> T readField(Field field, Class<T> type) {

        if (field.getType().isAnnotationPresent(CustomClass.class)) {
            return (T) readObject(field.getType());
        }

        // get rules for field
        List<Rule> rules = getRules(field);
        // form message for console
        String message = formMessage(rules, field);
        // read field until it properly validates
        return processUserInput(field, type, rules, message);
    }

    private <T> T processUserInput(Field field, Class<T> type, List<Rule> rules, String message) {
        String userInput;
        boolean flag = true;
        T castedUserInput = null;

        do {
            println(message, PrintType.INFO);
            userInput = scanner.nextLine();

            try {
                if (validate(rules, userInput)) {
                    flag = false;
                }
            } catch (NumberFormatException e) {
                println(e.getMessage(), PrintType.ERROR);
                flag = true;
            }
            if (flag) {
                println("Seems like your input doesn't match rules, try again.", PrintType.INFO);
            }
            // cast user input
            try {
                castedUserInput = (T) cast(userInput, field, type);
            } catch (TypeCastException e) {
                println(e.getMessage(), PrintType.ERROR);
                flag = true;
            } catch (NotFoundEnumTypeException e) {
                if (!flag) return null;
            }
        } while (flag && !userInput.trim().equalsIgnoreCase("break"));

        return castedUserInput;
    }

    private void print(String message, PrintType type) {
        if (INTERACTIVE_MODE) {
            type.type.print(message);
        }
    }

    private void println(String message, PrintType type) {
        print(message + '\n', type);
    }

    private String formMessage(List<Rule> rules, Field field) {
        StringBuilder ruleMessage = new StringBuilder();
        rules.forEach(r -> ruleMessage.append(r.getDescription()).append(" "));
        StringBuilder message = new StringBuilder();
        message.append("Enter ")
                .append(field.getDeclaringClass().getSimpleName())
                .append(" ")
                .append(field.getName())
                .append(!rules.isEmpty() ? (" (Rules: " + ruleMessage + ")") : "")
                .append(field.getType().isEnum() ? (", (Possible variants: " + (Arrays.toString(field.getType().getEnumConstants()) + ")")) : "")
                .append(":");

        return message.toString();
    }

    private Object cast(String userInput, Field field, Class type) {
        if (type.isPrimitive()) {
            type = Wrapper.wrap(type);
        }

        if (type.isEnum()) {
            return fromStringToEnum(userInput, type);
        } else {
            Convertor<?> converter = factory.getConvertor(type);
            try{
                return converter.convert(userInput, type);
            } catch (Exception e) {
                throw new TypeCastException(String.format("Mismatching field type and input type. Must be: %s", field.getType().getSimpleName()));
            }
        }
    }

    private <T extends Enum<T>> T fromStringToEnum(String str, Class<T> enm) {
        try {
            return (T) Enum.valueOf(enm, str.toString());
        } catch (Exception e) {
            throw new NotFoundEnumTypeException(e.getMessage() + '\n');
        }
    }

    @SneakyThrows
    private List<Rule> getRules(Field field) {
        List<Rule> result = new ArrayList<>();
        if (field.isAnnotationPresent(Rules.class)) {
            String[] rules = field.getAnnotation(Rules.class).value();
            for (String rule : rules) {
                Rule ruleObject = (Rule) Class.forName(rule).getDeclaredConstructor().newInstance();
                result.add(ruleObject);
            }
        }
        return result;
    }

    private boolean validate(List<Rule> rules, String value) {
        boolean flag = true;
        for (Rule rule : rules) {
            if (!rule.validateRule(value)) {
                flag = false;
                break;
            }
        }
        return flag;
    }
}
