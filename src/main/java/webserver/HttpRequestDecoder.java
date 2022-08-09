package webserver;

import io.netty.buffer.ByteBuf;

public class HttpRequestDecoder extends HttpMessageDecoder{
	public HttpRequestDecoder() {
		super(new RequestLineDecoder());
	}

	public HttpRequest decode(ByteBuf buffer) {
		RequestLine requestLine = lineDecoder.decode(readLine(buffer));

		return new HttpRequest(requestLine, decodeHeaders(), requestLine.getHttpVersion());
	}
}
