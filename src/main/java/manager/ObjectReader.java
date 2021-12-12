package manager;

import java.io.InputStream;

public interface ObjectReader {
    <T> T readObject(Class<T> type);
    void setInputStream(InputStream inputStream);
}
