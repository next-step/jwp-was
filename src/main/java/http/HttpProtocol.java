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
            throw new IllegalArgumentException();
        }

        return new HttpProtocol(splitBySlash[0], splitBySlash[1]);
    }

    public String getProtocol() {
        return protocol;
    }

    public String getVersion() {
        return version;
    }
}
