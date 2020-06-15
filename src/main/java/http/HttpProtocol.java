package http;

import javax.annotation.Nonnull;

public class HttpProtocol implements Protocol {
    private final String protocol;
    private final String version;

    public HttpProtocol(String protocol, String version) {
        this.protocol = protocol;
        this.version = version;
    }

    @Nonnull
    public static HttpProtocol from(@Nonnull String httpProtocol) {
        String[] splitBySlash = httpProtocol.split("/");
        if (splitBySlash.length < 2) {
            return makeEmptyHttpProtocol();
        }

        return new HttpProtocol(splitBySlash[0], splitBySlash[1]);
    }

    private static HttpProtocol makeEmptyHttpProtocol() {
        return new EmptyHttpProtocol();
    }

    static class EmptyHttpProtocol extends HttpProtocol {
        private EmptyHttpProtocol() {
            super("", "");
        }
    }

    public String getProtocol() {
        return protocol;
    }

    public String getVersion() {
        return version;
    }
}
