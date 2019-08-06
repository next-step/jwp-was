package webserver.resolver;

import webserver.http.ContentType;
import webserver.http.ModelAndView;

import static utils.FileIoUtils.loadFileFromClasspath;

public class HtmlViewResolver implements ViewResolver {

    private static final String CURRENT_PATH = ".";

    @Override
    public byte[] resolve(String viewName) throws Exception {
        return loadFile(viewName);
    }

    @Override
    public byte[] resolve(ModelAndView modelAndView) throws Exception {
        return loadFile(modelAndView.getViewName());
    }

    private byte[] loadFile(String viewName) throws Exception {
        return loadFileFromClasspath(toRelativePath() + viewName);
    }

    private String toRelativePath() {
        return CURRENT_PATH + DEFAULT_ROOT_PATH;
    }

    @Override
    public String getContentType() {
        return ContentType.TEXT_HTML_UTF_8.getValue();
    }
}