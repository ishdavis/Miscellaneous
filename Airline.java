import java.util.Scanner;
import java.io.*;
import java.util.*;
public class Airline
{
	private static Vertex [] graph; //Index begins at 1
	private static Scanner inScan;
	private static String thisFile;
	private static String filename; //Substitute filename into the toFile and setTable methods
	private static final int MAX = 1000000; //for dijkstra's
	private static final double MAXED = 1000000;//for dijkstra's
	private static double userMax = 0;
	
	public static void main(String[] args) throws IOException{
		inScan = new Scanner(System.in);
		setTable();
		System.out.println("Welcome to the Airline! Please choose an option below by inputting the integer");
		while(true){
			System.out.println("\n1. Show All Routes");
			System.out.println("2. Minimum Spanning Tree");
			System.out.println("3. Distance Shortest Path");
			System.out.println("4. Price Shortest Path");
			System.out.println("5. Hops Shortest Path");
			System.out.println("6. All Trips Under a Price");
			System.out.println("7. Add a new Route");
			System.out.println("8. Remove a Route");
			System.out.println("9. Quit");
			int input = Integer.parseInt(inScan.next());
			if(input == 1){printGraph();}
			else if(input == 2){kruskals();}
			else if(input == 3){dijkstraDistance();}
			else if(input == 4){dijkstraPrice();}
			else if(input == 5){shortestHop();}
			else if(input == 6){userPrice();}
			else if(input == 7){addRoute();}
			else if(input == 8){deleteRoute();}
			else if(input == 9){toFile(); System.exit(0);}

		}
	}
	
	private static void printGraph(){
		System.out.println("\nThe list of routes and prices begins below:\n");
		for(int i = 1; i < graph.length; i++){
			graph[i].printEdges();
		}
	}
	
	private static void dijkstraPrice(){
		System.out.println("Please input the flight origin");	//User input
		String s = inScan.next();
		System.out.println("Please input the flight destination");
		String d = inScan.next();
		int source = getArrayPosition(s);
		int destination = getArrayPosition(d);
		//Initialize data structures
		int times = 0;
		double [] price = new double[graph.length];
		int [] previous = new int[graph.length];
		boolean [] chosen = new boolean[graph.length];
		for(int i = 1; i < graph.length; i++){
			if(i != source){
				price[i] = MAXED;
			}
			if(i == source){
				price[i] = 0;
			}
		}
		int size = graph.length;
		boolean done = false;
		while(size != 0 && done == false && source != destination){
			size--;
			int min = getMin(price, chosen);
			Vertex temp = graph[min];
			ArrayList<Edge> edges = temp.edgeList();
			for(int i = 0; i < edges.size(); i++){
				Vertex dest = edges.get(i).getDestination();
				if(dest.getPosition() == destination){
					previous[dest.getPosition()] = min;
					price[dest.getPosition()] = edges.get(i).getPrice(); //Might not need it
					i = edges.size();
					done = true;
				}
				else if(chosen[dest.getPosition()] != true){
					double alt = price[min] + edges.get(i).getPrice();
					if(alt < price[dest.getPosition()]){
						price[dest.getPosition()] = alt;
						previous[dest.getPosition()] = min;
					}
				}
			}
		}
		if(done == false){
			System.out.println("Sorry no path was found");
			return;
		}
		Stack<Integer> stack = new Stack<Integer>();
		stack.push(destination);
		
		int otherdest = destination;
		while(previous[otherdest] != 0){
			stack.push(previous[otherdest]);
			otherdest = previous[otherdest];
		}
		
		double total = 0;
		while(!stack.empty()){
				int num = stack.pop();
				Vertex temps = graph[num];
				if(destination != num){
					Edge tempEdge = temps.getEdge(stack.peek());
					System.out.println(tempEdge.printPrice());
					total += tempEdge.getPrice();
				}
		}
		System.out.println("Total cost is $" + total);
	}
	
	public static int getMin(double [] price, boolean [] chosen){
		double min = MAXED;
		int minIndex = 0;
		for(int i = 1; i < price.length; i++){
			if(price[i] < min && !chosen[i]){min = price[i]; minIndex = i;}
		}
		chosen[minIndex] = true;
		return minIndex;
	}
	
	private static void dijkstraDistance(){
		System.out.println("Please input the flight origin");	//User input
		String s = inScan.next();
		System.out.println("Please input the flight destination");
		String d = inScan.next();
		int source = getArrayPosition(s);
		int destination = getArrayPosition(d);
		//Initialize data structures
		int times = 0;
		int [] distance = new int[graph.length];
		int [] previous = new int[graph.length];
		boolean [] chosen = new boolean[graph.length];
		for(int i = 1; i < graph.length; i++){
			if(i != source){
				distance[i] = MAX;
			}
			if(i == source){
				distance[i] = 0;
			}
		}
		int size = graph.length;
		boolean done = false;
		while(size != 0 && done == false && source != destination){
			size--;
			int min = getMin(distance, chosen);
			Vertex temp = graph[min];
			ArrayList<Edge> edges = temp.edgeList();
			for(int i = 0; i < edges.size(); i++){
				Vertex dest = edges.get(i).getDestination();
				if(dest.getPosition() == destination){
					previous[dest.getPosition()] = min;
					distance[dest.getPosition()] = edges.get(i).getDistance(); //Might not need it
					i = edges.size();
					done = true;
				}
				else if(chosen[dest.getPosition()] != true){
					int alt = distance[min] + edges.get(i).getDistance();
					if(alt < distance[dest.getPosition()]){
						distance[dest.getPosition()] = alt;
						previous[dest.getPosition()] = min;
					}
				}
			}
		}
		if(done == false){
			System.out.println("Sorry no path was found");
			return;
		}
		Stack<Integer> stack = new Stack<Integer>();
		stack.push(destination);
		
		int otherdest = destination;
		while(previous[otherdest] != 0){
			stack.push(previous[otherdest]);
			otherdest = previous[otherdest];
		}
		
		int total = 0;
		while(!stack.empty()){
				int num = stack.pop();
				Vertex temps = graph[num];
				if(destination != num){
					Edge tempEdge = temps.getEdge(stack.peek());
					System.out.println(tempEdge.printDistance());
					total += tempEdge.getDistance();
				}
		}
		System.out.println("Total mileage is " + total);
	}
	
	public static int getMin(int [] distance, boolean [] chosen){
		int min = MAX, minIndex = 0;
		for(int i = 1; i < distance.length; i++){
			if(distance[i] < min && !chosen[i]){min = distance[i]; minIndex = i;}
		}
		chosen[minIndex] = true;
		return minIndex;
	}
	
	private static void kruskals(){//Implement kruskals to find minimum spanning tree
		//Initialize data structures
		ArrayList<Edge> edges = new ArrayList<Edge>();	//Initialize edge table
		for(int i = 1; i < graph.length; i++){
			ArrayList<Edge> temp = graph[i].edgeList();
			for(int k = 0; k < temp.size(); k++){
				edges.add(temp.get(k));
			}
		}
		Collections.sort(edges, new Comparator<Edge>(){	//Sort the edges
			public int compare(Edge edge1, Edge edge2){
				return edge2.compareTo(edge1);
				}
		});
		int [] index = new int[graph.length];
		ArrayList<Integer>[] cluster = (ArrayList<Integer>[])new ArrayList[graph.length];	//Cluster
		for(int i = 1; i < index.length; i++){
			index[i] = i; 
			ArrayList<Integer> temp = new ArrayList<Integer>();
			temp.add(i);
			cluster[i] = temp;
		}
		Queue<Edge> q = new LinkedList<Edge>();	//Output edges at the end
		int time = 0;
		for(int k = 0; k < edges.size(); k++){
			time++;
			Edge temp = edges.get(k);
			int source = temp.getSource().getPosition();
			int destination = temp.getDestination().getPosition();
			//do checking first here
			if(index[source] == index[destination]){}
			//adding here
			else if(source != index[source] && destination != index[destination]){//want to add destination to where source is
				int actual = index[source], clustered = index[destination];
				for(int i = 0; i < cluster[clustered].size(); i++){
					int value = cluster[clustered].get(i);
					index[value] = index[actual];
				}
				cluster[actual].addAll(cluster[clustered]);
				q.add(temp);
			}
			else if(source == index[source] && destination != index[destination]){
				int clustered = index[source];
				for(int i = 0; i < cluster[clustered].size(); i++){
					int value = cluster[clustered].get(i);
					index[value] = index[destination];
				}
				cluster[index[source]].addAll(cluster[source]);
				q.add(temp);
			}
			else if(source != index[source] && destination == index[destination]){//swap source and destination
				int clustered = index[destination];
				for(int i = 0; i < cluster[clustered].size(); i++){
					int value = cluster[clustered].get(i);
					index[value] = index[source];
				}
				cluster[index[destination]].addAll(cluster[destination]);
				q.add(temp);
			}
			else{
				int clustered = index[destination];
				for(int i = 0; i < cluster[clustered].size(); i++){
					int value = cluster[clustered].get(i);
					index[value] = index[source];
				}
				cluster[index[destination]].addAll(cluster[destination]);
				q.add(temp);
				/*for(int i = 0; i < index.length; i++){
					//System.out.print(index[i] + " ");
				}
				//System.out.println("\n");*/
			}
			
		}
		while(true){
			Edge temp = q.poll();
			if(temp == null){
				return;
			}
			System.out.println(temp.printDistance());
		}
		
	}
	
	private static void shortestHop(){//Implement a bfs and find the shortest path
		boolean[] passed = new boolean[graph.length];	//Data structures
		Queue<Integer> q = new LinkedList<Integer>();
		int[] previouslySeen = new int[graph.length];
		System.out.println("Please input the flight origin");	//User input
		String s = inScan.next();
		System.out.println("Please input the flight destination");
		String d = inScan.next();
		//Work is done below
		boolean finished = false;
		int source = getArrayPosition(s);
		int destination = getArrayPosition(d);	
		q.add(source);//r is the initial index of the town
		passed[source] = true;
		while(!q.isEmpty() && !finished){	//start bfs below
			int i = q.remove();
			ArrayList<Edge> edges = graph[i].edgeList();
			for(int k = 0; k < edges.size(); k++){
				Edge temp = edges.get(k);
				if(temp.getDestination().getPosition() == destination){
					finished = true;
					previouslySeen[temp.getDestination().getPosition()] = i;
				}
				else if(!passed[temp.getDestination().getPosition()]){
					q.add(temp.getDestination().getPosition());
					passed[temp.getDestination().getPosition()] = true;
					previouslySeen[temp.getDestination().getPosition()] = i;
				}
				else{}
			}
		}//end bfs
		if(!finished){System.out.println("Sorry a path wasn't found");}
		else{	//Come back to print out result
			Stack<Edge> stacker = new Stack<Edge>();
			int start = previouslySeen[destination];
			Edge temp = graph[destination].getEdge(start);
			stacker.push(temp);
			while(start != 0){
				int previous = start;
				start = previouslySeen[start];
				if(start != 0){
					Edge temp1 = graph[previous].getEdge(start);
					stacker.push(temp1);
				}
			}
			double totalPrice = 0;
			while(!stacker.empty()){	//actually print out result
				Edge printed = stacker.pop();
				totalPrice += printed.getPrice();
				System.out.println(printed.printHop());
			}
			System.out.println("The total price is $" + totalPrice);
		}
		
	}
	
	private static void userPrice(){
		System.out.println("Please input the maximum price");
		userMax = inScan.nextDouble();
		for(int i = 1; i < graph.length; i++){
			ArrayList<Integer> temp = new ArrayList<Integer>();
			int [] source = new int[graph.length];
			getAll(graph[i], 0, temp, source, i);
			System.out.print("\n");
		}
	}
	
	private static void getAll(Vertex current, double cost, ArrayList<Integer> previous, int [] source, int digit){
		previous.add(current.getPosition());
		ArrayList<Edge> edges = current.edgeList();
		for(int i = 0; i < edges.size(); i++){
			double price = edges.get(i).getPrice();
			if((cost + price) < userMax && !previous.contains(edges.get(i).getDestination().getPosition())){
					source[edges.get(i).getDestination().getPosition()] = current.getPosition();
					int temp = edges.get(i).getDestination().getPosition();
					Stack<Vertex> q = new Stack<Vertex>();
					while(source[temp] != digit){
						int newPosition = source[source[temp]];
						q.push(graph[newPosition]);
						temp = source[temp];
					}
					while(!q.empty()){
						Vertex temps = q.pop();
						System.out.print(temps.getName() + " to ");
					}
					System.out.print(edges.get(i).printPrice() + ", "); //Finish this
					System.out.println("total cost: $" + (price + cost));
				getAll(edges.get(i).getDestination(), price + cost, previous, source, digit); //Possibly here
			}
		}
	}
	
	private static void toFile() throws IOException{//Outputs both directions of each flight 
		FileWriter fwriter = new FileWriter(thisFile, false);
		PrintWriter outputFile = new PrintWriter(fwriter);
		
		outputFile.println(graph.length - 1);
		for(int i = 1; i < graph.length; i++){
			outputFile.println(graph[i].getName());
		}
		for(int i = 1; i < graph.length; i++){
			outputFile.print(graph[i].getOutput());
		}
		outputFile.close();
	}
	
	private static void deleteRoute(){//Delete edge in graph
		System.out.println("Input the flight origin by giving the int position in the input file");
		int position1 = inScan.nextInt();
		System.out.println("Input the flight destination by giving the int position in the input file");
		int position2 = inScan.nextInt();
		//Get vertices
		Vertex first = graph[position1]; //Delete vertices 
		Vertex second = graph[position2];
		if(first.connectedTo(position2)){
			first.deleteEdge(position2);
			second.deleteEdge(position1);
		}
		else{
			System.out.println("Sorry that connection didn't exist");
		}
	}
	
	private static void addRoute(){ //Recieve input from user as an integer index
		System.out.println("Input the flight origin by giving the int position in the input file");
		int position1 = inScan.nextInt();
		System.out.println("Input the flight destination by giving the int position in the input file");
		int position2 = inScan.nextInt();
		System.out.println("Input the price of the trip");
		double price = inScan.nextDouble();
		System.out.println("Input the distance of the trip");
		int distance = inScan.nextInt();
		
		//Actually do work on the edge
		Vertex first = graph[position1];
		Vertex second = graph[position2];
		if(first.connectedTo(position2)){	//If they're already connected, update
			Edge temp = first.getEdge(position2);
			Edge temp1 = second.getEdge(position1);
			temp.setPrice(price);
			temp.setDistance(distance);
			temp1.setPrice(price);
			temp1.setDistance(distance);
		}
		else{	//If they're not connected, connect them
			Edge newEdge = new Edge(distance, price, graph[position1], graph[position2]);
			Edge secondEdge = new Edge(distance, price, graph[position2], graph[position1]);
			graph[position1].addToList(newEdge);
			graph[position2].addToList(secondEdge);
		}
	}
	
	private static void setTable() throws IOException{	//Initially set up Graph
		System.out.println("Please input the name of the text file");
		thisFile = inScan.next();
		File myFile = new File(thisFile);
		Scanner input = new Scanner(myFile);
		int vertices = input.nextInt();
		graph = new Vertex[vertices + 1]; 	//initialize graph
		for(int i = 1; i < vertices + 1; i++){
			String name = input.next();
			Vertex temp = new Vertex(name, i);
			graph[i] = temp;
		}
		String here = input.nextLine();	//consume extra input character
		while(input.hasNext()){//Populate Graph
			String [] temp1 = input.nextLine().split(" ");
			int source = Integer.parseInt(temp1[0]);
			int destination = Integer.parseInt(temp1[1]);
			int distance = Integer.parseInt(temp1[2]);
			double price = Double.parseDouble(temp1[3]);
			Edge first = new Edge(distance,price,graph[source],graph[destination]);
			Edge second = new Edge(distance, price, graph[destination], graph[source]);
			graph[source].addToList(first);
			graph[destination].addToList(second);
		}
	}
	
	public static int getArrayPosition(String city){
		for(int i = 1; i < graph.length; i++){
			if(graph[i].getName().toLowerCase().equals(city.toLowerCase())){
				return i;
			}
		}
		return -1;
	}
}