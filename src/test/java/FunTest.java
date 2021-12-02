import convertors.ConvertorFactory;
import convertors.Convertor;
import convertors.StringToFloatConvertor;
import lombok.SneakyThrows;
import manager.ObjectReader;
import manager.ObjectReaderImpl;
import org.junit.jupiter.api.Test;
import org.reflections.Reflections;
import testObject.SpaceMarine;
import testObject.Weapon;

import java.lang.reflect.Type;
import java.util.*;

public class FunTest {
    @Test
    public void test1() {
        String str = "200";
        Integer value;
        Class<?> clazz = Integer.class;
        Object cast = clazz.cast(str);
        System.out.println(cast);
    }

    @Test
    public void test2() {
        ConvertorFactory factory = new ConvertorFactory();
        Integer exactConvertor = factory.getExactConvertor(Integer.class, "34");
        System.out.println(exactConvertor);
    }

    @Test
    public void test3() {
        StringToFloatConvertor convertor = new StringToFloatConvertor();
        Class<? extends StringToFloatConvertor> aClass = convertor.getClass();
        Type[] type = convertor.getClass().getGenericInterfaces();

        Class<?>[] interfaces = convertor.getClass().getInterfaces();
        String name = type[0].getTypeName();
        System.out.println(aClass);
        System.out.println(Arrays.toString(type));
        System.out.println(name);
        System.out.println(Arrays.toString(interfaces));
    }

//    @Test
//    public void test4() {
//
//        Convertor<Weapon> convertor = new StringToEnumConvertor<>();
//        Weapon bolt_rifle = convertor.convert("BOLT_RIFLE", Weapon.class);
//        System.out.println(bolt_rifle);
//    }
//
//    @Test
//    public void test5() {
//        StringToEnumConvertor<Weapon> convertor1 = new StringToEnumConvertor<>();
//        Convertor<Float> convertor = ConvertorFactory.getConvertor(StringToFloatConvertor.class);
////        ConvertorFactory.getConvertor(Class<StringToFloatConvertor>);
//        Convertor<Weapon> convertor2 = ConvertorFactory.getConvertor((Class<StringToEnumConvertor<Weapon>>) convertor1.getClass());
//        System.out.println(convertor2.convert("BOLT_RIFLE", Weapon.class));
//    }

    @Test
    public void test6() {
//        Convertor converter = ConvertorFactory.getConverter(Weapon.class);
//        assert converter != null;
//        System.out.println(converter.getTypeOf());
    }

    @Test
    public void test7() {
        Reflections scanner = new Reflections("");
        Set<Class<? extends Enum>> types = scanner.getSubTypesOf(Enum.class);
        types.forEach(x -> {
            System.out.println(x);
        });
    }

    @Test
    public void test8() {
        ObjectReaderImpl reader = new ObjectReaderImpl();
//        reader.scanObject(SpaceMarine.class);
        Object o = reader.readObject(SpaceMarine.class);
        System.out.println(o);
    }

    @Test
    public void test9() {
        HashMap<Integer, Set<String>> map = new HashMap<>();
        map.computeIfAbsent(1, k-> new HashSet<>()).add("sdhi");
        map.computeIfAbsent(1, k -> new HashSet<>()).add("sdhi");
        System.out.println(map);
    }

    @Test
    @SneakyThrows
    public void test10() {
        Convertor o = (Convertor) Class.forName("convertors.StringToFloatConvertor").getDeclaredConstructor().newInstance();
        Float convert = (Float) o.convert("0.45", Float.class);
        System.out.println(convert);

        Reflections scanner = new Reflections("convertors");
        Set<Class<? extends Convertor>> types = scanner.getSubTypesOf(Convertor.class);
        System.out.println(types);
        Convertor convertor = types.iterator().next().getDeclaredConstructor().newInstance();
        System.out.println(convertor.getTypeOf());
        convert = (Float) convertor.convert("0.45", Float.class);
        System.out.println(convert);
    }

    @Test
    public void test11() {
        System.out.println(int.class.getGenericSuperclass());
    }

}
