package seaofdecay;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * Line class, for Field of View and other useful things.
 */
public class Line implements Iterable<Point> {
	private List<Point> points;
	/** Not used now, but might be useful for future. */
	public List<Point> getPoints() {
		return points;
	}


	/** I know, all the work is done in the constructor - which is bad practice. However,
	 * it doesn't really do that much, it just creates a list of points. */
	public Line(int x0, int y0, int x1, int y1) {
		this.points = new ArrayList<>();

		int dx = Math.abs(x1 - x0);
		int dy = Math.abs(y1-y0);

		int sx = x0 < x1 ? 1 : -1;
		int sy = y0 < y1 ? 1 : -1;
		int err = dx - dy;

		while (true) {
			points.add(new Point(x0, y0));

			if (x0 == x1 && y0 == y1)
				break;

			int e2 = err * 2;

			if (e2 > -dx) {
				err -= dy;
				x0 += sx;
			}
			if (e2 < dx) {
				err += dx;
				y0 += sy;
			}
		}


	}

	@Override
	public Iterator<Point> iterator() {
		return points.iterator();
	}

}
