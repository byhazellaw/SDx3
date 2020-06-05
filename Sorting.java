public class Sorting {

	/**
	 * Implement the mergesort function, which should sort the array of integers in
	 * place
	 * 
	 * You will probably want to use helper functions here, as described in the
	 * lecture recordings (ex. merge(), a helper mergesort function)
	 * 
	 * @param arr
	 */

	public static void merge(CompareInt[] arr, CompareInt[] aux, int l, int mid, int h){
		
		//copy arr to aux
		for (int i=l; i<=h; i++) {
			
			aux[i]=arr[i];
		}
		
		int i=l; int j=mid+1; int k=l;
		
		
		
		//comparing elements 
		while (i<=mid && j<=h) {
			
			
			if (aux[i].compareTo(aux[j])<0) {
				
				arr[k++] = aux[i++];
				
			} else {
				
				arr[k++] = aux[j++];
			}	
		}
		
		//copy remaining elements to result
		while(i<=mid) arr[k++] = aux[i++];
		while(j<=h) arr[k++] = aux[j++];
		
		
	}
   

	public static void mergeSort(CompareInt[] arr, CompareInt[] aux, int l, int h){
		
		if (h<=l) return;
		int mid = (l+h)/2;
		
		mergeSort(arr, aux, l, mid);
		mergeSort(arr, aux, mid+1, h);
		
		merge(arr, aux, l, mid, h);
		
		
	}
		
		
	public static void mergeSort(CompareInt[] arr) {
		
		//empty list to hold result pass to mergeSort
		CompareInt[] aux = new CompareInt[arr.length];	
		
		mergeSort(arr, aux, 0, arr.length-1);
	}


	/**
	 * Implement the quickSelect
	 * 
	 * Again, you will probably want to use helper functions here (ex. partition(),
	 * a helper quickselect function)
	 */
	public static CompareInt quickSelect(int k, CompareInt[] arr) {
		//TODO
		return quickSelect(arr, 0, arr.length-1, k);
	}
	
	
	
	public static CompareInt quickSelect(CompareInt[] arr, int l, int h, int k){
		
		
		if(k>0 && k<=h-l+1){
			
			int index = partition(arr, l, h);
			
			//k=1 == arr[0]
			
			if(index - l == k - 1) {
				return arr[index];
			}
				
			
			if(index - l > k-1) {
				return quickSelect(arr, l, index-1, k);
			}
				
			return quickSelect(arr, index+1, h, k-index+l-1);
		}
		return arr[h];
	}
	
	
	public static int partition(CompareInt[] arr, int l, int h){
		
		CompareInt x = arr[h]; 
		
		int i=l;
		
		for(int j=l; j<h; j++){
			
			
			if(arr[j].compareTo(x)<0){
				
				CompareInt tmp = arr[i];
				arr[i] = arr[j];
				arr[j] = tmp;
				i++;		
			}
		}
		
		//copy pivot to correct position
		CompareInt tmp = arr[h];
		arr[h] = arr[i];
		arr[i] = tmp;
		return i;
	}

	
	

}
