package webserver.http.response.handler.get;

import db.DataBase;
import model.User;
import utils.UserParser;
import webserver.http.request.header.RequestHeader;
import webserver.http.response.HttpResponseStatus;
import webserver.http.response.handler.ResponseHandler;
import webserver.http.response.header.ContentType;
import webserver.http.response.header.ResponseHeader;

public class UserCreateGetResponseHandler implements ResponseHandler {
    private static final String REDIRECT_INDEX_HTML = "/index.html";

    @Override
    public ResponseHeader run(RequestHeader requestHeader, String requestBody, byte[] responseBody) {
        User user = UserParser.createUser(
                requestHeader.requestParams(UserParser.FIELD_USER_ID),
                requestHeader.requestParams(UserParser.FIELD_PASSWORD),
                requestHeader.requestParams(UserParser.FIELD_NAME),
                requestHeader.requestParams(UserParser.FIELD_EMAIL)
        );

        DataBase.addUser(user);

        return new ResponseHeader(requestHeader.protocolVersion(), HttpResponseStatus.FOUND)
                .addContentType(ContentType.HTML)
                .addContentLength(responseBody.length)
                .addLocation(REDIRECT_INDEX_HTML);
    }
}
