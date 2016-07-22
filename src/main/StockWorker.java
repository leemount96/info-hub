package main;
import yahoofinance.*;

import javafx.beans.property.*;
import javafx.concurrent.*;
import javafx.application.Platform;

public class StockWorker {
    private final String queryTicker;
    private final StringProperty queryTickerProperty;
    private Stock thisStock;
    private Thread refreshThread;
    
    public StockWorker(String ticker){
        queryTicker = ticker;
        queryTickerProperty = new SimpleStringProperty(ticker);
        startRefreshThread();
        updateTicker();

    }
    
    /**
     * Update the ticker of this stock to be most up to date
     */
    public void updateTicker(){
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
    
    /*
     * Start the refresh thread of this stock worker
     */
    private void startRefreshThread(){
    	Task<Void> refresh = new Task<Void>(){
    		@Override
    		public Void call() throws Exception{
    			while(true){
    				Platform.runLater(new Runnable(){
    					@Override
    					public void run(){
    						try{
    							updateTicker();
    						}catch(Exception e){}
    					}
    				});
    				Thread.sleep(15000);
    			}
    		}
    	};
    	refreshThread = new Thread(refresh);
    	refreshThread.setDaemon(true);
    	refreshThread.start();
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
