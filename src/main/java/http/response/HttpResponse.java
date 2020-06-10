package http.response;

import http.request.HttpRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.io.DataOutputStream;
import java.io.IOException;
import java.io.OutputStream;

@Slf4j
@RequiredArgsConstructor
public class HttpResponse {

    private final DataOutputStream dos;
    private final HttpRequest httpRequest;
    private final HttpResponseMetaData metaData;

    public static HttpResponse of(OutputStream outputStream, HttpRequest httpRequest) {
        DataOutputStream dos = new DataOutputStream(outputStream);
        return new HttpResponse(dos, httpRequest, new HttpResponseMetaData());
    }

    public void updateResponseBodyContent(byte[] responseBody) {
        metaData.updateContentType(httpRequest.getMimeType(), responseBody.length);
        metaData.updateResponseBody(responseBody);
    }

    public void flush() throws IOException {
        metaData.writeResponseLine(dos, httpRequest.getProtocolSpec());
        metaData.writeResponseHeaders(dos);
        metaData.writeResponseBody(dos);
        dos.flush();
    }
}
