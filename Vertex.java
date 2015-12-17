import java.util.*;
public class Vertex{

	private String name;
	private int position;
	private ArrayList<Edge> adjacency;
	
	public Vertex(String name, int position){
		this.name = name;
		this.position = position;
		adjacency = new ArrayList<Edge>();
	}
	
	public String getName(){return name;}
	public int getPosition(){return position;}
	public void addToList(Edge e){adjacency.add(e);}
	public String toString(){
		StringBuilder b = new StringBuilder();
		b.append("name is " + name);
		b.append("\n position is " + position);
		return b.toString();
	}
	
	public void printEdges(){
		int size = adjacency.size();
		for(int i = 0; i < size; i++){
			System.out.println(adjacency.get(i).printAll() + "\n");
		}
	}
	
	public boolean connectedTo(int connection){
		for(int i = 0; i < adjacency.size(); i++){
			if(adjacency.get(i).getDestination().getPosition() == connection){return true;}
		}
		return false;
	}
	
	public void deleteEdge(int position){
		for(int i = 0; i < adjacency.size(); i++){
			if(adjacency.get(i).getDestination().getPosition() == position){
				adjacency.remove(i);
				return;
			}
		}
	}
	
	public String getOutput(){
		StringBuilder b = new StringBuilder();
		for(int i = 0; i < adjacency.size(); i++){
			b.append(adjacency.get(i).getOutput());
		}
		return b.toString();
	}
	
	public ArrayList<Edge> edgeList(){return adjacency;}
	
	public Edge getEdge(int position){
		for(int i = 0; i < adjacency.size(); i++){
			if(adjacency.get(i).getDestination().getPosition() == position){return adjacency.get(i);}
		}
		return null;
	}
	
}