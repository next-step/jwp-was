package webserver;

import io.netty.buffer.ByteBuf;

public interface LineDecoder {
	int LINE_ELEMENT_SIZE = 3;

	RequestLine decode(ByteBuf buffer);
}
