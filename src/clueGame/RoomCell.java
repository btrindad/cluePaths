package clueGame;

public class RoomCell extends BoardCell{
	public enum DoorDirection {UP, DOWN, LEFT, RIGHT, NONE;}
	private DoorDirection doorDirection;
	private char roomInitial;
	
	public RoomCell(char r, char direction) {
		super();
		roomInitial = r;
		
		if (direction == 'U') {
			doorDirection = DoorDirection.UP;
		}
		else if (direction == 'D') {
			doorDirection = DoorDirection.DOWN;
		}
		else if (direction == 'L') {
			doorDirection = DoorDirection.LEFT;
		}
		else if (direction == 'R') {
			doorDirection = DoorDirection.RIGHT;
		}
		else {
			doorDirection = DoorDirection.NONE;
		}
	}
	
	@Override
	public boolean isDoorway() {
		if (doorDirection != DoorDirection.NONE) {
			return true;
		}
		else {
			return false;
		}
	}
	
	@Override
	public boolean isRoom() {
		return true;
	}
	
	public DoorDirection getDoorDirection() {
		return doorDirection;
	}
	
	public char getInitial() {
		return roomInitial;
	}
	
}
