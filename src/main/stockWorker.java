package main;
import yahoofinance.*;

public class StockWorker {
    private final String queryTicker; 
    private Stock thisStock;
    
    public StockWorker(String ticker){
        queryTicker = ticker;
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
    
    
}
