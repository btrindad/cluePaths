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
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
	}
	
	public void loadRoomConfig() throws BadConfigFormatException, FileNotFoundException {
		FileReader reader = new FileReader(layoutFile);
		Scanner inScan = new Scanner(reader);
		
		try {
			while (inScan.hasNext()) {
				String s = inScan.next();
				if (s != "W") {
					char tempDD = 'N';
					char tempRI = s.charAt(0);

					if (s.length() > 1) {
						tempDD = s.charAt(1);
					}
					
					RoomCell tempRC = new RoomCell(tempRI, tempDD);
					cells.add(tempRC);
				}
				else {
					WalkwayCell tempWC = new WalkwayCell();
					cells.add(tempWC);
				}

			}
		}
		finally {
			inScan.close();
		}
	}
	
	public void loadBoardConfig() throws BadConfigFormatException, FileNotFoundException {
		FileReader reader = new FileReader(legendFile);
		Scanner inScan = new Scanner(reader);
		
		try {
			String c = inScan.next();
			
		}
		finally {
			inScan.close();
		}
		
	}
	
	//returns length of legend
	public int getNumRooms(){
		return rooms.size();
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
