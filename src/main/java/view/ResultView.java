package view;

import request.RequestLine;

public class ResultView {
    public void printRequestLine(RequestLine requestLine) {
        System.out.println("method는 " + requestLine.getMethod());
        System.out.println("path는 " + requestLine.getPath());
        System.out.println("protocol은 " + requestLine.getProtocol());
        System.out.println("version은 " + requestLine.getVersion());
    }
}
