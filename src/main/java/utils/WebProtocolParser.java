package utils;

import java.util.HashMap;
import java.util.Map;

public class WebProtocolParser {
    private static final Integer PROTOCOL_INDEX = 0;
    private static final Integer PROTOCOL_VERSION_INDEX = 1;
    private static final String VERSION_SEPARATOR = "/";

    public static Map<String, String> parse(String protocol) {
        String[] split = protocol.split(VERSION_SEPARATOR);

        HashMap<String, String> parsedProtocol = new HashMap<>();
        parsedProtocol.put("protocol", split[PROTOCOL_INDEX]);
        parsedProtocol.put("protocolVersion", split[PROTOCOL_VERSION_INDEX]);

        return parsedProtocol;
    }
}
