package webserver.http;

public class HttpProtocolFixture {

    public static final String PROTOCOL = "HTTP";
    public static final String VERSION = "1.1";

    public static String ofProtocol(){
        return String.join("/", PROTOCOL, VERSION);
    }
}
