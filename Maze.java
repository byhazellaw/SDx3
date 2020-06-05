import java.io.*;
import java.util.*;

public class Maze {

	 Graph g; // We will store the maze internally as a graph
	int startVertex; // position of the start
	int endVertex; // position of the end
	List<Move> result;
	ArrayList<Integer> path;
	int n;
	int count;
	
	/**
	 * Maze vertex position: 01234 56789
	 */

	/**
	 * We will store an nxn maze in a textfile, and read it in.
	 * 
	 * A maze is simply represented as a textfile with 4 numbers: 0, 1, 2, 3
	 * 
	 * 0s represent walls- this is not a valid part of the maze 1s represent valid
	 * parts of the maze (i.e. blocks you can move to 2 represents the starting
	 * point of the maze 3 represents the end point of the maze.
	 * 
	 * Our constructor will create the 2d array of integers from the file for you
	 * 
	 */
	public Maze(String filename) throws IOException {


		path = new ArrayList<>();
		// create the 2d grid from the maze textfile
		int[][] grid = createGrid(filename);

		// identify start and end vertices
		n = grid.length;
		
		
		
		for (int row = 0; row < n; row++) {
			for (int col = 0; col < n; col++) {

				if (grid[row][col]!=0) {
					count++;
				}
				
				if (grid[row][col] == 2) {

					startVertex = row * n + col;

				}
				if (grid[row][col] == 3) {
					endVertex = row * n + col;
				}

			}
		}

		g = new Graph(n * n);

	
		for (int row = 0; row < n; row++) {
			for (int col = 0; col < n -1; col++) {

				if (grid[row][col]!=0 && grid[row][col+1]!=0) {
					
					g.addEdge(row * n + col, row * n + col+1);
				}
				
			}
			
		}
		
		for (int row = 0; row < n -1; row++) {
			for (int col = 0; col < n ; col++) {

				if (grid[row][col]!=0 && grid[row+1][col]!=0) {
					
					g.addEdge(row * n + col, (row+1) * n + col);
				}
				
			}
			
		}
	
	}
	
	

	/**
	 * 
	 * This algorithm should solve the maze output a list of "moves", beginning at
	 * the start vertex, that can be taken to arrive at the end vertex. You should
	 * solve the maze on the graph, using some sort of graph traversal.
	 * 
	 * More information on figuring out what "move" to output at each step can be
	 * found in the writeup!
	 * 
	 * @param g           - the graph to traverse
	 * @param startVertex - the starting vertex
	 * @param endVertex   - we will stop the traversal and output the list of moves
	 *                    when we hit this vertex
	 * 
	 */
	public List<Move> solveMaze() {

		result = new LinkedList<>();
		
		DFS(startVertex, endVertex); 
		
		for (int i=0; i<path.size()-1; i++) {
			
			if ((path.get(i) - path.get(i+1)==1)) {
				
				result.add(Move.LEFT);
				
			} else if ((path.get(i) - path.get(i+1)== -1)) {
				
				result.add(Move.RIGHT);
				
			} else if ((path.get(i) - path.get(i+1)== n)) {
				
				result.add(Move.UP);
				
			} else {
				
				result.add(Move.DOWN);
			}
		}
		
		return result;
	}

	

	/**
	 * Move is an enum type- when navigating a maze, you can either move UP, DOWN,
	 * LEFT, or RIGHT
	 */
	public enum Move {
		UP, DOWN, LEFT, RIGHT
	}

	public boolean DFSUtil(int current, int end, boolean visited[]) {
		
		if (current == end) {
			
			return true;
		}
		

		final int[] coor = {
				1,	//going right
				-1, //going left
				-n, //going up
				n, //going down
				
		};
		
		for (int i = 0; i<coor.length; i++) {
			
			int next = current + coor[i];
			
			LinkedList<Integer> list = g.getList()[current];
			
			
			//if next is a neighbor and not visited
			if (list.contains(next) && !visited[next]) {
				
				visited[next] = true;
				path.add(next);
				
				//if we can reach end from this node
				if (DFSUtil(next, end, visited)) {
					
					return true;
				}
				
				//no cannot
				path.remove(path.size()-1);
			}
			

			
		}
		
		
	
		return false;
	}
	
	
	public void DFS(int start, int end) {
		
		boolean[] visited = new boolean[g.size()];
	
		visited[start]=true;
		
		path.add(start);
		
		if (!DFSUtil(start, end, visited)) {
			
			//remove start
			path.remove(path.size()-1);
		}
		
	}
	

	/**
	 * Helper function for creating a 2d grid to represent the maze, given a file
	 * name
	 * 
	 * @param filename - the name of the file to be read from, containing the maze
	 *                 data
	 */
	public static int[][] createGrid(String filename) throws IOException {
		// create the 2d array from the maze textfile
		BufferedReader br = new BufferedReader(new FileReader(filename));
		String line = br.readLine();
		int n = line.length(); // the grid will always be an nxn square
		int[][] grid = new int[n][n];
		int row = 0;
		while (line != null) {
			int col = 0;
			for (char c : line.toCharArray()) {
				int val = Character.getNumericValue(c);
				grid[row][col] = val;
				col++;
			}
			line = br.readLine();
			row++;
		}
		br.close();
		return grid;
	}

}
