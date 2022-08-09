package webserver;

import static webserver.HttpMessageByteProcessor.*;

import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import io.netty.buffer.ByteBuf;
import io.netty.util.ByteProcessor;

public class RequestLineDecoder implements LineDecoder{
	private static final int HTTP_METHOD_INDEX = 0;
	private static final int HTTP_URI_INDEX = 1;
	private static final int HTTP_VERSION_INDEX = 2;

	public RequestLine decode(ByteBuf buffer) {
		List<ByteBuf> buffers = splitLine(buffer);
		UriDecoder uriDecoder = new UriDecoder();

		String requestMethod = convertString(buffers.get(HTTP_METHOD_INDEX));
		Uri uri = uriDecoder.decode(buffers.get(HTTP_URI_INDEX));
		HttpVersion httpVersion = HttpVersion.valueOf(convertString(buffers.get(HTTP_VERSION_INDEX)));
		return new RequestLine(requestMethod, uri, httpVersion);
	}

	private List<ByteBuf> splitLine(ByteBuf buffer) {
		List<ByteBuf> requestLine = new ArrayList<>(LINE_ELEMENT_SIZE);
		while (buffer.isReadable()) {
			if (isIncluded(buffer, IS_LINEAR_WHITESPACE)) {
				throw new IllegalArgumentException("Invalid separator. only a single space or horizontal tab allowed.");
			}

			requestLine.add(sliceBuffer(buffer, FIND_LINEAR_WHITESPACE));
		}

		if (requestLine.size() < LINE_ELEMENT_SIZE) {
			throw new IllegalArgumentException(
				"Invalid argument count exception. There must be 3 elements, but received (" + requestLine.size()
					+ ")");
		}
		return requestLine;
	}

	private boolean isIncluded(ByteBuf buffer, ByteProcessor processor) {
		try {
			return processor.process(buffer.getByte(buffer.readerIndex()));
		} catch (Exception exception) {
			throw new RuntimeException(exception);
		}
	}

	private void skipBytes(ByteBuf buffer) {
		int skipSize = 1;
		if(buffer.isReadable() && isIncluded(buffer, IS_CR)) {
			skipSize = 2;
		}

		if(!buffer.isReadable()){
			skipSize = 0;
		}
		buffer.skipBytes(skipSize);
	}
	private ByteBuf sliceBuffer(ByteBuf buffer, ByteProcessor byteProcessor) {
		int offset = findReadableLength(buffer, byteProcessor);
		ByteBuf subBuffer = buffer.readRetainedSlice(offset);
		skipBytes(buffer);
		return subBuffer;
	}

	private int findReadableLength(ByteBuf buffer, ByteProcessor byteProcessor) {
		int offset = buffer.forEachByte(
			buffer.readerIndex(), buffer.readableBytes(), byteProcessor);

		if (offset == -1) {
			int lastIndex = buffer.readerIndex() + buffer.readableBytes() - 1;
			offset = 0;
			if (buffer.getByte(lastIndex) == LF) {
				offset = -2;
			}
			offset += buffer.capacity();
		}
		return offset -= buffer.readerIndex();
	}

	private String convertString(ByteBuf buffer) {
		return buffer.toString(StandardCharsets.UTF_8);
	}
	private class UriDecoder {
		public Uri decode(ByteBuf buffer) {
			if (isIncluded(buffer, IS_NOT_SLASH)) {
				throw new IllegalArgumentException("Unsupported URI format. URI must start with '/'");
			}

			return new Uri(convertString(sliceBuffer(buffer, FIND_QUESTION_MARK)),
				decodeParams(buffer));
		}

		private HashMap<String, String> decodeParams(ByteBuf buffer) {
			HashMap<String, String> params = new HashMap<>();

			while (buffer.isReadable()) {
				ByteBuf keyValue = sliceBuffer(buffer, FIND_AMPERSAND);

				String key = convertString(sliceBuffer(keyValue, FIND_EQUALS));
				String value = convertString(sliceBuffer(keyValue, FIND_ASCII_SPACE));

				params.put(key, value);
				keyValue.release();
			}
			return params;
		}
	}
}
