package webserver.http;

public class RequestFixture {

    public static final String PATH = "/users";
    public static final String PROTOCOL = HttpProtocolFixture.ofProtocol();
    public static final String QUERY_STRING = "userId=javajigi&password=password&name=JaeSung";


    public static String ofBadRequest(){
        return String.join(" ", HttpMethod.GET.name(), PATH);
    }

    public static String ofGetRequest(){
        return String.join(" ", HttpMethod.GET.name(), PATH, PROTOCOL);
    }

    public static String ofGetAndQueryStringRequest(){
        return String.join(" ", HttpMethod.GET.name(), String.join("?", PATH, QUERY_STRING), PROTOCOL);
    }

    public static String ofPostRequest(){
        return String.join(" ", HttpMethod.POST.name(), PATH, PROTOCOL);
    }

}
