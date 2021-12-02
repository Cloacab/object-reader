package manager;

import java.lang.reflect.Constructor;

public interface ObjectCreatorIfc {
    <T> T create(Constructor<T> constructor, Object[] args);
}
