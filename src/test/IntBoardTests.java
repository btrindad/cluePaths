package test;

import static org.junit.Assert.*;

import java.util.ArrayList;

import junit.framework.Assert;

import org.junit.Before;
import org.junit.Test;

import board.IntBoard;


public class IntBoardTests {
	IntBoard testBoard;
	
	@Before
	public void setupBoard() {
		testBoard = new IntBoard();
	}

	@Test
	public void test() {
		fail("Not yet implemented");
		
	}
	
	/*@Test
	public void testAdjacencyLists() {

		
	}*/
	@Test
	public void testAdjacencyLists0()
	{
		ArrayList testList = testBoard.getAdjList(0);
		Assert.assertTrue(testList.contains(1));
		Assert.assertTrue(testList.contains(4));
		Assert.assertEquals(2, testList.size());
	}
	
	@Test 
	public void testAdjacencyLists15()
	{
		int[] expected = {14,11};
		ArrayList testList = testBoard.getAdjList(15);
		for(int i : expected){
			Assert.assertTrue(testList.contains(i));
		}
		Assert.assertEquals(expected.length, testList.size());
	}
	
	@Test 
	public void testAdjacencyLists11()
	{
		int[] expected = {10,7,15};
		ArrayList testList = testBoard.getAdjList(15);
		for(int i : expected){
			Assert.assertTrue(testList.contains(i));
		}
		Assert.assertEquals(expected.length, testList.size());
	}
	
	@Test 
	public void testAdjacencyLists4()
	{
		int[] expected = {5,0,8};
		ArrayList testList = testBoard.getAdjList(15);
		for(int i : expected){
			Assert.assertTrue(testList.contains(i));
		}
		Assert.assertEquals(expected.length, testList.size());
	}
	
	@Test 
	public void testAdjacencyLists9()
	{
		int[] expected = {8,5,13,10};
		ArrayList testList = testBoard.getAdjList(15);
		for(int i : expected){
			Assert.assertTrue(testList.contains(i));
		}
		Assert.assertEquals(expected.length, testList.size());
	}
	
	@Test 
	public void testAdjacencyLists6()
	{
		int[] expected = {10,5,7,2};
		ArrayList testList = testBoard.getAdjList(15);
		for(int i : expected){
			Assert.assertTrue(testList.contains(i));
		}
		Assert.assertEquals(expected.length, testList.size());
	}
	
	@Test
	public void testCalcIndex() {
		int expected = 0;
		int actual = IntBoard.calcIndex(0,0);
		Assert.assertEquals(expected, actual);
		expected = 5;
		actual = IntBoard.calcIndex(1, 1);
		Assert.assertEquals(expected, actual);
		expected = 7;
		actual = IntBoard.calcIndex(1, 3);
		Assert.assertEquals(expected, actual);
		
	}
	
	@Test
	public void testCalcTargets() {
		ArrayList<Integer> expected = new ArrayList();
		expected.add(1);
		expected.add(4);
		ArrayList<Integer> actual = IntBoard.calcTargets(0, 1);
		Assert.assertEquals(expected, actual);
		
		expected.clear();
		expected.add(8);
		expected.add(5);
		expected.add(10);
		expected.add(15);
		actual = IntBoard.calcTargets(13, 2);
		Assert.assertEquals(expected, actual);
		
		
	}

}
