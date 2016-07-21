package test;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

import main.TextFileWorker;

public class TextFileWorkerTest {
	
	
	//Test empty file, add, and delete ticker
	@Test
	public void testEmptyFile(){
		TextFileWorker t = new TextFileWorker("text-files/empty");
		assertEquals(t.getBaseTickers(), new ArrayList<>());
		t.addTicker("AAPL");
		
		ArrayList<String> list = new ArrayList<String>();
		list.add("AAPL");
		
		assertEquals(t.getBaseTickers(), list);
		
		t.deleteTicker("AAPL");
		assertEquals(t.getBaseTickers(), new ArrayList<>());
	}
	
	//Test short file add & remove
	@Test
	public void testShortFile(){
		TextFileWorker t = new TextFileWorker("text-files/base-tickers-test");
		ArrayList<String> list = new ArrayList<String>();
		list.add("AAPL");
		list.add("AMZN");
		list.add("TSLA");
		list.add("MSFT");
		list.add("SPY");
		
		List<String> fromT = t.getBaseTickers();
		java.util.Collections.sort(fromT);
		java.util.Collections.sort(list);
		
		assertEquals(fromT, list);
		
		t.deleteTicker("AMZN");
		list.remove("AMZN");
		
		fromT = t.getBaseTickers();
		java.util.Collections.sort(fromT);
		java.util.Collections.sort(list);
		
		assertEquals(fromT, list);
		
		t.addTicker("AMZN");
		list.add("AMZN");
		
		fromT = t.getBaseTickers();
		java.util.Collections.sort(fromT);
		java.util.Collections.sort(list);
		
		assertEquals(fromT, list);
	}
	
	//Test adding invalid ticker & ticker that is already on list
	@Test
	public void testAddInvalidTicker(){
		TextFileWorker t = new TextFileWorker("text-files/base-tickers-test");
		assertFalse(t.addTicker("ABCDEFG"));
		assertTrue(t.addTicker("K"));
		assertFalse(t.addTicker("K"));
		t.deleteTicker("K");
	}
	
	//Test removing invalid ticker, ticker in list, ticker not in list
	@Test
	public void testDeleteInvalidTicker(){
		TextFileWorker t = new TextFileWorker("text-files/base-tickers-test");
		assertTrue(t.deleteTicker("AAPL"));
		assertFalse(t.deleteTicker("AAPL"));
		assertFalse(t.deleteTicker("ABCDEFG"));
		assertTrue(t.addTicker("AAPL"));
	}
}
