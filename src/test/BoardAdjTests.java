package test;

import java.util.LinkedList;
import java.util.Set;

import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

import clueGame.Board;
import clueGame.BoardCell;

public class BoardAdjTests {
	private static Board board;
	@BeforeClass
	public static void setUp() {
		board = new Board();
		board.loadConfigFiles();
		board.calcAdjacencies();

	}

	// Ensure that player does not move around within room
	// These cells are ORANGE on the planning spreadsheet
	// check the assert values, as they are the test is passing
	// while returning an empty list
	@Test
	public void testAdjacenciesInsideRooms()
	{
		// Test a corner
		LinkedList<Integer> testList = board.getAdjList(board.calcIndex(0, 0));
		Assert.assertEquals(0, testList.size());
		// Test one that has walkway underneath
		testList = board.getAdjList(board.calcIndex(6, 0));
		Assert.assertEquals(0, testList.size());
		// Test one that has walkway above
		testList = board.getAdjList(board.calcIndex(16, 20));
		Assert.assertEquals(0, testList.size());
		// Test one that is in middle of room
		testList = board.getAdjList(board.calcIndex(18, 11));
		Assert.assertEquals(0, testList.size());
		// Test one beside a door
		testList = board.getAdjList(board.calcIndex(16, 12));
		Assert.assertEquals(0, testList.size());
		// Test one in a corner of room
		testList = board.getAdjList(board.calcIndex(7, 19));
		Assert.assertEquals(0, testList.size());
	}

	// Ensure that the adjacency list from a doorway is only the
	// walkway. NOTE: This test could be merged with door 
	// direction test. 
	// These tests are PURPLE on the planning spreadsheet
	@Test
	public void testAdjacencyRoomExit()
	{
		// TEST DOORWAY RIGHT 
		LinkedList<Integer> testList = board.getAdjList(board.calcIndex(13, 4));
		Assert.assertEquals(1, testList.size());
		Assert.assertTrue(testList.contains(board.calcIndex(13, 5)));
		// TEST DOORWAY LEFT 
		testList = board.getAdjList(board.calcIndex(10, 17));
		Assert.assertEquals(1, testList.size());
		Assert.assertTrue(testList.contains(board.calcIndex(10, 16)));
		//TEST DOORWAY DOWN
		testList = board.getAdjList(board.calcIndex(5, 18));
		Assert.assertEquals(1, testList.size());
		Assert.assertTrue(testList.contains(board.calcIndex(6, 18)));
		//TEST DOORWAY UP
		testList = board.getAdjList(board.calcIndex(16, 11));
		Assert.assertEquals(1, testList.size());
		Assert.assertTrue(testList.contains(board.calcIndex(15, 11)));
		
	}
	
	// Test adjacency at entrance to rooms
	// These tests are GREEN in planning spreadsheet
	@Test
	public void testAdjacencyDoorways_I()
	{
		// Test beside a door direction RIGHT
		LinkedList<Integer> testList = board.getAdjList(board.calcIndex(13, 5));
		Assert.assertTrue(testList.contains(board.calcIndex(13, 4)));
		Assert.assertTrue(testList.contains(board.calcIndex(12, 5)));
		Assert.assertTrue(testList.contains(board.calcIndex(14, 5)));
		Assert.assertTrue(testList.contains(board.calcIndex(13, 6)));
		Assert.assertEquals(4, testList.size());
		// Test beside a door direction DOWN
		testList = board.getAdjList(board.calcIndex(7, 3));
		Assert.assertTrue(testList.contains(board.calcIndex(7, 4)));
		Assert.assertTrue(testList.contains(board.calcIndex(8, 3)));
		Assert.assertTrue(testList.contains(board.calcIndex(6, 3)));
		Assert.assertTrue(testList.contains(board.calcIndex(7, 2)));
		Assert.assertEquals(4, testList.size());
		// Test beside a door direction LEFT
		testList = board.getAdjList(board.calcIndex(18, 16));
		Assert.assertTrue(testList.contains(board.calcIndex(17, 16)));
		Assert.assertTrue(testList.contains(board.calcIndex(19, 16)));
		Assert.assertTrue(testList.contains(board.calcIndex(18, 17)));
		Assert.assertEquals(3, testList.size());
		// Test beside a door direction UP
		testList = board.getAdjList(board.calcIndex(15, 10));
		Assert.assertTrue(testList.contains(board.calcIndex(16, 10)));
		Assert.assertTrue(testList.contains(board.calcIndex(15, 9)));
		Assert.assertTrue(testList.contains(board.calcIndex(15, 11)));
		Assert.assertTrue(testList.contains(board.calcIndex(14, 10)));
		Assert.assertEquals(4, testList.size());
	}
	@Test
	public void testAdjacencyDoorways_II() {
		// Test beside a door that's not the right direction
		LinkedList<Integer> testList = board.getAdjList(board.calcIndex(5, 3));
		Assert.assertTrue(testList.contains(board.calcIndex(5, 2)));
		Assert.assertTrue(testList.contains(board.calcIndex(5, 4)));
		Assert.assertTrue(testList.contains(board.calcIndex(6, 3)));
		// This ensures we haven't included cell (4, 3) which is a doorway
		Assert.assertEquals(3, testList.size());		
	}

	// Test a variety of walkway scenarios
	// These tests are LIGHT PURPLE on the planning spreadsheet
	@Test
	public void testAdjacency4Walkways()
	{
		// Test surrounded by 4 walkways
		LinkedList<Integer> testList = board.getAdjList(board.calcIndex(15,7));
		Assert.assertTrue(testList.contains(board.calcIndex(15, 8)));
		Assert.assertTrue(testList.contains(board.calcIndex(15, 6)));
		Assert.assertTrue(testList.contains(board.calcIndex(14, 7)));
		Assert.assertTrue(testList.contains(board.calcIndex(16, 7)));
		Assert.assertEquals(4, testList.size());
	}
	
	@Test
	public void testAdjacencyTopEdge() {
		// Test on top edge of board
		LinkedList<Integer> testList = board.getAdjList(board.calcIndex(0, 5));
		Assert.assertTrue(testList.contains(6));
		Assert.assertTrue(testList.contains(4));
		Assert.assertEquals(2, testList.size());
	}
		
	@Test
	public void testAdjacencyLeftEdge() {
		// Test on left edge of board
		LinkedList<Integer> testList = board.getAdjList(board.calcIndex(8, 0));
		Assert.assertTrue(testList.contains(board.calcIndex(9, 0)));
		Assert.assertTrue(testList.contains(board.calcIndex(8, 1)));
		Assert.assertTrue(testList.contains(board.calcIndex(7, 0)));
		Assert.assertEquals(3, testList.size());
	}

	@Test
	public void testAdjacencyRoomCells() {
		// Test between two rooms, walkways right and left
		LinkedList<Integer> testList = board.getAdjList(board.calcIndex(8, 20));
		Assert.assertTrue(testList.contains(board.calcIndex(8, 21)));
		Assert.assertTrue(testList.contains(board.calcIndex(8, 19)));
		Assert.assertEquals(2, testList.size());
	}
	
	@Test
	public void testAdjacencyBottomEdge() {
		// Test on bottom edge of board, next to 1 room piece
		LinkedList<Integer> testList = board.getAdjList(board.calcIndex(21, 5));
		Assert.assertTrue(testList.contains(board.calcIndex(21, 6)));
		Assert.assertTrue(testList.contains(board.calcIndex(20, 5)));
		Assert.assertEquals(2, testList.size());
	}
	
	@Test
	public void testAdjacencyRightEdge() {
		// Test on right edge of board, next to 1 room piece
		LinkedList<Integer> testList = board.getAdjList(board.calcIndex(14, 22));
		Assert.assertTrue(testList.contains(board.calcIndex(14, 21)));
		Assert.assertTrue(testList.contains(board.calcIndex(15, 22)));
		Assert.assertEquals(2, testList.size());
	}
	
	@Test
	public void testAdjacencyDoorwayWrongDirection() {
		// Test on walkway next to  door that is not in the needed
		// direction to enter
		LinkedList<Integer> testList = board.getAdjList(board.calcIndex(5, 16));
		Assert.assertTrue(testList.contains(board.calcIndex(4, 16)));
		Assert.assertTrue(testList.contains(board.calcIndex(5, 15)));
		Assert.assertTrue(testList.contains(board.calcIndex(6, 16)));
		Assert.assertEquals(3, testList.size());
	}
	
	// Tests of just walkways, 1 step, includes on edge of board
	// and beside room
	// Have already tested adjacency lists on all four edges, will
	// only test two edges here
	// These are LIGHT BLUE on the planning spreadsheet
	@Test
	public void testTargetsOneStep() {
		board.calcTargets(21, 6, 1);
		Set<BoardCell> targets= board.getTargets();
		Assert.assertEquals(2, targets.size());
		Assert.assertTrue(targets.contains(board.getCell(board.calcIndex(20, 7))));
		Assert.assertTrue(targets.contains(board.getCell(board.calcIndex(21, 5))));	
		
		board.calcTargets(15, 0, 1);
		targets= board.getTargets();
		Assert.assertEquals(2, targets.size());
		Assert.assertTrue(targets.contains(board.getCell(board.calcIndex(14, 0))));
		Assert.assertTrue(targets.contains(board.getCell(board.calcIndex(15, 1))));			
	}
	// Tests of just walkways, 2 steps
	// These are LIGHT BLUE on the planning spreadsheet
	@Test
	public void testTargetsTwoSteps() {
		board.calcTargets(21, 6, 2);
		Set<BoardCell> targets= board.getTargets();
		Assert.assertEquals(2, targets.size());
		Assert.assertTrue(targets.contains(board.getCell(board.calcIndex(19, 6))));
		Assert.assertTrue(targets.contains(board.getCell(board.calcIndex(20, 5))));
		
		board.calcTargets(14, 0, 2);
		targets= board.getTargets();
		Assert.assertEquals(1, targets.size());	
		Assert.assertTrue(targets.contains(board.getCell(board.calcIndex(15, 1))));			
	}
	// Tests of just walkways, 4 steps
	// These are LIGHT BLUE on the planning spreadsheet
	@Test
	public void testTargetsFourSteps() {
		board.calcTargets(21, 6, 4);
		Set<BoardCell> targets= board.getTargets();
		Assert.assertEquals(2, targets.size());
		Assert.assertTrue(targets.contains(board.getCell(board.calcIndex(17, 6))));
		Assert.assertTrue(targets.contains(board.getCell(board.calcIndex(18, 5))));
		
		// Includes a path that doesn't have enough length
		board.calcTargets(15, 1, 4);
		targets= board.getTargets();
		Assert.assertEquals(2, targets.size());
		Assert.assertTrue(targets.contains(board.getCell(board.calcIndex(16, 4))));
		Assert.assertTrue(targets.contains(board.getCell(board.calcIndex(15, 5))));		
	}	
	// Tests of just walkways plus one door, 6 steps
	// These are LIGHT BLUE on the planning spreadsheet

	@Test
	public void testTargetsSixSteps() { // All test functions up to here updated
		board.calcTargets(6, 10, 6);
		Set<BoardCell> targets= board.getTargets();
		Assert.assertEquals(8, targets.size());
		Assert.assertTrue(targets.contains(board.getCell(board.calcIndex(2, 9))));
		Assert.assertTrue(targets.contains(board.getCell(board.calcIndex(0, 10))));	
		Assert.assertTrue(targets.contains(board.getCell(board.calcIndex(6, 11))));	
		Assert.assertTrue(targets.contains(board.getCell(board.calcIndex(6, 6))));	
		Assert.assertTrue(targets.contains(board.getCell(board.calcIndex(7, 5))));	
		Assert.assertTrue(targets.contains(board.getCell(board.calcIndex(8, 6))));	
		Assert.assertTrue(targets.contains(board.getCell(board.calcIndex(7, 15))));
		Assert.assertTrue(targets.contains(board.getCell(board.calcIndex(8, 14))));
	}	
	
	// Test getting into a room
	// These are LIGHT BLUE on the planning spreadsheet
	@Test 
	public void testTargetsIntoRoom()
	{
		// One room is exactly 2 away
		board.calcTargets(17, 16, 2);
		Set<BoardCell> targets= board.getTargets();
		Assert.assertEquals(7, targets.size());
		// directly left (can't go right 2 steps)
		Assert.assertTrue(targets.contains(board.getCell(board.calcIndex(17, 14))));
		// directly up and down
		Assert.assertTrue(targets.contains(board.getCell(board.calcIndex(15, 16))));
		Assert.assertTrue(targets.contains(board.getCell(board.calcIndex(19, 16))));
		// one up/down, one left/right
		Assert.assertTrue(targets.contains(board.getCell(board.calcIndex(18, 17))));
		Assert.assertTrue(targets.contains(board.getCell(board.calcIndex(18, 15))));
		Assert.assertTrue(targets.contains(board.getCell(board.calcIndex(16, 17))));
		Assert.assertTrue(targets.contains(board.getCell(board.calcIndex(16, 15))));
	}
	
	// Test getting into room, doesn't require all steps
	// These are LIGHT BLUE on the planning spreadsheet
	@Test
	public void testTargetsIntoRoomShortcut() 
	{
		board.calcTargets(12, 7, 3);
		Set<BoardCell> targets= board.getTargets();
		Assert.assertEquals(12, targets.size());
		// directly up and down
		Assert.assertTrue(targets.contains(board.getCell(board.calcIndex(15, 7))));
		Assert.assertTrue(targets.contains(board.getCell(board.calcIndex(9, 7))));
		// directly right (can't go left)
		Assert.assertTrue(targets.contains(board.getCell(board.calcIndex(12, 10))));
		// right then down
		Assert.assertTrue(targets.contains(board.getCell(board.calcIndex(13, 9))));
		Assert.assertTrue(targets.contains(board.getCell(board.calcIndex(13, 7))));
		// down then left/right
		Assert.assertTrue(targets.contains(board.getCell(board.calcIndex(14, 6))));
		Assert.assertTrue(targets.contains(board.getCell(board.calcIndex(14, 8))));
		// right then up
		Assert.assertTrue(targets.contains(board.getCell(board.calcIndex(10, 8))));
		// into the rooms
		Assert.assertTrue(targets.contains(board.getCell(board.calcIndex(11, 6))));
		Assert.assertTrue(targets.contains(board.getCell(board.calcIndex(10, 6))));		
		// 
		Assert.assertTrue(targets.contains(board.getCell(board.calcIndex(11, 7))));		
		Assert.assertTrue(targets.contains(board.getCell(board.calcIndex(12, 8))));		
		
	}

	// Test getting out of a room
	// These are LIGHT BLUE on the planning spreadsheet
	@Test
	public void testRoomExit()
	{
		// Take one step, essentially just the adj list
		board.calcTargets(4, 20, 1);
		Set<BoardCell> targets= board.getTargets();
		// Ensure doesn't exit through the wall
		Assert.assertEquals(1, targets.size());
		Assert.assertTrue(targets.contains(board.getCell(board.calcIndex(4, 19))));
		// Take two steps
		board.calcTargets(4, 20, 2);
		targets= board.getTargets();
		Assert.assertEquals(3, targets.size());
		Assert.assertTrue(targets.contains(board.getCell(board.calcIndex(3, 19))));
		Assert.assertTrue(targets.contains(board.getCell(board.calcIndex(5, 19))));
		Assert.assertTrue(targets.contains(board.getCell(board.calcIndex(4, 18))));
	}

}