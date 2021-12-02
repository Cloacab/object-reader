package convertors;

public class StringToDoubleConvertor extends Convertor<Double> {
    @Override
    public Double convert(String value, Class<Double> type) {
        return Double.valueOf(value);
    }
}
