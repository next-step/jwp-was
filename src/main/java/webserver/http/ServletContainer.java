package webserver.http;

import webserver.converter.HttpFileConverter;

import java.io.IOException;
import java.net.URISyntaxException;

public class ServletContainer {

    public HttpResponse getResponse(String socketMsg){
        return getResultContent(socketMsg);
    }

    private HttpResponse getResultContent
            (String socketMsg){
        HttpRequest httpRequest = HttpRequest.parse(socketMsg);

        try{
            if(HttpController.isContainUrl(httpRequest.getUrlPath())){
                return HttpController.callMethod(httpRequest);
            }

            String readFile = HttpFileConverter.readFile(httpRequest.getUrlPath());
            HttpResponse response = HttpResponse.ok(httpRequest);
            response.setResultBody(readFile);

            return response;
        }catch (IOException e){
            return HttpResponse.serverError(httpRequest);
        }catch (URISyntaxException e){
            return HttpResponse.pageNotFound(httpRequest);
        }
    }

}
