package manager;

import java.io.PrintStream;

public enum PrintType {
    ERROR(System.err),
    INFO(System.out);

    public PrintStream type;

    PrintType(PrintStream type) {
        this.type = type;
    }
}
