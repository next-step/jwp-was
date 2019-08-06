package view;

import com.github.jknack.handlebars.Handlebars;
import com.github.jknack.handlebars.Template;
import org.apache.commons.lang3.StringUtils;

import java.io.IOException;
import java.nio.charset.StandardCharsets;

public class HandlebarsView implements View {

    private final static String DEFAULT_FILE_PATH = "index.html";

    private final String responseBody;

    public HandlebarsView(final Handlebars handlebars,
                          final String path,
                          final ModelAndView modelAndView) throws IOException {

        if (handlebars == null) {
            throw new NullPointerException("handlebars는 필수입니다.");
        }

        Template template = handlebars.compile(convertedPath(path));

        this.responseBody = template.apply(modelAndView.getModel());
    }

    private String convertedPath(String path) {
        String convertedPath = path;
        if (StringUtils.isBlank(path) || StringUtils.equals(path, SLASH)) {
            convertedPath = DEFAULT_FILE_PATH;
        }

        return convertedPath;
    }

    @Override
    public byte[] responseBody() {
        return responseBody.getBytes(StandardCharsets.UTF_8);
    }

    @Override
    public int contentLength() {
        return responseBody().length;
    }
}
