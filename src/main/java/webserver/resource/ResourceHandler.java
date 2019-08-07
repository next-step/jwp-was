package webserver.resource;

import exception.HttpException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import webserver.http.HttpStatusCode;
import webserver.http.ModelAndView;

import java.util.List;

import static webserver.Context.RESOURCE_LOADERS;

public class ResourceHandler {

    private static final Logger logger = LoggerFactory.getLogger(ResourceHandler.class);

    private List<ResourceLoader> resourceLoaders;

    public ResourceHandler() {
        resourceLoaders = RESOURCE_LOADERS;
    }

    /**
     * if load static file (in static folder)
     * @see StaticResourceLoader
     * if load html file (in templates folder)
     * @see HandlebarsResourceLoader
     * else raise NotFoundException
     */
    public String getContents(ModelAndView mav) {
        for (ResourceLoader resourceLoader : resourceLoaders) {
            if (resourceLoader.support(mav.getViewName())) {
                return resourceLoader.getResource(mav);
            }
        }

        throw new HttpException(HttpStatusCode.NOT_FOUND);
    }

    public void setResourceLoaders(List<ResourceLoader> resourceLoaders) {
        this.resourceLoaders = resourceLoaders;
    }

}
