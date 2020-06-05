package http.requestline;

import java.util.function.Function;

public enum Protocol2 {
    PROTOCOL(requestLine -> RequestUtils.getProtocol(requestLine)),
    VERSION(requestLine -> RequestUtils.getVersion(requestLine));

    private Function<String, String> expression;

    Protocol2(Function<String, String> expression) {
        this.expression = expression;
    }

    public static String getProtocol(String requestLine) {
        return PROTOCOL.expression
                .apply(requestLine)
                .toUpperCase();
    }

    public static String getVersion(String requestLine) {
        return VERSION.expression
                .apply(requestLine);
    }
}
