package convertors;

public class StringToFloatConvertor extends Convertor<Float> {
    @Override
    public Float convert(String value, Class<Float> type) {
        return Float.valueOf(value);
    }
}
