package manager;

import java.io.InputStream;

public interface ObjectReader {
    Object readObject(Class<?> type);
    void setInputStream(InputStream inputStream);
}
