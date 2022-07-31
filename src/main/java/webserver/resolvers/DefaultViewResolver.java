package webserver.resolvers;

import utils.FileIoUtils;
import webserver.domain.DefaultView;
import webserver.exception.ViewResolveException;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.Objects;

public class DefaultViewResolver implements Resolver {
    @Override
    public boolean support(Object obj) {
        return Objects.nonNull(obj) && obj instanceof DefaultView;
    }

    @Override
    public String resolve(Object obj) {
        try {
            DefaultView view = (DefaultView) obj;
            byte[] bytes = FileIoUtils.loadFileFromClasspath(view.getPrefix() + view.getViewName() + view.getSuffix());

            return new String(bytes);
        } catch (IOException | URISyntaxException e) {
            throw new ViewResolveException(this.getClass(), e.getMessage());
        }


    }
}
