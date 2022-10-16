package webserver.http;

import java.nio.ByteBuffer;
import java.util.Iterator;
import java.util.function.BiConsumer;
import java.util.function.BinaryOperator;
import java.util.stream.Collector;

import org.apache.logging.log4j.util.Strings;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

import com.google.common.base.Splitter;

public class QueryStringDecoder {
	public static MultiValueMap<String, String> parseQueryString(ByteBuffer content) {
		String queryString = new String(getBytes(content));
		return parseQueryString(queryString);
	}

	public static MultiValueMap<String, String> parseQueryString(String queryString) {
		if (Strings.isBlank(queryString)) {
			return new LinkedMultiValueMap<>();
		}
		return Splitter.on("&")
			.splitToStream(queryString)
			.map(entry -> Splitter.on("=")
				.omitEmptyStrings()
				.trimResults()
				.split(entry))
			.collect(Collector.of(LinkedMultiValueMap<String, String>::new
				, accumulate, combine));
	}

	public static BiConsumer<MultiValueMap<String, String>, Iterable<String>> accumulate = (map, entryFields) -> {
		Iterator<String> iterator = entryFields.iterator();
		if (iterator.hasNext()) {
			String key = iterator.next();
			String value = "";
			if (iterator.hasNext()) {
				value = iterator.next();
			}
			map.add(key, value);
		}
	};

	public static BinaryOperator<MultiValueMap<String, String>> combine = (oldMap, newMap) -> {
		oldMap.addAll(newMap);
		return oldMap;
	};

	private static byte[] getBytes(ByteBuffer content) {
		if (content.hasArray()) {
			return content.array();
		}
		byte[] buffer = new byte[content.remaining()];
		content.get(buffer);
		return buffer;
	}
}
