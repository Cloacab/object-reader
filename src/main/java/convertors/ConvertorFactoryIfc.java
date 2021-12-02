package convertors;

public interface ConvertorFactoryIfc {
    Convertor getConvertor(Class<?> type);
    void addConvertor(Class<?> type, Convertor convertor);
}
