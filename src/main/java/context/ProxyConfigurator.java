package context;

public interface ProxyConfigurator {
    Object replaceProxyIfNeeded(Object t, Class implClass);
}
