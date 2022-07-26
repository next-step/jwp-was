package endpoint;

import utils.TemplatePageLoader;

public class TemplatePage {
    private String pageFileName;
    private byte[] pageFileBytes;

    public TemplatePage(String pageFileName, byte[] pageFileBytes) {
        this.pageFileName = pageFileName;
        this.pageFileBytes = pageFileBytes;
    }

    public byte[] getPageFileBytes() {
        return pageFileBytes;
    }

    public static final TemplatePage NOT_FOUND_PAGE = TemplatePageLoader.notFoundPage();
}
