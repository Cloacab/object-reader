import context.Application;
import context.ApplicationContext;
import lombok.SneakyThrows;
import manager.ObjectReader;
import manager.ObjectReaderImpl;
import manager.Outputter;
import testObject.SpaceMarine;

import java.io.FileInputStream;
import java.util.HashMap;

public class Main {
    @SneakyThrows
    public static void main(String[] args) {
        ApplicationContext context = Application.run();
        ObjectReader reader = context.getObject(ObjectReader.class);
        Outputter outputter = context.getObject(Outputter.class);
        outputter.setVerbose(false);
        reader.setInputStream(new FileInputStream("item.txt"));
//        ObjectReaderImpl.INTERACTIVE_MODE = false;
        Object o = reader.readObject(SpaceMarine.class);
        System.out.println(o);
    }
}
