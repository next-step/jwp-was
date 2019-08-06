package view;

import lombok.NoArgsConstructor;
import webserver.http.EntityHeaderFields;
import webserver.http.HttpStatus;
import webserver.http.response.HttpResponse;

import java.util.function.Function;

@NoArgsConstructor
public class StaticViewResolver implements ViewResolver {


    public View toView(final HttpResponse response,
                       final String path,
                       final Function<String, View> callback) {

        return stylesheet(response, path, callback);
    }

    private View stylesheet(final HttpResponse response,
                            final String path,
                            final Function<String, View> callback) {

        View view = callback.apply(path);

        response.selectHttpStatus(HttpStatus.OK);
        response.put(EntityHeaderFields.CONTENT_LENGTH, String.valueOf(view.contentLength()));
        response.put(EntityHeaderFields.CONTENT_TYPE, "text/css,*/*;q=0.1");

        return view;
    }
}
