
package com.waynaut.pojo.controller;

import com.waynaut.database.DatabaseManager;
import com.waynaut.engine.ParserEngine;
import com.waynaut.exception.ParserException;
import com.waynaut.log.Log4J;
import com.waynaut.utility.Display;
import com.waynaut.pojo.objects.Agency;
import com.waynaut.pojo.objects.CalendarDates;
import com.waynaut.pojo.objects.Calendar;
import com.waynaut.pojo.objects.FeedInfo;
import com.waynaut.pojo.objects.Frequencies;
import com.waynaut.pojo.objects.Routes;
import com.waynaut.pojo.objects.StopTime;
import com.waynaut.pojo.objects.Stops;
import com.waynaut.pojo.objects.Transfer;
import com.waynaut.pojo.objects.Trips;
import java.util.ArrayList;

/**
 *
 * @author Rahman Md Redwanur
 */
public class ObjectController {
    
    private final Log4J log;
    private final DatabaseManager dbManager;
    private final Display dispaly;
    
    private final ArrayList<Agency> agencyList;
    private final ArrayList<Calendar> calanderList;
    private final ArrayList<CalendarDates> calanderDateList;
    private final ArrayList<FeedInfo> feedInfoList;
    private final ArrayList<Frequencies> frequenciesList;
    private final ArrayList<Routes> routesList;
    private final ArrayList<StopTime> stopTimeList;
    private final ArrayList<Stops> stopsList;
    private final ArrayList<Transfer> transferList;
    private final ArrayList<Trips> tripsList;
    
    private final String sAgency = "agency.txt";
    private final String sCalendar = "calendar.txt";
    private final String sCalendar_dates = "calendar_dates.txt";
    private final String sFeed_info = "feed_info.txt";
    private final String sFrequencies = "frequencies.txt";
    private final String sRoutes = "routes.txt";
    private final String sStop_times = "stop_times.txt";
    private final String sStops = "stops.txt";
    private final String sTransfers = "transfers.txt";
    private final String sTrips = "trips.txt";
    
    private double total = 0;
    
    public ObjectController(DatabaseManager dbManager,Log4J log,Display dispaly) {
       
        this.dbManager = dbManager;
        this.log = log;
        this.dispaly = dispaly;
        
        agencyList = new ArrayList<>(); 
        calanderList = new ArrayList<>(); 
        calanderDateList = new ArrayList<>(); 
        feedInfoList = new ArrayList<>(); 
        frequenciesList = new ArrayList<>(); 
        routesList = new ArrayList<>();
        stopsList = new ArrayList<>();
        stopTimeList = new ArrayList<>();
        transferList = new ArrayList<>(); 
        tripsList = new ArrayList<>(); 
    }
    
    /**
     * Assign data from file to objects
     * @param engineInstance instance of Parser Engine
     * @param fileName file name
     * @param path  file path that have to load in object as list
     * @throws Exceptiongeneral exception string type
     */
    public void setDataToObject(ParserEngine engineInstance,String fileName, String path) throws Exception{

        try {
            
            if(engineInstance != null ){
            
                if(path.isEmpty()){
                    throw new Exception("File path is empty");
                }
            
                switch(fileName.toLowerCase()){
                    
                    case sAgency: 
                        setAgencyData(engineInstance,path);
                        break;
                    case sCalendar: 
                        setCalendarData(engineInstance,path);
                        break;
                    case sCalendar_dates: 
                        setCalendarDatesData(engineInstance,path);
                        break;
                    case sFeed_info: 
                        setFeedInfoData(engineInstance,path);
                        break;
                    case sFrequencies: 
                        setFrequencyData(engineInstance,path);
                        break;
                    case sRoutes: 
                        setRoutesData(engineInstance,path);
                        break;
                    case sStop_times: 
                        setStopTimeData(engineInstance,path);
                        break;
                    case sStops: 
                        setStopsData(engineInstance,path);
                        break;  
                    case sTransfers: 
                        setTransferData(engineInstance,path);
                        break;
                    case sTrips: 
                        setTripsData(engineInstance,path);
                        break;
                   
                }
            }
            else{
                throw new Exception("Engine instance is null");
            }
        } 
        catch (Exception ex) {
            
            String systemError = String.format("%s->%s error : %s",ObjectController.class.getName(),ParserException.getCallingMethod(),ex.getMessage());
            System.out.println(ex.getMessage());
            
            if(log != null){
                log.getLogger().error(systemError);
            }
            throw new Exception(ex.getMessage());
        }
    }
    
    /**
     * Insert All data from object to database
     * @param fileName File name 
     * @throws Exceptiongeneral exception string type
     */
    
    public void insertAllDataInDatabse(String fileName) throws Exception{

        try {

            switch(fileName.toLowerCase()){

                case sAgency: 
                    insertAgencyData(fileName);
                    break;
                case sCalendar: 
                    insertCalendarData(fileName);
                    break;
                case sCalendar_dates: 
                	//This take at lest 7 minutes to load data in database
                    //insertCalendarDatesData(fileName); 
                    break;
                case sFeed_info: 
                    insertFeedInfoData(fileName);
                    break;
                case sFrequencies: 
                    insertFrequencyData(fileName);
                    break;
                case sRoutes: 
                    insertRoutesData(fileName);
                    break;
                case sStop_times: 
                    insertStopTimeData(fileName);
                    break;
                case sStops: 
                    insertStopsData(fileName);
                    break;  
                case sTransfers: 
                    insertTransferData(fileName);
                    break;
                case sTrips: 
                    insertTripsData(fileName);
                    break;
            }
        } 
        catch (Exception ex) {
            
            String systemError = String.format("%s->%s error : %s",ObjectController.class.getName(),ParserException.getCallingMethod(),ex.getMessage());
            System.out.println(ex.getMessage());
            
            if(log != null){
                log.getLogger().error(systemError);
            }
            throw new Exception(ex.getMessage());
        }
    }
    /**
     * Set data from file agency to Object agency
     * @param engineInstance parser engine instance
     * @param path  file path
     */
    
    private void setAgencyData(ParserEngine engineInstance, String path){
       
        try{
            
            ArrayList <ArrayList<String>> dataList = engineInstance.parse(path);
            
            Agency agency;

            boolean firstRow = true;        
            
            for (ArrayList<String> eachLineDataList : dataList) {

                if(!firstRow) {

                    agency = new Agency(eachLineDataList.get(0),
                            eachLineDataList.get(1),
                            eachLineDataList.get(2),
                            eachLineDataList.get(3),
                            eachLineDataList.get(4),
                            eachLineDataList.get(5)
                    );
                    
                    agencyList.add(agency);
                }
                
                firstRow = false;
            }
        } 
        catch (Exception ex) {
            
            String systemError = String.format("%s->%s error : %s",ObjectController.class.getName(),ParserException.getCallingMethod(),ex.getMessage());
            System.out.println(ex.getMessage());
            
            if(log != null){
                log.getLogger().error(systemError);
            }
        }
    } 
    
    /**
     * Insert data from agency object to database
     * @param fileName Parsing file name 
     */
    public void insertAgencyData(String fileName){
        
        try{
            
            total = agencyList.size();
            if(dbManager != null && dbManager.isConnected()){
                
                dbManager.setCounter(0);       
                boolean allDataInserted = false;

                for (Agency  agency : agencyList) {
                    allDataInserted = dbManager.insertAgency(agency);
                    dispaly.progress(dbManager.getCounter(),total,fileName);  
                }

                if(allDataInserted){

                    String Info = String.format("%s->%s info : %s all data inserted successfully",ObjectController.class.getName(),ParserException.getCallingMethod(),fileName);
                    if(log != null){
                        log.getLogger().info(Info);
                    }
                } 
            }
        } 
        catch (Exception ex) {
            
            String systemError = String.format("%s->%s error : %s",ObjectController.class.getName(),ParserException.getCallingMethod(),ex.getMessage());
            System.out.println(ex.getMessage());
            
            if(log != null){
                log.getLogger().error(systemError);
            }
        }
    }
    
    /**
     * Set data from file Calendar to Object Calendar
     * @param engineInstance parser Engine instance
     * @param path  file path
     */
    private void setCalendarData(ParserEngine engineInstance, String path){
        
        try{
            
            ArrayList <ArrayList<String>> dataList = engineInstance.parse(path);
            
            Calendar calander;

            boolean firstRow = true;        

            for (ArrayList<String> eachLineDataList : dataList) {

                if(!firstRow) {

                    calander = new Calendar(
                            eachLineDataList.get(0),
                            eachLineDataList.get(1),
                            eachLineDataList.get(2),
                            eachLineDataList.get(3),
                            eachLineDataList.get(4),
                            eachLineDataList.get(5),
                            eachLineDataList.get(6),
                            eachLineDataList.get(7),
                            eachLineDataList.get(8),
                            eachLineDataList.get(9)
                    );
                    
                    calanderList.add(calander);
                }
                
                firstRow = false;
            }
        } 
        catch (Exception ex) {
            
            String systemError = String.format("%s->%s error : %s",Display.class.getName(),ParserException.getCallingMethod(),ex.getMessage());
            System.out.println(ex.getMessage());
            
            if(log != null){
                log.getLogger().error(systemError);
            }
        }
    }
    /**
     * Inser data from Calander object to database
     * @param fileName csv file name 
     */
    
    private void insertCalendarData(String fileName){
        
        try{

            total = calanderList.size();
            
            if(dbManager != null && dbManager.isConnected()){
                
                dbManager.setCounter(0);              
                boolean allDataInserted = false;
                
                for (Calendar calander: calanderList) {
                    allDataInserted = dbManager.insertCalander(calander);
                    dispaly.progress(dbManager.getCounter(),total,fileName);  
                }
            
                if(allDataInserted){

                    String Info = String.format("%s->%s info : %s all data inserted successfully",ObjectController.class.getName(),ParserException.getCallingMethod(),fileName);
                    if(log != null){
                        log.getLogger().info(Info);
                    }
                }
           }
  
        } 
        catch (Exception ex) {
            
            String systemError = String.format("%s->%s error : %s",ObjectController.class.getName(),ParserException.getCallingMethod(),ex.getMessage());
            System.out.println(ex.getMessage());
            
            if(log != null){
                log.getLogger().error(systemError);
            }
        }
    }
    
    private void setCalendarDatesData(ParserEngine engineInstance, String path){
        
        try{
            
            ArrayList <ArrayList<String>> dataList = engineInstance.parse(path);

            CalendarDates calanderDates;

            boolean firstRow = true;        

            for (ArrayList<String> eachLineDataList : dataList) {

                if(!firstRow) {

                    calanderDates = new CalendarDates(
                            eachLineDataList.get(0),
                            eachLineDataList.get(1),
                            eachLineDataList.get(2)
                    );
                    calanderDateList.add(calanderDates);
                }
                
                firstRow = false;
            }
        } 
        catch (Exception ex) {
            
            String systemError = String.format("%s->%s error : %s",ObjectController.class.getName(),ParserException.getCallingMethod(),ex.getMessage());
            System.out.println(ex.getMessage());
            
            if(log != null){
                log.getLogger().error(systemError);
            }
        }
    }
    
    private void insertCalendarDatesData(String fileName){
        
        try{
           
            total = calanderDateList.size();
            if(dbManager != null && dbManager.isConnected()){
                
                dbManager.setCounter(0);      
                boolean allDataInserted = false;

                for (CalendarDates calanderDates : calanderDateList) {

                    allDataInserted = dbManager.insertCalanderDates(calanderDates);
                    dispaly.progress(dbManager.getCounter(),total,fileName);  
                }

                if(allDataInserted){

                    String Info = String.format("%s->%s info : %s all data inserted successfully",ObjectController.class.getName(),ParserException.getCallingMethod(),fileName);
                    if(log != null){
                        log.getLogger().info(Info);
                    }
                }
            }
        } 
        catch (Exception ex) {
            
            String systemError = String.format("%s->%s error : %s",ObjectController.class.getName(),ParserException.getCallingMethod(),ex.getMessage());
            System.out.println(ex.getMessage());
            
            if(log != null){
                log.getLogger().error(systemError);
            }
        }
    }
    
    private void setFeedInfoData(ParserEngine engineInstance, String path){
        
        try{
            
            ArrayList <ArrayList<String>> dataList = engineInstance.parse(path);

            FeedInfo feedInfo;

            boolean firstRow = true;        

            for (ArrayList<String> eachLineDataList : dataList) {

                if(!firstRow) {

                    feedInfo = new FeedInfo(
                            eachLineDataList.get(0),
                            eachLineDataList.get(1),
                            eachLineDataList.get(2),
                            eachLineDataList.get(3),
                            eachLineDataList.get(4),
                            eachLineDataList.get(5)
                    );

                    feedInfoList.add(feedInfo);
                }
                firstRow = false;
            }
        } 
        catch (Exception ex) {
            
            String systemError = String.format("%s->%s error : %s",ObjectController.class.getName(),ParserException.getCallingMethod(),ex.getMessage());
            System.out.println(ex.getMessage());
            
            if(log != null){
                log.getLogger().error(systemError);
            }
        }
    }
    
    private void insertFeedInfoData( String fileName){
        
        try{
            
            total = feedInfoList.size();
            if(dbManager != null && dbManager.isConnected()){
                
                dbManager.setCounter(0);            
                boolean allDataInserted = false;
                
                for (FeedInfo feedInfo : feedInfoList) {

                    allDataInserted = dbManager.insertFeedInfo(feedInfo);
                    dispaly.progress(dbManager.getCounter(),total,fileName);  
                }
            
                if(allDataInserted){

                    String Info = String.format("%s->%s info : %s all data inserted successfully",ObjectController.class.getName(),ParserException.getCallingMethod(),fileName);
                    if(log != null){
                        log.getLogger().info(Info);
                    }
                }
            }
        } 
        catch (Exception ex) {
            
            String systemError = String.format("%s->%s error : %s",Display.class.getName(),ParserException.getCallingMethod(),ex.getMessage());
            System.out.println(ex.getMessage());
            
            if(log != null){
                log.getLogger().error(systemError);
            }
        }
    }
     
    private void setFrequencyData(ParserEngine engineInstance, String path){
        
        try{
            
            ArrayList <ArrayList<String>> dataList = engineInstance.parse(path);

            Frequencies frequencies;
            boolean firstRow = true;        

            for (ArrayList<String> eachLineDataList : dataList) {

                if(!firstRow) {

                    frequencies = new Frequencies(
                            eachLineDataList.get(0),
                            eachLineDataList.get(1),
                            eachLineDataList.get(2),
                            eachLineDataList.get(3)
                    );
                    
                    frequenciesList.add(frequencies);
                }
                firstRow = false;
            }
        } 
        catch (Exception ex) {
            
            String systemError = String.format("%s->%s error : %s",ObjectController.class.getName(),ParserException.getCallingMethod(),ex.getMessage());
            System.out.println(ex.getMessage());
            
            if(log != null){
                log.getLogger().error(systemError);
            }
        }
    }
     
    private void insertFrequencyData(String fileName){
        
        try{

            total = frequenciesList.size();
            if(dbManager != null && dbManager.isConnected()){
                
                dbManager.setCounter(0);            
      
                boolean allDataInserted = false;
                
                for (Frequencies frequencies : frequenciesList) {

                    allDataInserted = dbManager.insertFrequencies(frequencies);
                    dispaly.progress(dbManager.getCounter(),total,fileName);  
                }
            
                if(allDataInserted){

                    String Info = String.format("%s->%s info : %s all data inserted successfully",ObjectController.class.getName(),ParserException.getCallingMethod(),fileName);
                    if(log != null){
                        log.getLogger().info(Info);
                    }
                }
            }
  
        } 
        catch (Exception ex) {
            
            String systemError = String.format("%s->%s error : %s",ObjectController.class.getName(),ParserException.getCallingMethod(),ex.getMessage());
            System.out.println(ex.getMessage());
            
            if(log != null){
                log.getLogger().error(systemError);
            }
        }
    }
    
    private void setRoutesData(ParserEngine engineInstance, String path){
        
        try{
            
            ArrayList <ArrayList<String>> dataList = engineInstance.parse(path);

            Routes routes;
            boolean firstRow = true;        

            for (ArrayList<String> eachLineDataList : dataList) {

                if(!firstRow) {

                    routes = new Routes(
                            eachLineDataList.get(0),
                            eachLineDataList.get(1),
                            eachLineDataList.get(2),
                            eachLineDataList.get(3),
                            eachLineDataList.get(4),
                            eachLineDataList.get(5),
                            eachLineDataList.get(6),
                            eachLineDataList.get(7),
                            eachLineDataList.get(8)
                    );

                    routesList.add(routes);
                }
                
                firstRow = false;
            }
        } 
        catch (Exception ex) {
            
            String systemError = String.format("%s->%s error : %s",ObjectController.class.getName(),ParserException.getCallingMethod(),ex.getMessage());
            System.out.println(ex.getMessage());
            
            if(log != null){
                log.getLogger().error(systemError);
            }
        }
    }
    
    private void insertRoutesData(String fileName){
        
        try{

            total = routesList.size();
            
            if(dbManager != null && dbManager.isConnected()){
                
                dbManager.setCounter(0);       
   
                boolean allDataInserted = false;

                for (Routes routes : routesList) {

                    allDataInserted = dbManager.insertRoute(routes);
                    dispaly.progress(dbManager.getCounter(),total,fileName); 

                }

                if(allDataInserted){

                    String Info = String.format("%s->%s info : %s all data inserted successfully",ObjectController.class.getName(),ParserException.getCallingMethod(),fileName);
                    if(log != null){
                        log.getLogger().info(Info);
                    }
                }
            }
        } 
        catch (Exception ex) {
            
            String systemError = String.format("%s->%s error : %s",Display.class.getName(),ParserException.getCallingMethod(),ex.getMessage());
            System.out.println(ex.getMessage());
            
            if(log != null){
                log.getLogger().error(systemError);
            }
        }
    }
    
    private void setStopsData(ParserEngine engineInstance, String path){
        
        try{
            
            ArrayList <ArrayList<String>> dataList = engineInstance.parse(path);

            Stops stops;
            boolean firstRow = true;        

            for (ArrayList<String> eachLineDataList : dataList) {

                if(!firstRow) {

                    stops = new Stops(
                            eachLineDataList.get(0),
                            eachLineDataList.get(1),
                            eachLineDataList.get(2),
                            eachLineDataList.get(3),
                            eachLineDataList.get(4),
                            eachLineDataList.get(5),
                            eachLineDataList.get(6),
                            eachLineDataList.get(7),
                            eachLineDataList.get(8),
                            eachLineDataList.get(9),
                            eachLineDataList.get(10),
                            eachLineDataList.get(11),
                            eachLineDataList.get(12),
                            eachLineDataList.get(13),
                            eachLineDataList.get(14),
                            eachLineDataList.get(15),
                            eachLineDataList.get(16)
                    );
                    stopsList.add(stops);
                }
                firstRow = false;
            }
        } 
        catch (Exception ex) {
            
            String systemError = String.format("%s->%s error : %s",ObjectController.class.getName(),ParserException.getCallingMethod(),ex.getMessage());
            System.out.println(ex.getMessage());
            
            if(log != null){
                log.getLogger().error(systemError);
            }
        }
    }
    
    private void insertStopsData(String fileName){
        
        try{
            
            total = stopsList.size();
            if(dbManager != null && dbManager.isConnected()){
                
                dbManager.setCounter(0);       

                boolean allDataInserted = false;

                for (Stops stops : stopsList) {
                    allDataInserted = dbManager.insertStops(stops);
                    dispaly.progress(dbManager.getCounter(),total,fileName); 
                }

                if(allDataInserted){

                    String Info = String.format("%s->%s info : %s all data inserted successfully",ObjectController.class.getName(),ParserException.getCallingMethod(),fileName);
                    if(log != null){
                        log.getLogger().info(Info);
                    }
                }
            }
        } 
        catch (Exception ex) {
            
            String systemError = String.format("%s->%s error : %s",ObjectController.class.getName(),ParserException.getCallingMethod(),ex.getMessage());
            System.out.println(ex.getMessage());
            
            if(log != null){
                log.getLogger().error(systemError);
            }
        }
    }

    private void setStopTimeData(ParserEngine engineInstance, String path){
        
        try{
            
            ArrayList <ArrayList<String>> dataList = engineInstance.parse(path);

            StopTime stoptime;
            boolean firstRow = true;        

            for (ArrayList<String> eachLineDataList : dataList) {

                if(!firstRow) {

                    stoptime = new StopTime(
                            eachLineDataList.get(0),
                            eachLineDataList.get(1),
                            eachLineDataList.get(2),
                            eachLineDataList.get(3),
                            eachLineDataList.get(4),
                            eachLineDataList.get(5),
                            eachLineDataList.get(6),
                            eachLineDataList.get(7),
                            eachLineDataList.get(8),
                            eachLineDataList.get(9)
                    );
                    stopTimeList.add(stoptime);
                }
                firstRow = false;
            }
        } 
        catch (Exception ex) {
            
            String systemError = String.format("%s->%s error : %s",ObjectController.class.getName(),ParserException.getCallingMethod(),ex.getMessage());
            System.out.println(ex.getMessage());
            
            if(log != null){
                log.getLogger().error(systemError);
            }
        }
    }
    
    private void insertStopTimeData(String fileName){
        
        try{
            
            total = stopTimeList.size();
            if(dbManager != null && dbManager.isConnected()){
                
                dbManager.setCounter(0);   
           
                boolean allDataInserted = false;
            
                for (StopTime stopTime : stopTimeList) {
                    allDataInserted = dbManager.insertStopTime(stopTime);
                    dispaly.progress(dbManager.getCounter(),total,fileName); 
                }
            
                if(allDataInserted){

                    String Info = String.format("%s->%s info : %s all data inserted successfully",ObjectController.class.getName(),ParserException.getCallingMethod(),fileName);
                    if(log != null){
                        log.getLogger().info(Info);
                    }
                }
            }
        } 
        catch (Exception ex) {
            
            String systemError = String.format("%s->%s error : %s",ObjectController.class.getName(),ParserException.getCallingMethod(),ex.getMessage());
            System.out.println(ex.getMessage());
            
            if(log != null){
                log.getLogger().error(systemError);
            }
        }
    }
    
    private void setTransferData(ParserEngine engineInstance, String path){
        
        try{
            
            ArrayList <ArrayList<String>> dataList = engineInstance.parse(path);
            Transfer transfer;

            boolean firstRow = true;        
            
            for (ArrayList<String> eachLineDataList : dataList) {

                if(!firstRow) {

                    transfer = new Transfer(
                            eachLineDataList.get(0),
                            eachLineDataList.get(1),
                            eachLineDataList.get(2),
                            eachLineDataList.get(3)
                    );
                    transferList.add(transfer);
                }
                
                firstRow = false;
            }
        } 
        catch (Exception ex) {
            
            String systemError = String.format("%s->%s error : %s",ObjectController.class.getName(),ParserException.getCallingMethod(),ex.getMessage());
            System.out.println(ex.getMessage());
            
            if(log != null){
                log.getLogger().error(systemError);
            }
        }
    }
    
    private void insertTransferData(String fileName){
        
        try{
            

            total = transferList.size();
            if(dbManager != null && dbManager.isConnected()){
                
                dbManager.setCounter(0); 
    
                boolean allDataInserted = false;

                for (Transfer transfer : transferList) {
                    allDataInserted = dbManager.insertTransfer(transfer);
                    dispaly.progress(dbManager.getCounter(),total,fileName);
                }

                if(allDataInserted){

                    String Info = String.format("%s->%s info : %s all data inserted successfully",ObjectController.class.getName(),ParserException.getCallingMethod(),fileName);
                    if(log != null){
                        log.getLogger().info(Info);
                    }
                }
            }
        } 
        catch (Exception ex) {
            
            String systemError = String.format("%s->%s error : %s",ObjectController.class.getName(),ParserException.getCallingMethod(),ex.getMessage());
            System.out.println(ex.getMessage());
            
            if(log != null){
                log.getLogger().error(systemError);
            }
        }
    }
    
    private void setTripsData(ParserEngine engineInstance, String path){
        
        try{
            
            ArrayList <ArrayList<String>> dataList = engineInstance.parse(path);
            Trips trips;

            boolean firstRow = true;        
            
            for (ArrayList<String> eachLineDataList : dataList) {

                if(!firstRow) {

                    trips = new Trips(
                            eachLineDataList.get(0),
                            eachLineDataList.get(1),
                            eachLineDataList.get(2),
                            eachLineDataList.get(3),
                            eachLineDataList.get(4),
                            eachLineDataList.get(5),
                            eachLineDataList.get(6),
                            eachLineDataList.get(7),
                            eachLineDataList.get(8),
                            eachLineDataList.get(9)
                    );
                    tripsList.add(trips);
                }
                firstRow = false;
            }
        } 
        catch (Exception ex) {
            
            String systemError = String.format("%s->%s error : %s",ObjectController.class.getName(),ParserException.getCallingMethod(),ex.getMessage());
            System.out.println(ex.getMessage());
            
            if(log != null){
                log.getLogger().error(systemError);
            }
        }
    }
    
    private void insertTripsData( String fileName){
        
        try{
           
            total = tripsList.size();
            if(dbManager != null && dbManager.isConnected()){
                
                dbManager.setCounter(0); 

                boolean allDataInserted = false;

                for (Trips trips : tripsList) {
                    allDataInserted = dbManager.insertTrips(trips);
                    dispaly.progress(dbManager.getCounter(),total,fileName);
                }

                if(allDataInserted){

                    String Info = String.format("%s->%s info : %s all data inserted successfully",ObjectController.class.getName(),ParserException.getCallingMethod(),fileName);
                    if(log != null){
                        log.getLogger().info(Info);
                    }
                }
            }
  
        } 
        catch (Exception ex) {
            
            String systemError = String.format("%s->%s error : %s",ObjectController.class.getName(),ParserException.getCallingMethod(),ex.getMessage());
            System.out.println(ex.getMessage());
            
            if(log != null){
                log.getLogger().error(systemError);
            }
        }
    }

    public ArrayList<Agency> getAgencyList() {
        return agencyList;
    }

    public ArrayList<Calendar> getCalanderList() {
        return calanderList;
    }

    public ArrayList<CalendarDates> getCalanderDateList() {
        return calanderDateList;
    }

    public ArrayList<FeedInfo> getFeedInfoList() {
        return feedInfoList;
    }

    public ArrayList<Frequencies> getFrequenciesList() {
        return frequenciesList;
    }

    public ArrayList<Routes> getRoutesList() {
        return routesList;
    }

    public ArrayList<StopTime> getStopTimeList() {
        return stopTimeList;
    }

    public ArrayList<Stops> getStopsList() {
        return stopsList;
    }

    public ArrayList<Transfer> getTransferList() {
        return transferList;
    }

    public ArrayList<Trips> getTripsList() {
        return tripsList;
    }
    
    
}
