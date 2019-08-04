package webserver.http;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import webserver.domain.HttpParseVO;
import webserver.domain.HttpStatus;

import java.io.DataOutputStream;
import java.io.IOException;
import java.util.Optional;


public class HttpResponse {
    private static final Logger logger = LoggerFactory.getLogger(HttpResponse.class);

    public void sendResponse(DataOutputStream dos, HttpParseVO httpParseVO){
        try {
            byte[] returnContent = Optional.ofNullable(httpParseVO.getReturnContent())
                    .orElse("").getBytes();

            dos.writeBytes(httpParseVO.getVersion() +
                    HttpRequest.SEPARATOR +
                    httpParseVO.getResultCode() +
                    HttpRequest.SEPARATOR +
                    HttpStatus.getHttpStatusMessage(httpParseVO.getResultCode()) +
                    HttpRequest.SEPARATOR +
                    HttpRequest.QUERY_NEW_LINE
            );

            if(httpParseVO.getUrlPath().indexOf(HttpRequest.CSS_FILE_NAMING) != -1){
                dos.writeBytes("Accept: text/css,*/*;q=0.1\r\n");
                dos.writeBytes("Connection: keep-alive\r\n");
            }else{
                dos.writeBytes("Content-Type: text/html;charset=utf-8" + HttpRequest.QUERY_NEW_LINE);
                dos.writeBytes("Content-Length: " + returnContent.length + "\r\n");
            }

            if(httpParseVO.getResultCode() == HttpStatus.REDIRECT.getHttpStatusCode()){
                dos.writeBytes("Location: " + httpParseVO.getLocation() + "\r\n");
            }

            if(httpParseVO.getCookie() != null){
                dos.writeBytes("Set-Cookie: " + httpParseVO.getCookie() + "\r\n");
            }

            dos.writeBytes("\r\n");
            dos.write(returnContent, 0, returnContent.length);
            dos.flush();
        } catch (IOException e) {
            logger.error(e.getMessage());
        }
    }


}
