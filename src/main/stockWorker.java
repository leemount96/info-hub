package main;
import yahoofinance.*;

public class stockWorker {
    private String queryTicker; 
    private Stock thisStock;
    
    public stockWorker(String ticker){
        final String queryTicker = ticker;
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
