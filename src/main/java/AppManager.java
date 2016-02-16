import java.util.LinkedHashMap;
import java.util.Set;

import com.waynaut.database.DatabaseManager;
import com.waynaut.engine.ParserEngine;
import com.waynaut.exception.ParserException;
import com.waynaut.log.Log4J;
import com.waynaut.pojo.controller.ObjectController;
import com.waynaut.utility.AppPath;
import com.waynaut.utility.Configuration;
import com.waynaut.utility.Display;
import com.waynaut.utility.Display.DISPLAY_TYPE;

public class AppManager {

	private Log4J log;
    private Configuration config;
    private DatabaseManager dbManager;
    private AppPath appPath;
    private Display dataDisplay;
    private ParserEngine engInstance;
    private ObjectController objectController;

    public AppManager(){
    	
    	try{
	    	
    		String logPath = "C:\\Waynaut\\logs";
	        
	        log = new Log4J(RunMe.class.getName(),String.format("%s/FileParser.log",logPath),"INFO");
	        
	        appPath = AppPath.getInstance(log);
	        config = Configuration.getInstance(log, appPath);
	        
	        dbManager = DatabaseManager.getInstance(log, config);
	        engInstance = new ParserEngine(log);
	        dataDisplay = new Display(engInstance,log,config); 
	        objectController = new ObjectController(dbManager,log,dataDisplay);
		    
    	} catch (Exception ex) {
		        String systemError = String.format("%s->%s error : %s",AppManager.class.getName(),ParserException.getCallingMethod(),ex.getMessage());
		        
		        if(log != null){
		            log.getLogger().error(systemError);
		        }
		        
	    }
    	
    }
    
    
    public Configuration getConfig() {
		return config;
	}

	/**
	 * Parse all file's data and assign to POJOs
	 */
    public void doFileParsing(){
    	
    	try{
    		
	    	
	         
	         LinkedHashMap<String, String> fileLinkedList = appPath.getFiles(config.getFolderPathOfFiles());
	         
	         Set<String> setFiles = fileLinkedList.keySet();
	          
	         
	         
	         for (Object fileName : setFiles) {
	             
	             String filePath = fileLinkedList.get(fileName.toString());
	             
	             //With out object data show
	             //dataDisplay.show(fileName.toString(),filePath);
	             
	             //Assign data to object
	             objectController.setDataToObject(engInstance, fileName.toString(), filePath);
	             
	             if(dbManager != null && dbManager.isConnected()){
	                 objectController.insertAllDataInDatabse(fileName.toString());
	             }
	         }
	         
	    } catch (Exception ex) {
	        String systemError = String.format("%s->%s error : %s",AppManager.class.getName(),ParserException.getCallingMethod(),ex.getMessage());
	        
	        if(log != null){
	            log.getLogger().error(systemError);
	        }
	    }
    }
    
    /**
     * Display the Objects data to console
     */
    public void displayDataFromObjects(){
    	
    	try{
    		
		    dataDisplay.showAgency(objectController.getAgencyList(),DISPLAY_TYPE.FILE);
		    dataDisplay.showCalander(objectController.getCalanderList(),DISPLAY_TYPE.FILE);
		    dataDisplay.showCalanderDates(objectController.getCalanderDateList(),DISPLAY_TYPE.FILE);
		    dataDisplay.showFeedInfo(objectController.getFeedInfoList(),DISPLAY_TYPE.FILE);
		    dataDisplay.showFrequencies(objectController.getFrequenciesList(),DISPLAY_TYPE.FILE);
		    dataDisplay.showRoutes(objectController.getRoutesList(),DISPLAY_TYPE.FILE);
		    dataDisplay.showStopTimes(objectController.getStopTimeList(),DISPLAY_TYPE.FILE);
		    dataDisplay.showStops(objectController.getStopsList(),DISPLAY_TYPE.FILE);
		    dataDisplay.showTransfer(objectController.getTransferList(),DISPLAY_TYPE.FILE);
		    dataDisplay.showTrips(objectController.getTripsList(),DISPLAY_TYPE.FILE);
		    
    	} catch (Exception ex) {
	        String systemError = String.format("%s->%s error : %s",AppManager.class.getName(),ParserException.getCallingMethod(),ex.getMessage());
	        
	        if(log != null){
	            log.getLogger().error(systemError);
	        }
	    }
    }
    
    /**
     * Insert all Objects data to database
     */
    public void insertDataInDatabse(){
    	
    	try{
    		
    		dbManager.connect();
            
            if(dbManager.isConnected()){
                dbManager.createtDbAndTables();
            }
	         
            LinkedHashMap<String, String> fileLinkedList = appPath.getFiles(config.getFolderPathOfFiles());
	         
	        Set<String> setFiles = fileLinkedList.keySet();
	         
	        for (Object fileName : setFiles) {
	             
	        	if(dbManager != null && dbManager.isConnected() && config.getLoadInDatabse().toUpperCase().equals("ACTIVE")){
	                 objectController.insertAllDataInDatabse(fileName.toString());
	        	}
	        }
	         
	    } catch (Exception ex) {
	        String systemError = String.format("%s->%s error : %s",AppManager.class.getName(),ParserException.getCallingMethod(),ex.getMessage());
	        
	        if(log != null){
	            log.getLogger().error(systemError);
	        }
	    }
    	finally{
            
    		if(dbManager.isConnected())
                dbManager.disconnect();
        }
    }
    
    /**
     * Display data from database
     */
    public void displayDataFromDatabase(){
    	
    	try{

    		dbManager.connect();
          
            if(dbManager != null && dbManager.isConnected() && config.getLoadInDatabse().toUpperCase().equals("ACTIVE")){
                
                dataDisplay.showAgency(dbManager.getAllAgency(),DISPLAY_TYPE.DATABSE);
                dataDisplay.showCalander(dbManager.getAllCalander(),DISPLAY_TYPE.DATABSE);
                dataDisplay.showCalanderDates(dbManager.getAllCalanderDates(),DISPLAY_TYPE.DATABSE);
                dataDisplay.showFeedInfo(dbManager.getAllFeedInfo(),DISPLAY_TYPE.DATABSE);
                dataDisplay.showFrequencies(dbManager.getAllFrequencies(),DISPLAY_TYPE.DATABSE);
                dataDisplay.showRoutes(dbManager.getAllRoute(),DISPLAY_TYPE.DATABSE);
                dataDisplay.showStopTimes(dbManager.getAllStopTime(),DISPLAY_TYPE.DATABSE);
                dataDisplay.showStops(dbManager.getAllStops(),DISPLAY_TYPE.DATABSE);
                dataDisplay.showTransfer(dbManager.getAllTransfer(),DISPLAY_TYPE.DATABSE);
                dataDisplay.showTrips(dbManager.getAllTrips(),DISPLAY_TYPE.DATABSE);
            }

    	} catch (Exception ex) {
	        String systemError = String.format("%s->%s error : %s",AppManager.class.getName(),ParserException.getCallingMethod(),ex.getMessage());
	        
	        if(log != null){
	            log.getLogger().error(systemError);
	        }
	    }
    	finally{
            
    		if(dbManager.isConnected())
                dbManager.disconnect();
        }
    }
}
