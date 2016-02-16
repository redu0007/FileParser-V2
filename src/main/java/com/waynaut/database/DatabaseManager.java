package com.waynaut.database;

import com.waynaut.log.Log4J;
import com.waynaut.exception.ParserException;
import com.waynaut.utility.Configuration;
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
import java.sql.DatabaseMetaData;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Connection;
import java.sql.Statement;
import java.util.ArrayList;

/**
 *
 * @author Rahman Md Redwanur
 */
public class DatabaseManager {
   
    private static final String driverName = "com.mysql.jdbc.Driver";
    private static final String createAgenctTable = String.format("CREATE TABLE agency (agency_id VARCHAR(250) NOT NULL, agency_name VARCHAR(250) NOT NULL, agency_url VARCHAR(250) NOT NULL,agency_time_zone VARCHAR(50) NOT NULL,agency_lang VARCHAR(50),agency_phone VARCHAR(50))");
    private static final String createCalanderTable = String.format("CREATE TABLE calander (service_id VARCHAR(50) NOT NULL,monday VARCHAR(2), tuesday VARCHAR(2),wednesday VARCHAR(50),thursday VARCHAR(50),friday VARCHAR(2),saturday VARCHAR(2),sunday VARCHAR(2),start_date VARCHAR(20),end_date VARCHAR(20))");
    private static final String createCalanderDatesTable = String.format("CREATE TABLE calander_date (service_id VARCHAR(50) NOT NULL,date VARCHAR(20), exception_type VARCHAR(50))");
    private static final String createFeedInfoTable = String.format("CREATE TABLE feed_info (feed_publisher_name VARCHAR(50) NOT NULL,feed_publisher_url VARCHAR(100), feed_lang VARCHAR(20),feed_start_date VARCHAR(20),feed_end_date VARCHAR(20),feed_version VARCHAR(50))");
    private static final String createFrequencyTable = String.format("CREATE TABLE frequency (trip_id VARCHAR(50) NOT NULL,start_time VARCHAR(20), end_time VARCHAR(20),headway_secs VARCHAR(50))");
    private static final String createRouteTable = String.format("CREATE TABLE route (route_id VARCHAR(50) NOT NULL,agency_id VARCHAR(50) NOT NULL, route_short_name VARCHAR(50),route_long_name VARCHAR(250),route_desc VARCHAR(50),route_type VARCHAR(50),route_url VARCHAR(250),route_color VARCHAR(50),route_text_color VARCHAR(50))");
    private static final String createStopTimeTable = String.format("CREATE TABLE stop_time (trip_id VARCHAR(50) NOT NULL,arrival_time VARCHAR(50), departure_time VARCHAR(50),stop_id VARCHAR(50),stop_sequence VARCHAR(50),stop_headsign VARCHAR(50),pickup_type VARCHAR(50),drop_off_type VARCHAR(50),shape_dist_traveled VARCHAR(50),attributes_ch VARCHAR(50))");
    private static final String createStopsTable = String.format("CREATE TABLE stops (stop_id VARCHAR(50) NOT NULL,stop_code VARCHAR(50), stop_name VARCHAR(250),stop_desc VARCHAR(50),stop_lat VARCHAR(50),stop_lon VARCHAR(50),stop_elevation VARCHAR(50),zone_id VARCHAR(50),stop_url VARCHAR(250),location_type VARCHAR(20),parent_station VARCHAR(20),platform_code VARCHAR(20),ch_station_long_name VARCHAR(250),ch_station_synonym1 VARCHAR(20),ch_station_synonym2 VARCHAR(20),ch_station_synonym3 VARCHAR(20),ch_station_synonym4 VARCHAR(20))");
    private static final String createTransferTable = String.format("CREATE TABLE transfer (from_stop_id VARCHAR(50) NOT NULL,to_stop_id VARCHAR(50), transfer_type VARCHAR(50),min_transfer_time VARCHAR(50))");
    private static final String createTripsTable = String.format("CREATE TABLE trips (route_id VARCHAR(50) NOT NULL,service_id VARCHAR(50), trip_id VARCHAR(50),trip_headsign VARCHAR(50),trip_short_name VARCHAR(50),direction_id VARCHAR(50),block_id VARCHAR(50),shape_id VARCHAR(50),bikes_allowed VARCHAR(50),attributes_ch VARCHAR(50))");
   
    private static String dBUrl;
    private static String userId;
    private static String userPass;
    private static DatabaseManager instance;
    private boolean connected;
    private Log4J log;
    private double counter;
    
    private static Connection connection = null;
    private static Statement statement = null;
    
    public synchronized static DatabaseManager getInstance(Log4J log,Configuration config){
        
        if(instance == null){
            instance = new DatabaseManager(log,config);
        }
        
    	return instance;
    }
    
    public DatabaseManager (Log4J log,Configuration config){
    
        try{
            
            this.log = log;
            dBUrl = config.getDbUrlPath();
            userId = config.getDbUserId();
            userPass = config.getDbUserPass();
        }
        catch(Exception ex){
            
            if(log != null){
                String systemError = String.format("%s->%s error : %s",DatabaseManager.class.getName(),ParserException.getCallingMethod(),ex.getMessage());
                log.getLogger().error(systemError);
            }      
        }
    }
    
    public boolean connect(){

        try {  
            
            connected = false;
            Class.forName(driverName);
        
            connection = DriverManager.getConnection(dBUrl,userId,userPass);  
            statement = connection.createStatement();
            
            connected = true;
            
        } 
        catch (ClassNotFoundException |SQLException ex) {
            
        	if(log != null){
                String systemError = String.format("%s->%s error : %s",DatabaseManager.class.getName(),ParserException.getCallingMethod(),ex.getMessage());
                log.getLogger().error(systemError);
            }
        
            try{
                if (statement != null){
                    statement.close();
                }
                if (connection != null){
                    connection.close();
                }
                
            } catch (SQLException exp) {
                
                if(log != null){
                    String systemError = String.format("%s->%s warn : %s",DatabaseManager.class.getName(),ParserException.getCallingMethod(),exp.getMessage());
                    log.getLogger().warn(systemError);
                }
            }
        }
        return connected;
    }
    
    public void disconnect(){

        try {  
            
            if (statement != null){
                statement.close();
            }
            
            if (connection != null){
                connection.close();
            }
            
            connected = false;
            
        } 
        catch (SQLException exp) {
            
            if(log != null){
                String systemError = String.format("%s->%s warn : %s",DatabaseManager.class.getName(),ParserException.getCallingMethod(),exp.getMessage());
                log.getLogger().warn(systemError);
            }
        }
    }
    
    
    public void createtDbAndTables() throws Exception{
        
        
        try{

            if(connection != null && statement != null){
            
                if(!isTableExists(connection,"agency")){
                    statement.executeUpdate(createAgenctTable);
                }
                if(!isTableExists(connection,"calander")){
                    statement.executeUpdate(createCalanderTable);
                }
                if(!isTableExists(connection,"calander_date")){
                    statement.executeUpdate(createCalanderDatesTable);
                }
                if(!isTableExists(connection,"feed_info")){
                    statement.executeUpdate(createFeedInfoTable);
                }
                if(!isTableExists(connection,"frequency")){
                    statement.executeUpdate(createFrequencyTable);
                }
                if(!isTableExists(connection,"route")){
                    statement.executeUpdate(createRouteTable);
                }
                if(!isTableExists(connection,"stop_time")){
                    statement.executeUpdate(createStopTimeTable);
                }
                if(!isTableExists(connection,"stops")){
                    statement.executeUpdate(createStopsTable);
                }
                if(!isTableExists(connection,"transfer")){
                    statement.executeUpdate(createTransferTable);
                }
                if(!isTableExists(connection,"trips")){
                    statement.executeUpdate(createTripsTable);
                }
            }

        }
        catch (Exception ex){
            
            if(log != null){
                String systemError = String.format("%s->%s error : %s",DatabaseManager.class.getName(),ParserException.getCallingMethod(),ex.getMessage());
                log.getLogger().error(systemError);
            }
            throw new Exception(ex.getMessage());
        }
    }
    
    /**
     * If Database table exists this method will return true
     * @param con Connection instance
     * @param tableName table name
     * @return true if exists otherwise false
     * @throws Exception General exception
     */
    private boolean isTableExists(Connection con, String tableName) throws Exception{
        
        boolean bRet = false;
        ResultSet res = null;
        
        try {
            
            DatabaseMetaData meta = con.getMetaData();
            res = meta.getTables(null, null, null, 
            new String[] {"TABLE"});
                
            while (res.next()) {
             
                String table = res.getString("TABLE_NAME");
                
                if(table != null){
                   
                    if(table.toLowerCase().equals(tableName.toLowerCase())){
                        bRet = true; 
                        break;
                    }
                }
                
            }
            
        } 
        catch (Exception ex) {
            
            if(log != null){
                String systemError = String.format("%s->%s error : %s",DatabaseManager.class.getName(),ParserException.getCallingMethod(),ex.getMessage());
                log.getLogger().error(systemError);
            }
            
            throw new Exception(ex.getMessage());
        }
        finally {  
            
            try {   
                if(res != null){
                    res.close();
                }
            }catch (Exception ex) {   
                
                if(log != null){
                    String systemError = String.format("%s->%s warn : %s",DatabaseManager.class.getName(),ParserException.getCallingMethod(),ex.getMessage());
                    log.getLogger().warn(systemError);
                }
            }  
        } 
        
        return bRet;
            
    }

    public boolean isConnected() {
        return connected;
    }
    
    /**
     * This method will manage a special char that conflict with database SQL Query
     * @param value
     * @return
     */
    private String manageConflictChars(String value){
    
        String retValue;
        
        if(value.contains("'")){
            retValue = value.replaceAll("'", "''");
        }else{
            retValue = value;
        }
        return retValue;
    
    }

    public double getCounter() {
        return counter;
    }

    public void setCounter(double counter) {
        this.counter = counter;
    }

    public boolean insertAgency(Agency agency) throws Exception{
        
        boolean bRet = false;
        
        try {  
            
            String sQuery = String.format("INSERT INTO agency(agency_id,agency_name,agency_url,agency_time_zone,agency_lang,agency_phone)VALUES('%s','%s','%s','%s','%s','%s')",
                    agency.getAgencyId(),
                    manageConflictChars(agency.getAgencyName()),
                    manageConflictChars(agency.getAgencyUrl()),
                    manageConflictChars(agency.getAgencyTimezone()),
                    agency.getAgencyLang(),
                    agency.getAgencyPhone()
            );
            
            statement.executeUpdate(sQuery);
            bRet = true;
            counter++;
        }
        catch (SQLException ex) {  
            String systemError = String.format("%s->%s error : %s",DatabaseManager.class.getName(),ParserException.getCallingMethod(),ex.getMessage());
            
            if(log != null){
                log.getLogger().error(systemError);
            }
            throw new Exception(ex.getMessage());
            
        }

        return bRet;
    }
    
    public ArrayList<Agency> getAllAgency() throws Exception{
        
        ResultSet rs = null;
        ArrayList<Agency> agencyList = new ArrayList<>();
        
        try {  

            String sQuery = String.format("SELECT * FROM agency");
            rs = statement.executeQuery(sQuery);
            
            while(rs.next()){
                
                agencyList.add(
                   
                        new Agency(
                        
                            rs.getString("agency_id"),
                            rs.getString("agency_name"),
                            rs.getString("agency_url"),
                            rs.getString("agency_time_zone"),
                            rs.getString("agency_lang"),
                            rs.getString("agency_phone")
                        )
                );
            }
            
        }catch (SQLException ex) {  
            
            String systemError = String.format("%s->%s error : %s",DatabaseManager.class.getName(),ParserException.getCallingMethod(),ex.getMessage());
            
            if(log != null){
                log.getLogger().error(systemError);
            }
            
            throw new Exception(ex.getMessage());
            
        }finally{
            if(rs != null)
                rs.close();
        }
        
        return agencyList;
    }
    
    public boolean insertCalander(Calendar calander) throws Exception{
        
        boolean bRet = false;
        
        try {  

            String sQuery = String.format("INSERT INTO calander(service_id,monday,tuesday,wednesday,thursday,friday,saturday,sunday,start_date,end_date)VALUES('%s','%s','%s','%s','%s','%s','%s','%s','%s','%s')",
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
            
            statement.executeUpdate(sQuery);
            bRet = true;
            counter++;
        }
        catch (SQLException ex) {  
            String systemError = String.format("%s->%s error : %s",DatabaseManager.class.getName(),ParserException.getCallingMethod(),ex.getMessage());
            
            if(log != null){
                log.getLogger().error(systemError);
            }
            throw new Exception(ex.getMessage());
            
        }

        return bRet;
    }
    
    public ArrayList<Calendar> getAllCalander() throws Exception{
        
       
        ArrayList<Calendar> calanderList = new ArrayList<>();
        ResultSet rs = null;
        try {  
            
            String sQuery = String.format("SELECT * FROM calander");
            rs = statement.executeQuery(sQuery);
            
            while(rs.next()){
                
                calanderList.add(
                   
                        new Calendar(
                        
                            rs.getString("service_id"),
                            rs.getString("monday"),
                            rs.getString("tuesday"),
                            rs.getString("wednesday"),
                            rs.getString("thursday"),
                            rs.getString("friday"),
                            rs.getString("saturday"),
                            rs.getString("sunday"),
                            rs.getString("start_date"),
                            rs.getString("end_date")
                        )
                );
            }
            
            
            
        }catch (SQLException ex) {  
            
            String systemError = String.format("%s->%s error : %s",DatabaseManager.class.getName(),ParserException.getCallingMethod(),ex.getMessage());
            
            if(log != null){
                log.getLogger().error(systemError);
            }
            
            throw new Exception(ex.getMessage());
            
        }finally{
            if(rs != null)
                rs.close();
        }
        
        return calanderList;
    }
     
    public boolean insertCalanderDates(CalendarDates calanderdates) throws Exception{
        
        boolean bRet = false;
        
        try {  
   
            String sQuery = String.format("INSERT INTO calander_date(service_id,date,exception_type)VALUES('%s','%s','%s')",
                    calanderdates.getServiceId(),
                    calanderdates.getDate(),
                    calanderdates.getExceptionType()
            );
            
            statement.executeUpdate(sQuery);
            bRet = true;
            counter++;
        }
        catch (SQLException ex) {  
            String systemError = String.format("%s->%s error : %s",DatabaseManager.class.getName(),ParserException.getCallingMethod(),ex.getMessage());
            
            if(log != null){
                log.getLogger().error(systemError);
            }
            throw new Exception(ex.getMessage());
            
        }

        return bRet;
    }
    
    public ArrayList<CalendarDates> getAllCalanderDates() throws Exception{

        ArrayList<CalendarDates> calanderDateList = new ArrayList<>();
        ResultSet rs = null;
        try {  

            String sQuery = String.format("SELECT * FROM calander_date");
            rs = statement.executeQuery(sQuery);
            
            while(rs.next()){
                
                calanderDateList.add(
                   
                        new CalendarDates(
                        
                            rs.getString("service_id"),
                            rs.getString("date"),
                            rs.getString("exception_type")
                        )
                );
            }
            
        }catch (SQLException ex) {  
            
            String systemError = String.format("%s->%s error : %s",DatabaseManager.class.getName(),ParserException.getCallingMethod(),ex.getMessage());
            
            if(log != null){
                log.getLogger().error(systemError);
            }
            
            throw new Exception(ex.getMessage());
            
        }
        finally{
            if(rs != null)
                rs.close();
        }
        
        return calanderDateList;
    }
    
    public boolean insertFeedInfo(FeedInfo feedInfo) throws Exception{
        
        boolean bRet = false;

        try {  
         
            String sQuery = String.format("INSERT INTO feed_info(feed_publisher_name,feed_publisher_url,feed_lang,feed_start_date,feed_end_date,feed_version)VALUES('%s','%s','%s','%s','%s','%s')",
                    manageConflictChars(feedInfo.getFeedPublisherName()),
                    manageConflictChars(feedInfo.getFeedPublisherUrl()),
                    feedInfo.getFeedLang(),
                    feedInfo.getFeedStartDate(),
                    feedInfo.getFeedEndDate(),
                    feedInfo.getFeedVersion()
            );
            
            statement.executeUpdate(sQuery);
            bRet = true;
            counter++;
        }
        catch (SQLException ex) {  
            String systemError = String.format("%s->%s error : %s",DatabaseManager.class.getName(),ParserException.getCallingMethod(),ex.getMessage());
            
            if(log != null){
                log.getLogger().error(systemError);
            }
            throw new Exception(ex.getMessage());
            
        }
        
        return bRet;
    }
    
    public ArrayList<FeedInfo> getAllFeedInfo() throws Exception{
        
        ArrayList<FeedInfo> feedInfoList = new ArrayList<>();
        ResultSet rs = null;
        try {  

            String sQuery = String.format("SELECT * FROM feed_info");
            rs = statement.executeQuery(sQuery);
            
            while(rs.next()){
                
                feedInfoList.add(
                   
                        new FeedInfo(
                        
                            rs.getString("feed_publisher_name"),
                            rs.getString("feed_publisher_url"),
                            rs.getString("feed_lang"),
                            rs.getString("feed_start_date"),
                            rs.getString("feed_end_date"),
                            rs.getString("feed_version")
                        )
                );
            }
            
        }catch ( SQLException ex) {  
            
            String systemError = String.format("%s->%s error : %s",DatabaseManager.class.getName(),ParserException.getCallingMethod(),ex.getMessage());
            
            if(log != null){
                log.getLogger().error(systemError);
            }
            
            throw new Exception(ex.getMessage());
            
        }
        finally{
            if(rs != null)
                rs.close();
        }
        return feedInfoList;
    }
    
    public boolean insertFrequencies(Frequencies fraquency) throws Exception{
        
        boolean bRet = false;
        
        
        try {  
            
            String sQuery = String.format("INSERT INTO frequency(trip_id,start_time,end_time,headway_secs)VALUES('%s','%s','%s','%s')",
                    fraquency.getTripId(),
                    fraquency.getStartTime(),
                    fraquency.getEndTime(),
                    fraquency.getHeadwaySecs()
            );
            
            statement.executeUpdate(sQuery);
            bRet = true;
            counter++;
        }
        catch ( SQLException ex) {  
            String systemError = String.format("%s->%s error : %s",DatabaseManager.class.getName(),ParserException.getCallingMethod(),ex.getMessage());
            
            if(log != null){
                log.getLogger().error(systemError);
            }
            throw new Exception(ex.getMessage());
            
        }

        return bRet;
    }
    
    public ArrayList<Frequencies> getAllFrequencies() throws Exception{
        
        
        ArrayList<Frequencies> frequenciesList = new ArrayList<>();
        ResultSet rs = null;
        try {  
            
            
            String sQuery = String.format("SELECT * FROM frequency");
            rs = statement.executeQuery(sQuery);
            
            while(rs.next()){
                
                frequenciesList.add(
                   
                        new Frequencies(
                            rs.getString("trip_id"),
                            rs.getString("start_time"),
                            rs.getString("end_time"),
                            rs.getString("headway_secs")
                        )
                );
            }
            
        }catch (SQLException ex) {  
            
            String systemError = String.format("%s->%s error : %s",DatabaseManager.class.getName(),ParserException.getCallingMethod(),ex.getMessage());
            
            if(log != null){
                log.getLogger().error(systemError);
            }
            
            throw new Exception(ex.getMessage());
            
        }
        finally{
            if(rs != null)
                rs.close();
        }
        return frequenciesList;
    }
    
    public boolean insertRoute(Routes route) throws Exception{
        
        boolean bRet = false;
        
        try {  

            String sQuery = String.format("INSERT INTO route(route_id,agency_id,route_short_name,route_long_name,route_desc,route_type,route_url,route_color,route_text_color)VALUES('%s','%s','%s','%s','%s','%s','%s','%s','%s')",
                    route.getRouteId(),
                    route.getAgencyId(),
                    route.getRouteShortName(),
                    route.getRouteLongName(),
                    route.getRouteDesc(),
                    route.getRouteType(),
                    route.getRouteUrl(),
                    route.getRouteColor(),
                    route.getRouteTextColor()
            );
            
            statement.executeUpdate(sQuery);
            bRet = true;
            counter++;
        }
        catch (SQLException ex) {  
            String systemError = String.format("%s->%s error : %s",DatabaseManager.class.getName(),ParserException.getCallingMethod(),ex.getMessage());
            
            if(log != null){
                log.getLogger().error(systemError);
            }
            throw new Exception(ex.getMessage());
            
        }
        
        
        return bRet;
    }
    
    public ArrayList<Routes> getAllRoute() throws Exception{

        ArrayList<Routes> routeList = new ArrayList<>();
        ResultSet rs = null;
        try {  
            
            String sQuery = String.format("SELECT * FROM route");
            rs = statement.executeQuery(sQuery);
            
            while(rs.next()){
                
                routeList.add(
                   
                        new Routes(
                            rs.getString("route_id"),
                            rs.getString("agency_id"),
                            rs.getString("route_short_name"),
                            rs.getString("route_long_name"),
                            rs.getString("route_desc"),
                            rs.getString("route_type"),
                            rs.getString("route_url"),
                            rs.getString("route_color"),
                            rs.getString("route_text_color")
                        )
                );
            }
            
        }catch (SQLException ex) {  
            
            String systemError = String.format("%s->%s error : %s",DatabaseManager.class.getName(),ParserException.getCallingMethod(),ex.getMessage());
            
            if(log != null){
                log.getLogger().error(systemError);
            }
            
            throw new Exception(ex.getMessage());
            
        }
        finally{
            if(rs != null)
                rs.close();
        }
        
        return routeList;
    }
    
    public boolean insertStopTime(StopTime stopTime) throws Exception{
        
        boolean bRet = false;
        
        try {  
            
            String sQuery = String.format("INSERT INTO stop_time(trip_id,arrival_time,departure_time,stop_id,stop_sequence,stop_headsign,pickup_type,drop_off_type,shape_dist_traveled,attributes_ch)VALUES('%s','%s','%s','%s','%s','%s','%s','%s','%s','%s')",
                    stopTime.getTriId(),
                    stopTime.getArrivalTime(),
                    stopTime.getDepartureTime(),
                    stopTime.getStopId(),
                    stopTime.getStopSequence(),
                    stopTime.getStopHeadsign(),
                    stopTime.getPickuptype(),
                    stopTime.getDropOffType(),
                    stopTime.getShapeDistTraveled(),
                    stopTime.getAttributesCh()
            );
            
            statement.executeUpdate(sQuery);
            bRet = true;
            counter++;
        }
        catch ( SQLException ex) {  
            String systemError = String.format("%s->%s error : %s",DatabaseManager.class.getName(),ParserException.getCallingMethod(),ex.getMessage());
            
            if(log != null){
                log.getLogger().error(systemError);
            }
            throw new Exception(ex.getMessage());
            
        }
        
        return bRet;
    }
    
    public ArrayList<StopTime> getAllStopTime() throws Exception{

        ArrayList<StopTime> stopTimeList = new ArrayList<>();
        ResultSet rs = null;
        try {  
            
            String sQuery = String.format("SELECT * FROM stop_time");
            rs = statement.executeQuery(sQuery);
            
            while(rs.next()){
                
                stopTimeList.add(
                   
                        new StopTime(
                            rs.getString("trip_id"),
                            rs.getString("arrival_time"),
                            rs.getString("departure_time"),
                            rs.getString("stop_id"),
                            rs.getString("stop_sequence"),
                            rs.getString("stop_headsign"),
                            rs.getString("pickup_type"),
                            rs.getString("drop_off_type"),
                            rs.getString("shape_dist_traveled"),
                            rs.getString("attributes_ch")
                        )
                );
            }
            
        }catch (SQLException ex) {  
            
            String systemError = String.format("%s->%s error : %s",DatabaseManager.class.getName(),ParserException.getCallingMethod(),ex.getMessage());
            
            if(log != null){
                log.getLogger().error(systemError);
            }
            
            throw new Exception(ex.getMessage());
            
        }
        finally{
            if(rs != null)
                rs.close();
        }
        
        return stopTimeList;
    }
    
    public boolean insertStops(Stops stops) throws Exception{
        
        boolean bRet = false;

        
        try {  
            
            String sQuery = String.format("INSERT INTO stops (stop_id,stop_code,stop_name,stop_desc,stop_lat,stop_lon,stop_elevation,zone_id,stop_url,location_type,parent_station,platform_code,ch_station_long_name,ch_station_synonym1,ch_station_synonym2,ch_station_synonym3,ch_station_synonym4)VALUES('%s','%s','%s','%s','%s','%s','%s','%s','%s','%s','%s','%s','%s','%s','%s','%s','%s')",
                    stops.getStop_id(),
                    stops.getStop_code(),
                    manageConflictChars(stops.getStop_name()),
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
            
            statement.executeUpdate(sQuery);
            bRet = true;
            counter++;
        }
        catch (SQLException ex) {  
            String systemError = String.format("%s->%s error : %s",DatabaseManager.class.getName(),ParserException.getCallingMethod(),ex.getMessage());
            
            if(log != null){
                log.getLogger().error(systemError);
            }
            throw new Exception(ex.getMessage());
            
        }
        
        
        return bRet;
    }
    
    public ArrayList<Stops> getAllStops() throws Exception{
        
        ArrayList<Stops> stopsList = new ArrayList<>();
        ResultSet rs = null;
        try {  

            String sQuery = String.format("SELECT * FROM stops");
            rs = statement.executeQuery(sQuery);
            
            while(rs.next()){
                
                stopsList.add(
                   
                        new Stops(
                            rs.getString("stop_id"),
                            rs.getString("stop_code"),
                            rs.getString("stop_name"),
                            rs.getString("stop_desc"),
                            rs.getString("stop_lat"),
                            rs.getString("stop_lon"),
                            rs.getString("stop_elevation"),
                            rs.getString("zone_id"),
                            rs.getString("stop_url"),
                            rs.getString("location_type"),
                            rs.getString("parent_station"),
                            rs.getString("platform_code"),
                            rs.getString("ch_station_long_name"),
                            rs.getString("ch_station_synonym1"),
                            rs.getString("ch_station_synonym2"),
                            rs.getString("ch_station_synonym3"),
                            rs.getString("ch_station_synonym4")
                        )
                );
            }
            
        }catch (SQLException ex) {  
            
            String systemError = String.format("%s->%s error : %s",DatabaseManager.class.getName(),ParserException.getCallingMethod(),ex.getMessage());
            
            if(log != null){
                log.getLogger().error(systemError);
            }
            
            throw new Exception(ex.getMessage());
            
        }
        finally{
            if(rs != null)
                rs.close();
        }
        return stopsList;
    }
    
    public boolean insertTransfer(Transfer transfer) throws Exception{
        
        boolean bRet = false;
        
        try {  
            
            String sQuery = String.format("INSERT INTO transfer (from_stop_id,to_stop_id,transfer_type,min_transfer_time)VALUES('%s','%s','%s','%s')",
                    transfer.getFrom_stop_id(),
                    transfer.getTo_stop_id(),
                    transfer.getTransfer_type(),
                    transfer.getMin_transfer_time()
            );
            
            statement.executeUpdate(sQuery);
            bRet = true;
            counter++;
        }
        catch (SQLException ex) {  
            String systemError = String.format("%s->%s error : %s",DatabaseManager.class.getName(),ParserException.getCallingMethod(),ex.getMessage());
            
            if(log != null){
                log.getLogger().error(systemError);
            }
            throw new Exception(ex.getMessage());
            
        }
        
        
        return bRet;
    }
    
    public ArrayList<Transfer> getAllTransfer() throws Exception{
        
        ArrayList<Transfer> transferList = new ArrayList<>();
        ResultSet rs = null;
        try {  

            String sQuery = String.format("SELECT * FROM transfer");
            rs = statement.executeQuery(sQuery);
            
            while(rs.next()){
                
                transferList.add(
                   
                        new Transfer(
                            rs.getString("from_stop_id"),
                            rs.getString("to_stop_id"),
                            rs.getString("transfer_type"),
                            rs.getString("min_transfer_time")
                            
                        )
                );
            }
            
        }catch (SQLException ex) {  
            
            String systemError = String.format("%s->%s error : %s",DatabaseManager.class.getName(),ParserException.getCallingMethod(),ex.getMessage());
            
            if(log != null){
                log.getLogger().error(systemError);
            }
            
            throw new Exception(ex.getMessage());
            
        }
        finally{
            if(rs != null)
                rs.close();
        }
        return transferList;
    }
    
    public boolean insertTrips(Trips trips) throws Exception{
        
        boolean bRet = false;
        
        try {  

            String sQuery = String.format("INSERT INTO trips (route_id,service_id,trip_id,trip_headsign,trip_short_name,direction_id,block_id,shape_id,bikes_allowed,attributes_ch)VALUES('%s','%s','%s','%s','%s','%s','%s','%s','%s','%s')",
                    trips.getRoute_id(),
                    trips.getService_id(),
                    trips.getTrip_id(),
                    manageConflictChars(trips.getTrip_headsign()),
                    manageConflictChars(trips.getTrip_short_name()),
                    trips.getDirection_id(),
                    trips.getBlock_id(),
                    trips.getShape_id(),
                    trips.getBikes_allowed(),
                    trips.getAttributes_ch()
            );
            
            statement.executeUpdate(sQuery);
            bRet = true;
            counter++;
        }
        catch (SQLException ex) {  
            String systemError = String.format("%s->%s error : %s",DatabaseManager.class.getName(),ParserException.getCallingMethod(),ex.getMessage());
            
            if(log != null){
                log.getLogger().error(systemError);
            }
            throw new Exception(ex.getMessage());
            
        }
        
        
        return bRet;
    }
    
    public ArrayList<Trips> getAllTrips() throws Exception{
        
        ArrayList<Trips> tripsList = new ArrayList<>();
        ResultSet rs = null;
        try {  
            
            String sQuery = String.format("SELECT * FROM trips");
            rs = statement.executeQuery(sQuery);
            
            while(rs.next()){
                
                tripsList.add(
                   
                        new Trips(
                            rs.getString("route_id"),
                            rs.getString("service_id"),
                            rs.getString("trip_id"),
                            rs.getString("trip_headsign"),
                            rs.getString("trip_short_name"),
                            rs.getString("direction_id"),
                            rs.getString("block_id"),
                            rs.getString("shape_id"),
                            rs.getString("bikes_allowed"),
                            rs.getString("attributes_ch")
                        )
                );
            }
            
        }catch (SQLException ex) {  
            
            String systemError = String.format("%s->%s error : %s",DatabaseManager.class.getName(),ParserException.getCallingMethod(),ex.getMessage());
            
            if(log != null){
                log.getLogger().error(systemError);
            }
            
            throw new Exception(ex.getMessage());
            
        }
        finally{
            if(rs != null)
                rs.close();
        }
        return tripsList;
    }
    
    
}
