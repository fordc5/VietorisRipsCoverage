import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Hashtable;
import java.util.Set;
import org.apache.commons.math3.linear.*;

public class NetworkConstruction {

	private Hashtable<Integer, Set<Integer>> network;
	private ArrayList<Triangle> triangles;
	private ArrayList<Segment> edges;
	
	public NetworkConstruction (String path) {
		network = new Hashtable<Integer, Set<Integer>>();
		triangles = new ArrayList<Triangle>();
		edges = new ArrayList<Segment>();
		
		// read file and populate network Hashtable
		try {
			BufferedReader br = new BufferedReader(new FileReader(path));
			String line;
			while ((line = br.readLine()) != null) {
			    line = line.trim();
			    String[] lineSplit = line.split("\\s+");
			    int node0 = Integer.parseInt(lineSplit[0]);
			    int node1 = Integer.parseInt(lineSplit[1]);
			    if (!network.containsKey(node0)) {
			    	Set<Integer> h = new HashSet<Integer>(Arrays.asList(node1));
			    	network.put(node0, h);
			    } else {
			    	Set<Integer> existingSet = network.get(node0);
			    	existingSet.add(node1);
			    	network.put(node0, existingSet);
			    }
			    if (!network.containsKey(node1)) {
			    	Set<Integer> h = new HashSet<Integer>(Arrays.asList(node0));
			    	network.put(node1, h);
			    } else {
			    	Set<Integer> existingSet = network.get(node1);
			    	existingSet.add(node0);
			    	network.put(node1, existingSet);
			    }
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	private void findTriangles() {
		Set<Integer> nodes = network.keySet();
		for (int headNode : nodes) {
			for (int tailNode : network.get(headNode)) {
				//add to edges
				Segment edge = new Segment (headNode, tailNode);
				edges.add(edge);
				
				//iterate over all nodes and create triangles
				for (int thirdNode : nodes) {
					if (network.get(thirdNode).contains(headNode) && network.get(thirdNode).contains(tailNode)) {
						//then we have a triangle. 
						Triangle t = new Triangle(headNode, tailNode, thirdNode);
						triangles.add(t);
					}
				}
			}
		}
	}
	

	private double[][] generateTriangleEdgeMatrix() {
		
		double[][] triMat = new double[getNumEdges()][triangles.size()]; //[rows][cols]
		
		for (int triIndex = 0; triIndex < triangles.size(); triIndex++) {
			HashMap<Segment, Integer> edgeValueMap = triangles.get(triIndex).getEdgeCoefficientMap();
			for (Segment edge : edgeValueMap.keySet()) {
				int row = edges.indexOf(edge);
				int value = edgeValueMap.get(edge);
				int col = triIndex;
				triMat[row][col] = value;
			}
		}
		return triMat;
		
	}
	
	//solve Mx = b
	private RealVector solveMatrix(double[][] M, double[] b) {
		
		RealMatrix coefficients =
			    new Array2DRowRealMatrix(M);
		DecompositionSolver solver = new QRDecomposition(coefficients).getSolver();
		RealVector constants = new ArrayRealVector(b);
		RealVector solution = solver.solve(constants);
		return solution;
	}
	
	
	
	public boolean vietorisRipsCoverageTest(ArrayList<Segment> gamma) {
		
		findTriangles();
		System.out.println(triangles.size());
		
		// construct b column vector with values of 1 for edges in gamma
		// and zeros for everything else.
		double[] b = new double[getNumEdges()];
		for (int index = 0; index < edges.size(); index++) {
			
			if (gamma.indexOf(edges.get(index)) >= 0) {
				b[index] = 1.0;
			}
		}
		
		double[][] M = generateTriangleEdgeMatrix();
		
		RealVector sol = solveMatrix(M, b);
		
		System.out.println("solution" + Arrays.toString(sol.toArray()));
		
		//System.out.println(M.length);
		//System.out.println(M[0].length);
		
		//print2d(M);
		
		System.out.println("okay");
		
		print1d(b);
		
		return false;
	}
	
	public Hashtable<Integer, Set<Integer>> getNetwork() {
		return network;
	}
	
	public void printNetwork() {
		System.out.println(Arrays.toString(network.entrySet().toArray()));
	}
	
	public void printTriangles() {
		System.out.println(triangles);
	}
	
	public int getNumNodes() {
		return network.size();
	}
	
	public int getNumEdges() {
		int count = 0;
		for (int key : network.keySet()) {
			count += network.get(key).size();
		}
		return count;
	}
	
	public void print2d(double[][] arr) {
		System.out.println(Arrays.deepToString(arr).replace("], ", "]\n"));
	}
	
	public void print1d(double[] arr) {
		System.out.println(Arrays.toString(arr));
	}
	
	
	public static void main(String[] args) {
		String test1Path = "data/robotdata1.txt";
		String test2Path = "data/robotdata2.txt";
		String testSimplePath = "data/simplerobot.txt";
		
		NetworkConstruction test2 = new NetworkConstruction(test1Path);
		Segment[] gArr = { new Segment(1,2), new Segment(2,3), new Segment(3,4),
						   new Segment(4,5), new Segment(5,6), new Segment(6,7),
						   new Segment(7,8), new Segment(8,9), new Segment(9,10),
						   new Segment(10,11), new Segment(11,1)
						 };
		ArrayList<Segment> gamma = new ArrayList<Segment>(Arrays.asList(gArr));
		test2.vietorisRipsCoverageTest(gamma);
		//test2.printNetwork();
		//test2.findTriangles();
		//test2.printTriangles();
		//System.out.println(test2.getNumEdges());
		//System.out.println(test2.getNumNodes());
		
		
		
	}

}
