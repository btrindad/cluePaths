package board;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
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
		for (int i = 0; i < (MAP_SIZE*MAP_SIZE); i++) {
			ArrayList<Integer> adjList = new ArrayList<Integer>();

			if (i%4 == 0) {
				adjList.add(i + 1);

				if (i < (MAP_SIZE*MAP_SIZE - MAP_SIZE)) {
					adjList.add(i + MAP_SIZE);
				}

				if (i > 0) {
					adjList.add(i - MAP_SIZE);
				}
			}
			else if (i%4 == 1) {
				adjList.add(i + 1);
				adjList.add(i - 1);

				if (i < (MAP_SIZE*MAP_SIZE - (MAP_SIZE - 1))) {
					adjList.add(i + MAP_SIZE);
				}

				if (i > 1) {
					adjList.add(i - MAP_SIZE);
				}
			}
			else if (i%4 == 2) {
				adjList.add(i + 1);
				adjList.add(i - 1);

				if (i < (MAP_SIZE*MAP_SIZE - (MAP_SIZE - 2))) {
					adjList.add(i + MAP_SIZE);
				}

				if (i > 2) {
					adjList.add(i - MAP_SIZE);
				}

			}
			else if (i%4 == 3) {
				adjList.add(i - 1);

				if (i < (MAP_SIZE*MAP_SIZE - (MAP_SIZE - 3))) {
					adjList.add(i + MAP_SIZE);
				}

				if (i > 3) {
					adjList.add(i - MAP_SIZE);
				}
			}

			adjMap.put(i, adjList);
		}
	}
	
	//wrapper class for target calculation logic
	public void startTargets(int location, int steps) {
		Set<Integer> targets = new HashSet<Integer>();
		
		//need to add error handling for negative numbers?
		//for now decided to use absolute value to at least prevent infinite loops while testing
		calcTargets(location, Math.abs(steps), targets);
	}
	
	//recursive function to find all targets for a given location.
	public static void calcTargets(int location, int steps, Set<Integer> targets){
		visited[location] = true;
		if(steps == 0){
			targets.add(location);
		}
		else{
			for(int adj : getAdjList(location)){
				if(!visited[adj]){
					calcTargets(adj, steps-1, targets);
				}
			}
		}
		visited[location] = false;
	}
	
	public Set<Integer> getTargets() {
		return null;
	}
	
	public static ArrayList<Integer> getAdjList(int cell) {
		return adjMap.get(cell);
	}
	
	public static int calcIndex(int row, int column) {
		return ((4*row) + column);
	}
	
}
