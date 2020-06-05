package http.responsetemplate;

import http.HttpResponse;

import java.io.DataOutputStream;
import java.io.IOException;
import java.util.Map;

public abstract class ResponseTemplate {
    private static final String SET_COOKIE_FORMAT = "Set-Cookie: %s=%s; Path=%s \r\n";

    public void write(HttpResponse httpResponse, DataOutputStream dataOutputStream) {
        writeResponseHeader(httpResponse, dataOutputStream);
        writeCookies(httpResponse, dataOutputStream);
        writeHeader(httpResponse, dataOutputStream);
        writeBody(httpResponse, dataOutputStream);
        flush(dataOutputStream);
    }

    private void flush(DataOutputStream dataOutputStream) {
        try {
            dataOutputStream.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    protected abstract void writeResponseHeader(HttpResponse httpResponse, DataOutputStream dataOutputStream);
    protected abstract void writeBody(HttpResponse httpResponse, DataOutputStream dataOutputStream);
    protected abstract void writeHeader(HttpResponse httpResponse, DataOutputStream dataOutputStream);

    protected void writeCookies(HttpResponse httpResponse, DataOutputStream dataOutputStream) {
        Map<String, String> cookies = httpResponse.getCookies();

        cookies.keySet()
                .forEach(key -> setCookie(dataOutputStream, cookies, key));
    }

    private void setCookie(DataOutputStream dataOutputStream, Map<String, String> cookies, String key) {
        try {
            dataOutputStream.writeBytes(String.format(SET_COOKIE_FORMAT, key, cookies.get(key), "/"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
