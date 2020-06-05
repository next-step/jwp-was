package http.requestline;

public class RequestUtils {
    public static String getUrl(String requestLine){
        return splitPath(extractPath(requestLine))[0];
    }

    public static String getQueries(String requestLine){
        return splitPath(extractPath(requestLine))[1];
    }

    public static String getProtocol(String requestLine){
        return splitProtocol(extractProtocol(requestLine))[0];
    }

    public static String getVersion(String requestLine){
        return splitProtocol(extractProtocol(requestLine))[1];
    }

    private static String[] splitProtocol(String protocol){
        return protocol.split("/");
    }

    private static String extractProtocol(String requestLine){
        return requestLine.split(" ")[2];
    }

    private static String[] splitPath(String path){
        return path.split("\\?");
    }

    private static String extractPath(String requestLine){
        return requestLine.split(" ")[1];
    }
}
