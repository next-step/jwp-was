package webserver.converter;

import webserver.domain.HttpEntity;
import webserver.http.HttpRequest;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.HashMap;

public class HttpHeaderConverter extends HttpConverter{


    public HttpHeaderConverter(HttpRequest httpRequest, String httpMsg) throws IOException, URISyntaxException {
        setParseHeader(httpRequest, httpMsg);
    }

    private void setParseHeader(HttpRequest httpRequest, String httpMsg) throws IOException, URISyntaxException {
        String[] httpSplit = httpMsg.split(QUERY_NEW_LINE);
        setHtmlHeaderMsgParsing(httpRequest, httpSplit);
        setHtmlOptionMsgParsing(httpRequest, httpSplit);
    }

    private void setHtmlHeaderMsgParsing(HttpRequest httpRequest, String[] httpSplit) throws IOException, URISyntaxException {
        HttpEntity httpEntity = httpRequest.getHttpEntity();
        String[] separatorMethodPath = httpSplit[0].split(SEPARATOR);
        if(separatorMethodPath.length == 1){
            return;
        }

        httpEntity.setMethod(separatorMethodPath[0]);
        httpEntity.setUrlPath(setSeparatorUrlParameter(separatorMethodPath[1]));
        httpEntity.setVersion(separatorMethodPath[2]);
        HttpParameterConverter.HttpParameterParse(httpEntity, httpSplit);
    }

    private void setHtmlOptionMsgParsing(HttpRequest httpRequest, String[] str){
        HashMap<String, String> etcHeader = new HashMap<>();
        for (int i = 1; i < str.length; i++) {
            String[] keyValue = setSeparatorKeyValue(str[i]);
            if(keyValue != null){
                etcHeader.put(keyValue[0], keyValue[1]);
            }
        }
        httpRequest.getHttpEntity().setEtcHeader(etcHeader);
    }

    private String setSeparatorUrlParameter(String urlPath){
        return urlPath.split(QUERY_PREFIX)[0];
    }


    private String[] setSeparatorKeyValue(String line){
        String[] keyValueStr = line.split(QUERY_HEADER_KEY_VALUE);

        if(keyValueStr.length < 2){
            return null;
        }

        String[] kvArray = new String[2];
        kvArray[0] = keyValueStr[0];
        kvArray[1] = line.substring(
                line.indexOf(QUERY_HEADER_KEY_VALUE) + 2, line.length());

        return kvArray;
    }
}
