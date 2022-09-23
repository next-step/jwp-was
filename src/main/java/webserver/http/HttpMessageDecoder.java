package webserver.http;

import java.nio.ByteBuffer;
import java.util.List;
import java.util.function.Predicate;

import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

import com.google.common.collect.Lists;

import io.netty.buffer.ByteBuf;
import io.netty.util.internal.AppendableCharSequence;

public abstract class HttpMessageDecoder<T extends HttpMessage> {
	protected LineSplitter lineSplitter = new LineSplitter(new AppendableCharSequence(1024));

	public final T decode(ByteBuf buffer) {
		ByteBuf firstLine = readLine(buffer);
		MultiValueMap<String, String> headers = decodeHeaders(buffer);
		ByteBuf inboundBytes = buffer.readBytes(buffer.readableBytes());
		return initMessage(firstLine, headers, inboundBytes);
	}

	protected MultiValueMap<String, String> decodeHeaders(ByteBuf buffer) {
		MultiValueMap<String, String> headers = new LinkedMultiValueMap<>();

		while (buffer.isReadable() && isEndOfHeader(buffer)) {
			ByteBuf headerLine = readLine(buffer);
			String key = lineSplitter.slice(headerLine, ch -> ch == ':', false);
			String value = lineSplitter.slice(headerLine, ch -> ch == '\n', true);
			headers.add(key, value);
		}
		return headers;
	}

	protected abstract T initMessage(ByteBuf firstLine, MultiValueMap<String, String> headers, ByteBuf inboundBytes);

	protected ByteBuf readLine(ByteBuf byteBuffer) {
		return readSlice(byteBuffer, (byte)'\n');
	}

	protected ByteBuf readSlice(ByteBuf byteBuffer, byte delimiter) {
		int index = byteBuffer.bytesBefore(delimiter);
		return byteBuffer.readSlice(index + 1);
	}

	protected ByteBuffer getBytes(ByteBuf inboundBytes) {
		int length = inboundBytes.readableBytes();
		ByteBuffer contnet = inboundBytes.readBytes(length)
			.nioBuffer();
		inboundBytes.release();
		return contnet;
	}
	private boolean isEndOfHeader(ByteBuf buffer) {
		if (buffer.getByte(buffer.readerIndex()) == 13) {
			buffer.skipBytes(2);
			return false;
		}
		return true;
	}

	class LineSplitter {
		private final AppendableCharSequence header;

		public LineSplitter(AppendableCharSequence header) {
			this.header = header;
		}

		private boolean isOWS(char character) {
			return character == ' ' || character == '\t';
		}
		private void skipOWS() {
			int length = header.length() - 1;
			for (int index = length; index >= 0; index--) {
				if (!isOWS(header.charAtUnsafe(index))) {
					header.setLength(index + 1);
					break;
				}
			}
		}
		public List<String> split(ByteBuf buffer) {
			List<String> fragments = Lists.newArrayList();
			while(buffer.isReadable()){
				fragments.add(slice(buffer, ch -> ch == ' ' || ch == '\t' || ch == '\n', false));
			}
			return fragments;
		}

		public String slice(ByteBuf buffer, Predicate<Character> predicate, boolean canTrim) {
			header.reset();
			int index = buffer.forEachByte(value -> {
				char next = (char)value;
				int length = header.length();

				if (predicate.test(next)) {
					if (header.length() > 0 && header.charAtUnsafe(length - 1) == '\r') {
						header.setLength(length - 1);
					}
					if (canTrim) {
						skipOWS();
					}
					return false;
				}

				if (canTrim && header.length() == 0 && isOWS(next)) {
					return true;
				}
				header.append(next);
				return true;
			});

			if (index == -1) {
				index += (buffer.readerIndex() + buffer.readableBytes());
			}
			buffer.readerIndex(index + 1);
			return header.toString();
		}
	}
}
