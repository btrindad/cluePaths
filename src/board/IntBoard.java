package board;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;


public class IntBoard {
	private Map<Integer, ArrayList<Integer>> adjMap;
	private final static int MAP_SIZE = 4;
	private boolean[] visited;

	public IntBoard() {
		adjMap = new HashMap<Integer, ArrayList<Integer>>();
		visited = new boolean[MAP_SIZE * MAP_SIZE];
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
		ArrayList<Integer> adjList = new ArrayList<Integer>();
		
		if (cell%4 == 0) {
			adjList.add(cell + 1);
			
			if (cell < (MAP_SIZE*MAP_SIZE - MAP_SIZE)) {
				adjList.add(cell + MAP_SIZE);
			}
			
			if (cell > 0) {
				adjList.add(cell - MAP_SIZE);
			}
		}
		else if (cell%4 == 1) {
			adjList.add(cell + 1);
			adjList.add(cell - 1);
			
			if (cell < (MAP_SIZE*MAP_SIZE - (MAP_SIZE - 1))) {
				adjList.add(cell + MAP_SIZE);
			}
			
			if (cell > 1) {
				adjList.add(cell - MAP_SIZE);
			}
		}
		else if (cell%4 == 2) {
			adjList.add(cell + 1);
			adjList.add(cell - 1);
			
			if (cell < (MAP_SIZE*MAP_SIZE - (MAP_SIZE - 2))) {
				adjList.add(cell + MAP_SIZE);
			}
			
			if (cell > 2) {
				adjList.add(cell - MAP_SIZE);
			}
			
		}
		else if (cell%4 == 3) {
			adjList.add(cell - 1);
			
			if (cell < (MAP_SIZE*MAP_SIZE - (MAP_SIZE - 3))) {
				adjList.add(cell + MAP_SIZE);
			}
			
			if (cell > 3) {
				adjList.add(cell - MAP_SIZE);
			}
		}
		
		return adjList;
	}
	
	public static int calcIndex(int row, int column) {
		return ((4*row) + column);
	}
	
}
