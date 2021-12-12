package manager;

import context.Singleton;

@Singleton
public class OutputterImpl implements Outputter {
//    public static boolean INTERACTIVE_MODE = true;
    private boolean verbose = true;
    @Override
    public void print(String message, PrintType type) {
        if (verbose) {
            type.type.print(message);
        }
    }

    @Override
    public void println(String message, PrintType type) {
        print(message + '\n', type);
    }

    @Override
    public void setVerbose(Boolean verbose) {
        this.verbose = verbose;
    }

    @Override
    public boolean isVerbose() {
        return verbose;
    }
}
