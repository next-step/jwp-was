package webserver.converter;

import webserver.http.HttpResponse;
import webserver.http.HttpStatus;

public class HttpResponseConverter {

    public static String getResponse(HttpResponse httpResponse){
        String cotentBodyStr = (httpResponse.getResultBody() == null) ?
                "" : httpResponse.getResultBody();

        StringBuffer headerStr = new StringBuffer();
        headerStr.append(httpResponse.getHttpHeader().getVersion() +
                HttpConverter.SEPARATOR +
                httpResponse.getStatusCode() +
                HttpConverter.SEPARATOR +
                HttpStatus.getHttpStatusMessage(httpResponse.getStatusCode()) +
                HttpConverter.SEPARATOR +
                HttpConverter.QUERY_NEW_LINE);

        setResponseAddOption(httpResponse, headerStr, cotentBodyStr.getBytes().length);

        headerStr.append("\r\n");
        return headerStr.toString();
    }

    private static void setResponseAddOption(HttpResponse httpResponse,
                                             StringBuffer buffer,
                                             int contentLength){
        setFileResourceOption(httpResponse, buffer, contentLength);
        setRedirectOption(httpResponse, buffer);
        setCookieOption(httpResponse, buffer);
    }

    private static void setFileResourceOption(HttpResponse httpResponse,
                                              StringBuffer buffer,
                                              int contentLength){
        if(httpResponse.getHttpHeader().getEtcHeader().containsKey("Accept")){
            buffer.append("Accept: " + httpResponse.getHttpHeader().getEtcHeader().get("Accept"));
        }

        buffer.append("Connection: keep-alive\r\n");
        buffer.append("Content-Length: " + contentLength + "\r\n");
    }

    private static void setRedirectOption(HttpResponse httpResponse, StringBuffer buffer){
        if(httpResponse.getHttpStatus() == HttpStatus.REDIRECT){
            buffer.append("Location: " + httpResponse.getRedirectUrl() + "\r\n");
        }
    }

    private static void setCookieOption(HttpResponse httpResponse, StringBuffer buffer){
        String cookieStr = "";
        if(httpResponse.getSession() != null){
            cookieStr = httpResponse.getSession().getToString() + HttpConverter.QUERY_COOKIE_SEPARATOR
                    + HttpConverter.SEPARATOR;
        }

        cookieStr += httpResponse.getCookie();

        buffer.append("Set-Cookie: " + cookieStr + "\r\n");
    }

}
