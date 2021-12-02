package convertors;

import lombok.Getter;

import java.lang.reflect.ParameterizedType;

public abstract class Convertor<T> {
    @Getter
    protected Class typeOf = null;

    public Convertor() {
        this.typeOf = (Class)
                ((ParameterizedType)getClass()
                        .getGenericSuperclass())
                        .getActualTypeArguments()[0];
    }

    public abstract T convert(String value, Class<T> type);
}
