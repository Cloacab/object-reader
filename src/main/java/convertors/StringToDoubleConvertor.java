package convertors;

public class StringToDoubleConvertor extends Convertor<Double> {
    @Override
    public Double convert(String value) {
        return Double.valueOf(value);
    }
}
