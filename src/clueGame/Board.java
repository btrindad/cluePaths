package clueGame;

import java.util.ArrayList;
import java.util.Map;

public class Board {
	private ArrayList<BoardCell> cells;
	private Map<Character,String> rooms;
	private int numRows;
	private int numColumns;
	
	public void loadConfigFiles() {
		
	}
	
	public int calcIndex (int row, int column) {
		return 0; //((numColumns*row) + column);
	}
	
	public BoardCell getCell(int location) {
		BoardCell aBoardCell = new BoardCell();
		return aBoardCell;//cells.get(location);
	}
	
	public String getRoom(char c) {
		return "";//rooms.get(c);
		
	}
	
	public int getNumRows() {
		return 0; // numRows;
	}
	
	
	public int getNumColumns() {
		return 0; //numColumns
	}
	
	
}
