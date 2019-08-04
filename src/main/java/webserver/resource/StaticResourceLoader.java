package webserver.resource;

import exception.HttpException;
import utils.StringUtils;
import webserver.ModelAndView;
import webserver.StatusCode;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.List;

import static java.util.Arrays.asList;

public class StaticResourceLoader extends AbstractResourceLoader {

    private static final List<String> AVAILABLE_RESOURCE = asList("js", "css", "woff", "svg", "ttf", "eot", "svg", "png");

    public StaticResourceLoader() {
    }

    public StaticResourceLoader(ClassLoader classLoader) {
        super(classLoader);
    }

    @Override
    public boolean support(String name) {
        return AVAILABLE_RESOURCE.contains(StringUtils.endLastSplit(name, '.'));
    }

    public String getResource(ModelAndView mav) {
        String name = "static" + mav.getViewName();
        validate(name);
        try (InputStream in = classLoader.getResourceAsStream(name)) {
            if (in == null) {
                throw new HttpException(StatusCode.NOT_FOUND);
            }

            BufferedReader reader = new BufferedReader(new InputStreamReader(in));
            StringBuilder content = new StringBuilder();
            String read;
            while ((read = reader.readLine()) != null) {
                content.append(read);
            }
            return content.toString();
        } catch (IOException e) {
            throw new HttpException(StatusCode.NOT_FOUND);
        }
    }

}
