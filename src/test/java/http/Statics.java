package http;

public class Statics {
    private Statics() {}

    public static final String RAW_REQUEST_STR =
            "GET /foo/bar?zoo=xoo HTTP/1.1\n" +
                    "Host: example.org\n" +
                    "User-Agent: Mozilla/5.0 (Macintosh; U; Intel Mac OS X 10.6; fr; rv:1.9.2.8) Gecko/20100722 Firefox/3.6.8\n" +
                    "Accept: */*\n" +
                    "Accept-Language: fr,fr-fr;q=0.8,en-us;q=0.5,en;q=0.3\n" +
                    "Accept-Encoding: gzip,deflate\n" +
                    "Accept-Charset: ISO-8859-1,utf-8;q=0.7,*;q=0.7\n" +
                    "Keep-Alive: 115\n" +
                    "Connection: keep-alive\n" +
                    "Content-Type: application/x-www-form-urlencoded\n" +
                    "X-Requested-With: XMLHttpRequest\n" +
                    "Referer: http://example.org/test\n" +
                    "Cookie: foo=bar; lorem=ipsum;";
}
