package http.handler;

import http.HttpEntity;
import http.HttpHeaders;
import http.HttpStatus;
import http.request.HttpRequest;
import http.response.HttpResponse;
import http.response.StatusLine;
import lombok.extern.slf4j.Slf4j;

import java.io.DataOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.URISyntaxException;

@Slf4j
public abstract class AbstractHandler implements Handler {

    @Override
    public HttpResponse getResponse(HttpRequest httpRequest) throws IOException, URISyntaxException {
        byte [] httpBody = getHttpBody(httpRequest.getPath());

        HttpResponse httpResponse = new HttpResponse(
            StatusLine.of(httpRequest.getProtocol(), HttpStatus.OK),
            new HttpEntity(getHttpHeaders(httpBody.length), new String(httpBody))
        );

        return httpResponse;
    }

    protected abstract HttpHeaders getHttpHeaders(int length);


    @Override
    public byte[] getHttpBody(String path) throws IOException, URISyntaxException {
        return new byte[0];
    }

    @Override
    public void writeResponse(OutputStream out, HttpResponse httpResponse) {
        try {
            DataOutputStream dos = new DataOutputStream(out);
            dos.writeBytes(httpResponse.getStatusLine() + NEW_LINE_STRING);

            if (httpResponse.hasHttpHeaders()) {
                dos.writeBytes(httpResponse.getHttpHeaderString());
                dos.writeBytes(NEW_LINE_STRING);
            }

            dos.writeBytes(NEW_LINE_STRING);

            if (httpResponse.hasHttpBody()) {
                log.debug("httpResponseBody: {}", httpResponse.getHttpBody());
                dos.write(httpResponse.getHttpBody().getBytes(), 0, httpResponse.getHttpBodyLength());
                dos.flush();
            }
            else {
                dos.flush();
            }
        }
        catch (Exception e) {
            log.error(e.getMessage());
        }
    }
}
