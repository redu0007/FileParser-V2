
import com.waynaut.utility.Configuration;


/**
 *
 * @author Rahman Md Redwanur
 */

public class RunMe {

	private static Configuration config;
	
    public static void main(String[] args) {
            
    	AppManager manager = new AppManager();
    	config = manager.getConfig();
    	
    	manager.doFileParsing();
    	manager.displayDataFromObjects();
    	
    	if(config.getLoadInDatabse().toUpperCase().equals("ACTIVE")){
	    	manager.insertDataInDatabse();
	    	manager.displayDataFromDatabase();
    	}
        
    }
    
}
