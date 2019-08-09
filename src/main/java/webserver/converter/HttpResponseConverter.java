package webserver.converter;

import webserver.http.HttpResponse;
import webserver.http.HttpStatus;

import java.util.Optional;

public class HttpResponseConverter {

    HttpResponse httpResponse;
    public HttpResponseConverter(HttpResponse response) {
        this.httpResponse = response;
    }

    public StringBuffer getResponse(){
        byte[] returnContent = Optional.ofNullable(httpResponse.getResultBody())
                .orElse("").getBytes();

        StringBuffer headerStr = new StringBuffer();
        headerStr.append(httpResponse.getHttpHeader().getVersion() +
                HttpConverter.SEPARATOR +
                httpResponse.getStatusCode() +
                HttpConverter.SEPARATOR +
                HttpStatus.getHttpStatusMessage(httpResponse.getStatusCode()) +
                HttpConverter.SEPARATOR +
                HttpConverter.QUERY_NEW_LINE);

        setResponseAddOption(headerStr, returnContent.length);

        headerStr.append("\r\n");
        return headerStr;
    }

    private void setResponseAddOption(StringBuffer buffer, int contentLength){
        setFileResourceOption(buffer, contentLength);
        setRedirectOption(buffer);
        setCookieOption(buffer);
    }

    private void setFileResourceOption(StringBuffer buffer, int contentLength){
        if(httpResponse.getHttpHeader().getEtcHeader().containsKey("Accept")){
            buffer.append("Accept: " + httpResponse.getHttpHeader().getEtcHeader().get("Accept"));
        }

        buffer.append("Connection: keep-alive\r\n");
        buffer.append("Content-Length: " + contentLength + "\r\n");
    }

    private void setRedirectOption(StringBuffer buffer){
        if(httpResponse.getHttpStatus() == HttpStatus.REDIRECT){
            buffer.append("Location: " + httpResponse.getRedirectUrl() + "\r\n");
        }
    }

    private void setCookieOption(StringBuffer buffer){
        if(httpResponse.getCookie() != null){
            buffer.append("Set-Cookie: " + httpResponse.getCookie() + "\r\n");
        }
    }

}
