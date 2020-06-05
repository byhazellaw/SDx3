
import java.util.*;
import java.io.*;

/**
 * Arbitrage class
 *
 */
public class Arbitrage {

	/**
	 * 
	 * This function reads in a file containing exchange rate information (we've
	 * done this for you), and should create a weighted, directed graph
	 * 
	 * The output should be a list of currencies (i.e. vertices) that you can follow
	 * to create an arbitrage opportunity
	 * 
	 * e.x. if trading from currency 1 to 2 to 3 back to 1 yields an arbitrage
	 * opportunity, you should output a list containing {1, 2, 3, 1}
	 * 
	 * If no arbitrage opportunity is present, you should output an empty list
	 */

	@SuppressWarnings({ "unchecked", "rawtypes" })
	public static List<Integer> arbitrageOpportunity(String filename) throws IOException {

		// parses the input file into a list of exchange rates
		// see the comments above readExchangeRates for details on its output
		double[][] exchangeRates = readExchangeRates(filename);

		int v = exchangeRates.length;

		double[][] graph = new double[v][v];

		for (int i = 0; i < v; i++) {
			
			//[i][i] weight is 0.0
			for (int j = i + 1; j < v; j++) {

				graph[i][j] = -Math.log(exchangeRates[i][j]);
				graph[j][i] = -Math.log(exchangeRates[j][i]);

				
			}
		}
		
		//https://stackoverflow.com/questions/2282427/interesting-problem-currency-arbitrage
		
		//bellman ford algorithm
		
		double[] distance = new double[v];
		int[] pred = new int[v];
		
		//set inifinity to all vertex
		for (int i=0; i < v; i++) {
			
			distance[i] = Double.POSITIVE_INFINITY;
			
			//the vertex before vertex i
			pred[i] = -1;
			
		}
		
		//start at 0
		distance[0] = 0; 
		
		// V-1 passes
		for (int i = 0; i < v-1; i++) { 
			
			//edges
            for (int j = 0; j < v; j++) {
                for (int k = j+1; k < v; k++) {
                	
                    relax(j, k, graph, distance, pred);
                    relax(k, j, graph, distance, pred); 
                }
            }
        }
		

        // make a final pass to detect negative weight cycle
        for (int i = 0; i < v; i++) {
            for (int j = i+1; j < v; j++) {
                if (distance[i] + graph[i][j] < distance[j] ) {
                   //negative cycle detected
                	pred[j] = i;								//j : beginning of the cycle detected
                	
                    return findCycle(j, pred);
                    
                    
                }
                
                //opposite direction
                
                if (distance[j] + graph[j][i] < distance[i] ) {
                	
                	pred[i] = j;
  
                    return findCycle(i, pred);
                }
            }
        }

        //empty list if no arbitrage can be made
        return new ArrayList<Integer>();
		
	}

    private static void relax(int i, int j, double[][] g, double[] distance, int[] pred) {
    	
        if (distance[i] + g[i][j] < distance[j]) {
        	
            distance[j] = distance[i] + g[i][j];
            
            pred[j] = i;			
        }
    }
    
    
    

    private static ArrayList<Integer> findCycle(int s, int[] pred) {		

        ArrayList<Integer> cycle = new ArrayList<>();
        
        int[] count = new int[pred.length]; // count the num of times a vertex is in the path
        
        s = pred[s];				//source of cycle

       
        while (true) {

            cycle.add(s);
            
            count[s]++;
            
            if (count[s] > 1) {
            	
                break; // stop when vertex has been encountered on the path twice - indicates cycle
            }

            s = pred[s];
            
        }
   
        
        
        Collections.reverse(cycle); // reverse path to reflect direction of edges

        return cycle;
    }
	
	/**
	 * You don't have to modify this function
	 * 
	 * Parses a file containing exchange rates between countries into an NxN 2d
	 * array
	 * 
	 * In general, if we have two countries i and j, arr[i][j] gives the exchange
	 * rate from country i to j.
	 * 
	 * I.e. if arr[i][j] = 4.00, then 1 quantity of currency i can be exchanged for
	 * 4.00 quantities of currency j
	 */
	public static double[][] readExchangeRates(String filename) throws IOException {
		// System.out.println("starting to read exchange rates");
		BufferedReader br = new BufferedReader(new FileReader(filename));

		// first line contains the number of countries
		int n = Integer.parseInt(br.readLine());
		double[][] exchangeRates = new double[n][n];

		// parse the file as a 2d array
		// in general, element j in row i has the exchange rate from country i to
		// country j
		for (int i = 0; i < n; i++) {
			String[] line = br.readLine().split(" ");
			for (int j = 0; j < n; j++) {
				double rate = Double.parseDouble(line[j]);
				exchangeRates[i][j] = rate;
			}
		}
		br.close();
		return exchangeRates;

	}

}
