package view;

import lombok.NoArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import webserver.http.EntityHeaderFields;
import webserver.http.HttpStatus;
import webserver.http.response.HttpResponse;
import webserver.http.response.ResponseHeaderFields;

import java.util.function.Function;

@NoArgsConstructor
public class CallbackViewResolver implements ViewResolver {

    public View toView(final HttpResponse response,
                       final ModelAndView modelAndView,
                       final Function<String, View> callback) {

        String path = modelAndView.getPath();
        if (startsWithRedirect(path)) {
            return redirect(response, path, callback);
        }

        return html(response, path, callback);
    }

    private boolean startsWithRedirect(final String path) {
        return StringUtils.startsWith(path, REDIRECT);
    }

    private View redirect(final HttpResponse response,
                          final String path,
                          final Function<String, View> callback) {

        final String convertedPath = StringUtils.removeStart(path, REDIRECT);

        response.selectHttpStatus(HttpStatus.FOUND);
        response.put(ResponseHeaderFields.LOCATION, convertedPath);

        return callback.apply(convertedPath);
    }

    private View html(final HttpResponse response,
                      final String path,
                      final Function<String, View> callback) {

        View view = callback.apply(path);

        response.selectHttpStatus(HttpStatus.OK);
        response.put(EntityHeaderFields.CONTENT_LENGTH, String.valueOf(view.contentLength()));
        response.put(EntityHeaderFields.CONTENT_TYPE, "text/html;charset=utf-8");

        return view;
    }
}
