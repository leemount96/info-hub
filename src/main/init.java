package main;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import yahoofinance.*;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.concurrent.*;

/**
 * Application that serves as an easy to use finance and other related news source
 */
public class Init extends Application{
	private final static TextFileWorker tfw = new TextFileWorker("text-files/base-tickers-short");
	private final static EmailGenerator generator = new EmailGenerator();
	
	/**
	 * Main entry point to the InfoHub app
	 * @param args
	 */
    public static void main(String[] args){
        launch(args);
    }
    
    @Override
    public void start(Stage primaryStage) throws IOException {
        primaryStage.setTitle("Sample updating price page");
        
        List<String> tickerList = tfw.getBaseTickers();
        
        GridPane grid = new GridPane();
        grid.setAlignment(Pos.CENTER);
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(25, 25, 25, 25));
        
        Scene scene = new Scene(grid, 500, 500);
        scene.getStylesheets().add(getClass().getResource("tickerlist.css").toExternalForm());
        
        primaryStage.setScene(scene);
        
        Text scenetitle = new Text("Ticker List");
        scenetitle.setFont(Font.font("Tahoma", FontWeight.NORMAL, 20));
        grid.add(scenetitle, 0, 0, 2, 1);
        
        Map<String, Label> priceMap = new HashMap<>();
        Map<String, Label> changeMap = new HashMap<>();
        
        for(String ticker : tickerList){
        	Label name = new Label(ticker + ": ");
        	grid.add(name, 0, tickerList.indexOf(ticker) + 1);
        	Label price = new Label();
        	grid.add(price, 1, tickerList.indexOf(ticker) + 1);
        	Label change = new Label();
        	grid.add(change, 2, tickerList.indexOf(ticker) + 1);
        	priceMap.put(ticker, price);
        	changeMap.put(ticker, change);
        }
        
        for(String ticker : priceMap.keySet()){
        	Task<Void> refresh = new Task<Void>(){
        		@Override
        		public Void call() throws Exception{
        			while(true){
        				Platform.runLater(new Runnable(){
        					@Override
        					public void run(){
        						try{
        							Stock stock = YahooFinance.get(ticker);
        							priceMap.get(ticker).setText("" + stock.getQuote().getPrice());
        							changeMap.get(ticker).setText("" + stock.getQuote().getChangeInPercent() + "%");
        						}catch(Exception e){}
        					}
        				});
        				Thread.sleep(5000);
        			}
        		}
        	};
        	Thread th = new Thread(refresh);
        	th.setDaemon(true);
        	th.start();
        }
            
        primaryStage.show();    
    }
}
