package com.waynaut.utility;

import com.waynaut.exception.ParserException;
import com.waynaut.log.Log4J;
import java.io.File;
import java.util.LinkedHashMap;

/**
 *
 * @author Rahman Md Redwanur
 */

public class AppPath {
  
    private String systemDrive;
    private String userHomeDrive;
    private Log4J log;
    
    private static AppPath instance;
    
    /**
     * To set application paths  
     * @param log log instance
     * @return ApplicationPaths
     * @throws Exception 
     */
    public synchronized static AppPath getInstance(Log4J log) throws Exception{
        
        if(instance == null){
            instance = new AppPath(log);
        }
        
    	return instance;
    }
    
   /**
    * Constructor of AppPath
    * @param log Log4J instance
    * @throws Exception General exception
    */
    private AppPath(Log4J log) throws Exception{
        
        try{
            
            this.log = log;
            this.systemDrive = getSystemDirve();
            this.userHomeDrive = getUserHomeDirectory();
            
        }catch(Exception ex){
            String systemError = String.format("%s->%s error : %s",AppPath.class.getName(),ParserException.getCallingMethod(),ex.getMessage());
            
            if(log != null){
                log.getLogger().error(systemError);
            }
        }
    }
    
    /**
     * Get System drive name
     * @return System drive where operating system present like C:, D:, E: etc.
     * @throws Exception General exception
     */
    private String getSystemDirve() throws Exception {
        
        String SystemDrive = "";  
        
        try{
            
            SystemDrive = System.getenv("SystemDrive");
            
            if (SystemDrive == null) {
                throw new IllegalStateException("System drive value is null");
            }
            
            File home = new File(SystemDrive);
            File fSystemDrive = new File(home, "");
            
            if (!fSystemDrive.exists()) {
                throw new IllegalStateException(fSystemDrive.toString());
            }
            
        }catch(Exception ex){
            String systemError = String.format("%s->%s error : %s",AppPath.class.getName(),ParserException.getCallingMethod(),ex.getMessage());
            
            if(log != null){
                log.getLogger().error(systemError);
            }
            throw new Exception(String.format("%s",ex.getMessage()));
        }
        
        return SystemDrive;
    }
    
    /**
     * User home directory where application is running
     * @return
     * @throws Exception General exception
     */
    private String getUserHomeDirectory() throws Exception {
        
        String userHome = "";  
        
        try{

            userHome = System.getProperty("user.home");

            
            if (userHome == null) {
                throw new IllegalStateException("User home dirctory value is null");
            }
            
            File home = new File(userHome);
            File fSystemDrive = new File(home, "");
            
            if (!fSystemDrive.exists()) {
                throw new IllegalStateException(fSystemDrive.toString());
            }
            
        }catch(Exception ex){
            String systemError = String.format("%s->%s error : %s",AppPath.class.getName(),ParserException.getCallingMethod(),ex.getMessage());
            
            if(log != null){
                log.getLogger().error(systemError);
            }
            throw new Exception(String.format("%s",ex.getMessage()));
        }
        
        return userHome;
    }

    /**
     * Get System drive name
     * @return System drive where operating system present like C:, D:, E: etc.
     */
    public String getSystemDirectory(){
        return this.systemDrive;
    }
    
    /**
     * User home directory where application is running
     * @return
     */
    public String getUserDirectory(){
        return this.userHomeDrive;
    }
    
    /**
     * Application directory where log, configuration file need to set.
     * @return System Drive + Waynaut like C:/Waynaut, D:/Waynaut etc.
     */
    public String getApplicationDirectory(){ 
        
        return String.format("%s/Waynaut",getSystemDirectory());
    }
    
    /**
     * Get all files from a folder path
     * @param folderPath folder path of files
     * @return list of available files of the folder
     */
    private File[] getFilefromFolder(String folderPath){
        
        File folder = new File(folderPath);
        File[] listOfFiles = folder.listFiles();
        return listOfFiles;
    }
    
    /**
     * Get file path with name,
     * @param folderPath folder path of files
     * @return Linked has map where key is file name and value is path.
     * @throws java.lang.Exception General exception
     */
    public LinkedHashMap<String, String> getFiles(String folderPath) throws Exception{
        
        LinkedHashMap<String, String> fileList =  new LinkedHashMap<>();
        try{
            
            File[] files = getFilefromFolder(folderPath);

            if(files == null) {
                throw new Exception(String.format("Files are not available in folder %s",folderPath));
            }

            for (File file : files) {

                if(file != null)
                    fileList.put(file.getName(), file.getAbsolutePath());
            }
        
        }catch(Exception ex){
            String systemError = String.format("%s->%s error : %s",AppPath.class.getName(),ParserException.getCallingMethod(),ex.getMessage());
            
            if(log != null){
                log.getLogger().error(systemError);
            }
        }
        return fileList;
    
    }
   
}

