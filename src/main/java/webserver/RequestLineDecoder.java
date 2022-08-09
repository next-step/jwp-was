package webserver;

import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;

public class RequestLineDecoder {
	private ByteBuf buffer;
	private List<ByteBuf> buffers;

	public RequestLineDecoder(byte[] buffer) {
		this.buffer = Unpooled.wrappedBuffer(buffer);
		splitRequestLine();
	}

	public HttpRequestLine decode() {
		UriDecoder uriDecoder = new UriDecoder();

		String requestMethod = buffers.get(0).toString(StandardCharsets.UTF_8);
		Uri uri = uriDecoder.decode(buffers.get(1));
		HttpVersion httpVersion = HttpVersion.valueOf(buffers.get(2).toString(StandardCharsets.UTF_8));
		return new HttpRequestLine(requestMethod, uri, httpVersion);
	}

	private List<ByteBuf> splitRequestLine() {
		List<ByteBuf> requestLine = new ArrayList<>(3);
		while(buffer.isReadable()) {

			// \r\n 인 경우 스킵
			if(buffer.getByte(buffer.readerIndex()) == 0x20 || buffer.getByte(buffer.readerIndex()) == 0x09) { // 0x20 : space, 0x09 : tab
				throw new IllegalArgumentException("Invalid separator. only a single space or horizontal tab allowed.");
			}

			// 다음 문자 길이 구하기
			int offset = buffer.forEachByte(buffer.readerIndex(), buffer.readableBytes(), (byte b) -> (b == 0x20 || b == 0x09) ? false : true); // 0x20 : space, 0x09 : tab

			if (offset == -1) {
				int lastIndex = buffer.readerIndex() + buffer.readableBytes() - 1;
				if(buffer.getByte(lastIndex) == 0x0a) {
					offset--;
				}
				offset += buffer.capacity();
			}
			offset -= buffer.readerIndex();


			// System.out.println(buffer.retainedSlice(buffer.readerIndex(), offset).toString(StandardCharsets.UTF_8));
			System.out.println("readIndex : " + buffer.readerIndex() + ", readableBytes : " + buffer.readableBytes() + ", offset : " + offset + "capacity : " + buffer.capacity());
			requestLine.add(buffer.readRetainedSlice(offset));
			for(ByteBuf byteBuf : requestLine) {
				System.out.println(byteBuf.toString(StandardCharsets.UTF_8));
			}

			if(buffer.getByte(buffer.readerIndex()) == 0x0d) { // carriage return
				buffer.skipBytes(2);
			}else {
				buffer.skipBytes(1);
			}
		}


		if (requestLine.size() < 3) {
			throw new IllegalArgumentException("Invalid argument count exception. There must be 3 elements, but received (" + requestLine.size() + ")");
		}
		buffers = requestLine;


		return requestLine;
	}

	private ByteBuf extracted() {
		int offset = buffer.readerIndex();
		int index = buffer.forEachByte((byte b) -> {
			if (b == 0x20 || b == 0x0a || b == 0x0d) { // space, newline, carriage return
				return false;
			}
			return true;
		});
		return buffer.readRetainedSlice(index - offset);
	}

	private void skipBytes() {
		buffer.forEachByte((byte b) -> {
			if (b == 0x20 || b == 0x0a || b == 0x0d || b == 0x09) { // space, newline, carriage return, tab
				buffer.skipBytes(1);
				return true;
			}
			return false;
		});
	}

	private class UriDecoder {
		public Uri decode(ByteBuf buffer) {
			if (buffer.getByte(0) != 0x2F) { // "/"
				throw new IllegalArgumentException("Unsupported URI format. URI must start with '/'");
			}

			// path 의 길이 구하기
			int readableLength = findReadableLength(buffer, (byte)0x3F);

			String path = toString(buffer, readableLength);
			HashMap<String, String> params = decodeParams(buffer);

			buffer.release();
			return new Uri(path, params);
		}

		private HashMap<String, String> decodeParams(ByteBuf buffer) {
			HashMap<String, String> params = new HashMap<>();

			// parse key and value from key=value
			while (buffer.isReadable()) {
				int index = buffer.readerIndex();

				if (buffer.getByte(index) == 0x3F || buffer.getByte(index) == 0x26) {
					buffer.skipBytes(1); // if current byte is  "?" or "&" , skip index

					// find length of key=value pair
					int readableLength = findReadableLength(buffer, (byte)0x26);

					ByteBuf keyValue = buffer.retainedSlice(index + 1, readableLength);

					// skip buffer to next pair
					buffer.skipBytes(readableLength);

					// split key and value by '='
					String key = toString(keyValue, keyValue.bytesBefore((byte)0x3D));
					keyValue.skipBytes(1); // skip '='
					String value = toString(keyValue, keyValue.readableBytes());
					keyValue.release();

					params.put(key, value);
				}
			}
			return params;
		}

		private String toString(ByteBuf byteBuf, int length) {
			String value = byteBuf.toString(byteBuf.readerIndex(), length, StandardCharsets.UTF_8);
			byteBuf.skipBytes(length);
			return value;
		}

		private int findReadableLength(ByteBuf buffer, byte target) {
			int readableLength = buffer.bytesBefore(target);

			if (readableLength == -1) {
				return buffer.readableBytes();
			}
			return readableLength;
		}
	}
}
