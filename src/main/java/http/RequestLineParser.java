package http;

import org.springframework.util.StringUtils;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
//
//public class RequestLineParser {
//
//    private RequestLineParser() {
//    }
//
//    @Nonnull
//    public static RequestLine parse(@Nullable String requestLine) {
//        if (StringUtils.isEmpty(requestLine)) {
//            return RequestLine.makeEmptyRequestLine();
//        }
//
//        String lines[] = requestLine.split("\\r?\\n");
//        String[] s = lines[0].split(" ");
//
//        String method = s[0];
//        String path = s[1];
//
//        String protocolAndVersion = s[2];
//        String[] split = protocolAndVersion.split("/");
//        String protocol = split[0];
//        String version = split[1];
//
//        return new RequestLine(method, path, protocol, version);
//    }
//}
