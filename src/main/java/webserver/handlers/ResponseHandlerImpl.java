package webserver.handlers;

import webserver.domain.ResponseEntity;

import java.io.BufferedWriter;
import java.io.IOException;
import java.util.Objects;

public class ResponseHandlerImpl implements ResponseHandler {

    public static final String NOT_SETTING_WRITER_MSG = "현재 writer가 설정되지 않았습니다.";
    private BufferedWriter writer;

    @Override
    public Void handle(ResponseEntity<?> responseEntity) throws IOException {
        if (Objects.isNull(writer)) {
            throw new IllegalStateException(NOT_SETTING_WRITER_MSG);
        }

        writer.write(responseEntity.toString());
        writer.flush();
        writer = null;

        return null;
    }

    @Override
    public ResponseHandler changeWriter(BufferedWriter writer) {
        this.writer = writer;

        return this;
    }
}
