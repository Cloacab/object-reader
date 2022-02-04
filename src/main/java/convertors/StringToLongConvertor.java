package convertors;

public class StringToLongConvertor extends Convertor<Long> {
    @Override
    public Long convert(String value) {
        return Long.valueOf(value);
    }
}
