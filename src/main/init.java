package main;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.List;

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

public class init extends Application{
    public static void main(String[] args){
        launch(args);
    }
    
    @Override
    public void start(Stage primaryStage) throws IOException {
        primaryStage.setTitle("Sample updating price page");
        
        GridPane grid = new GridPane();
        grid.setAlignment(Pos.CENTER);
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(25, 25, 25, 25));
        
        Scene scene = new Scene(grid, 300, 275);
        primaryStage.setScene(scene);
        
        Text scenetitle = new Text("Sample updating prices");
        scenetitle.setFont(Font.font("Tahoma", FontWeight.NORMAL, 20));
        grid.add(scenetitle, 0, 0, 2, 1);
        
        Label stuff = new Label("AAPL:");
        grid.add(stuff, 0, 1);
        
        Label aaplPrice = new Label();
        grid.add(aaplPrice, 1, 1);
        
        Task aaplTask = new Task<Void>(){
            @Override
            public Void call() throws Exception{
                while(true){
                    Platform.runLater(new Runnable(){
                        @Override
                        public void run(){
                            try{
                                Stock aapl = YahooFinance.get("AAPL");
                                aaplPrice.setText("" + aapl.getQuote().getPrice());
                            }catch(Exception e){}
                        }
                    });
                    Thread.sleep(5000);
                }
            }
        };
        
        Label otherStuff = new Label("MSFT:");
        grid.add(otherStuff, 0, 2);
        
        Label msftPrice = new Label();
        grid.add(msftPrice, 1, 2);
        
        Task msftTask = new Task<Void>(){
            @Override
            public Void call() throws Exception{
                while(true){
                    Platform.runLater(new Runnable(){
                        @Override
                        public void run(){
                            try{
                                Stock msft = YahooFinance.get("MSFT");
                                msftPrice.setText("" + msft.getQuote().getPrice());
                                }catch(Exception e){}
                            }
                        });
                        Thread.sleep(5000);
                    }
                }
            };
            
            Thread th1 = new Thread(aaplTask);
            th1.setDaemon(true);
            th1.start();
            
            Thread th2 = new Thread(msftTask);
            th2.setDaemon(true);
            th2.start();
            
            primaryStage.show();    
        }
}
