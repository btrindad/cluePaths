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
	
	//wrapper class for target calculation logic
	public void startTargets(int location, int steps) {
		ArrayList targets = new ArrayList<Integer>();
		calcTargets(location, Math.abs(steps), targets);
	}
	
	//recursive function to find all targets for a given location.
	public static void calcTargets(int location, int steps, ArrayList<Integer> targets){
		visited[location] = true;
		if(steps == 0){
			targets.add(location);
		}
		else{
			for(int adj : getAdjList(location)){
				if(!visited[adj]){
					calcTargets(adj, steps--, targets);
				}
			}
		}
		visited[location] = false;
	}
	
	public Set<Integer> getTargets() {
		return null;
	}
	
	//added filler so test fails instead of returning a null pointer exception
	public static ArrayList<Integer> getAdjList(int cell) {
		ArrayList<Integer> test = new ArrayList();
		return test;
	}
	
	public static int calcIndex(int row, int column) {
		return ((4*row) + column);
	}
	
}
