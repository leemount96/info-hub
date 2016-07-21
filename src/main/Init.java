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
import javafx.fxml.FXMLLoader;

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
//        primaryStage.setTitle("Sample updating price page");
//        
//        List<String> tickerList = tfw.getBaseTickers();
//        
//        GridPane grid = new GridPane();
//        
//        grid.setAlignment(Pos.CENTER);
//        grid.setVgap(3);
//        grid.setHgap(3);
//        grid.setPadding(new Insets(10, 10, 10, 10));     
//        
//        // Setting columns size in percent
//        ColumnConstraints column = new ColumnConstraints();
//        column.setPercentWidth(33.4);
//        column.setFillWidth(true);
//        grid.getColumnConstraints().add(column);
//
//        column = new ColumnConstraints();
//        column.setPercentWidth(33.3);
//        column.setFillWidth(true);
//        grid.getColumnConstraints().add(column);
//
//        column = new ColumnConstraints();
//        column.setPercentWidth(33.3);
//        column.setFillWidth(true);
//        grid.getColumnConstraints().add(column);
//        
//        grid.setPrefSize(500, 600);
//        
//        Scene scene = new Scene(grid, 500, 600);
//        scene.getStylesheets().add(getClass().getResource("tickerlist.css").toExternalForm());
//        
//        
//        primaryStage.setScene(scene);
//        
//        
//        Text scenetitle = new Text("Ticker List");
//        scenetitle.setFont(Font.font("Tahoma", FontWeight.BOLD, 20));
//        grid.add(scenetitle, 0, 0, 3, 1);
//        
//        Label statusBar = new Label("Status:");
//        grid.add(statusBar, 0, 1);
//        
//        Label currentStatus = new Label("Running.");
//        grid.add(currentStatus, 1,1,2,1);
//        
//        TextField tickerField = new TextField();
//        tickerField.setPromptText("Enter a ticker.");
//        grid.add(tickerField, 0, 2, 1, 1);
//        
//        Button addButton = new Button("Add");
//        addButton.setMaxSize(Double.MAX_VALUE, Double.MAX_VALUE);
//        HBox addBox = new HBox(10);
//        addBox.setAlignment(Pos.BOTTOM_RIGHT);
//        addBox.getChildren().add(addButton);
//        grid.add(addButton, 1, 2,1,1);
//        GridPane.setFillWidth(addButton, true);
//        
//        Button delButton = new Button("Delete");
//        delButton.setMaxSize(Double.MAX_VALUE, Double.MAX_VALUE);
//        HBox delBox = new HBox(10);
//        delBox.setAlignment(Pos.BOTTOM_RIGHT);
//        delBox.getChildren().add(delButton);
//        grid.add(delButton, 2, 2, 1,1);
//        GridPane.setFillWidth(delButton, true);        
//        
//        Button tickerButton = new Button("Ticker");
//        tickerButton.setMaxSize(Double.MAX_VALUE, Double.MAX_VALUE);
//        HBox tickerBox = new HBox(10);
//        tickerBox.setAlignment(Pos.BOTTOM_RIGHT);
//        tickerBox.getChildren().add(tickerButton);
//        grid.add(tickerButton, 0, 3, 1,1);
//        GridPane.setFillWidth(tickerButton, true);
// 
//        Button priceButton = new Button("Price");
//        priceButton.setMaxSize(Double.MAX_VALUE, Double.MAX_VALUE);
//        HBox priceBox = new HBox(10);
//        priceBox.setAlignment(Pos.BOTTOM_RIGHT);
//        priceBox.getChildren().add(tickerButton);
//        grid.add(priceButton, 1, 3, 1,1);
//        GridPane.setFillWidth(priceButton, true);
//        
//        Button changeButton = new Button("Change");
//        changeButton.setMaxSize(Double.MAX_VALUE, Double.MAX_VALUE);
//        HBox changeBox = new HBox(10);
//        changeBox.setAlignment(Pos.BOTTOM_RIGHT);
//        changeBox.getChildren().add(changeButton);
//        changeButton.setOnAction(new EventHandler<ActionEvent>(){
//        	@Override
//        	public void handle(ActionEvent event){
//        		
//        	}
//        });
//        grid.add(changeButton, 2, 3, 1,1);
//        GridPane.setFillWidth(changeButton, true);
//        
//        Map<String, Label> priceMap = new HashMap<>();
//        Map<String, Label> changeMap = new HashMap<>();
//        
//        for(String ticker : tickerList){
//        	Label name = new Label(ticker + ": ");
//        	//name.setFont(Font.font("Tahoma", FontWeight.BOLD, 14));
//        	grid.add(name, 0, tickerList.indexOf(ticker) + 4);
//        	
//        	Label price = new Label();
//        	//price.setFont(Font.font("Tahoma", 14));
//        	grid.add(price, 1, tickerList.indexOf(ticker) + 4);
//        	
//        	Label change = new Label();
//            //change.setFont(Font.font("Tahoma",14));
//        	grid.add(change, 2, tickerList.indexOf(ticker) + 4);
//        	priceMap.put(ticker, price);
//        	changeMap.put(ticker, change);
//        }
//        
//        for(String ticker : priceMap.keySet()){
//        	Task<Void> refresh = new Task<Void>(){
//        		@Override
//        		public Void call() throws Exception{
//        			while(true){
//        				Platform.runLater(new Runnable(){
//        					@Override
//        					public void run(){
//        						try{
//        							Stock stock = YahooFinance.get(ticker);
//        							priceMap.get(ticker).setText("" + stock.getQuote().getPrice());
//        							changeMap.get(ticker).setText("" + stock.getQuote().getChangeInPercent() + "%");
//        							changeMap.get(ticker).setStyle("-fx-background-color: green);");
//        						}catch(Exception e){}
//        					}
//        				});
//        				Thread.sleep(5000);
//        			}
//        		}
//        	};
//        	Thread th = new Thread(refresh);
//        	th.setDaemon(true);
//        	th.start();
//        }
//            
//        primaryStage.show();    
    	
        primaryStage.setTitle("InfoHub Application");
        try {
            FXMLLoader loader = new FXMLLoader(Init.class.getResource("TickerTable.fxml"));
            AnchorPane page = (AnchorPane) loader.load();
            Scene scene = new Scene(page);
            primaryStage.setScene(scene);
            primaryStage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
