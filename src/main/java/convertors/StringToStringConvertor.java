package convertors;

public class StringToStringConvertor extends Convertor<String> {
    @Override
    public String convert(String value, Class<String> type) {
        if (value.isBlank() || value.isEmpty()) {
            return null;
        }
        return value;
    }
}
