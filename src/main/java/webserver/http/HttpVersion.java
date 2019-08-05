package webserver.http;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Arrays;

@Getter
@AllArgsConstructor
public enum HttpVersion {

    HTTP_1_1("HTTP/1.1"),
    NONE("")
    ;

    private final String version;

    public static HttpVersion valueOfVersion(final String version) {
        return Arrays.stream(values())
                .filter(value -> value.version.equals(version))
                .findFirst()
                .orElse(NONE);
    }
}
