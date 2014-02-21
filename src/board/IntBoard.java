package board;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;


public class IntBoard {
	//check if these really need to be static
	private static Map<Integer, ArrayList<Integer>> adjMap;
	private final static int MAP_SIZE = 4;
	private static boolean[] visited;

	public IntBoard() {
		adjMap = new HashMap<Integer, ArrayList<Integer>>();
		visited = new boolean[MAP_SIZE * MAP_SIZE];
	}
	
	public void calcAdjacencies() {
		
	}
	
	public void startTargets(int location, int steps) {
		ArrayList targets = new ArrayList<Integer>();
	}
	
	public static void calcTargets(int location, int steps, ArrayList<Integer> targets){
		visited[location] = true;
		if(steps == 1){
			targets.add(location);
		}
		else{
			for(int adj : adjMap.get(location)){
				calcTargets(adj, steps--, targets);
			}
		}
		visited[location] = false;
		return;
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
		return ((4*row) + column);
	}
	
}
