package webserver.resource;

import exception.HttpException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import utils.StringUtils;
import webserver.http.ModelAndView;
import webserver.http.HttpStatusCode;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.List;

import static java.util.Arrays.asList;

public class StaticResourceLoader extends AbstractResourceLoader {

    private static final Logger logger = LoggerFactory.getLogger(StaticResourceLoader.class);
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
        logger.debug("## retrieve resource name: {}", name);
        try (InputStream in = classLoader.getResourceAsStream(name)) {
            if (in == null) {
                throw new HttpException(HttpStatusCode.NOT_FOUND);
            }

            BufferedReader reader = new BufferedReader(new InputStreamReader(in));
            StringBuilder content = new StringBuilder();
            String read;
            while ((read = reader.readLine()) != null) {
                content.append(read);
            }
            return content.toString();
        } catch (IOException e) {
            throw new HttpException(HttpStatusCode.NOT_FOUND);
        }
    }

}
