package http.requestline.protocol;

import http.requestline.exception.IllegalRequestLineParsingException;
import lombok.Getter;
import org.springframework.util.StringUtils;

@Getter
public class ProtocolSpec {

    private static final String PROTOCOL_SPEC_DELIMITER = "/";
    private static final int TOKEN_SIZE = 2;

    private final Protocol protocol;
    private final ProtocolVersion version;

    ProtocolSpec(String protocolSpecText) {
        if (StringUtils.isEmpty(protocolSpecText)) {
            throw new IllegalRequestLineParsingException("Parameter for creating ProtocolSpec is Empty.");
        }

        String[] tokens = splitProtocolSpecText(protocolSpecText);

        this.protocol = Protocol.valueOf(tokens[0]);
        this.version = new ProtocolVersion(tokens[1]);
    }

    public String getVersion() {
        return version.getVersion();
    }

    private String[] splitProtocolSpecText(String protocolSpecText) {
        String[] tokens = protocolSpecText.split(PROTOCOL_SPEC_DELIMITER);
        if (tokens.length != TOKEN_SIZE) {
            throw new IllegalRequestLineParsingException();
        }
        return tokens;
    }
}
