package clueGame;

public class BoardCell {
	int row;
	int column;
	
	public BoardCell(int r, int c) {
		row = r;
		column = c;
	}
	
	public boolean isWalkway() {
		return false;
	}
	
	public boolean isRoom() {
		return false;
	}
	
	public boolean isDoorway() {
		return false;
	}
	
}
