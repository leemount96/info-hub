package main;

import java.io.File;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Scanner;
import java.util.Set;

/** Stores text file ticker data and interfaces with text files when text file
 * changes are needed. Tickers should only occur in baseTickerList once, if at all.
 */
public class textFileWorker {
    final List<String> baseTickerList = new ArrayList<>();
    final Set<String> containedTickers = new HashSet<>();
    
    /** Creates a new textFileWorker instance. Initializes the baseTickerList to the tickers stored
     * in base-tickers.txt. Works better if all tickers are on one line. 
     */
    public textFileWorker(){
        final String baseTickersPath = "text-files/base-tickers.txt";
        Scanner scanner = null;
        try{   
            scanner = new Scanner(new File(baseTickersPath));
            while(scanner.hasNextLine()){
                Scanner tickerScanner = new Scanner(scanner.nextLine());
                while (tickerScanner.hasNext()){
                    String nextTicker = tickerScanner.next();
                    if(!(containedTickers.contains(nextTicker))){
                        baseTickerList.add(nextTicker);
                        containedTickers.add(nextTicker);
                    }
                }
                tickerScanner.close();
            }
            scanner.close();
        }
        catch(Exception e){
            throw new IllegalArgumentException();
        }
    }
    /**Return the tickers contained in base-tickers.txt.
     * @return the current list of base tickers
     */
    public List<String> getBaseTickers(){
        return new ArrayList<>(baseTickerList);
    }
    
    /** Adds a ticker to base-tickers.txt.
     * @param ticker the ticker to add to base-tickers.txt. If the ticker is not a valid
     * ticker that works with YahooFinance, then no ticker is added (nothing happens). 
     * @return the updated ticker list.
     */
    public List<String> addTicker(String ticker){
        return new ArrayList<>(baseTickerList);       
    }
    
    /** Removes a ticker from base-tickers.txt.
     * @param ticker the ticker to remove from base-tickers.txt. If the ticker is not contained
     * in base-tickers.txt, then no ticker is removed (nothing happens).
     * @return the updated ticker list.
     */
    public List<String> deleteTicker(String ticker){
        return new ArrayList<>(baseTickerList);
    }
    
    /** 
     * Sort and return the tickers in baseTickerList. This mutates the ordering of baseTickerList
     * internal to this instance textFileWorker instance. 
     * @return the sorted baseTickerList.
     */
    public List<String> sortTickers(){
        return new ArrayList<>(baseTickerList);
    }
}
