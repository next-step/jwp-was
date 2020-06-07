package http.request;

import utils.RequestLineUtils;

import java.util.function.Function;

public enum Protocol {
    PROTOCOL(requestLine -> RequestLineUtils.getProtocol(requestLine)),
    VERSION(requestLine -> RequestLineUtils.getVersion(requestLine));

    private Function<String, String> expression;

    Protocol(Function<String, String> expression) {
        this.expression = expression;
    }

    public static String getProtocol(String requestLine) {
        return PROTOCOL.expression
                .apply(requestLine);
    }

    public static String getVersion(String requestLine) {
        return VERSION.expression
                .apply(requestLine);
    }
}
