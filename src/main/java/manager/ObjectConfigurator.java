package manager;

import java.lang.reflect.Constructor;

public interface ObjectConfigurator<T> {
    T configure(Constructor<T> constructor, Object[] args);
}
