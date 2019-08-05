package view;

import lombok.Getter;
import org.apache.commons.lang3.StringUtils;
import webserver.http.EntityHeaderFields;
import webserver.http.HttpStatus;
import webserver.http.response.HttpResponse;
import webserver.http.response.ResponseHeaderFields;

import java.io.IOException;
import java.net.URISyntaxException;

@Getter
public class ResourceViewResolver implements ViewResolver {

    private final static String REDIRECT = "redirect:";

    public ResourceViewResolver() {
    }

    public View toView(final HttpResponse response, final String path) throws IOException, URISyntaxException {
        if (startsWithRedirect(path)) {
            return redirect(response, path);
        }

        return html(response, path);
    }

    private boolean startsWithRedirect(final String path) {
        return StringUtils.startsWith(path, REDIRECT);
    }

    private View redirect(final HttpResponse response, final String path) throws IOException, URISyntaxException {
        final String convertedPath = StringUtils.removeStart(path, REDIRECT);

        response.selectHttpStatus(HttpStatus.FOUND);
        response.put(ResponseHeaderFields.LOCATION, convertedPath);

        return new ResourceView(convertedPath);
    }

    private View html(final HttpResponse response, final String path) throws IOException, URISyntaxException {
        ResourceView resourceView = new ResourceView(path);

        response.selectHttpStatus(HttpStatus.OK);
        response.put(EntityHeaderFields.CONTENT_LENGTH, String.valueOf(resourceView.contentLength()));
        response.put(EntityHeaderFields.CONTENT_TYPE, "text/html;charset=utf-8");

        return resourceView;
    }
}
