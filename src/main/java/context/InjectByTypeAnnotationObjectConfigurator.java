package context;

import lombok.SneakyThrows;

import java.lang.reflect.Field;

public class InjectByTypeAnnotationObjectConfigurator implements ObjectConfigurator {
    @SneakyThrows
    @Override
    public void configure(Object t, ApplicationContext context) {
        for (Field declaredField : t.getClass().getDeclaredFields()) {
            if (declaredField.getAnnotation(InjectByType.class) != null) {
                declaredField.setAccessible(true);
                Object object = context.getObject(declaredField.getType());
                declaredField.set(t, object);
            }
        }
    }
}
