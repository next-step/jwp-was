package response;

import org.springframework.http.HttpStatus;
import utils.FileIoUtils;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.Arrays;
import java.util.Optional;
import java.util.function.Function;

public enum FileResponse {
    HTML(Constants.HTTP_SUFFIX, path -> new Response(HttpStatus.OK, getBody(path))),
    CSS(Constants.CSS_SUFFIX, path -> new Response(HttpStatus.OK, getBody(path)));

    private static class Constants { // 정적 멤버 클래스
        private static final String HTTP_SUFFIX = ".html";
        private static final String CSS_SUFFIX = ".css";
    }

    String suffix;
    Function<String, Response> responseFunction;

    FileResponse(String suffix, Function<String, Response> responseFunction) {
        this.suffix = suffix;
        this.responseFunction = responseFunction;
    }

    private static byte[] getBody(String path) {
        try {
            return FileIoUtils.loadFileFromClasspath(path);
        } catch (IOException | URISyntaxException e) {
            e.printStackTrace();
        }
        return new byte[0];
    }

    public static Optional<Response> getFileResponse(String requestPath) {
        return Arrays.stream(FileResponse.values())
                .filter(value -> requestPath.endsWith(value.suffix))
                .map(value -> value.responseFunction.apply(requestPath))
                .findAny();
    }
}
