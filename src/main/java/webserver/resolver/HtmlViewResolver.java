package webserver.resolver;

import webserver.http.ContentType;

import static utils.FileIoUtils.loadFileFromClasspath;

public class HtmlViewResolver implements ViewResolver {

    @Override
    public byte[] resolve(String viewName) throws Exception {
        return loadFileFromClasspath("./templates" + viewName);
    }

    @Override
    public String getContentType() {
        return ContentType.TEXT_HTML_UTF_8.getValue();
    }
}