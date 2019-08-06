package webserver.http.response;

import webserver.http.HeaderName;

public interface Response extends AutoCloseable {

    void addHeader(final String key, final String value);
    void addHeader(final HeaderName key, final String value);
    void notFound();
    void ok(final String body);
    void ok(final byte[] body);
    void forward(final String forwardPath, final String contentType) throws Exception;
    void sendRedirect(final String redirectPath);
}
