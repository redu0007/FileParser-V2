package com.waynaut.pojo.objects;

/**
 *
 * @author Rahman Md Redwanur
 */
public class StopTime {
    
    private final String tripId;
    private final String arrivalTime;
    private final String departureTime;
    private final String stopId;
    private final String stopSequence;
    private final String stopHeadsign;
    private final String pickuptype;
    private final String dropOffType;
    private final String shapeDistTraveled;
    private final String attributesCh;

   
    public StopTime(String tripId, String arrivalTime, String departureTime, String stopId, String stopSequence, String stopHeadsign, String pickuptype, String dropOffType, String shapeDistTraveled, String attributesCh) {
        this.tripId = tripId;
        this.arrivalTime = arrivalTime;
        this.departureTime = departureTime;
        this.stopId = stopId;
        this.stopSequence = stopSequence;
        this.stopHeadsign = stopHeadsign;
        this.pickuptype = pickuptype;
        this.dropOffType = dropOffType;
        this.shapeDistTraveled = shapeDistTraveled;
        this.attributesCh = attributesCh;
    }

    public String getTriId() {
        return tripId;
    }

    public String getArrivalTime() {
        return arrivalTime;
    }

    public String getDepartureTime() {
        return departureTime;
    }

    public String getStopId() {
        return stopId;
    }

    public String getStopSequence() {
        return stopSequence;
    }

    public String getStopHeadsign() {
        return stopHeadsign;
    }

    public String getPickuptype() {
        return pickuptype;
    }

    public String getDropOffType() {
        return dropOffType;
    }

    public String getShapeDistTraveled() {
        return shapeDistTraveled;
    }

    public String getAttributesCh() {
        return attributesCh;
    }
    
    
}
