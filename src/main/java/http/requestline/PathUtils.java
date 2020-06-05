package http.requestline;

public class PathUtils {
    public static String getUrl(String requestLine){
        return splitPath(extractPath(requestLine))[0];
    }

    public static String getQueries(String requestLine){
        return splitPath(extractPath(requestLine))[1];
    }

    private static String[] splitPath(String path){
        return path.split("\\?");
    }

    private static String extractPath(String requestLine){
        return requestLine.split(" ")[1];
    }
}
