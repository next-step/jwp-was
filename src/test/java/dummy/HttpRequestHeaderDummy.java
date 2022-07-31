package dummy;

import model.HttpHeader;
import model.HttpRequestHeader;
import model.RequestLine;
import model.WebProtocol;

import java.util.Map;

public class HttpRequestHeaderDummy {
    public static String HTTP_REQUEST_HEADER_STRING_DUMMY = "GET /jason/test/ HTTP/1.1\n" +
        "Host: localhost:8080\n" +
        "User-Agent: Mozilla/5.0 (Windows; U; Windows NT 6.1; en-US; rv:1.9.1.5) Gecko/20091102 Firefox/3.5.5 (.NET CLR 3.5.30729)\n" +
        "Accept: text/html,application/xhtml+xml,application/xml;q=0.9,*/*;q=0.8\n" +
        "Accept-Language: en-us,en;q=0.5\n" +
        "Accept-Encoding: gzip,deflate\n" +
        "Accept-Charset: ISO-8859-1,utf-8;q=0.7,*;q=0.7\n" +
        "Keep-Alive: 300\n" +
        "Connection: keep-alive\n" +
        "Cookie: PHPSESSID=r2t5uvjq435r4q7ib3vtdjq120\n" +
        "Pragma: no-cache\n" +
        "Cache-Control: no-cache\n"
        + "\n";

    public static HttpRequestHeader GET_USER_HTTP_REQUEST_DUMMY
        = new HttpRequestHeader(
        new RequestLine(
            "GET",
            "/user/something",
            Map.of(
                "userId", "javajigi",
                "password", "password",
                "name", "JaeSung"
            ),
            new WebProtocol("HTTP", "1.1")),
        new HttpHeader(Map.of("Host", "localhost:8080")
        ));

    public static HttpRequestHeader GET_INDEX_HTTP_REQUEST_DUMMY
        = new HttpRequestHeader(new RequestLine("GET", "/index.html", new WebProtocol("HTTP", "1.1")), new HttpHeader(Map.of("Host", "localhost:8080")));

}
