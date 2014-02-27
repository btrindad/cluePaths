package clueGame;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.Map;
import java.util.Scanner;
import java.util.Set;

public class Board {
	private ArrayList<BoardCell> cells;
	private Map<Character,String> rooms;
	private int numRows;
	private int numColumns;
	private String layoutFile;
	private String legendFile;
	
	//check if these really need to be static
	private Map<Integer, HashSet<Integer>> adjMap;
	private boolean[] visited;
	private Set<Integer> targets;
	
	public Board() {
		cells = new ArrayList<BoardCell>();
		adjMap = new HashMap<Integer, HashSet<Integer>>();
		rooms = new HashMap<Character, String>();
	}
	
	public Board(String layout, String legend) {
		cells = new ArrayList<BoardCell>();
		rooms = new HashMap<Character, String>();
		layoutFile = layout;
		legendFile = legend;
	}
	
	public void loadConfigFiles() {
		try {
			loadBoardConfig();
			loadRoomConfig();
		}
		catch (Exception e) {
			System.out.println(e.getMessage());
		}
	}
	
	public void loadRoomConfig() throws BadConfigFormatException, FileNotFoundException {
		FileReader reader = new FileReader(layoutFile);
		Scanner inScan = new Scanner(reader);
		
		try {
			int i = 0;
			while (inScan.hasNext()) {
				String s = inScan.nextLine();
				String[] queue = s.split(",");
				if (queue.length != numColumns && (numColumns > 0)) {
					throw new BadConfigFormatException("Problem with the format of the board file.");
				}
				
				numColumns = queue.length;
				
				for (int j=0; j<queue.length; j++) {
					String t = queue[j];
					if (!rooms.containsKey(t.charAt(0))) {
						throw new BadConfigFormatException("Problem with the format of the board file: Invalid room key.");
					}
					
					if (t != "W") {
						char tempDD = 'N';
						char tempRI = t.charAt(0);

						if (t.length() > 1) {
							tempDD = t.charAt(1);
						}
						
						RoomCell tempRC = new RoomCell(i, j, tempRI, tempDD);
						cells.add(tempRC);
					}
					else {
						WalkwayCell tempWC = new WalkwayCell(i, j);
						cells.add(tempWC);
					}
				}
				i++;
			}
			numRows = i;
		}
		finally {
			inScan.close();
		}
		
		//is it better to set this to the size of the board, or just the number of visitable areas?
		visited = new boolean[cells.size()];
	}
	
	public void loadBoardConfig() throws BadConfigFormatException, FileNotFoundException {
		FileReader reader = new FileReader(legendFile);
		Scanner inScan = new Scanner(reader);
		
		try {
			while (inScan.hasNext()) {
				String u = inScan.nextLine();
				String[] queue = u.split(",");
				if (queue.length != 2) {
					throw new BadConfigFormatException("Problem with the format of the room legend file.");
				}
				
				//clean up whitespace from the value
				queue[1] = queue[1].replaceFirst(" ", "");
				
				rooms.put(queue[0].charAt(0), queue[1]);
			}
			
		}
		finally {
			inScan.close();
		}
		
	}
	
	//returns legend
	public Map<Character, String> getRooms(){
		return rooms;
	}
	
	public int calcIndex (int row, int column) {;
		return ((numColumns*row) + column);
	}
	
	public BoardCell getCell(int location) {
		return cells.get(location);
	}
	
	public BoardCell getCell(int r, int c) {
		int location = calcIndex(r, c);
		return cells.get(location);
	}
	
	public RoomCell getRoomCell(int r, int c) throws RuntimeException {
		int location = calcIndex(r, c);
		if (cells.get(location).isRoom()) {
			return (RoomCell) cells.get(location);
		}
		
		else { // We chose to handle a non-RoomCell situation by throwing a RuntimeException
			throw new RuntimeException("The given location does not contain a RoomCell.");
		}
	}

	public String getRoom(char c) {
		c = Character.toUpperCase(c);
		return rooms.get(c);
	}
	
	public int getNumRows() {
		return numRows;
	}
	
	public int getNumColumns() {
		return numColumns;
	}
	
	/* ------------------------------------------------- */
	//logic from IntBoard class
	public void calcAdjacencies() {
		BoardCell current;
		HashSet<Integer> adjList;
		for (int i = 0; i < numRows; i++) {
			for (int j = 0; j < numColumns; j++) {
				adjList = new HashSet<Integer>();
				current = getCell(calcIndex(i,j));
				
				if(checkAdjacency(calcIndex(i-1,j))){
					adjList.add(calcIndex(i-1, j));
				}
				if(checkAdjacency(calcIndex(i+1,j))){
					adjList.add(calcIndex(i+1, j));
				}
				if(checkAdjacency(calcIndex(i,j-1))){
					adjList.add(calcIndex(i, j-1));
				}
				if(checkAdjacency(calcIndex(i,j+1))){
					adjList.add(calcIndex(i, j+1));
				}
				
				
				adjMap.put(calcIndex(current.row, current.column), adjList);
				
			}
			
			
			/*
			//ArrayList<Integer> adjList = new ArrayList<Integer>();

			//if i%numColumns == 0, increment row
			if (i%numColumns == 0) {
				adjList.add(i + 1);

				if (i < (numRows*numColumns - numColumns)) {
					adjList.add(i + numColumns);
				}

				if (i > 0) {
					adjList.add(i - numColumns);
				}
			}
			else if (i%numColumns > 0 && i%numColumns < (numColumns - 1)) {
				adjList.add(i + 1);
				adjList.add(i - 1);

				if (i < (numRows*numColumns - numColumns)) {
					adjList.add(i + numColumns);
				}

				if (i > 1) {
					adjList.add(i - numColumns);
				}
			}
			else if (i%numColumns == numColumns - 1) {
				adjList.add(i - 1);

				if (i < (numRows*numColumns - numColumns)) {
					adjList.add(i + numColumns);
				}

				if (i > numColumns - 1) {
					adjList.add(i - numColumns);
				}
			}

			adjMap.put(i, adjList);
			*/
		}
	}
	
	public boolean checkAdjacency(int index){
		return false;
	}
	
	public HashSet<Integer> getAdjList(int cell){
		return adjMap.get(cell);
	}
	
	/*
	 * public static void calcTargets(int location, int steps, Set<Integer> targets){
	}*/
	
	//what is the third parameter supposed to be? the above commented out
	//stub is from IntBoard
	
	public void startTargets(int location, int steps){
		
	}
	public void calcTargets(int location, int steps){
		visited[location] = true;
		if(steps == 0){
			targets.add(location);
		}
		else{
			for(int adj : getAdjList(location)){
				if(!visited[adj]){
					calcTargets(adj, steps-1);
				}
			}
		}
		visited[location] = false;
	}
	
	public Set<BoardCell> getTargets(){
		//filler to prevent errors
		return new HashSet<BoardCell>();
	}
	
}
