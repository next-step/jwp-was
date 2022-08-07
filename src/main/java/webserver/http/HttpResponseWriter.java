package webserver.http;

import org.springframework.http.HttpStatus;
import utils.FileIoUtils;

import java.io.DataOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.URISyntaxException;

public class HttpResponseWriter {
    private final DataOutputStream dos;

    public HttpResponseWriter(OutputStream out) {
        this.dos = new DataOutputStream(out);
    }

    public void write(HttpResponse httpResponse) {
        try {
            byte[] payload = getPayload(httpResponse);
            writeStatusLine(httpResponse.getStatusLine());
            writeHeaders(httpResponse.getHeaders());
            writeContext(payload);
            dos.flush();
        } catch (IOException | URISyntaxException e) {
            throw new IllegalStateException(e);
        }
    }

    private byte[] getPayload(HttpResponse httpResponse) throws IOException, URISyntaxException {
        if (httpResponse.isDynamic()) {
            final byte[] payload = HandlebarCompiler.compile(httpResponse.getPath(), httpResponse.getContent());
            httpResponse.setContentLength(payload.length);
            return payload;
        }

        final byte[] payload = getStaticPayload(httpResponse);
        httpResponse.setContentLength(payload.length);
        return payload;
    }

    private byte[] getStaticPayload(HttpResponse httpResponse) throws IOException, URISyntaxException {
        final byte[] payload = FileIoUtils.loadFileFromClasspath("./templates" + httpResponse.getPath());
        if (payload.length > 0) {
            return payload;
        }
        return FileIoUtils.loadFileFromClasspath("./static" + httpResponse.getPath());
    }

    private void writeStatusLine(StatusLine statusLine) throws IOException {
        final HttpProtocol httpProtocol = statusLine.getHttpProtocol();
        final HttpStatus httpStatus = statusLine.getHttpStatus();
        dos.writeBytes(String.format("%s/%s %d %s\r\n",
                httpProtocol.getProtocol(), httpProtocol.getVersion(), httpStatus.value(), httpStatus.getReasonPhrase()));
    }

    private void writeHeaders(Headers headers) {
        headers.getHeaders().forEach((key, value) -> {
            try {
                dos.writeBytes(String.format("%s: %s\r\n", key, value));
            } catch (IOException e) {
                throw new IllegalStateException(e);
            }
        });
    }

    private void writeContext(byte[] payload) throws IOException {
        dos.writeBytes("\r\n");
        dos.write(payload, 0, payload.length);
    }
}
