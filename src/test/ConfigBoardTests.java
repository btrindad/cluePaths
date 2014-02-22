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

}
