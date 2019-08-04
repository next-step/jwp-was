package header;

import response.HeaderResponse;

/**
 * Created by youngjae.havi on 2019-08-04
 */
public enum ContentType implements HeaderResponse {
    TEXT_HTML("text/html;charset=utf-8"){
        @Override
        public String valueAll() {
            return text();
        }
    },
    CSS("text/css;charset=utf-8"){
        @Override
        public String valueAll() {
            return text();
        }
    };

    private String contentType;

    ContentType(String contentType) {
        this.contentType = contentType;
    }

    public String text() {
        return contentType;
    }

    @Override
    public String key() {
        return "Content-Type";
    }

}
