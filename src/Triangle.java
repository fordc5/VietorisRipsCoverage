import java.util.HashMap;

public class Triangle {

	public int node1, node2, node3;
	private Segment edge1, edge2, edge3;
	private HashMap<Segment, Integer> edgeCoefficientMap;
	
	public Triangle(int node1, int node2, int node3) {
		this.node1 = node1;
		this.node2 = node2;
		this.node3 = node3;
		
		edge1 = new Segment(node1, node2);
		edge2 = new Segment(node2, node3);
		edge3 = new Segment(node1, node3);
		
		
		edgeCoefficientMap = new HashMap<Segment, Integer>();
		edgeCoefficientMap.put(edge1, 1);
		edgeCoefficientMap.put(edge2, 1);
		edgeCoefficientMap.put(edge3, -1);
	}
	
	
	public HashMap<Segment, Integer> getEdgeCoefficientMap() {
		return edgeCoefficientMap;
	}
	
	public String toString() {
		return "(" + node1 + "," + node2 + "," + node3 + ")";
	}
	
	public static void main(String[] args) {
		//test construction
		Triangle t = new Triangle(5,6,7);
		System.out.println(t.getEdgeCoefficientMap());
		

	}

}
