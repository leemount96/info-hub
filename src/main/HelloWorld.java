package main;
import java.io.IOException;
import java.math.BigDecimal;
import yahoofinance.*;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

public class HelloWorld extends Application{
	public static void main(String[] args){
		launch(args);
	}
	
    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("Test UI Example");
        Button btn = new Button();
        btn.setText("Get current AAPL Price");
        btn.setOnAction(new EventHandler<ActionEvent>() {
 
            @Override
            public void handle(ActionEvent event) {
            	try {
					Stock aapl = YahooFinance.get("AAPL");
	    			BigDecimal price = aapl.getQuote().getPrice();
	                System.out.println("AAPL Price: " + price);
				} catch (IOException e) {
					e.printStackTrace();
				}
            }
        });
        
        StackPane root = new StackPane();
        root.getChildren().add(btn);
        primaryStage.setScene(new Scene(root, 300, 250));
        primaryStage.show();
    }
}