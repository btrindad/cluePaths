package board;

import java.util.ArrayList;
import java.util.Map;
import java.util.Set;


public class IntBoard {
	private Map<Integer, ArrayList<Integer>> adjMap;
	private boolean[] visited;

	public IntBoard() {
		
	}
	
	public void calcAdjacencies() {
		
	}
	
	public void startTargets(int location, int steps) {
		
	}
	
	public static ArrayList<Integer> calcTargets(int location, int steps) {
		return null;
		
	}
	
	public Set<Integer> getTargets() {
		return null;
	}
	
	//added filler so test fails instead of returning a null pointer exception
	public ArrayList<Integer> getAdjList(int cell) {
		ArrayList<Integer> test = new ArrayList();
		return test;
	}
	
	public static int calcIndex(int row, int column) {
		return 0;
	}
	
}
