package webserver.http;

import webserver.converter.HttpFileConverter;
import webserver.domain.HttpResponseEntity;

import java.io.IOException;
import java.net.URISyntaxException;

public class RequestLine {

    public static HttpResponse parse(HttpController httpController, String socketMsg){
        HttpRequest httpRequest = new HttpRequest(httpController);
        return new HttpResponse(getResultContent(httpRequest, socketMsg));
    }

    private static HttpResponseEntity getResultContent
            (HttpRequest httpRequest, String socketMsg){
        try{
            httpRequest.parse(socketMsg);
            if(httpRequest.getHttpController()
                    .isContainUrl(httpRequest.getUrlPath())){
                return httpRequest.getHttpController()
                        .callMethod(httpRequest);
            }


            String readFile = HttpFileConverter.readFile(httpRequest.getHttpEntity());
            HttpResponseEntity responseEntity =
                    new HttpResponseEntity(httpRequest.getHttpHeader(),
                            HttpStatus.OK.getHttpStatusCode(),
                            httpRequest.getVersion());

            responseEntity.setResultBody(readFile);

            return responseEntity;

        }catch (IOException e){
            return HttpResponseEntity.setStatusResponse(httpRequest,
                    HttpStatus.SERVER_ERROR);
        }catch (URISyntaxException e){
            return HttpResponseEntity.setStatusResponse(httpRequest,
                    HttpStatus.NOT_FOUND);
        }
    }

}
