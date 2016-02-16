
package com.waynaut.pojo.objects;

/**
 *
 * @author Rahman Md Redwanur
 */
public class Trips {
    
    private final String route_id;
    private final String service_id;
    private final String trip_id;
    private final String trip_headsign;
    private final String trip_short_name;
    private final String direction_id;
    private final String block_id;
    private final String shape_id;
    private final String bikes_allowed;
    private final String attributes_ch;

   
    public Trips(String route_id,String service_id, String trip_id, String trip_headsign, String trip_short_name, String direction_id, String block_id, String shape_id, String bikes_allowed, String attributes_ch) {
        this.route_id = route_id;
        this.service_id = service_id;
        this.trip_id = trip_id;
        this.trip_headsign = trip_headsign;
        this.trip_short_name = trip_short_name;
        this.direction_id = direction_id;
        this.block_id = block_id;
        this.shape_id = shape_id;
        this.bikes_allowed = bikes_allowed;
        this.attributes_ch = attributes_ch;
    }

    public String getRoute_id() {
        return route_id;
    }

    public String getService_id() {
        return service_id;
    }

    public String getTrip_id() {
        return trip_id;
    }

    public String getTrip_headsign() {
        return trip_headsign;
    }

    public String getTrip_short_name() {
        return trip_short_name;
    }

    public String getDirection_id() {
        return direction_id;
    }

    public String getBlock_id() {
        return block_id;
    }

    public String getShape_id() {
        return shape_id;
    }

    public String getBikes_allowed() {
        return bikes_allowed;
    }

    public String getAttributes_ch() {
        return attributes_ch;
    }
    
    
    
}
