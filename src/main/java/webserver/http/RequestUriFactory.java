package webserver.http;

public class RequestUriFactory {

    private static final String PATH_SEPARATOR = "\\?";

    public static RequestUri parse(String line) {
        String[] split = line.split(PATH_SEPARATOR);

        if (!hasQueryParams(split)) {
            return new RequestUri(split[0]);
        }

        return new RequestUri(split[0], split[1]);
    }


    private static boolean hasQueryParams(String[] split) {
        return split.length > 1;
    }

}
