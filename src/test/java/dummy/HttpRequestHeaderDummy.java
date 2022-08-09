package dummy;

import model.HttpHeader;
import model.request.HttpRequestMessage;
import model.request.RequestLine;

public class HttpRequestHeaderDummy {
    public static String HTTP_REQUEST_HEADER_STRING_DUMMY = "GET /jason/test/create?userId=javajigi&password=password&name=JaeSung HTTP/1.1\n" +
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

    public static String HTTP_POST_REQUEST_HEADER_STRING_DUMMY = "POST /user/create HTTP/1.1\n" +
        "Host: localhost:8080\n" +
        "User-Agent: Mozilla/5.0 (Windows; U; Windows NT 6.1; en-US; rv:1.9.1.5) Gecko/20091102 Firefox/3.5.5 (.NET CLR 3.5.30729)\n" +
        "Accept: text/html,application/xhtml+xml,application/xml;q=0.9,*/*;q=0.8\n" +
        "Accept-Language: en-us,en;q=0.5\n" +
        "Accept-Encoding: gzip,deflate\n" +
        "Accept-Charset: ISO-8859-1,utf-8;q=0.7,*;q=0.7\n" +
        "Keep-Alive: 300\n" +
        "Connection: keep-alive\n" +
        "Content-Length: 93\n" +
        "Content-Type: application/x-www-form-urlencoded\n" +
        "Cookie: PHPSESSID=r2t5uvjq435r4q7ib3vtdjq120\n" +
        "Pragma: no-cache\n" +
        "Cache-Control: no-cache\n" +
        "\n" +
        "userId=javajigi&password=password&name=%EB%B0%95%EC%9E%AC%EC%84%B1&email=javajigi%40slipp.net\n";

    public static HttpRequestMessage GET_USER_HTTP_REQUEST_DUMMY
        = HttpRequestMessage.getRequestHeaderOf(
        new RequestLine("GET /user/something?userId=javajigi&password=password&name=JaeSung HTTP/1.1"),
        new HttpHeader.Builder()
            .addHeader("Host: localhost:8080")
            .build()
    );

    public static HttpRequestMessage GET_INDEX_HTTP_REQUEST_DUMMY
        = HttpRequestMessage.getRequestHeaderOf(
        new RequestLine("GET /index.html HTTP/1.1"),
        new HttpHeader.Builder()
            .addHeader("Host: localhost:8080")
            .build()
    );
}
