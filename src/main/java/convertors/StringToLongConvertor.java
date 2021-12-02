package convertors;

public class StringToLongConvertor extends Convertor<Long> {
    @Override
    public Long convert(String value, Class<Long> type) {
        return Long.valueOf(value);
    }
}
