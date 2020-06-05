import java.util.*;

/**
 * A undirected graph class
 * 
 * Also includes functions for running dfs and bfs
 *
 */
public class Graph {

	private int n; // number of vertices
	private LinkedList<Integer>[] adj; // adjacency list

	private ArrayList<Integer> path;
	private Map<Integer, Integer> dist;

	/**
	 * Constructs a graph with n vertices (containing no edges)
	 * 
	 * @param n - the number of vertices in the graph
	 */
	@SuppressWarnings("unchecked")
	public Graph(int n) {
		this.n = n;

		path = new ArrayList<>();

		adj = (LinkedList<Integer>[]) new LinkedList[n];

		for (int i = 0; i < n; i++) {

			// i - positions of nodes
			adj[i] = new LinkedList<>();

		}

	}

	/**
	 * return adj list
	 */
	public LinkedList<Integer>[] getList() {
		return adj;
	}

	/**
	 * add an edge between position v and w
	 */
	public void addEdge(int v, int w) {

		adj[v].add(w); // add w to v's adjacency list
		adj[w].add(v);

	}

	/**
	 * outputs the neighbors of a vertex at position v
	 */
	public List<Integer> neighbors(int v) {

		return adj[v];
	}

	/**
	 * @return the number of vertices in the graph
	 */
	public int size() {
		return n;
	}

	/**
	 * returns the number of shortest paths from s to t 
	 * 
	 * ex. if the shortest path from s to t is of length 4, and there are two
	 * distinct paths from s to t of length 4, then you should return 2
	 * 
	 * @param s the source vertex
	 * @param t the destination vertex
	 * @return
	 */
	public int numShortestPaths(int s, int t) {

		return BFS(s,t);

	}

	public ArrayList<Integer> getPath() {
		return path;
	}

	
	
	/*
	 * BFS - find the shortest path from a to b
	 * each vertex should keep track of distance from a
	 * compares distance from a of all neighbours of b 
	 * return the number of shortest distance
	 */
	
	public int BFS(int s, int t) {
		
		boolean[] visited = new boolean[n];
		
		int[] dist = new int[n];				//distance from start
		
		int[] path = new int[n];				//number of shortest paths from s to this vertex
		
		Queue<Integer> toExplore = new LinkedList<Integer>();
		
		visited[s] = true;

		toExplore.add(s);
		
		dist[s]=0;								//count of vertexs from s

		path[s]=1;								//number of shortest paths from s to this vertex
		
		if (adj[s].contains(t)) {
			
			return 1;
		}
		
		if (s==t) {
			
			return 1;
			
		}
	
		while(!toExplore.isEmpty()) {
			
			int current = toExplore.remove();
			
			for (int i : adj[current]) {
				
				
				if (!visited[i]) {
					
					if (i!=t) {						//compare t's neighours' shortest path
						
						dist[i]=dist[current]+1;

						path[i]= path[current];
						
						visited[i] = true;
						
						toExplore.add(i);
						
					} 

				} else {
					
					if (dist[i] == dist[current]+1) {
						
						path[i]=path[i] + path[current];
						
					} else if (dist[i] > dist[current]+1) {
						
						dist[i] = dist[current] + 1;
						path[i] = path[current] + 1;
						
					}

				}
			}
			
		}
		

		
		int result=0;
		
		int smallest = dist[adj[t].get(0)];  	//first neighbor in adj t

		
		for (int i = 0; i < adj[t].size(); i++) {
			
			if (dist[adj[t].get(i)] < smallest) {
				
				smallest = dist[adj[t].get(i)];
				
			}
		
		}
		
		for (int i : adj[t]) {
			
			if (dist[i]==smallest) {
				
				result = result + path[i];
			}
		}
		
		return result;

	}

}
