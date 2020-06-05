
/**
 * A Heap implementation class
 * 
 * @param heap the array that holds the heap data
 * @param size the number of elements currently stored in the heap
 */
public class MinHeap {
	
	CompareInt[] heap;
	int size;

	/**
	 * Constructs a new heap with maximum capacity n
	 * Remember to index your heap at 1 instead of 0!
	 * @param n the maximum number of elements allowed in the heap
	 */
	public MinHeap(int n) {
		heap = new CompareInt[n+1];
		size = 0;
	}
	
	/**
	 * Adds an element to the heap
	 * 
	 * @param val the value to be added to the heap
	 */
	public void add(CompareInt val) {
		size++;
		heap[size] = val;
		swim(size);
		return;
	}
	
	/**
	 * Extracts the smallest element from the heap
	 */
	public CompareInt extractMin() {
		
		
		CompareInt min = heap[1];		//smallest number at heap[1]
		
		
		heap[1] = heap[size];			//rearrange last element to root
		size--;
		sink(1);						//sink root element
		
		
		return min;
	}
	
	public void sink(int k){
		
		while(2*k <=size){
			
			int min = 2*k;
			
			if(2*k < size) {
				
				min = (heap[2*k].compareTo(heap[2*k + 1]) < 0)?
						2*k : 2*k +1;
			}
			
			if(heap[k].compareTo(heap[min])<=0) break;
			
			
			
			CompareInt tmp = heap[min];
			heap[min] = heap[k];
			heap[k] = tmp;
			
			k = min;
			}
	}
	
	public void swim(int k){
		
		while(k>1 && heap[k].compareTo(heap[k/2])<0){
			
			
//			swap(heap[k], heap[k/2]);
			
			CompareInt tmp = heap[k];
			heap[k] = heap[k/2];
			heap[k/2] = tmp;
			
			
			k = k/2;
		}
	}

	public void swap(CompareInt a, CompareInt b) {
		
		CompareInt tmp = a;
		a = b;
		b = tmp;

	}
	
}