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

import clueGame.RoomCell.DoorDirection;

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
		adjMap = new HashMap<Integer, HashSet<Integer>>();
		targets = new HashSet<Integer>();
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

					if (t.charAt(0) != 'W') {
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
		for(int i=0; i<visited.length; i++){
			visited[i] = false;
		}
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

	public int calcIndex (int row, int column) {
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

				if((i - 1) >= 0 && checkAdjacency(calcIndex(i-1,j), calcIndex(i,j))){
					adjList.add(calcIndex(i-1, j));

				}

				if((i + 1) < numRows && checkAdjacency(calcIndex(i+1,j), calcIndex(i,j))){
					adjList.add(calcIndex(i+1, j));
				}

				if((j-1) >= 0 && checkAdjacency(calcIndex(i,j-1), calcIndex(i,j))){
					adjList.add(calcIndex(i, j-1));
				}

				if((j+1) < numColumns && checkAdjacency(calcIndex(i,j+1), calcIndex(i,j))){
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


	public boolean checkAdjacency(int index, int origin){
		
		if (cells.get(index).isWalkway()) {
			if (cells.get(origin).isWalkway()) {
				return true;
			}
			else if (cells.get(origin).isDoorway()) {
				RoomCell oRoomCell = (RoomCell)cells.get(origin);
				if (oRoomCell.getDoorDirection() == DoorDirection.DOWN && index == origin + numColumns) {
					return true;
				}
				else if (oRoomCell.getDoorDirection() == DoorDirection.UP && index == origin - numColumns) {
					return true;
				}
				else if (oRoomCell.getDoorDirection() == DoorDirection.LEFT && index == origin - 1) {
					return true;
				}
				else if (oRoomCell.getDoorDirection() == DoorDirection.RIGHT && index == origin + 1) {
					return true;
				}
				else {
					return false;
				}
			}
			
			else {
				return false;
			}
			
		}
		else if (cells.get(index).isDoorway()) {
			RoomCell iRoomCell = (RoomCell)cells.get(index);
			if (cells.get(origin).isWalkway()) {
				if (iRoomCell.getDoorDirection() == DoorDirection.UP && index == origin + numColumns) {
					return true;
				}
				else if (iRoomCell.getDoorDirection() == DoorDirection.DOWN && index == origin - numColumns) {
					return true;
				}
				else if (iRoomCell.getDoorDirection() == DoorDirection.LEFT && index == origin + 1) {
					return true;
				}
				else if (iRoomCell.getDoorDirection() == DoorDirection.RIGHT && index == origin - 1) {
					return true;
				}
				else {
					return false;
				}
				
			}
			else {
				return false;
			}
		}
		else if (cells.get(index).isRoom()) {
			return false;
		}
		else {
			return false;
		}
		
	}
		
		
	public HashSet<Integer> getAdjList(int cell){
		return adjMap.get(cell);

	}


	public void startTargets(int row, int column, int steps){
		for(int i=0; i<visited.length; i++){
			visited[i] = false;
		}
		targets.clear();
		calcTargets(calcIndex(row, column), steps);
	}

	public void calcTargets(int location, int steps){
		visited[location] = true;
		if(steps == 0){
			targets.add(location);
		}
		else{
			try{
				if(!getAdjList(location).isEmpty()){
					for(int adj : getAdjList(location)){
						if(!visited[adj]){
							if(getCell(adj).isDoorway()){
								
								targets.add(adj);
							} else{
								calcTargets(adj, steps-1);
							}
						}
					}
				}
			} catch (NullPointerException e){
				System.out.println(e.getMessage());
			}
		}
		visited[location] = false;
	}

	//this function checks if from a given cell, if the given door can be entered from the direction
	public boolean checkDoorDirection(BoardCell current, BoardCell door){
		if(!door.isDoorway()){ return false; }
		else{
			int diff = calcIndex(current.row, current.column)-calcIndex(door.row, door.column);
			//at this point we are guaranteed that door is actually a doorway, thus we are safe to cast as RoomCell
			switch(((RoomCell) door).getDoorDirection()){
			case UP: if( diff == (-1*numColumns) ){ return true; }else{ break; }
			case DOWN: if( diff == numColumns ){ return true; } else{ break;}
			case LEFT: if( diff == -1 ){ return true; } else{ break; }
			case RIGHT: if( diff == 1 ){ return true; } else{ break; }
			}
			return false;
		}
	}

	public Set<Integer> getTargets(){
		return targets;
	}

}
