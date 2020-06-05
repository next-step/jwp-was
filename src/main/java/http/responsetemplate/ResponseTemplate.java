package http.responsetemplate;

import http.HttpResponse;

import java.io.DataOutputStream;
import java.io.IOException;
import java.util.Map;

public abstract class ResponseTemplate {
    private static final String SET_COOKIE_FORMAT = "Set-Cookie: %s=%s; Path=%s \r\n";

    public void write(final HttpResponse httpResponse, final DataOutputStream dataOutputStream) {
        try {
            writeResponseHeader(httpResponse, dataOutputStream);
            writeCookies(httpResponse, dataOutputStream);
            writeHeader(httpResponse, dataOutputStream);
            writeBody(httpResponse, dataOutputStream);
            flush(dataOutputStream);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void flush(final DataOutputStream dataOutputStream) throws IOException {
        dataOutputStream.flush();
    }

    protected abstract void writeResponseHeader(final HttpResponse httpResponse,
                                                final DataOutputStream dataOutputStream) throws IOException;
    protected abstract void writeBody(final HttpResponse httpResponse,
                                      final DataOutputStream dataOutputStream) throws IOException;
    protected abstract void writeHeader(final HttpResponse httpResponse,
                                        final DataOutputStream dataOutputStream) throws IOException;

    protected void writeCookies(final HttpResponse httpResponse, final DataOutputStream dataOutputStream) {
        Map<String, String> cookies = httpResponse.getCookies();

        cookies.keySet()
                .forEach(key -> setCookie(dataOutputStream, cookies, key));
    }

    private void setCookie(final DataOutputStream dataOutputStream,
                           final Map<String, String> cookies,
                           final String key) {
        try {
            dataOutputStream.writeBytes(String.format(SET_COOKIE_FORMAT, key, cookies.get(key), "/"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
