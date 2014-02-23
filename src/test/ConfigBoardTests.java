package test;

import static org.junit.Assert.*;

import java.io.FileNotFoundException;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import clueGame.BadConfigFormatException;
import clueGame.Board;
import clueGame.BoardCell;
import clueGame.RoomCell;

public class ConfigBoardTests {
	Board testBoard;
	public static final int NUM_ROOMS = 11;
	public static final int NUM_ROWS = 22;
	public static final int NUM_COLUMNS = 23;

	
	@Before
	public void setUpBoard(){
		testBoard = new Board();
	}

	@Test
	public void roomLegendTest() {
		final int EXPECTED_NUM_ROOMS = 11;
		Assert.assertEquals(testBoard.getNumRooms(), EXPECTED_NUM_ROOMS);
	}
	
	@Test
	public void testMappings() {
		Assert.assertEquals("Conservatory", testBoard.getRoom('C'));
		Assert.assertEquals("Kitchen", testBoard.getRoom('K'));
		Assert.assertEquals("Ballroom", testBoard.getRoom('B'));
		Assert.assertEquals("Billiard room", testBoard.getRoom('R'));
		Assert.assertEquals("Library", testBoard.getRoom('L'));
		Assert.assertEquals("Study", testBoard.getRoom('S'));
		Assert.assertEquals("Dining Room", testBoard.getRoom('D'));
		Assert.assertEquals("Lounge", testBoard.getRoom('O'));
		Assert.assertEquals("Hall", testBoard.getRoom('H'));
		Assert.assertEquals("Closet", testBoard.getRoom('X'));
		Assert.assertEquals("Walkway", testBoard.getRoom('W'));
		
		//also ran tests to make sure that mappings work even with lowercase letters as
		//input. These are optional but they test if code handles irregular input
		Assert.assertEquals("Conservatory", testBoard.getRoom('c'));
		Assert.assertEquals("Kitchen", testBoard.getRoom('k'));
		Assert.assertEquals("Ballroom", testBoard.getRoom('b'));
		Assert.assertEquals("Billiard room", testBoard.getRoom('r'));
		Assert.assertEquals("Library", testBoard.getRoom('l'));
		Assert.assertEquals("Study", testBoard.getRoom('s'));
		Assert.assertEquals("Dining Room", testBoard.getRoom('d'));
		Assert.assertEquals("Lounge", testBoard.getRoom('o'));
		Assert.assertEquals("Hall", testBoard.getRoom('h'));
		Assert.assertEquals("Closet", testBoard.getRoom('x'));
		Assert.assertEquals("Walkway", testBoard.getRoom('w'));
	}
	
	@Test
	public void testDimensions() {
		assertEquals(NUM_ROWS, testBoard.getNumRows());
		assertEquals(NUM_COLUMNS, testBoard.getNumColumns());		
	}
	
	@Test
	public void testCalcIndex() {
		assertEquals(0, testBoard.calcIndex(0, 0));
		assertEquals(NUM_COLUMNS-1, testBoard.calcIndex(0, NUM_COLUMNS-1));
		assertEquals(483, testBoard.calcIndex(NUM_ROWS-1, 0));
		assertEquals(505, testBoard.calcIndex(NUM_ROWS-1, NUM_COLUMNS-1));
		assertEquals(26, testBoard.calcIndex(1, 3));
		assertEquals(64, testBoard.calcIndex(2, 18));		
	}
	
	@Test
	public void testInitials() {
		assertEquals('B', testBoard.getRoomCell(0, 0).getInitial());
		assertEquals('R', testBoard.getRoomCell(4, 8).getInitial());
		assertEquals('C', testBoard.getRoomCell(12, 1).getInitial());
		assertEquals('O', testBoard.getRoomCell(21, 22).getInitial());
		assertEquals('K', testBoard.getRoomCell(21, 0).getInitial());
		assertEquals('D', testBoard.getRoomCell(20, 10).getInitial());
		assertEquals('S', testBoard.getRoomCell(2, 20).getInitial());
		assertEquals('L', testBoard.getRoomCell(2, 12).getInitial());
	}
	
	@Test
	public void testDoorDirections() {
		RoomCell room = testBoard.getRoomCell(0, 4);
		assertTrue(room.isDoorway());
		assertEquals(RoomCell.DoorDirection.RIGHT, room.getDoorDirection());
		room = testBoard.getRoomCell(6, 3);
		assertTrue(room.isDoorway());
		assertEquals(RoomCell.DoorDirection.DOWN, room.getDoorDirection());
		room = testBoard.getRoomCell(16, 11);
		assertTrue(room.isDoorway());
		assertEquals(RoomCell.DoorDirection.UP, room.getDoorDirection());
		room = testBoard.getRoomCell(10, 17);
		assertTrue(room.isDoorway());
		assertEquals(RoomCell.DoorDirection.LEFT, room.getDoorDirection());
		room = testBoard.getRoomCell(14, 14);
		assertFalse(room.isDoorway());	
		BoardCell cell = testBoard.getCell(testBoard.calcIndex(0, 6));
		assertFalse(cell.isDoorway());		
	}
	
	@Test
	public void testDoorways() {
		int numDoors = 0;
		int totalCells = testBoard.getNumColumns() * testBoard.getNumRows();
		Assert.assertEquals(506, totalCells);
		for (int i=0; i<totalCells; i++)
		{
			BoardCell cell = testBoard.getCell(i);
			if (cell.isDoorway())
				numDoors++;
		}
		Assert.assertEquals(16, numDoors);
	}
	
	@Test (expected = BadConfigFormatException.class)
	public void testBadColumns() throws BadConfigFormatException, FileNotFoundException {
		Board tBoard = new Board("ClueLayoutBadColumns.csv", "ClueLegend.txt");
		tBoard.loadRoomConfig();
		tBoard.loadBoardConfig();
	}

	@Test (expected = BadConfigFormatException.class)
	public void testBadRoom() throws BadConfigFormatException, FileNotFoundException {
		Board tBoard = new Board("ClueLayoutBadRoom.csv", "ClueLegend.txt");
		tBoard.loadRoomConfig();
		tBoard.loadBoardConfig();
	}

	@Test (expected = BadConfigFormatException.class)
	public void testBadRoomFormat() throws BadConfigFormatException, FileNotFoundException {
		Board tBoard = new Board("ClueLayout.csv", "ClueLegendBadFormat.txt");
		tBoard.loadRoomConfig();
		tBoard.loadBoardConfig();
	}
	
}
