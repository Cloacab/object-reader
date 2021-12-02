package rules;

public class XCoordinateRule extends Rule{
    public XCoordinateRule() {
        description = "x value mast be less than 411.";
    }
    @Override
    public boolean validateRule(String value) {
        long tmp = Long.parseLong(value);
        return tmp < 411;
    }
}
