
package com.waynaut.utility;

import com.waynaut.log.Log4J;
import com.waynaut.engine.ParserEngine;
import com.waynaut.exception.ParserException;
import com.waynaut.pojo.objects.Agency;
import com.waynaut.pojo.objects.Calendar;
import com.waynaut.pojo.objects.CalendarDates;
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
public class Display {
   
    private final ParserEngine engineInstance;
    private final Log4J log;
    private final Configuration config;
    public enum DISPLAY_TYPE {FILE,DATABSE};
    
    public Display(ParserEngine engineInstance,Log4J log,Configuration config) {
        this.engineInstance = engineInstance;
        this.log = log;
        this.config = config;
    }
    
    /**
     * Display the progress of data inserting in to database.
     * @param counter current insert number
     * @param total total record number
     * @param fileName from which file 
     */
    public void progress(double counter, double total, String fileName) {
        
        if(config!=null && config.getShowPogress().toUpperCase().equals("ACTIVE")){
            System.out.println(String.format("Data inserted in database  %.2f %% done from file %s",((counter/total)*100),fileName)); 
        }
    }
    /**
     * Display file data to console. This method directly show data from file without assigning data to POJO
     * @param fileName File name 
     * @param path file path that data have to display in console
     */
    public void show(String fileName, String path){

        try {
            
            if(path.isEmpty()){
                throw new Exception("File path is empty");
            }
            
            if(engineInstance != null ){
            
                ArrayList <ArrayList<String>> dataList = engineInstance.parse(path);
            
                System.out.println("############################################ START DATA DISPLAY FROM FILES DIRECTLY #######################################");
                
                for (ArrayList<String> eachLineDataList : dataList) {

                    String dataToDisplay = "";
                    
                    for (String eachData : eachLineDataList) {
                        dataToDisplay += String.format("%s ", eachData);

                    }
                    
                    System.out.println(dataToDisplay);
                }
                System.out.println(String.format("TOTAL RECORD FOUND IN %s File :%d ",fileName,dataList.size() - 1) );
                System.out.println("############################################ END DATA DISPLAY FROM FILES DIRECTLY #######################################");
                
            }
            else{
                throw new Exception("Engine instance is null");
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
    
    public void showAgency(ArrayList<Agency> agencyList,DISPLAY_TYPE enmDisplayType){

        try {
            
            if(agencyList != null){
            
                System.out.println(String.format("############################################ START DATA DISPLAY FROM %s #######################################",enmDisplayType.toString()));
               
                for (Agency agency : agencyList) {

                    if(agency != null) {

                        String dataToDisplay = String.format("%s %s %s %s %s %s",
                                agency.getAgencyId(),
                                agency.getAgencyName(),
                                agency.getAgencyUrl(),
                                agency.getAgencyTimezone(),
                                agency.getAgencyLang(),
                                agency.getAgencyPhone()
                        );

                        System.out.println(dataToDisplay);
                    }
                }

                System.out.println("TOTAL RECORD FOUND IN AGENCY TABLE : "+ agencyList.size());
            
                System.out.println(String.format("############################################ END DATA DISPLAY FROM %s #######################################",enmDisplayType.toString()));
               
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
    
    public void showCalander(ArrayList<Calendar> calanderList,DISPLAY_TYPE enmDisplayType){

        try {
            
            if(calanderList != null){
            
                System.out.println(String.format("############################################ START DATA DISPLAY FROM %s #######################################",enmDisplayType.toString()));
               
                for (Calendar calander : calanderList) {

                    if(calander != null) {

                        String dataToDisplay = String.format("%s %s %s %s %s %s %s %s %s %s",
                                calander.getServiceId(),
                                calander.getMonday(),
                                calander.getTuesday(),
                                calander.getWednesday(),
                                calander.getThursday(),
                                calander.getFriday(),
                                calander.getSaturday(),
                                calander.getSunday(),
                                calander.getStartDate(),
                                calander.getEndDate()
                        );

                    System.out.println(dataToDisplay);
                    }
                }
                System.out.println("TOTAL RECORD FOUND IN CALANDER TABLE : "+ calanderList.size());

                System.out.println(String.format("############################################ END DATA DISPLAY FROM %s #######################################",enmDisplayType.toString()));
               
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
    
    public void showCalanderDates(ArrayList<CalendarDates> calanderDateList,DISPLAY_TYPE enmDisplayType){

        try {
            
            if(calanderDateList != null){
            
                System.out.println(String.format("############################################ START DATA DISPLAY FROM %s #######################################",enmDisplayType.toString()));
             
             
                for (CalendarDates calander : calanderDateList) {

                    if(calander != null) {

                        String dataToDisplay = String.format("%s %s %s",
                                calander.getServiceId(),
                                calander.getDate(),
                                calander.getExceptionType()
                        );

                        System.out.println(dataToDisplay);
                    }
                }
                
                System.out.println("TOTAL RECORD FOUND IN CALANDER_DATES TABLE : "+ calanderDateList.size());

                System.out.println(String.format("############################################ END DATA DISPLAY FROM %s #######################################",enmDisplayType.toString()));
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
    
    public void showFeedInfo(ArrayList<FeedInfo> feedInfoList,DISPLAY_TYPE enmDisplayType){

        try {
            
            if(feedInfoList != null){
            
                System.out.println(String.format("############################################ START DATA DISPLAY FROM %s #######################################",enmDisplayType.toString()));

                for (FeedInfo feedInfo : feedInfoList) {

                    if(feedInfo != null) {

                        String dataToDisplay = String.format("%s %s %s %s %s %s",
                                feedInfo.getFeedPublisherName(),
                                feedInfo.getFeedPublisherUrl(),
                                feedInfo.getFeedLang(),
                                feedInfo.getFeedStartDate(),
                                feedInfo.getFeedEndDate(),
                                feedInfo.getFeedVersion()
                        );

                        System.out.println(dataToDisplay);
                    }
                }
                
                System.out.println("TOTAL RECORD FOUND IN FEED_INFO TABLE : "+ feedInfoList.size());

                System.out.println(String.format("############################################ END DATA DISPLAY FROM %s #######################################",enmDisplayType.toString()));
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
    
    public void showFrequencies(ArrayList<Frequencies> frequenciesList,DISPLAY_TYPE enmDisplayType){

        try {
            if(frequenciesList != null){
            
                System.out.println(String.format("############################################ START DATA DISPLAY FROM %s #######################################",enmDisplayType.toString()));
           
                for (Frequencies frequencies : frequenciesList) {

                    if(frequencies != null) {

                        String dataToDisplay = String.format("%s %s %s %s",
                                frequencies.getTripId(),
                                frequencies.getStartTime(),
                                frequencies.getEndTime(),
                                frequencies.getHeadwaySecs()

                        );

                        System.out.println(dataToDisplay);
                    }
                }
                System.out.println("TOTAL RECORD FOUND IN FREQUENCY TABLE : "+ frequenciesList.size());

                System.out.println(String.format("############################################ END DATA DISPLAY FROM %s #######################################",enmDisplayType.toString()));
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
    
    public void showRoutes(ArrayList<Routes> routesList,DISPLAY_TYPE enmDisplayType){

        try {
            if(routesList != null){
            
                System.out.println(String.format("############################################ START DATA DISPLAY FROM %s #######################################",enmDisplayType.toString()));
           
                for (Routes routes : routesList) {

                    if(routes != null) {

                        String dataToDisplay = String.format("%s %s %s %s %s %s %s %s %s",
                                routes.getRouteId(),
                                routes.getAgencyId(),
                                routes.getRouteShortName(),
                                routes.getRouteLongName(),
                                routes.getRouteDesc(),
                                routes.getRouteType(),
                                routes.getRouteUrl(),
                                routes.getRouteColor(),
                                routes.getRouteTextColor()

                        );

                        System.out.println(dataToDisplay);
                    }
                }
                System.out.println("TOTAL RECORD FOUND IN ROUTES TABLE : "+ routesList.size());

                System.out.println(String.format("############################################ END DATA DISPLAY FROM %s #######################################",enmDisplayType.toString()));
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
    
    public void showStopTimes(ArrayList<StopTime> stoptimeList,DISPLAY_TYPE enmDisplayType){

        try {
            
            if(stoptimeList != null){
            
                System.out.println(String.format("############################################ START DATA DISPLAY FROM %s #######################################",enmDisplayType.toString()));
           
                for (StopTime stoptime : stoptimeList) {

                    if(stoptime != null) {

                        String dataToDisplay = String.format("%s %s %s %s %s %s %s %s %s %s",
                                stoptime.getTriId(),
                                stoptime.getArrivalTime(),
                                stoptime.getDepartureTime(),
                                stoptime.getStopId(),
                                stoptime.getStopSequence(),
                                stoptime.getStopHeadsign(),
                                stoptime.getPickuptype(),
                                stoptime.getDropOffType(),
                                stoptime.getShapeDistTraveled(),
                                stoptime.getAttributesCh()

                        );

                        System.out.println(dataToDisplay);
                    }
                }
                System.out.println("TOTAL RECORD FOUND IN STOP TIMES TABLE : "+ stoptimeList.size());

                System.out.println(String.format("############################################ END DATA DISPLAY FROM %s #######################################",enmDisplayType.toString()));
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
    
    public void showStops(ArrayList<Stops> stopsList,DISPLAY_TYPE enmDisplayType){

        try {
            if(stopsList != null){
            
                System.out.println(String.format("############################################ START DATA DISPLAY FROM %s #######################################",enmDisplayType.toString()));
           
                for (Stops stops : stopsList) {

                    if(stops != null) {

                        String dataToDisplay = String.format("%s %s %s %s %s %s %s %s %s %s  %s %s %s %s %s %s %s",
                                stops.getStop_id(),
                                stops.getStop_code(),
                                stops.getStop_name(),
                                stops.getStop_desc(),
                                stops.getStop_lat(),
                                stops.getStop_lon(),
                                stops.getStop_elevation(),
                                stops.getZone_id(),
                                stops.getStop_url(),
                                stops.getLocation_type(),
                                stops.getParent_station(),
                                stops.getPlatform_code(),
                                stops.getCh_station_long_name(),
                                stops.getCh_station_synonym1(),
                                stops.getCh_station_synonym2(),
                                stops.getCh_station_synonym3(),
                                stops.getCh_station_synonym4()

                        );

                        System.out.println(dataToDisplay);
                    }
                }
                System.out.println("TOTAL RECORD FOUND IN STOPS TABLE : "+ stopsList.size());

                System.out.println(String.format("############################################ END DATA DISPLAY FROM %s #######################################",enmDisplayType.toString()));
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
    
    public void showTransfer(ArrayList<Transfer> transferList,DISPLAY_TYPE enmDisplayType){

        try {
            if(transferList != null){
            
                System.out.println(String.format("############################################ START DATA DISPLAY FROM %s #######################################",enmDisplayType.toString()));
           
                for (Transfer transfer : transferList) {

                    if(transfer != null) {

                        String dataToDisplay = String.format("%s %s %s %s",
                                transfer.getFrom_stop_id(),
                                transfer.getTo_stop_id(),
                                transfer.getTransfer_type(),
                                transfer.getMin_transfer_time()
                                

                        );

                        System.out.println(dataToDisplay);
                    }
                }
                System.out.println("TOTAL RECORD FOUND IN TRANSFER TABLE : "+ transferList.size());

                System.out.println(String.format("############################################ END DATA DISPLAY FROM %s #######################################",enmDisplayType.toString()));
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
    
    public void showTrips(ArrayList<Trips> tripsList,DISPLAY_TYPE enmDisplayType){

        try {
            if(tripsList != null){
            
                System.out.println(String.format("############################################ START DATA DISPLAY FROM %s #######################################",enmDisplayType.toString()));
           
                for (Trips trips : tripsList) {

                    if(trips != null) {

                        String dataToDisplay = String.format("%s %s %s %s %s %s %s %s %s %s",
                                trips.getRoute_id(),
                                trips.getService_id(),
                                trips.getTrip_id(),
                                trips.getTrip_headsign(),
                                trips.getTrip_short_name(),
                                trips.getDirection_id(),
                                trips.getBlock_id(),
                                trips.getShape_id(),
                                trips.getBikes_allowed(),
                                trips.getAttributes_ch()
                                

                        );

                        System.out.println(dataToDisplay);
                    }
                }
                System.out.println("TOTAL RECORD FOUND IN TRIPS TABLE : "+ tripsList.size());

                System.out.println(String.format("############################################ END DATA DISPLAY FROM %s #######################################",enmDisplayType.toString()));
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
}
