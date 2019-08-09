package utils;

import java.util.Arrays;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

import com.google.common.base.Functions;

public class ConcurrentCollectionUtils {
	
	private static Boolean MAP_TO_SET_DEFAUT_VALUE = Boolean.TRUE;

	public static <T> Set<T> convertToConcurrentSet(T[] values) {
		Map<T, Boolean> methods = Arrays.stream(values)
				.collect(Collectors.toMap(Functions.identity(), (v) -> MAP_TO_SET_DEFAUT_VALUE, (v1, v2) -> MAP_TO_SET_DEFAUT_VALUE));

		return new ConcurrentHashMap<>(methods).keySet();
	}
}
