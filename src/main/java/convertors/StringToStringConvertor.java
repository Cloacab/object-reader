package convertors;

public class StringToStringConvertor extends Convertor<String> {
    @Override
    public String convert(String value, Class<String> type) {
        if (value.trim().isEmpty()) {
            return null;
        }
        return value;
    }
}
