package webserver.http.view.response;

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

    private static final String LINE_FEED_DELIMITER = "\r\n";
    private static final String RESPONSE_START_LINE_FORMAT = "%s %d %s" + LINE_FEED_DELIMITER;
    private static final String PROTOCOL_MESSAGE_FORMAT = "%s/%s";
    private static final String HEADER_FORMAT = "%s: %s";

    public void write(DataOutputStream dos, Response response) {
        try {
            responseStartLine(dos, response.getStatus());
            responseHeaders(dos, response.getHeaders());
            responseLineFeed(dos);
            responseBodyIfPresent(dos, response.getBody());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private void responseStartLine(DataOutputStream dos, Status status) throws IOException {
        Protocol protocol = status.getProtocol();
        String protocolMessage = String.format(PROTOCOL_MESSAGE_FORMAT, protocol.getType(), protocol.getVersion());
        StatusCode statusCode = status.getStatusCode();

        String startLineMessage = String.format(RESPONSE_START_LINE_FORMAT,
                protocolMessage,
                statusCode.getCode(),
                statusCode.getMessage());

        dos.writeBytes(startLineMessage
        );
    }

    private void responseHeaders(DataOutputStream dos, Headers headers) throws IOException {
        String headerMessages = headers.getAllKeys().stream()
                .map(key -> String.format(HEADER_FORMAT, key, headers.getValue(key)))
                .collect(Collectors.joining(LINE_FEED_DELIMITER));
        if (!headerMessages.isEmpty()) {
            headerMessages += LINE_FEED_DELIMITER;
        }

        dos.writeBytes(headerMessages);
    }

    private void responseLineFeed(DataOutputStream dos) throws IOException {
        dos.writeBytes(LINE_FEED_DELIMITER);
    }

    private void responseBodyIfPresent(DataOutputStream dos, byte[] body) throws IOException {
        if (Objects.isNull(body)) {
            return;
        }
        dos.write(body, 0, body.length);
    }
}
