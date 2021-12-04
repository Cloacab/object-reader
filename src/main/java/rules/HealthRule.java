package rules;

public class HealthRule extends Rule {
    public HealthRule() {
        this.description = "Health must be more than 0.";
    }
    @Override
    public boolean validateRule(String value) {
        long tmp = Long.parseLong(value);
        return tmp > 0;
    }
}
