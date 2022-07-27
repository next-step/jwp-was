package utils;

import com.github.jknack.handlebars.Handlebars;
import com.github.jknack.handlebars.Template;
import com.github.jknack.handlebars.io.ClassPathTemplateLoader;
import com.github.jknack.handlebars.io.TemplateLoader;
import endpoint.TemplateResource;

import java.nio.charset.StandardCharsets;
import java.util.Map;

public class TemplatePageLoader {
    public static final byte[] EMPTY_TEMPLATE_BYTES = new byte[]{};
    private static final String TEMPLATES_BASE_PATH = "./templates";
    private static final String NOT_FOUND_PAGE_FILE_PATH = "/nof_found.html";
    public static final String TEMPLATE_FILE_EXTENSION = ".html";
    public static final String PAGE_PATH_DELIMITER = "/";

    public static TemplateResource notFoundPage() {
        return getTemplatePage(NOT_FOUND_PAGE_FILE_PATH);
    }

    public static TemplateResource getTemplatePage(String templatePageName) {
        try {
            byte[] templatePageBytes = FileIoUtils.loadFileFromClasspath(TEMPLATES_BASE_PATH + templatePageName).orElse(EMPTY_TEMPLATE_BYTES);
            return new TemplateResource(templatePageName, templatePageBytes);
        } catch (Exception e) {
            return TemplateResource.NOT_FOUND_PAGE;
        }
    }

    public static TemplateResource getDynamicTemplatePage(String templatePageName, Map<String, ?> viewModelMap) {
        try {
            TemplateLoader loader = new ClassPathTemplateLoader();
            loader.setPrefix(TEMPLATES_BASE_PATH.replaceFirst("\\.", ""));
            loader.setSuffix(TEMPLATE_FILE_EXTENSION);
            Handlebars handlebars = new Handlebars(loader);

            String dynamicPageCompilePath = templatePageName.replaceFirst(PAGE_PATH_DELIMITER, "")
                    .replaceFirst(TEMPLATE_FILE_EXTENSION, "");

            Template template = handlebars.compile(dynamicPageCompilePath);

            String profilePage = template.apply(viewModelMap);

            return new TemplateResource(templatePageName, profilePage.getBytes(StandardCharsets.UTF_8));
        } catch (Exception e) {
            return TemplateResource.NOT_FOUND_PAGE;
        }
    }
}
