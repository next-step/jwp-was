package webserver.http.response;

import webserver.http.HeaderKey;

public interface Response extends AutoCloseable {

    void addHeader(final String key, final String value);
    void addHeader(final HeaderKey key, final String value);
    void notFound();
    void ok(final String body);
    void ok(final byte[] body);
    void redirect(final String redirectPath);
}
