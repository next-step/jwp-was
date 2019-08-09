package webserver.http;

import webserver.converter.HttpFileConverter;

import java.io.IOException;
import java.net.URISyntaxException;

public class ServletContainer {

    public static HttpResponse make(HttpController httpController, String socketMsg){
        HttpRequest httpRequest = new HttpRequest();
        return getResultContent(httpController, httpRequest, socketMsg);
    }

    private static HttpResponse getResultContent
            (HttpController controller, HttpRequest httpRequest, String socketMsg){
        try{
            httpRequest.parse(socketMsg);
            if(controller.isContainUrl(httpRequest.getUrlPath())){
                return controller.callMethod(httpRequest);
            }

            String readFile = HttpFileConverter.readFile(httpRequest.getUrlPath());
            HttpResponse response = HttpResponse.ok(httpRequest);
            response.setResultBody(readFile);

            return response;
        }catch (IOException e){
            return HttpResponse.setStatusResponse(HttpStatus.SERVER_ERROR);
        }catch (URISyntaxException e){
            return HttpResponse.setStatusResponse(HttpStatus.NOT_FOUND);
        }
    }

}
