package http.response;

import http.request.Protocol;

import java.util.HashMap;
import java.util.Map;

public abstract class HttpResponse_ {
    private final Protocol protocol;
    private final HttpResponseCode httpResponseCode;
    private final Map<String, String> headers;

    protected HttpResponse_(HttpResponseCode httpResponseCode, String version) {
        this.protocol = new Protocol("HTTP", version);
        this.httpResponseCode = httpResponseCode;
        headers = new HashMap<>();
    }

    public void addHeader(String name, String value) {
        headers.put(name, value);
    }

    protected String getResponseLine() {
        StringBuffer sb = new StringBuffer();
        sb.append(protocol.getProtocol());
        sb.append("/");
        sb.append(protocol.getVersion());
        sb.append(" ");
        sb.append(httpResponseCode.getCode());
        sb.append(" ");
        sb.append(httpResponseCode.toString());
        sb.append("\r\n");

        return sb.toString();
    }

    protected String getResponseHeaderOptions() {
        StringBuffer sb = new StringBuffer();

        headers.keySet()
                .stream()
                .forEach(key -> {
                    sb.append(key);
                    sb.append(": ");
                    sb.append(headers.get(key));
                    sb.append("\r\n");
                });
        return sb.toString();
    }

    public String getResponseHeader() {
        StringBuffer sb = new StringBuffer();
        sb.append(getResponseLine());
        sb.append(getResponseHeaderOptions());
        sb.append("\r\n");
        return sb.toString();
    }
}
