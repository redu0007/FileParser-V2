
package com.waynaut.pojo.objects;

/**
 *
 * @author Rahman Md Redwanur
 */
public class Frequencies {
 
    private final String tripId;
    private final String startTime;
    private final String endTime;
    private final String headwaySecs;
    

    public Frequencies(String tripId, String startTime, String endTime, String headwaySecs) {
        this.tripId = tripId;
        this.startTime = startTime;
        this.endTime = endTime;
        this.headwaySecs = headwaySecs;
    }

    public String getTripId() {
        return tripId;
    }

    public String getStartTime() {
        return startTime;
    }

    public String getEndTime() {
        return endTime;
    }

    public String getHeadwaySecs() {
        return headwaySecs;
    }
    
    
}
