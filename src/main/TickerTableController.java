package main;

import java.util.List;
import java.math.BigDecimal;


import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.*;
import yahoofinance.*;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.concurrent.Task;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import java.util.concurrent.atomic.AtomicInteger;

import javafx.application.Application;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.concurrent.ScheduledService;
import javafx.concurrent.Task;
import javafx.concurrent.WorkerStateEvent;
import javafx.event.EventHandler;
import javafx.stage.Stage;
import javafx.util.Duration;

/**
 * Class for making ticker table. Taken from http://code.makery.ch/blog/javafx-8-tableview-sorting-filtering/
 */
public class TickerTableController {
	@FXML
	private TextField tickerField;
	@FXML
	private TableView<StockWorker> tickerTable;
	@FXML
	private TableColumn<StockWorker, String> tickerColumn;
	@FXML
	private TableColumn<StockWorker, Number> priceColumn;
	@FXML
	private TableColumn<StockWorker, Number> changeColumn;

	private ObservableList<StockWorker> masterData = FXCollections.observableArrayList();
	
	private final TextFileWorker tfw = new TextFileWorker("text-files/base-tickers");
	
	private final EmailGenerator generator = new EmailGenerator();
	
	private final List<String> tickerList;

	/**
	 * Adding stock list to masterData
	 */
	public TickerTableController(){
		this.tickerList = tfw.getBaseTickers();
		for(String t : tickerList){
			masterData.add(new StockWorker(t));
		}
	}
	
	/**
	 * Initializes the controller class. 
	 * 
	 * Initializes the table columns and sets up sorting and filtering.
	 */
	@FXML
	private synchronized void initialize(){   
	    TimerService service = new TimerService();
	    AtomicInteger count = new AtomicInteger(0);
	    service.setCount(count.get());
	    service.setPeriod(Duration.seconds(15));
	    service.setOnSucceeded(new EventHandler<WorkerStateEvent>(){
	        @Override
	        public void handle(WorkerStateEvent t){
                System.out.println("Update number " + t.getSource().getValue());
                count.set((int) t.getSource().getValue());
                
                /* CODE THAT BLOCKS 
                if(!(count.get()==1)){
                    System.out.println("Reupdating the tickers.");
                    for(StockWorker worker: masterData){
                        worker.updateTicker();
                    }
                }
                 END CODE THAT BLOCKS */
                
        		tickerColumn.setCellValueFactory(cellData -> cellData.getValue().tickerProperty());
        		priceColumn.setCellValueFactory(cellData -> cellData.getValue().priceProperty());
        		changeColumn.setCellValueFactory(cellData -> cellData.getValue().changeProperty());
        		changeColumn.setCellFactory(column -> {
        			return new TableCell<StockWorker, Number>(){
        			    
        				@Override
        				protected void updateItem(Number item, boolean empty){
        					super.updateItem(item, empty);
        					
        		            if (item == null || empty) {
        		                setText(null);
        		                setStyle("");
        		            } else { 
        		            	setText(item.toString() + "%");
        		            	if(item.doubleValue() < 0){
        		            		setStyle("-fx-background-color: red");
        		            	}else{
        		            		setStyle("-fx-background-color: green");
        						}
        					}
        				}
        			};
        		});
        	SortedList<StockWorker> sortedData = new SortedList<>(masterData);
        	        
        	sortedData.comparatorProperty().bind(tickerTable.comparatorProperty());
        	        
        	tickerTable.setItems(sortedData); 
        	
        	System.out.println("Waiting " + service.getPeriod() + "until next update.");
	        }
	        
	    });
	    service.start();    
	}
	
    @FXML 
    protected synchronized void handleAddTickerAction(ActionEvent event) {
    	String tickerName = tickerField.getText();
    	if(tfw.addTicker(tickerName)){
    		masterData.add(new StockWorker(tickerName));
    	}else{
    		//Set status to say "Invalid Ticker" or something
    	}
    	tickerField.clear();
    }
    
    @FXML
    protected synchronized void handleRemoveTickerAction(ActionEvent event){
    	String tickerName = tickerField.getText();
    	if(tfw.deleteTicker(tickerName)){
    		for(StockWorker s : masterData){
    			if(s.getTicker().equals(tickerName)){
    				masterData.remove(s);
    				tfw.deleteTicker(tickerName);
    				tickerField.clear();
    				break;
    			}
    		}
    	}else{
    		//Set status to say "Invalid Ticker" or something
    	}
    }

    /** Helper class that allows for timed multi-threading. */
    private static class TimerService extends ScheduledService<Integer> {
        private IntegerProperty count = new SimpleIntegerProperty();

        public final void setCount(Integer value) {
            count.set(value);
        }

        public final Integer getCount() {
            return count.get();
        }

        public final IntegerProperty countProperty() {
            return count;
        }

        protected Task<Integer> createTask() {
            return new Task<Integer>() {
                protected Integer call() {
                    //Adds 1 to the count
                    count.set(getCount() + 1);
                    return getCount();
                }
            };
        }
    }
}
