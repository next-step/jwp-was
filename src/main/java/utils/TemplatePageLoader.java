package utils;

import endpoint.TemplatePage;

public class TemplatePageLoader {
    public static final byte[] EMPTY_TEMPLATE_BYTES = new byte[]{};
    private static final String TEMPLATES_BASE_PATH = "./templates";
    private static final String NOT_FOUND_PAGE_FILE_PATH = "/nof_found.html";

    public static TemplatePage notFoundPage() {
        return getTemplatePage(NOT_FOUND_PAGE_FILE_PATH);
    }

    public static TemplatePage getTemplatePage(String templatePageName) {
        try {
            byte[] templatePageBytes = FileIoUtils.loadFileFromClasspath(TEMPLATES_BASE_PATH + templatePageName).orElse(EMPTY_TEMPLATE_BYTES);
            return new TemplatePage(templatePageName, templatePageBytes);
        } catch (Exception e) {
            return TemplatePage.NOT_FOUND_PAGE;
        }
    }
}
