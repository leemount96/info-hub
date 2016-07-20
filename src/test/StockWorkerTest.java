package test;

import static org.junit.Assert.*;

import java.io.IOException;

import org.junit.Test;

import yahoofinance.Stock;
import yahoofinance.YahooFinance;

public class StockWorkerTest {
	
	//Test illegal ticker name
	@Test
	public void testIllegalTicker(){
		try {
			Stock test = YahooFinance.get("ABCDEFG");
			assertEquals(test.getQuote().getPrice(), null);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
