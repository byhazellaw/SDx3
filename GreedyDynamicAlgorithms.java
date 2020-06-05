
import java.util.*;

public class GreedyDynamicAlgorithms {

	/**
	 * Goal: find the smallest number of red intervals to select, such that every
	 * blue interval overlaps with at least ONE of the selected red intervals.
	 * Output this number
	 * 
	 * optimal red intervals should intersect more than one blue
	 * 
	 * @param red  - the list of red intervals
	 * @param blue - the list blue intervals
	 * @return
	 */
	public static int optimalIntervals(ArrayList<Interval> red, ArrayList<Interval> blue) {

		Interval.sortByStartTime(blue);

		// sort by finishing time - greedy
		Interval.sortByFinishTime(red);

		int count = 0;
		int i = 0;
		int j = 0;

		while (i < blue.size() && j < red.size()) {

			if (!isIntersected(blue.get(i), red.get(j))) {
				// get the first red that intersect with the current blue
				j++;
				continue;
			}

			while (j < red.size() && isIntersected(blue.get(i), red.get(j))) {

				// get the last red that intersects with the current blue
				j++;
			}

			// increment count when a red intersects with two or more blues
			count++;

			while (isIntersected(blue.get(i), red.get(j - 1))) {

				// switch to another blue
				i++;

				if (i == blue.size()) {
					return count;
				}
			}

		}

		if (i < blue.size())
			return -1;

		return count;
	}

	/*
	 * check to see if Intervals overlap
	 */
	public static boolean isIntersected(Interval blue, Interval red) {

		if (red.start < blue.start) {

			return red.finish >= blue.start;

		} else {

			return red.start <= blue.finish;
		}
	}

	////////////////////////////////////////////////////////////////////////////////

	/**
	 * Goal: find any path of lowest cost from the top-left of the grid (grid[0][0])
	 * to the bottom right of the grid (grid[m-1][n-1]). Output this sequence of
	 * directions
	 * 
	 * @param grid - the 2d grid containing the cost of each location in the grid.
	 * @return
	 */
	public static List<Direction> optimalGridPath(int[][] grid) {

		List<Direction> result = new LinkedList<>();
		
		List<Direction> reverse = reverseList(grid);
		
		for (int i = reverse.size()-1; i >=0; i--) {
			
			result.add(reverse.get(i));
		}
		
		return result;
	}

	/*
	 * create revesere direction list
	 */
	public static LinkedList<Direction> reverseList(int[][] grid) {

		List<Direction> reverse = new LinkedList<>();

		int[][] cost = costGrid(grid);

		int i = cost.length - 1;
		int j = cost[0].length - 1;

		

		while (i > 0 && j > 0) {

				if (cost[i - 1][j] < cost[i][j - 1]) {

					reverse.add(Direction.DOWN);
					// move index to the [i-1][j]
					i--;

				} else {
					reverse.add(Direction.RIGHT);
					j--;
				}
			
		}
		
		//at edge
		while (i==0 && j>0) {
			reverse.add(Direction.RIGHT);
			j--;
			
		}
		
		while (j==0 && i>0) {
			
			reverse.add(Direction.DOWN);
			// move index to the [i-1][j]
			i--;

		}
		
			
		return (LinkedList<Direction>) reverse;

	}
	
	

	/*
	 * compute min cost for every index
	 */
	public static int[][] costGrid(int[][] grid) {

		int[][] result = null;

		// gridLength: how many rows
		// grid[0]Length: how many columns

		if (grid != null) {

			result = new int[grid.length][grid[0].length];

			result[0][0] = grid[0][0];

			// compute cost for top row and leftmost column
			for (int i = 1; i < result.length; i++) {

				result[i][0] = result[i - 1][0] + grid[i][0];

			}
			for (int i = 1; i < result[0].length; i++) {

				result[0][i] = result[0][i - 1] + grid[0][i];
			}

			for (int i = 1; i < result.length; i++) {

				for (int j = 1; j < result[0].length; j++) {

					if (result[i - 1][j] < result[i][j - 1]) {

						result[i][j] = result[i - 1][j] + grid[i][j];

					} else {

						result[i][j] = result[i][j - 1] + grid[i][j];

					}

				}
			}

		}

		return result;

	}

	/**
	 * A simple Direction enum directions can be either DOWN or RIGHT You will
	 * output a list of these in the grid-path problem
	 */
	public static enum Direction {
		DOWN, RIGHT
	}

	///////////////////////////////////////////////////////////////////////////////////

	/**
	 * A private Interval class to help with the interval question
	 */
	public static class Interval {

		int start;
		int finish;

		public Interval(int start, int finish) {

			this.start = start;
			this.finish = finish;

		}

		/**
		 * sorts a list of intervals by start time, you are free to use this on the
		 * first question
		 */
		public static void sortByStartTime(ArrayList<Interval> l) {
			Collections.sort(l, new Comparator<Interval>() {
				public int compare(Interval o1, Interval o2) {
					Interval i1 = (Interval) o1;
					Interval i2 = (Interval) o2;
					return i1.start - i2.start;
				}
			});
		}

		/**
		 * sorts a list of intervals by finish time, you are free to use this on the
		 * first question
		 */
		public static void sortByFinishTime(ArrayList<Interval> l) {
			Collections.sort(l, new Comparator<Interval>() {
				public int compare(Interval o1, Interval o2) {
					Interval i1 = (Interval) o1;
					Interval i2 = (Interval) o2;
					return i1.finish - i2.finish;
				}
			});
		}
	}

}
