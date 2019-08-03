package coordinate;

import java.util.*;

public class FigureFactory {
	private static final Map<Integer, Class> FIGURE_MAP;

	static {
		Map<Integer, Class<? extends Figure>> initialMap = new HashMap<>();
		initialMap.put(Line.POINT_SIZE, Line.class);
		initialMap.put(Triangle.POINT_SIZE, Triangle.class);
		initialMap.put(Rectangle.POINT_SIZE, Rectangle.class);
		FIGURE_MAP = Collections.unmodifiableMap(initialMap);
	}

	static Figure getInstance(List<Point> points) {
		try {
			Class<? extends Figure> clazz = FIGURE_MAP.get(points.size());
			if (Objects.isNull(clazz)) {
				throw new IllegalArgumentException("유효하지 않은 도형입니다.");
			}
			return clazz.getDeclaredConstructor(List.class).newInstance(points);
		} catch (Exception e) {
			throw new IllegalArgumentException("객체를 생성 할 수 없습니다. " + e.getMessage());
		}

	}
}
