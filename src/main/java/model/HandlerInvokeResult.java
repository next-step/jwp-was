package model;

public class HandlerInvokeResult {

    private final Class<?> clazz;
    private final Object result;

    public HandlerInvokeResult(Class<?> clazz, Object result) {
        this.clazz = clazz;
        this.result = result;
    }

    public Class<?> getClazz() {
        return clazz;
    }

    public Object getResult() {
        return result;
    }
}
