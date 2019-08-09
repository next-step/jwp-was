package webserver.http.stub;

import webserver.http.HttpResponse;

import java.io.DataOutputStream;

public class StubHttpResponse extends HttpResponse {
    public StubHttpResponse() {
        super(null);
    }

    public StubHttpResponse(DataOutputStream dataOutputStream) {
        super(dataOutputStream);
    }
}
