package convertors;

import lombok.Getter;
import lombok.SneakyThrows;
import org.reflections.Reflections;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.*;
import java.util.function.Supplier;

public class ConvertorFactory {

    private final List<Convertor> convertors = new ArrayList<>();
    @Getter
    private final Map<Class, Convertor<?>> typeToConvertorMap = new HashMap<>();

    public ConvertorFactory() {
        Reflections scanner = new Reflections("convertors");
        Set<Class<? extends Convertor>> types = scanner.getSubTypesOf(Convertor.class);
        types.forEach(x -> {
            try {
                Convertor convertor = x.getDeclaredConstructor().newInstance();
                convertors.add(convertor);
                typeToConvertorMap.put(convertor.getTypeOf(), convertor);
            } catch (InstantiationException e) {
                e.printStackTrace();
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            } catch (InvocationTargetException e) {
                e.printStackTrace();
            } catch (NoSuchMethodException e) {
                e.printStackTrace();
            }
        });
    }

    public void configureFactory(Field[] fields) {
        Reflections scanner = new Reflections("convertors");
        Set<Class<? extends Convertor>> types = scanner.getSubTypesOf(Convertor.class);
        for (Field field : fields) {
            Class<?> type = field.getType();
            if (type.isEnum()) {
//                typeToConvertorMap.put(type, new StringToEnumConvertor<>());
            }
        }
    }

    @SneakyThrows
    public static <T> Convertor<T> getConvertor(Class<? extends Convertor<T>> type) {
        return type.getDeclaredConstructor().newInstance();
    }

    public Convertor create(Supplier<? extends Convertor> type) {
//        type.get().
        return null;
    }

    public static Convertor getConverter(Class<?> type) {
        if (type.isEnum()) {
            return getEnum(type);
        }
        return null;
    }

    private static <T extends Enum<T>> Convertor<T> getEnum(Class<?> type) {
        return new Convertor<T>() {
            @Override
            public T convert(String value, Class<T> type) {
                Enum.valueOf(type, value);
                return (T) Enum.valueOf(type, value);
            }
        };
    }

    public <T> T getExactConvertor(Class<T> targetType, String targetValue) {
//        Type
        for (Convertor convertor : convertors) {
            try {
                return (T) convertor.convert(targetValue, targetType);
            } catch (Exception ignored) {

            }
        }
        return null;
    }
}
