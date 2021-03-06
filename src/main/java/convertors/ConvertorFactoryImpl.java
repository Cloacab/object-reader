package convertors;

import context.InjectProperty;
import context.PostConstruct;
import context.Singleton;
import lombok.Getter;
import lombok.SneakyThrows;
import org.reflections.Reflections;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

@Singleton
public class ConvertorFactoryImpl implements ConvertorFactory {

    @Getter
    private final Map<Class<?>, Convertor<?>> typeToConvertorMap = new HashMap<>();
    @InjectProperty
    private String packageToScan;

    @SneakyThrows
    public ConvertorFactoryImpl() {
//        Reflections scanner = new Reflections("convertors");
//        Set<Class<? extends Convertor>> types = scanner.getSubTypesOf(Convertor.class);
//        for (Class<? extends Convertor> next : types) {
//            Convertor convertor = next.getDeclaredConstructor().newInstance();
//            typeToConvertorMap.put(convertor.getTypeOf(), convertor);
//        }
    }

    @PostConstruct
    @SneakyThrows
    public void init() {
        Reflections scanner = new Reflections(packageToScan);
        Set<Class<? extends Convertor>> types = scanner.getSubTypesOf(Convertor.class);
        for (Class<? extends Convertor> next : types) {
            Convertor convertor = next.getDeclaredConstructor().newInstance();
            typeToConvertorMap.put(convertor.getTypeOf(), convertor);
        }
    }

    @Override
    public Convertor getConvertor(Class<?> type) {
        return typeToConvertorMap.getOrDefault(type, null);
    }

    @Override
    public void addConvertor(Class<?> type, Convertor convertor) {
        typeToConvertorMap.putIfAbsent(type, convertor);
    }
}
