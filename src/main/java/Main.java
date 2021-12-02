import annotations.RulesAnnotationProcessor;
import manager.ObjectReader;
import manager.ObjectReaderImpl;
import testObject.SpaceMarine;

public class Main {
    public static void main(String[] args) {
//        RulesAnnotationProcessor rulesAnnotationProcessor = new RulesAnnotationProcessor();
//        SpaceMarine spaceMarine = new SpaceMarine();
//        System.out.println("starting processing.");
//        rulesAnnotationProcessor.process(spaceMarine, "");
        ObjectReader reader = new ObjectReaderImpl();
        Object o = reader.readObject(SpaceMarine.class);
        System.out.println(o);
    }
}
