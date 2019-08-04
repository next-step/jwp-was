package webserver;

import exception.HttpException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import webserver.request.RequestHeader;
import webserver.request.RequestHolder;
import webserver.resource.HandlebarsResourceLoader;
import webserver.resource.ResourceLoader;
import webserver.resource.StaticResourceLoader;

import java.io.IOException;
import java.util.List;

import static java.util.Arrays.asList;

public class ResourceHandler {

    private static final Logger logger = LoggerFactory.getLogger(RequestHeader.class);

    private List<ResourceLoader> resourceLoaders;

    public ResourceHandler() {
        resourceLoaders = asList(new StaticResourceLoader(), new HandlebarsResourceLoader());
    }

    public String getContents(ModelAndView mav) {
        try {
            return getContentsInternal(mav);
        } catch (IOException e) {
            throw new HttpException(StatusCode.NOT_FOUND);
        }
    }

    private String getContentsInternal(ModelAndView mav) throws IOException {
        for (ResourceLoader resourceLoader : resourceLoaders) {
            if (resourceLoader.support(mav.getViewName())) {
                return resourceLoader.getResource(mav);
            }
        }

        throw new UnsupportedOperationException("available ResourceLoader is empty");
    }

}
