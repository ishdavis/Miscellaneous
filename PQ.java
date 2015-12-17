import java.util.*;
public class PQ<K extends Comparable<K>>
{
	private K[] pq;
	private int n;
	public static HashMap<String,Integer> myMap = new HashMap<String,Integer>();
	
	public PQ(){
		n = 0;
		pq = (K[]) new Comparable[50];
	}
	
	public int size(){return n;}
	public boolean isEmpty(){return n == 0;}
	
	public K getCar(String vin){
		return pq[myMap.get(vin)];
	}
	
	public void insert(K c, String vin)
	{
		if(n == pq.length - 1){
			resize();
		}
		pq[++n] = c;
		myMap.put(vin, n);
		//System.out.println(myMap.get(vin));
		swim(n);
	}
	
	public void print(){
		for(int i = 1; i <= n; i++){
			System.out.println(pq[i].toString());
		}
	}
	
	public K min(){return pq[1];}
	
	private void resize(){
		int newSize = pq.length * 2;
		K[] temp = (K[]) new Object[newSize];
		for (int i = 1; i <= n; i++){
			temp[i] = pq[i];
		}
		pq = temp;
	}
	
	public K delMin(){
		exch(1, n);
		K min = pq[n--];
		sink(1);
		pq[n+1] = null;
		return min;
	}
	
	public void delMiddle(int i){
		exch(i, n);
		n--;
		sink(i);
		pq[n+1] = null;
	}
	
	private void sink(int k){
		while(2*k <= n){
			int j = 2*k;
			if(j < n && greater(j, j+1)){
				j++;
			}
			if(!greater(k, j)){break;}
			exch(k, j);
			k = j;
		}
	}
	
	private void swim(int k){
		while (k > 1 && greater(k/2, k)) {
			exch(k, k/2);
			k /= 2;
		}
		
	}
	
	private boolean greater(int i, int j){
		return (pq[i].compareTo(pq[j])) > 0;
	}
	
	private boolean less(int i, int j){
		K car1 = pq[i];
		K car2 = pq[j];
		Car first = (Car)car1;
		Car second = (Car)car2;
		return (first.compareMileage(second)) < 0;
	}
	
	private boolean lessPrice(int i, int j){
		K car1 = pq[i];
		K car2 = pq[j];
		Car first = (Car)car1;
		Car second = (Car)car2;
		return (first.compareTo(second)) < 0;
	}

	private void exch(int i, int j){
		K swap = pq[i];
		K swap2 = pq[j];
		Car first = (Car)swap;	//Get cars to change in hash
		Car second = (Car)swap2;
		pq[i] = pq[j];
		pq[j] = swap;
		changePositions(first, second);
	}
	
	private void changePositions(Car first, Car second)	//Change positions in map
	{
		int firstValue = myMap.get(first.getVin());
		int secondValue = myMap.get(second.getVin());
		myMap.put(first.getVin(), secondValue);
		myMap.put(second.getVin(), firstValue);
	}
	
	public void sort(int option){
		Sort(option);
	}
	
	private void Sort(int option){	//Sort by lowest mileage
		int N = n;
		for(int k = N/2; k >=1; k--){
			if(option == 1){sortSink(k,N);}
			else{sortPrice(k,N);}
		}
		while(N > 1){
			exch(1, N--);
			if(option == 1){sortSink(1, N);}
			else{sortPrice(1,N);}
		}
	}
	
	private void sortSink(int k, int N){ //Sink for lowest mileage
		while(2*k <=N){
			int j = 2*k;
			if(j < N && less(j, j+1))j++;
			if(!less(k,j)) break;
			exch(k,j);
			k = j;
		}
	}
	
	private void sortPrice(int k, int N){ //Sink for lowest price
		while(2*k <=N){
			int j = 2*k;
			if(j < N && lessPrice(j, j+1))j++;
			if(!lessPrice(k,j)) break;
			exch(k,j);
			k = j;
		}
	}
	
	public void getLowestPrice(String make, String model){
		Car low = null;
		boolean found = false;
		for(int i = 1; i <=n; i++){
			K temp = pq[i];
			Car cur = (Car)temp;
			if(cur.getMake().equals(make)){
				if(cur.getModel().equals(model)){
					if(!found){ 
					found = true;
					low = cur;
					}
					else{
						if(cur.getPrice() < low.getPrice()){ low = cur;}
					}
				}
			}
		}
		if(low == null){
		System.out.println("\nSorry, car was not found");
		}
		else{
		System.out.println(low.toString());
		}
	}
	
	public void getLowestMileage(String make, String model){
		Car low = null;
		boolean found = false;
		for(int i = 1; i <=n; i++){
			K temp = pq[i];
			Car cur = (Car)temp;
			if(cur.getMake().equals(make)){
				if(cur.getModel().equals(model)){
					if(!found){ 
					found = true;
					low = cur;
					}
					else{
						if(cur.getMileage() < low.getMileage()){ low = cur;}
					}
				}
			}
		}
		if(low == null){
		System.out.println("\nSorry, car was not found");
		}
		else{
		System.out.println(low.toString());
		}
	}
		
}