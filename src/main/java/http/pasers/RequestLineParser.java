package http.pasers;

import http.requests.RequestLine;

public class RequestLineParser {

    // TODO: 하는 일이 이것 뿐이므로 나중에 이 모듈은 제거 될 가능성 존재. Request Parser에 포함되던가. 일단은 기능만 유지.
    public static RequestLine parse(String requestLine) {
        return new RequestLine(requestLine);
    }
}
