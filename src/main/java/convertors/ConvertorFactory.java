package convertors;

public interface ConvertorFactory {
    Convertor getConvertor(Class<?> type);
    void addConvertor(Class<?> type, Convertor convertor);
}
