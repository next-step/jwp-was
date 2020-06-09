package http.request.requestline.protocol;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.util.HashMap;
import java.util.Map;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class ProtocolSpecPool {

    private static final Map<String, ProtocolSpec> PROTOCOL_SPEC_POOL = new HashMap<>();

    public static ProtocolSpec find(String protocolSpecText) {
        if (PROTOCOL_SPEC_POOL.containsKey(protocolSpecText)) {
            return PROTOCOL_SPEC_POOL.get(protocolSpecText);
        }

        ProtocolSpec protocolSpec = new ProtocolSpec(protocolSpecText);
        PROTOCOL_SPEC_POOL.put(protocolSpecText, protocolSpec);
        return protocolSpec;
    }
}
