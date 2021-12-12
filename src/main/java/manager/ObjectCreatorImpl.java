package manager;

import context.InjectByType;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;

public class ObjectCreatorImpl implements ObjectCreator {

    @InjectByType
    private Outputter outputter;

    @Override
    public <T> T create(Constructor<T> constructor, Object[] args) {
        try {
            return constructor.newInstance(args);
        } catch (InstantiationException | IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
            outputter.println(e.getMessage(), PrintType.ERROR);
            throw new IllegalArgumentException(e.getMessage());
        }
    }
}
