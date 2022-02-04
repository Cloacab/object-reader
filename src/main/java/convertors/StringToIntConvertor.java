package convertors;

public class StringToIntConvertor extends Convertor<Integer> {
    @Override
    public Integer convert(String value) {
        return Integer.valueOf(value);
    }
}
