package manager;

import annotations.AnnotationProcessor;
import annotations.UserInput;
import convertors.Convertor;
import convertors.ConvertorFactory;
import lombok.Getter;
import lombok.Setter;
import lombok.SneakyThrows;
import model.CustomClass;
import rules.Rule;
import rules.Rules;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.util.*;

public class ObjectReaderImpl implements ObjectReader {

    public static boolean INTERACTIVE_MODE = true;

    @Setter
    private Scanner scanner = new Scanner(System.in);
    private ObjectConfigurator objectConfigurator;
    private AnnotationProcessor rulesAnnotationProcessor;
    private Object configuringObject;
    private final HashMap<String, Class<?>> nameToTypeMap = new HashMap<>();
    private final HashMap<Class<?>, Set<Field>> typeToArgTypesMap = new HashMap<>();
    @Getter
    private List<Field> filedsToRead = new ArrayList<>();

    public ObjectReaderImpl() {

    }

    @SneakyThrows
    @Override
    public Object readObject(Class<?> type) {
        if (!type.isAnnotationPresent(CustomClass.class)) throw new RuntimeException("Class is not annotated by CustomClass annotation.");
        // scan object structure and all of its subclasses marked with CustomClass annotation.
        scanObject(type);

        Object t = type.getDeclaredConstructor().newInstance();
        configuringObject = t;
        
        ArrayList<Object> args = new ArrayList<>();
        for (Field field : type.getDeclaredFields()) {
            if (field.isAnnotationPresent(UserInput.class)){
                nameToTypeMap.put(field.getName(), field.getType());
                args.add(readField(field, field.getType()));
            }
        }
//        objectConfigurator.configure(t, args.toArray());
        return t;
    }

    public void scanObject(Class<?> type) {
        if (!type.isAnnotationPresent(CustomClass.class)) return;

        for (Field field : type.getDeclaredFields()) {
            if (field.getType().isAnnotationPresent(CustomClass.class)){
                scanObject(field.getType());
            }
            if (field.isAnnotationPresent(UserInput.class)) {
                filedsToRead.add(field);
                typeToArgTypesMap.computeIfAbsent(type, k -> new LinkedHashSet<>()).add(field);
            }
        }

        System.out.println(typeToArgTypesMap);

        try {
            List<Class<?>> arguments = new ArrayList<>();
            typeToArgTypesMap.get(type).forEach(f -> arguments.add(f.getType()));
            Constructor<?> declaredConstructor = type.getDeclaredConstructor(arguments.toArray(new Class[0]));
        } catch (NoSuchMethodException e) {
            throw new RuntimeException("Your custom class should have constructor, that contains all 'UserInput' annotated fields as parameters. Problem occurs in class: " + type.getName());
        }
    }

    @SneakyThrows
    private <T> T readField(Field field, Class<T> type) {

//        getRules() done
//        scan() done
//        validate() done
//        cast() ??????????????

        T castedUserInput = null;
        if (field.getType().isAnnotationPresent(CustomClass.class)) {
            return (T) readObject(field.getType());
        }

        // get rules for field
        List<Rule> rules = getRules(field);
        // form message for console
        String message = formMessage(rules, field);


        // read field until it properly validates
        String userInput;
        boolean flag = false;

        do {
            if (INTERACTIVE_MODE) {
                System.out.print(message);
            }
            userInput = scanner.nextLine();

            if (validate(rules, userInput)) flag = false;
            // cast user input
            castedUserInput = (T) cast(userInput, field, type);
        } while(flag && !userInput.trim().equalsIgnoreCase("break"));
        return castedUserInput;
    }

    private String formMessage(List<Rule> rules, Field field) {
        StringBuilder ruleMessage = new StringBuilder();
        rules.forEach(r -> ruleMessage.append(r.getDescription()).append(" "));
        StringBuilder message = new StringBuilder();
        message.append("Enter ")
                .append(field.getName())
                .append(!rules.isEmpty() ? (" (Rules: " + ruleMessage + ")") : "")
                .append(field.getType().isEnum() ? (", (Possible variants: " + (Arrays.toString(field.getType().getEnumConstants()) + ")")) : "")
                .append(":")
                .append("\n");

        return message.toString();
    }

    private Object cast(String userInput, Field field, Class type) {
        if (String.class.equals(type)) {
            return userInput;
        }
        if (type.isEnum()) {
            return fromStringToEnum(userInput, type);
        } else {
            ConvertorFactory factory = new ConvertorFactory();
            Convertor<?> converter = factory.getConverter(type);
            return converter.convert(userInput, type);
        }
    }

    @SneakyThrows
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
