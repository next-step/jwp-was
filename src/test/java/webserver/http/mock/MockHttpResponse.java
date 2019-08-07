package webserver.http.mock;

import webserver.http.HttpResponse;

import java.io.DataOutputStream;

public class MockHttpResponse extends HttpResponse {
    public MockHttpResponse() {
        super(null);
    }

    public MockHttpResponse(DataOutputStream dataOutputStream) {
        super(dataOutputStream);
    }
}
