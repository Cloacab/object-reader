package rules;

public class NullableRule extends Rule {
    public NullableRule() {
        this.description = "This field might be null.";
    }
    @Override
    public boolean validateRule(String value) {
        return true;
    }
}
