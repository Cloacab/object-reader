package manager;

import lombok.SneakyThrows;

import java.lang.reflect.Constructor;

public class ObjectConfiguratorImpl {

    @SneakyThrows
    public static <T> T configure(Constructor<T> constructor, Object[] args) {
        return constructor.newInstance(args);
    }
}
