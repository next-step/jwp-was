package webserver.resolvers.body;


import utils.QueryStringUtils;
import utils.StringUtils;
import webserver.http.HttpBodyRequest;
import webserver.http.HttpRequest;

public class FormBodyResolver implements BodyResolver{

    private final static String FORM_BODY_CONTENT_TYPE = "application/x-www-form-urlencoded";

    private FormBodyResolver(){}

    public static FormBodyResolver getInstance() {
        return LazyHolder.INSTANCE;
    }

    private static class LazyHolder {
        private static final FormBodyResolver INSTANCE = new FormBodyResolver();
    }

    @Override
    public boolean isMatchedContentType(String contentType) {
        if(StringUtils.isEmpty(contentType)) {
            return false;
        }
        return contentType.contains(FORM_BODY_CONTENT_TYPE);
    }

    @Override
    public HttpRequest resolve(HttpRequest httpRequest) {
        return new HttpBodyRequest(httpRequest, QueryStringUtils.parseToParameters(httpRequest.getBody()));
    }
}
