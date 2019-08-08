package webserver.http;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import webserver.converter.HttpConverter;
import webserver.domain.HttpResponseEntity;

import java.io.DataOutputStream;
import java.io.IOException;
import java.util.Optional;


public class HttpResponse {
    private static final Logger logger = LoggerFactory.getLogger(HttpResponse.class);

    HttpResponseEntity responseEntity;
    public HttpResponse(HttpResponseEntity responseEntity){
        this.responseEntity = responseEntity;
    }

    public void sendResponse(DataOutputStream dos){
        try {
            byte[] returnContent = Optional.ofNullable(responseEntity.getResultBody())
                    .orElse("").getBytes();

            dos.writeBytes(responseEntity.getVersion() +
                    HttpConverter.SEPARATOR +
                    responseEntity.getResultCode() +
                    HttpConverter.SEPARATOR +
                    HttpStatus.getHttpStatusMessage(responseEntity.getResultCode()) +
                    HttpConverter.SEPARATOR +
                    HttpConverter.QUERY_NEW_LINE
            );

            setResponseAddOption(dos, responseEntity, returnContent.length);

            dos.writeBytes("\r\n");
            dos.write(returnContent, 0, returnContent.length);
            dos.flush();
        } catch (IOException e) {
            logger.error(e.getMessage());
        }
    }

    private void setResponseAddOption(DataOutputStream dos, HttpResponseEntity responseEntity, int contentLength) throws IOException{
        setFileResourceOption(dos, responseEntity, contentLength);
        setRedirectOption(dos, responseEntity);
        setCookieOption(dos, responseEntity);
    }

    private void setFileResourceOption(DataOutputStream dos,
                                       HttpResponseEntity responseEntity,
                                       int contentLength) throws IOException{

        if(responseEntity.getHttpHeader().getEtcHeader().containsKey("Accept")){
            dos.writeBytes("Accept: " + responseEntity.getHttpHeader().getEtcHeader().get("Accept"));
        }

        dos.writeBytes("Connection: keep-alive\r\n");
        dos.writeBytes("Content-Length: " + contentLength + "\r\n");
    }

    private void setRedirectOption(DataOutputStream dos,
                                   HttpResponseEntity responseEntity) throws IOException{
        if(responseEntity.getResultCode() == HttpStatus.REDIRECT.getHttpStatusCode()){
            dos.writeBytes("Location: " + responseEntity.getRedirectUrl() + "\r\n");
        }
    }

    private void setCookieOption(DataOutputStream dos,
                                 HttpResponseEntity responseEntity) throws IOException{
        if(responseEntity.getCookie() != null){
            dos.writeBytes("Set-Cookie: " + responseEntity.getCookie() + "\r\n");
        }
    }
}
