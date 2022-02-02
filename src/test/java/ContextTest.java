import context.Application;
import context.ApplicationContext;
import org.junit.jupiter.api.Test;

public class ContextTest {
    @Test
    public void shouldRunContext() {
        ApplicationContext context = Application.run("");
    }

}
