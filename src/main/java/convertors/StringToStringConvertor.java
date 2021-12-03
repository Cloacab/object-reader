package convertors;

public class StringToStringConvertor extends Convertor<String> {
    @Override
    public String convert(String value, Class<String> type) {
        return value;
    }
}
