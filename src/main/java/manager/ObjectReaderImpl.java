package manager;

import annotations.UserInput;
import context.InjectByType;
import context.Singleton;
import convertors.Convertor;
import convertors.ConvertorFactoryIfc;
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

    @InjectByType
    private ConvertorFactoryIfc factory;
    @InjectByType
    private ObjectCreatorIfc creator;

    public static boolean INTERACTIVE_MODE = true;
    public final static Map<Class<?>, Class<?>> PRIMITIVE_TO_WRAPPER = new HashMap<Class<?>, Class<?>>();

    static {
        PRIMITIVE_TO_WRAPPER.put(boolean.class, Boolean.class);
        PRIMITIVE_TO_WRAPPER.put(byte.class, Byte.class);
        PRIMITIVE_TO_WRAPPER.put(short.class, Short.class);
        PRIMITIVE_TO_WRAPPER.put(char.class, Character.class);
        PRIMITIVE_TO_WRAPPER.put(int.class, Integer.class);
        PRIMITIVE_TO_WRAPPER.put(long.class, Long.class);
        PRIMITIVE_TO_WRAPPER.put(float.class, Float.class);
        PRIMITIVE_TO_WRAPPER.put(double.class, Double.class);
    }

    @Setter
    private Scanner scanner = new Scanner(System.in);
    @Setter
    private InputStream inputStream;
    private final HashMap<Class<?>, Set<Field>> typeToArgTypesMap = new HashMap<>();

    public ObjectReaderImpl() {

    }

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
        String userInput;
        boolean flag = true;
        T castedUserInput = null;

        do {
            print(message);
            userInput = scanner.nextLine();

            try {
                if (validate(rules, userInput)) {
                    flag = false;
                }
            } catch (NumberFormatException e) {
                System.err.println(e.getMessage());
                flag = true;
            }
            if (flag) print("Seems like your input doesn't match rules, try again.\n");
            // cast user input
            try {
                castedUserInput = (T) cast(userInput, field, type);
            } catch (NotFoundEnumTypeException | TypeCastException e) {
                System.err.println(e.getMessage());
                flag = true;
            }

        } while (flag && !userInput.trim().equalsIgnoreCase("break"));
        return castedUserInput;
    }

    private void print(String message) {
        if (INTERACTIVE_MODE) {
            System.out.print(message);
        }
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
                .append(":")
                .append("\n");

        return message.toString();
    }

    private Object cast(String userInput, Field field, Class type) {
        if (type.isPrimitive()) {
            type = PRIMITIVE_TO_WRAPPER.get(type);
        }
        if (String.class.equals(type)) {
            return userInput;
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

//    @SneakyThrows
    private <Q extends Enum<Q>> Q fromStringToEnum(Object str, Class<Q> enm) {
        try {
            return (Q) Enum.valueOf(enm, str.toString());
        } catch (Exception e) {
            throw new NotFoundEnumTypeException(e.getMessage());
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
