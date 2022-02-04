package convertors;

public class StringToFloatConvertor extends Convertor<Float> {
    @Override
    public Float convert(String value) {
        return Float.valueOf(value);
    }
}
