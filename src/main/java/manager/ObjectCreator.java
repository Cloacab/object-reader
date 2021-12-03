package manager;

import java.lang.reflect.Constructor;

public interface ObjectCreator {
    <T> T create(Constructor<T> constructor, Object[] args);
}
