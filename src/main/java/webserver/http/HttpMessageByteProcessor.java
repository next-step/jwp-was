package webserver;

import io.netty.util.ByteProcessor;

public interface HttpMessageByteProcessor extends ByteProcessor {
	byte EQUALS = (byte)'=';
	byte AMPERSAND = (byte)'&';
	byte QUESTION_MARK = (byte)'?';
	byte SLASH = (byte)'/';
	byte CR = (byte)'\r';
	byte LF = (byte)'\n';
	byte COLON = (byte)':';
	byte SPACE = (byte)' ';
	byte TAB = (byte)'\t';

	ByteProcessor FIND_EQUALS = new IndexOfProcessor(EQUALS);
	ByteProcessor FIND_AMPERSAND = new IndexOfProcessor(AMPERSAND);

	ByteProcessor FIND_QUESTION_MARK = new IndexOfProcessor(QUESTION_MARK);

	// TODO: 스페이스로 스플릿
	// 화이트 리스트로 스플릿해서
	// charactorsequnce 돌려주는 파서
	ByteProcessor FIND_WHITESPACE = new ByteProcessor() {
		@Override
		public boolean process(byte value) {
			return value != SPACE && value != TAB;
		}
	};

	ByteProcessor IS_NOT_SLASH = new ByteProcessor() {
		@Override
		public boolean process(byte value) {
			return value != SLASH;
		}
	};

	ByteProcessor IS_CR = new ByteProcessor() {
		@Override
		public boolean process(byte value) {
			return value == CR;
		}
	};

	ByteProcessor FIND_COLON = new ByteProcessor() {
		@Override
		public boolean process(byte value) {
			return value != COLON;
		}
	};
}
