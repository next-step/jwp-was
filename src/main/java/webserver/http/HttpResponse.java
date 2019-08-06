package webserver.http;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import webserver.converter.HttpConverter;

import java.io.DataOutputStream;
import java.io.IOException;
import java.util.Optional;


public class HttpResponse {
    private static final Logger logger = LoggerFactory.getLogger(HttpResponse.class);

    public void sendResponse(DataOutputStream dos, HttpRequest httpRequest){
        try {
            byte[] returnContent = Optional.ofNullable(httpRequest.getReturnContent())
                    .orElse("").getBytes();

            dos.writeBytes(httpRequest.getVersion() +
                    HttpConverter.SEPARATOR +
                    httpRequest.getResultCode() +
                    HttpConverter.SEPARATOR +
                    HttpStatus.getHttpStatusMessage(httpRequest.getResultCode()) +
                    HttpConverter.SEPARATOR +
                    HttpConverter.QUERY_NEW_LINE
            );

            if(isCssFile(httpRequest)){
                dos.writeBytes("Accept: text/css,*/*;q=0.1\r\n");
                dos.writeBytes("Connection: keep-alive\r\n");
            }else{
                dos.writeBytes("Content-Type: text/html;charset=utf-8" + HttpConverter.QUERY_NEW_LINE);
                dos.writeBytes("Content-Length: " + returnContent.length + "\r\n");
            }

            if(httpRequest.getResultCode() == HttpStatus.REDIRECT.getHttpStatusCode()){
                dos.writeBytes("Location: " + httpRequest.getLocation() + "\r\n");
            }

            if(httpRequest.getCookie() != null){
                dos.writeBytes("Set-Cookie: " + httpRequest.getCookie() + "\r\n");
            }

            dos.writeBytes("\r\n");
            dos.write(returnContent, 0, returnContent.length);
            dos.flush();
        } catch (IOException e) {
            logger.error(e.getMessage());
        }
    }

    public static void setRedirect(HttpRequest httpRequest, String location){
        httpRequest.setResultCode(HttpStatus.REDIRECT.getHttpStatusCode());
        httpRequest.setReturnContent(HttpStatus.REDIRECT.getHttpStatusMessage());
        httpRequest.setLocation(location);
    }

    public static void setPageNotFond(HttpRequest httpRequest){
        httpRequest.setResultCode(HttpStatus.NOT_FOUND.getHttpStatusCode());
        httpRequest.setReturnContent(HttpStatus.NOT_FOUND.getHttpStatusMessage());
    }

    public static void setPageError(HttpRequest httpRequest){
        httpRequest.setResultCode(HttpStatus.SERVER_ERROR.getHttpStatusCode());
        httpRequest.setReturnContent(HttpStatus.SERVER_ERROR.getHttpStatusMessage());
    }

    public boolean isCssFile(HttpRequest httpRequest){
        return (httpRequest.getUrlPath().indexOf(HttpConverter.CSS_FILE_NAMING) != -1) ? true : false;
    }
}
