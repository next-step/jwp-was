package webserver.http.response;

import exception.AlreadyExistsException;
import lombok.NoArgsConstructor;

import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

@NoArgsConstructor
public class ResponseHeader {

    private static final String CRLF = "\r\n";
    private final Map<ResponseHeaderFields, String> responseHeaders = new HashMap<>();

    public String get(ResponseHeaderFields field) {
        return responseHeaders.getOrDefault(field, "");
    }

    public void put(ResponseHeaderFields key, String value) {
        if (responseHeaders.containsKey(key)) {
            throw new AlreadyExistsException(String.format("이미 존재하는 필드 입니다. 입력값 : %s", key.getName()));
        }

        responseHeaders.put(key, value);
    }

    @Override
    public String toString() {
        return responseHeaders.entrySet()
                .stream()
                .map(header -> header.getKey().getName() + ": " + header.getValue())
                .collect(Collectors.joining(CRLF));
    }
}
