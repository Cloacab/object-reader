package context;

import java.util.Map;

public class Application {
    public static ApplicationContext run(String packageToScan, Map<Class, Class> ifc2ImplMap) {
        return run(new JavaConfig(packageToScan, ifc2ImplMap));
        //todo: init all non lazy singletons
//        Set<Class<?>> classes = config.getScanner().getSubTypesOf(Object.class);
//        for (Class<?> aClass : classes) {
//            if (!aClass.isAnnotationPresent(Lazy.class) && aClass.isAnnotationPresent(Singleton.class)) {
//                context.getObject(aClass);
//            }
//        }
    }

    public static ApplicationContext run(Config config) {
        ApplicationContext context = new ApplicationContext(config);
        ObjectFactory factory = new ObjectFactory(context);
        context.setFactory(factory);
        return context;
    }

    public static ApplicationContext run(String packageToScan) {
        return run(packageToScan, null );
    }

    public static ApplicationContext run() {
        return run("", null);
    }
}
