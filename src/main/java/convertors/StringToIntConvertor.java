package convertors;

public class StringToIntConvertor extends Convertor<Integer> {
    @Override
    public Integer convert(String value, Class<Integer> type) {
        return Integer.valueOf(value);
    }
}
