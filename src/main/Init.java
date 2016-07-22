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
	private final static TextFileWorker tfw = new TextFileWorker("text-files/base-tickers");
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
