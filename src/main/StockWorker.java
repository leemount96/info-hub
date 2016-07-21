package main;
import yahoofinance.*;

import javafx.beans.property.*;

public class StockWorker {
    private final String queryTicker;
    private final StringProperty queryTickerProperty;
    private Stock thisStock;
    
    public StockWorker(String ticker){
        queryTicker = ticker;
        queryTickerProperty = new SimpleStringProperty(ticker);
        updateTicker();
    }
    
    private void updateTicker(){
        try{
            thisStock = YahooFinance.get(queryTicker);
        }
        catch(Exception e){
            /*
             * Impossible to get here because queryTicker is immutable and if an invalid ticker was
             * passed the constructor would throw an error.
             */
            throw new IllegalArgumentException();
        }
    }
    
    /**
     * @return string name of this ticker
     */
    public String getTicker(){
    	return this.queryTicker;
    }
    
    /**
     * @return StringProperty ticker name, used in table
     */
    public StringProperty tickerProperty(){
    	return this.queryTickerProperty;
    }
    
    /**
     * @return double value of current stock price
     */
    public double getPrice(){
//    	updateTicker();
    	return thisStock.getQuote().getPrice().doubleValue();
    }
    
    /**
     * @return DoubleProperty price of stock
     */
    public DoubleProperty priceProperty(){
//    	updateTicker();
    	return new SimpleDoubleProperty(getPrice());
    }
    
    /**
     * @return double value of current %change
     */
    public double getChange(){
//    	updateTicker();
    	return thisStock.getQuote().getChangeInPercent().doubleValue();
    }
    
    /**
     * @return DoubleProperty %change of stock
     */
    public DoubleProperty changeProperty(){
//    	updateTicker();
    	return new SimpleDoubleProperty(getChange());
    }
}
