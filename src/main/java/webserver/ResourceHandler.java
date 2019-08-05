package webserver;

import exception.HttpException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import webserver.request.RequestHeader;
import webserver.resource.ResourceLoader;

import java.io.IOException;
import java.util.List;

import static webserver.provider.ServiceInstanceProvider.getDefaultResourceLoaders;

public class ResourceHandler {

    private static final Logger logger = LoggerFactory.getLogger(RequestHeader.class);

    private List<ResourceLoader> resourceLoaders;

    public ResourceHandler() {
        resourceLoaders = getDefaultResourceLoaders();
    }

    public String getContents(ModelAndView mav) {
        try {
            if (mav.isRedirect()) {
                return "";
            }

            for (ResourceLoader resourceLoader : resourceLoaders) {
                if (resourceLoader.support(mav.getViewName())) {
                    return resourceLoader.getResource(mav);
                }
            }

            logger.error("## Resource Loader not found: " + mav.getViewName());
            throw new HttpException(StatusCode.NOT_FOUND);
        } catch (IOException e) {
            throw new HttpException(StatusCode.NOT_FOUND);
        }
    }

}
