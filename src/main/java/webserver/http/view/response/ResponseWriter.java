package webserver.http.view.response;

import webserver.http.domain.Cookies;
import webserver.http.domain.Headers;
import webserver.http.domain.Protocol;
import webserver.http.domain.response.Response;
import webserver.http.domain.response.Status;
import webserver.http.domain.response.StatusCode;

import java.io.DataOutputStream;
import java.io.IOException;
import java.util.Objects;
import java.util.stream.Collectors;

public class ResponseWriter {

    public void write(DataOutputStream dos, Response response) {
        try {
            responseStartLine(dos, response.getStatus());
            responseHeaders(dos, response.getHeaders());
            responseCookies(dos, response.getAddedCookies());
            responseLineFeed(dos);
            responseBodyIfPresent(dos, response.getBody());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private void responseStartLine(DataOutputStream dos, Status status) throws IOException {
        Protocol protocol = status.getProtocol();
        String protocolMessage = String.format("%s/%s", protocol.getType(), protocol.getVersion());
        StatusCode statusCode = status.getStatusCode();

        String startLineMessage = String.format("%s %d %s\r\n",
                protocolMessage,
                statusCode.getCode(),
                statusCode.getMessage());

        dos.writeBytes(startLineMessage
        );
    }

    private void responseHeaders(DataOutputStream dos, Headers headers) throws IOException {
        String headerMessages = headers.getAllKeys().stream()
                .map(key -> String.format("%s: %s", key, headers.getValue(key)))
                .collect(Collectors.joining("\r\n"));
        if (!headerMessages.isEmpty()) {
            headerMessages += "\r\n";
        }

        dos.writeBytes(headerMessages);
    }

    private void responseCookies(DataOutputStream dos, Cookies addedCookies) throws IOException {
        String setCookieHeaderMessages = addedCookies.getCookies().stream()
                .map(cookie -> String.format("Set-Cookie: %s=%s; path=%s",
                                cookie.getName(),
                                cookie.getValue(),
                                cookie.getPath()
                        ))
                .collect(Collectors.joining("\r\n"));
        if (!setCookieHeaderMessages.isEmpty()) {
            setCookieHeaderMessages += "\r\n";
        }

        dos.writeBytes(setCookieHeaderMessages);
    }

    private void responseLineFeed(DataOutputStream dos) throws IOException {
        dos.writeBytes("\r\n");
    }

    private void responseBodyIfPresent(DataOutputStream dos, byte[] body) throws IOException {
        if (Objects.isNull(body)) {
            return;
        }
        dos.write(body, 0, body.length);
    }
}
