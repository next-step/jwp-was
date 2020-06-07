package http;

import http.enums.HttpResponseCode;

public class HttpResponse {
    private HttpResponseCode responseCode;
    private byte[] responseBody;
    private HttpHeaderInfo headerInfo;

    public HttpResponse(HttpResponseCode code, byte[] responseBody, HttpHeaderInfo headerInfo) {
        this.responseCode = code;
        this.responseBody = responseBody;
        this.headerInfo = headerInfo;
    }

    public byte[] makeResponseBody() {
        byte[] responseHeader = (responseCode.makeHeader() + headerInfo.makeResponseHeader()).getBytes();
        byte[] response = new byte[responseHeader.length + responseBody.length];
        System.arraycopy(responseHeader,0,response,0, responseHeader.length);
        System.arraycopy(responseBody,0,response,responseHeader.length, responseBody.length);

        return response;
    }
}
