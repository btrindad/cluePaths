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
	
	public Board() {
		cells = new ArrayList<BoardCell>();
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
			System.out.println("HELLO");
			int i = 0;
			while (inScan.hasNext()) {
				String s = inScan.nextLine();
				String[] queue = s.split(",");
				System.out.println("WORLD");
				if (queue.length != 23 /*numColumns && !(numColumns > 0) This does not work because at the start of the program, numColumns will equal 0*/) {
					throw new BadConfigFormatException("Problem with the format of the board file.");
				}
				
				numColumns = queue.length;
				System.out.println("NColumns: " + numColumns);
				
				for (String t : queue) {
					if (!rooms.containsKey(t.charAt(0))) {
						throw new BadConfigFormatException("Problem with the format of the board file: Invalid room key.");
					}
					
					if (t != "W") {
						char tempDD = 'N';
						char tempRI = t.charAt(0);

						if (t.length() > 1) {
							tempDD = t.charAt(1);
						}
						
						RoomCell tempRC = new RoomCell(tempRI, tempDD);
						cells.add(tempRC);
					}
					else {
						WalkwayCell tempWC = new WalkwayCell();
						cells.add(tempWC);
					}
				}
				System.out.println("NRows: "+ numRows);
			}
			numRows = i;
		}
		finally {
			inScan.close();
		}
	}
	
	public void loadBoardConfig() throws BadConfigFormatException, FileNotFoundException {
		FileReader reader = new FileReader(legendFile);
		Scanner inScan = new Scanner(reader);
		
		try {
			while (inScan.hasNext()) {
				String u = inScan.nextLine();
				String[] queue = u.split(",");
				if (queue.length != 1) {
					throw new BadConfigFormatException("Problem with the format of the room legend file.");
				}
				
				rooms.put(queue[0].charAt(0), queue[1]);
			}
			
		}
		finally {
			inScan.close();
		}
		
	}
	
	//returns length of legend
	public Map<Character, String> getRooms(){
		return rooms;
	}
	
	public int calcIndex (int row, int column) {
		System.out.println("Number of Columns: " + numColumns);
		System.out.println("Number of Rows: " + numRows);
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
			RoomCell e = new RoomCell('N', 'O');
			return e;
		}
	}

	public String getRoom(char c) {
		return rooms.get(c);
	}
	
	public int getNumRows() {
		return numRows;
	}
	
	public int getNumColumns() {
		return numColumns;
	}
	
}
