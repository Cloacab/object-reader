import context.Application;
import context.ApplicationContext;
import lombok.SneakyThrows;
import manager.ObjectReader;
import manager.Outputter;
import testObject.SpaceMarine;

public class Main {
    @SneakyThrows
    public static void main(String[] args) {
        ApplicationContext context = Application.run();
        ObjectReader reader = context.getObject(ObjectReader.class);
        Outputter outputter = context.getObject(Outputter.class);
        outputter.setVerbose(true);
//        reader.setInputStream(new FileInputStream("item.txt"));
//        ObjectReaderImpl.INTERACTIVE_MODE = false;
        Object o = reader.readObject(SpaceMarine.class);
        System.out.println(o);
    }
}
