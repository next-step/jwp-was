package handler;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import request.RequestHeader;
import response.Response;
import utils.FileIoUtils;
import webserver.RequestHandler;

/**
 * Created by youngjae.havi on 2019-08-03
 */
public class StaticMappingHandler implements RequestStrategy {
    private static final Logger logger = LoggerFactory.getLogger(RequestHandler.class);

    @Override
    public Response request(RequestHeader requestHeader) {
        try {
            return Response.css(FileIoUtils.loadFileFromClasspath("./static" + requestHeader.getRequestLine().getPath().trim()));
        } catch (Exception e) {
            logger.error(e.getMessage());
            throw new RuntimeException("StaticMappingHandler request failed: ", e);
        }
    }

    @Override
    public boolean isSupport(RequestHeader requestHeader) {
        String path = requestHeader.getRequestLine().getPath();
        return path.contains(".css") || path.contains(".js");
    }
}
