package webserver.http.header;

import webserver.http.header.type.EntityHeader;
import webserver.http.header.type.GeneralHeader;
import webserver.http.header.type.RequestHeader;
import webserver.http.header.type.ResponseHeader;

import java.util.Arrays;
import java.util.stream.Stream;

public interface HeaderKey {
    static HeaderKey valueOfKey(String keyString) {
        HeaderKey[] headerKeys = Stream.of(
                        GeneralHeader.values(), RequestHeader.values(), ResponseHeader.values(), EntityHeader.values())
                .flatMap(Stream::of).toArray(HeaderKey[]::new);
        return Arrays.stream(headerKeys)
                .filter(key -> key.getHeaderKey().equals(keyString))
                .findAny()
                .orElseThrow(() -> new IllegalArgumentException(String.format("전달받은 header key 는 존재하지 않는 헤더의 키 값 입니다. 전달 받은 key = %s", keyString)));
    }

    String getHeaderKey();
}
