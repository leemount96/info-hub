package main;
import java.io.IOException;
import java.math.BigDecimal;

import yahoofinance.*;

public class HelloWorld {
	public static void main(String[] args){
		try {
			Stock aapl = YahooFinance.get("AAPL");
			BigDecimal price = aapl.getQuote().getPrice();
			System.out.println("Current AAPL Price: " + price);
			aapl.print();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}