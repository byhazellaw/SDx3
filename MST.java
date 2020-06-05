
import java.util.*;

public class MST {

	private static int t = 0;
	private static int NIL = -1;
	private static int mstWeight;
	
	private static TreeSet<Tuple> notExist;
	private static TreeSet<Tuple> bridges;
	private static TreeSet<Tuple> mst;
	private static TreeSet<Tuple> allEdges;
	private static TreeSet<Tuple> light;
	private static TreeSet<Tuple> medium;
	private static TreeSet<Tuple> heavy;

	/**
	 * This function takes in a graph, with weights classified as light, medium or
	 * heavy It should output a list of all edges in the graph that must be a part
	 * of any minimum spanning tree
	 * 
	 * You should use the Tuple class provided to output edges. For instance, to
	 * output the edge (u,v), you would add "new Tuple(u,v)" to your list
	 * 
	 * @param g
	 * @return
	 */

	// In a tree, every edge is a bridge. Conversely, if every edge of a connected
	// graph is
	// a bridge, then the graph must be a tree.

	public static Set<Tuple> mustExist(WeightedUndirectedGraph<Weight> g) {

		
		getBridges(g);

		System.out.println(bridges.size() + " bridges must exist.");

		System.out.println();

		return bridges;
	}

	/**
	 * This function takes in a graph, with weights classified as light, medium or
	 * heavy It should output a list of all edges in the graph that must NOT be a
	 * part of any minimum spanning tree
	 * 
	 * @param g
	 * @return the set of edges (unordered tuples of vertices) that must not exist
	 *         in any MST
	 */
	public static Set<Tuple> mustNotExist(WeightedUndirectedGraph<Weight> g) {

		
		
		getBridges(g); 
		
		
		
		System.out.println(notExist.size() + " edges must not exist");
		System.out.println();

		return notExist;
	}

	
	/**
	 * Find bridges
	 * 
	 * @param g
	 * @param u
	 * @return
	 */
	public static void getBridges(WeightedUndirectedGraph<Weight> g) {

		notExist = new TreeSet<>();
		
		bridges = new TreeSet<>();
		
		

		int V = g.size();

		boolean[] visited = new boolean[V];

		// keep track of weights to each vertex from the parent vertex
		int[] weight = new int[V];

		int[] disc = new int[V];

		// low[] stores the lowest id of node that low[x] can reach. if there exists a
		// path from vertex 4 to 0, vertex 4 low id is 0.
		int[] low = new int[V];

		int[] parent = new int[V];

		for (int i = 0; i < V; i++) {

			parent[i] = NIL;

		}

		// vertex 0
		weight[0] = NIL;

		for (boolean i : visited) {

			if (!i) {

				
				
				bridgeUtil(g, visited, disc, low, parent, 0, weight, notExist);
				

			}

		}

		if (bridges.isEmpty()) {

			System.out.println("There are no bridges.");

		} else {

			for (Tuple t : bridges) {

				System.out.println("bridges are: " + t.u + " " + t.v + " weight: " + t.weight);
			}
		}

	}


	public static void bridgeUtil(WeightedUndirectedGraph<Weight> g, boolean[] visited, int[] disc, int[] low,
			int[] parent, int u, int[] weight, TreeSet<Tuple> notExist) {

		visited[u] = true; // mark current node as visited

		disc[u] = low[u] = (int) ++t; // initialize discovery time and low value

		Iterator<Integer> iter = g.adj[u].iterator();

		while (iter.hasNext()) {

			int v = iter.next();

			// unvisited
			if (!visited[v]) {

				parent[v] = u;

				int edgeWeight = (g.weight(u, v).equals(MST.Weight.LIGHT)) ? 0
						: ((g.weight(u, v).equals(MST.Weight.MEDIUM)) ? 1 : 2);

				weight[v] = edgeWeight;

				System.out.println(v + " edgeweight: " + weight[v]);

				bridgeUtil(g, visited, disc, low, parent, v, weight, notExist);

				low[u] = Math.min(low[u], low[v]);

				// critical edges without weights
				if (low[v] > disc[u]) {

					Tuple t = new Tuple(u, v);

					int w = (g.weight(u, v).equals(MST.Weight.LIGHT)) ? 0
							: ((g.weight(u, v).equals(MST.Weight.MEDIUM)) ? 1 : 2);

					t.setWeight(w);

					bridges.add(t);

					System.out.println("Real bridges are " + u + " " + v);

				} else {

					System.out.println("non Bridges are " + u + " " + v);

				}

				// visited vertexes

			} else if (v != parent[u]) {

				low[u] = Math.min(low[u], disc[v]);

				System.out.println("visited edges in MST: " + u + " " + v);

				int vW = (g.weight(u, v).equals(MST.Weight.LIGHT)) ? 0
						: ((g.weight(u, v).equals(MST.Weight.MEDIUM)) ? 1 : 2);

				if (weight[v] == NIL) {

					weight[v] = vW;

					System.out.println("weight v:" + weight[v]);

				}

				if (vW == weight[v]) {

//					existEdges.add(new Tuple(u, v));

					System.out.println("may exist edges: " + u + " " + v);

				} else if (vW > weight[v]) {

					notExist.add(new Tuple(u, v));

				}

			} else if (v == parent[u]) {

				System.out.println("may exist edges in MST: " + u + " " + v);

				int vW = (g.weight(u, v).equals(MST.Weight.LIGHT)) ? 0
						: ((g.weight(u, v).equals(MST.Weight.MEDIUM)) ? 1 : 2);

				if (vW < weight[u]) {

					bridges.add(new Tuple(u, v));
				}

				if (weight[v] > weight[u] && weight[parent[u]] > weight[u]) {

					bridges.add(new Tuple(u, v));

				}

			}

		}

	}

	/**
	 * Edges in this MST are classified as light, medium or heavy (their actual edge
	 * weights are not known)
	 */
	public enum Weight {
		LIGHT, MEDIUM, HEAVY
	}

	// add all edges in graph to allEdges and assign weights to edges
	public static void getEdges(WeightedUndirectedGraph<Weight> g) {

		allEdges = new TreeSet<>();

		light = new TreeSet<>();

		medium = new TreeSet<>();

		heavy = new TreeSet<>();

		for (int i = 0; i < g.size(); i++) {

			for (int j : g.neighbors(i)) {

				if (g.weight(i, j) != null) {

					Tuple t = new Tuple(i, j);
					int w = (g.weight(i, j).equals(MST.Weight.LIGHT)) ? 0
							: ((g.weight(i, j).equals(MST.Weight.MEDIUM)) ? 1 : 2);

					t.setWeight(w);

					if (w == 0) {
						light.add(t);

					} else if (w == 1) {

						medium.add(t);

					} else {

						heavy.add(t);
					}

					allEdges.add(t);

				}
			}

		}

	}

	// Kruskals MST
	public static void getMST(WeightedUndirectedGraph<Weight> g) {

		getEdges(g);

		mst = new TreeSet<>();

		// sort edges according to weights
		PriorityQueue<Tuple> pq = new PriorityQueue<>(allEdges.size(), Comparator.comparingInt(o -> o.weight));

		for (Tuple t : allEdges) {

			pq.add(t);
		}

		int[] parent = new int[g.size()];

		makeSet(parent);

		int index = 0;

		// A minimum spanning tree has (V – 1) edges where V is the number of vertices
		// in the given graph.
		while (index < g.size() - 1) {

			Tuple t = pq.remove();

			// check if isCycle
			int x_set = find(parent, t.u);
			int y_set = find(parent, t.v);

			// adding this edge will form a cycle
			if (x_set == y_set) {

				// if the weight is the same as the previous t - may exist in other MST
				// if the weight is different from the previous t - must not exist

				if (t.weight != mst.last().weight) {

					// TODO

					notExist.add(t);

					System.out.println("Edges must not exist are " + t.u + " " + t.v);

				}

			} else {

				mst.add(t);

				index++;
				union(parent, x_set, y_set);
			}

		}

		mstWeight = 0;

		for (Tuple t : mst) {

			mstWeight = mstWeight + t.weight;
		}

		System.out.println("MST weight is: " + mstWeight + ". Here is the tree:");

		printTree(mst);

	}

	public static void makeSet(int[] parent) {

		// make a list of vertex with pointer to itself : parent[0] = 0
		for (int i = 0; i < parent.length; i++) {

			parent[i] = i;
		}
	}

	public static int find(int[] parent, int vertex) {

		// isCycle or not - if the parent is itself than it's a cycle
		if (parent[vertex] != vertex) {

			return find(parent, parent[vertex]);
		}

		return vertex;
	}

	// TODO what does union do?
	public static void union(int[] parent, int x, int y) {

		int x_set_parent = find(parent, x);
		int y_set_parent = find(parent, y);

		// make x as parent of y
		parent[y_set_parent] = x_set_parent;
	}

	public static void printTree(TreeSet<Tuple> set) {

		Iterator iter = set.iterator();

		while (iter.hasNext()) {

			Tuple t = (Tuple) iter.next();

			System.out.println("Edge:" + t.u + " " + t.v + " weight: " + t.weight);
		}
	}

}
