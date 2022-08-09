package webserver;

import java.util.HashMap;

import io.netty.buffer.ByteBuf;

public class HttpMessageDecoder {
	protected LineDecoder lineDecoder;

	public HttpMessageDecoder(LineDecoder lineDecoder) {
		this.lineDecoder = lineDecoder;
	}

	protected HashMap<String, String> decodeHeaders() {
		return new HashMap<>();
	}

	protected ByteBuf readLine(ByteBuf buffer){
		int offset = buffer.forEachByte(HttpMessageByteProcessor.FIND_CRLF);
		return buffer.readRetainedSlice(offset);
	}

	protected ByteBuf[] readLines(ByteBuf buffer) {
		return null;
	}
}
