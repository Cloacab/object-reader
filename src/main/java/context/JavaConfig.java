package context;

import lombok.Getter;
import lombok.SneakyThrows;
import org.reflections.Reflections;
import org.reflections.scanners.SubTypesScanner;

import java.util.Map;
import java.util.Set;

public class JavaConfig implements Config {

    @Getter
    private final Reflections scanner;
    private final Map<Class, Class> ifc2ImplClass;

    public JavaConfig(String packageToScan, Map<Class, Class> ifc2ImplClass) {
        this.scanner = new Reflections(packageToScan, new SubTypesScanner(false));
        this.ifc2ImplClass = ifc2ImplClass;
    }

    @SneakyThrows
    @Override
    public <T> Class<? extends T> getImplClass(Class<T> ifc) {
        return ifc2ImplClass.computeIfAbsent(ifc, aClass -> {
            Set<Class<? extends T>> types = scanner.getSubTypesOf(ifc);
            System.out.println(types);
            if (types.size()!=1) {
                throw new RuntimeException("Ifc should have only 1 implementation, got: " + types.size() + ". (working on that)");
            }
            return types.iterator().next();
        });
    }
}
