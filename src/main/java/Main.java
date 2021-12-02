import context.Application;
import context.ApplicationContext;
import manager.ObjectReader;
import testObject.SpaceMarine;

import java.util.HashMap;

public class Main {
    public static void main(String[] args) {
        ApplicationContext context = Application.run("", new HashMap<>());
        ObjectReader reader = context.getObject(ObjectReader.class);
        Object o = reader.readObject(SpaceMarine.class);
        System.out.println(o);
    }
}
