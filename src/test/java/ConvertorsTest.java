import convertors.*;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class ConvertorsTest {
    @Test
    public void stringToDoubleConvertorTest() {
        StringToDoubleConvertor stringToDoubleConvertor = new StringToDoubleConvertor();
        Double expected = 1.123;
        Double actual = stringToDoubleConvertor.convert("1.123", Double.class);
        Assertions.assertEquals(expected, actual);
    }

    @Test
    public void stringToDoubleConvertorTest2() {
        StringToDoubleConvertor stringToDoubleConvertor = new StringToDoubleConvertor();
        Double expected = 5d;
        Double actual = stringToDoubleConvertor.convert("5", Double.class);
        Assertions.assertEquals(expected, actual);
    }

    @Test
    public void stringToFloatConvertorTest() {
        StringToFloatConvertor stringToFloatConvertor = new StringToFloatConvertor();
        Float expected = 2.123f;
        Float actual = stringToFloatConvertor.convert("2.123", Float.class);
        Assertions.assertEquals(expected, actual);
    }

    @Test
    public void stringToFloatConvertorTest2() {
        StringToFloatConvertor stringToFloatConvertor = new StringToFloatConvertor();
        Float expected = 2f;
        Float actual = stringToFloatConvertor.convert("2", Float.class);
        Assertions.assertEquals(expected, actual);
    }

    @Test
    public void stringToIntConvertorTest() {
        StringToIntConvertor stringToIntConvertor = new StringToIntConvertor();
        Integer expected = 4;
        Integer actual = stringToIntConvertor.convert("4", Integer.class);
        Assertions.assertEquals(expected, actual);
    }

    @Test
    public void stringToLongConvertorTest() {
        StringToLongConvertor stringToLongConvertor = new StringToLongConvertor();
        Long expected = 6L;
        Long actual = stringToLongConvertor.convert("6", Long.class);
        Assertions.assertEquals(expected, actual);
    }

    @Test
    public void stringToStringConvertorTest() {
        StringToStringConvertor stringToStringConvertor = new StringToStringConvertor();
        String expected = "  df";
        String actual = stringToStringConvertor.convert("  df", String.class);
        Assertions.assertEquals(expected, actual);
    }
}
