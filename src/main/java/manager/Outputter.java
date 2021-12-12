package manager;

public interface Outputter {
    void print(String message, PrintType type);
    void println(String message, PrintType type);
    void setVerbose(Boolean verbose);
    boolean isVerbose();
}
