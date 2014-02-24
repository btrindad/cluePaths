package clueGame;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class Board {
	private ArrayList<BoardCell> cells;
	private Map<Character,String> rooms;
	private int numRows;
	private int numColumns;
	private String layoutFile;
	private String legendFile;
	
	//check if these really need to be static
	private static Map<Integer, ArrayList<Integer>> adjMap;
	private static boolean[] visited;
	
	public Board() {
		cells = new ArrayList<BoardCell>();
		adjMap = new HashMap<Integer, ArrayList<Integer>>();
		rooms = new HashMap<Character, String>();
		numRows = 0;
		numColumns = 0;
	}
	
	public Board(String layout, String legend) {
		cells = new ArrayList<BoardCell>();
		rooms = new HashMap<Character, String>();
		numRows = 0;
		numColumns = 0;
		layoutFile = layout;
		legendFile = legend;
		
	}
	
	public void loadConfigFiles() {
		try {
			loadRoomConfig();
			loadBoardConfig();
		}
		catch (Exception e) {
			System.out.println(e.getMessage());
		}
	}
	
	public void loadRoomConfig() throws BadConfigFormatException, FileNotFoundException {
		FileReader reader = new FileReader(layoutFile);
		Scanner inScan = new Scanner(reader);
	}
	
	public void loadBoardConfig() throws BadConfigFormatException, FileNotFoundException {
		FileReader reader = new FileReader(legendFile);
		Scanner inScan = new Scanner(reader);
		
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
	
	public RoomCell getRoomCell(int r, int c) {
		int location = calcIndex(r, c);
		if (cells.get(location).isRoom()) {
			return (RoomCell)cells.get(location);
		}
		
		else { // For now .....
			RoomCell e = new RoomCell();
			return e;
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
	
}
