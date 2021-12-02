package rules;

public abstract class Rule {
    protected String description;
    public abstract boolean validateRule(String value);

    public String getDescription() {
        return description;
    }
}
