package webserver.resource;

import exception.HttpException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import webserver.http.ModelAndView;
import webserver.http.HttpStatusCode;

import java.io.IOException;
import java.util.List;

import static webserver.provider.ServiceInstanceProvider.getDefaultResourceLoaders;

public class ResourceHandler {

    private static final Logger logger = LoggerFactory.getLogger(ResourceHandler.class);

    private List<ResourceLoader> resourceLoaders;

    public ResourceHandler() {
        resourceLoaders = getDefaultResourceLoaders();
    }

    public ResourceHandler(List<ResourceLoader> resourceLoaders) {
        this.resourceLoaders = resourceLoaders;
    }

    /**
     * if load static file (in static folder)
     * @see StaticResourceLoader
     * if load html file (in templates folder)
     * @see HandlebarsResourceLoader
     * else raise NotFoundException
     */
    public String getContents(ModelAndView mav) {
        try {
            for (ResourceLoader resourceLoader : resourceLoaders) {
                if (resourceLoader.support(mav.getViewName())) {
                    return resourceLoader.getResource(mav);
                }
            }

            logger.error("## Resource Loader not found: " + mav.getViewName());
            throw new HttpException(HttpStatusCode.NOT_FOUND);
        } catch (IOException e) {
            throw new HttpException(HttpStatusCode.NOT_FOUND);
        }
    }

}
