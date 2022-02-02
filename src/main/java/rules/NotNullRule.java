package rules;

public class NotNullRule extends Rule {
    public NotNullRule() {
        this.description = "This field can't be null.";
    }
    @Override
    public boolean validateRule(String value) {
        return !value.trim().isEmpty();
    }
}
