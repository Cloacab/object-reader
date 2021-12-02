package annotations;

public class UserInputAnnotationProcessor implements AnnotationProcessor {
    @Override
    public boolean process(Object t, Object value) {
        return true;
    }
}
