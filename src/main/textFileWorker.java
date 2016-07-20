package main;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Scanner;
import java.util.Set;

/** Stores text file ticker data and interfaces with text files when text file
 * changes are needed. Tickers should only occur in baseTickerList once, if at all.
 */
public class TextFileWorker {
    private final List<String> baseTickerList = new ArrayList<>();
    private final Set<String> containedTickers = new HashSet<>();
    private final String filePath;
    
    /** Creates a new textFileWorker instance. Initializes the baseTickerList to the tickers stored
     * in base-tickers.txt. Works better if all tickers are on one line. 
     */
    public TextFileWorker(String path){
        this.filePath = path;
        Scanner scanner = null;
        try{   
            scanner = new Scanner(new File(this.filePath));
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
    
    /** Adds a ticker to base-tickers.txt and this textFileWorker.
     * @param ticker the ticker to add to base-tickers.txt. If the ticker is not a valid
     * ticker that works with YahooFinance, then no ticker is added (nothing happens). 
     * @return the updated ticker list.
     * @throws IOException 
     */
    public List<String> addTicker(String ticker){
    	if (containedTickers.contains(ticker)){
    		return this.getBaseTickers();
    	}else{
    		ticker = ticker.toUpperCase();
    		
    		baseTickerList.add(ticker);
    		containedTickers.add(ticker);
    		
    		try {
				Files.write(Paths.get(this.filePath), ticker.getBytes(), StandardOpenOption.APPEND);
			} catch (IOException e) {
				e.printStackTrace();
			}
    		
    		return this.getBaseTickers();
    	}
    }
    
    /** Removes a ticker from base-tickers.txt.
     * @param ticker the ticker to remove from base-tickers.txt. If the ticker is not contained
     * in base-tickers.txt, then no ticker is removed (nothing happens).
     * @return the updated ticker list.
     */
    public List<String> deleteTicker(String ticker){
        if (!containedTickers.contains(ticker)){
        	return this.getBaseTickers();
        }else{
        	baseTickerList.remove(ticker);
        	containedTickers.remove(ticker);
        	File tickerFile = new File(this.filePath);
        	
        	try {
				FileOutputStream out = new FileOutputStream(tickerFile, false);
				for(String t : this.getBaseTickers()){
					t += " ";
					out.write(t.getBytes());
				}
				out.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
        	return this.getBaseTickers();
        }
    }
    
    /** 
     * Sort and return the tickers in baseTickerList. This mutates the ordering of baseTickerList
     * internal to this textFileWorker instance. 
     * @return the sorted baseTickerList.
     */
    public List<String> sortTickers(){
    	java.util.Collections.sort(baseTickerList);
        return new ArrayList<>(baseTickerList);
    }
}
