
public class Edge
{
	private int distance;
	private double price;
	private Vertex source, destination;
	
	public Edge(int distance, double price, Vertex source, Vertex destination){
		this.distance = distance;
		this.price = price;
		this.source = source;
		this.destination = destination;
	}
	
	public int getDistance(){return distance;}
	public double getPrice(){return price;}
	public Vertex getSource(){return source;}
	public Vertex getDestination(){return destination;}
	public String toString(){
		StringBuilder b = new StringBuilder();
		b.append("source is " + source.toString());
		b.append("\ndestination is " + destination.toString());
		return b.toString();
	}
	public void setPrice(double price){this.price = price;}
	public void setDistance(int distance){this.distance = distance;}
	
	public String printAll(){
		StringBuilder b = new StringBuilder();
		b.append(source.getName() + " to " + destination.getName() + " is " + distance + " miles for $" + price);
		return b.toString();
	}
	
	public String getOutput(){
		StringBuilder b = new StringBuilder();
		b.append(source.getPosition() + " " + destination.getPosition() + " " + distance + " " + price + "\n");
		return b.toString();
	}
	
	public String printHop(){
		StringBuilder b = new StringBuilder();
		b.append(destination.getName() + " to " + source.getName() + " for $" + price);
		return b.toString();
	}
	
	public String printDistance(){
		StringBuilder b = new StringBuilder();
		b.append(source.getName() + " to " + destination.getName() + " at " + distance + " miles");
		return b.toString();
	}
	
	public String printPrice(){
		StringBuilder b = new StringBuilder();
		b.append(source.getName() + " to " + destination.getName() + " at $" + price);
		return b.toString();
	}
	
	int compareTo(Edge other){
		if(this.distance < other.getDistance()){return 1;}
		else if(this.distance == other.getDistance()){return 0;}
		else{return -1;}
	}
	

}