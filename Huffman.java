
import java.util.*;
import java.util.Map.Entry;

public class Huffman {

	private String input;
	private Node huffmanTree; // the huffman tree
	private Map<Character, String> mapping; // maps characters to binary strings

	/*
	 * TODO: 1) add all nodes to the priority queue 2) continually merge the two
	 * lowest-frequency nodes until only one tree remains in the queue 3) Use this
	 * tree to create a mapping from characters (the leaves) to their binary strings
	 * (the path along the tree to that leaf)
	 * 
	 * Remember to store the final tree as a global variable, as you will need it to
	 * decode your encrypted string
	 */

	/**
	 * The Huffman constructor
	 * 
	 */
	public Huffman(String input) {

		this.input = input;
		mapping = new HashMap<>(); // global mapping

		// first, we create a map from the letters in our string to their frequencies
		Map<Character, Integer> freqMap = getFreqs(input);

		// we'll be using a priority queue to store each node with its frequency,
		// as we need to continually find and merge the nodes with smallest frequency
		PriorityQueue<Node> huffman = new PriorityQueue<>();

		for (Character c : freqMap.keySet()) {

			Node n = new Node(c, freqMap.get(c), null, null);

			huffman.add(n);

		}

		merge(huffman);

		huffmanTree = huffman.peek();

		findPath(huffmanTree, "");

	}

	/**
	 * calculate binary string for each letter
	 * 
	 * @param n
	 * @param s
	 */

	public void findPath(Node n, String s) {

		if (n.isLeaf()) {
			mapping.put(n.letter, s);
			return;
		}

		findPath(n.left, s + "0");
		findPath(n.right, s + "1");

	}

	/**
	 * merge nodes in the tree
	 */

	public PriorityQueue<Node> merge(PriorityQueue<Node> p) {

		int i = p.size();

//		System.out.println("original" + p.size());

		while (i >= 2) {

			// smallest two nodes

			Node a = p.poll();
			Node b = p.poll();

//			System.out.println("extract" + p.size());

			Node nLeaf = new Node(null, a.freq + b.freq, a, b);

			p.add(nLeaf);

//			System.out.println("Added" + p.size());

			i--;

		}

		return p;

	}

	/**
	 * Use the global mapping to convert your input string into a binary string
	 */
	public String encode() {

		String s = input;

		String result = "";

		char[] charArray = s.toCharArray();

		for (int i = 0; i < charArray.length; i++) {

			if (mapping.containsKey(charArray[i])) {

				result += mapping.get(charArray[i]);
			}

		}

		return result;
	}

	/**
	 * Use the huffmanTree to decrypt the encoding back into the original input
	 * 
	 * You should convert each prefix-free group of binary numbers in the encoding
	 * to a character
	 * 
	 * @param encoding - the encoded string that needs to be decrypted
	 * @return the original string (should be the same as "input")
	 */
	public String decode(String encoding) {

		String result = "";
		

		int j = 1;
		
		
		int k = 0;
		
		while (j <= encoding.length()) {

				String s = encoding.substring(k, j);

				if (mapping.containsValue(s)) {

					result += getKeyByValue(s);
					
					k = j;
				}

			j++;
		}

		return result;
	}

	/**
	 * get key by value form mapping
	 * 
	 */
	public Character getKeyByValue(String s) {

		for (Entry<java.lang.Character, java.lang.String> entry : mapping.entrySet()) {

			if (Objects.equals(s, entry.getValue())) {

				return entry.getKey();
			}

		}
		return null;
	}

	/**
	 * This function tells us how well the compression algorithm worked
	 * 
	 * note that a char is represented internal using 8 bits
	 * 
	 * ex. if the string "aabc" maps to "0 0 10 11", we would have a compression
	 * ratio of (6) / (8 * 4) = 0.1875
	 */
	public static double compressionRatio(String input) {
		Huffman h = new Huffman(input);
		String encoding = h.encode();
		int encodingLength = encoding.length();
		int originalLength = 8 * input.length();
		return encodingLength / (double) originalLength;
	}

	/**
	 * We've given you this function, which helps you create a frequency map from
	 * the input string
	 */
	private Map<Character, Integer> getFreqs(String input) {
		Map<Character, Integer> freqMap = new HashMap<>();
		for (char c : input.toCharArray()) {
			if (freqMap.containsKey(c)) {
				freqMap.put(c, freqMap.get(c) + 1);
			} else {
				freqMap.put(c, 1);
			}
		}
		return freqMap;
	}

	/**
	 * An inner Node class to build your huffman tree
	 * 
	 * Each node has: a frequency - the sum of the frequencies of all the node's
	 * leaves a letter - the character that this node represents (only for leaves)
	 * left and right children
	 */
	public class Node implements Comparable<Node> {
		private Character letter; // the letter of this node (only for leaves)
		private int freq; // frequency of this node
		private Node left; // add a 0 to you string
		private Node right; // add a 1 to your string

		public Node(Character letter, int freq, Node left, Node right) {
			this.letter = letter;
			this.freq = freq;
			this.left = left;
			this.right = right;
		}

		public boolean isLeaf() {
			return left == null && right == null;
		}

		@Override
		public int compareTo(Node o) {
			return this.freq - o.freq;
		}
	}

}
