import context.Application;
import context.ApplicationContext;
import lombok.SneakyThrows;
import manager.ObjectReader;
import testObject.SpaceMarine;

import java.io.FileInputStream;
import java.util.HashMap;

public class Main {
    @SneakyThrows
    public static void main(String[] args) {
        ApplicationContext context = Application.run("", new HashMap<>());
        ObjectReader reader = context.getObject(ObjectReader.class);
        reader.setInputStream(new FileInputStream("item.txt"));
        Object o = reader.readObject(SpaceMarine.class);
        System.out.println(o);
    }
}
