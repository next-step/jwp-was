package http;

public class PathAndQuerySpliter {

    public static PathAndQueryString split(String allRequestPath) {
        String[] values = allRequestPath.split("\\?");

        return new PathAndQueryString(values[0],values[1]);
    }
}
