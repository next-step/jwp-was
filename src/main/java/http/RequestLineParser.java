package http;

public class RequestLineParser {

    public static RequestLine parse(final String line) {
        String[] lines = line.split(" ");
        String method = lines[0];
        String path = lines[1];
        String protocol = lines[2];

        return RequestLine.ofGet(path, protocol);
    }
}
