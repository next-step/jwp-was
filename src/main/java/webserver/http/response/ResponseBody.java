package webserver.http.response;

import java.nio.charset.StandardCharsets;
import java.util.Arrays;

public class ResponseBody {
    private byte[] responseBody;

    public ResponseBody(byte[] responseBody) {
        this.responseBody = responseBody;
    }

    public ResponseBody(String value) {
        this.responseBody = value.getBytes(StandardCharsets.UTF_8);
    }

    public byte[] getResponseBody() {
        return responseBody;
    }

    public int getContentLength() {
        return responseBody.length;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ResponseBody that = (ResponseBody) o;
        return Arrays.equals(responseBody, that.responseBody);
    }

    @Override
    public int hashCode() {
        return Arrays.hashCode(responseBody);
    }

    @Override
    public String toString() {
        return "ResponseBody{" +
                "responseBody=" + Arrays.toString(responseBody) +
                '}';
    }
}
