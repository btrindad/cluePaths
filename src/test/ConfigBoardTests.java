package test;

import static org.junit.Assert.*;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import clueGame.Board;

public class ConfigBoardTests {
	Board testBoard;
	
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
}
