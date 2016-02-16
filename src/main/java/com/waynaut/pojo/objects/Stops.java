
package com.waynaut.pojo.objects;

/**
 *
 * @author Rahman Md Redwanur
 */
public class Stops {
   
    private final String stop_id;
    private final String stop_code;
    private final String stop_name;
    private final String stop_desc;
    private final String stop_lat;
    private final String stop_lon;
    private final String stop_elevation;
    private final String zone_id;
    private final String stop_url;
    private final String location_type;
    private final String parent_station;
    private final String platform_code;
    private final String ch_station_long_name;
    private final String ch_station_synonym1;
    private final String ch_station_synonym2;
    private final String ch_station_synonym3;
    private final String ch_station_synonym4;
   
    public Stops(String stop_id, String stop_code, String stop_name, String stop_desc, String stop_lat, String stop_lon, String stop_elevation, String zone_id, String stop_url, String location_type, String parent_station, String platform_code, String ch_station_long_name, String ch_station_synonym1, String ch_station_synonym2, String ch_station_synonym3, String ch_station_synonym4) {
        this.stop_id = stop_id;
        this.stop_code = stop_code;
        this.stop_name = stop_name;
        this.stop_desc = stop_desc;
        this.stop_lat = stop_lat;
        this.stop_lon = stop_lon;
        this.stop_elevation = stop_elevation;
        this.zone_id = zone_id;
        this.stop_url = stop_url;
        this.location_type = location_type;
        this.parent_station = parent_station;
        this.platform_code = platform_code;
        this.ch_station_long_name = ch_station_long_name;
        this.ch_station_synonym1 = ch_station_synonym1;
        this.ch_station_synonym2 = ch_station_synonym2;
        this.ch_station_synonym3 = ch_station_synonym3;
        this.ch_station_synonym4 = ch_station_synonym4;
    }

    public String getStop_id() {
        return stop_id;
    }

    public String getStop_code() {
        return stop_code;
    }

    public String getStop_name() {
        return stop_name;
    }

    public String getStop_desc() {
        return stop_desc;
    }

    public String getStop_lat() {
        return stop_lat;
    }

    public String getStop_lon() {
        return stop_lon;
    }

    public String getStop_elevation() {
        return stop_elevation;
    }

    public String getZone_id() {
        return zone_id;
    }

    public String getStop_url() {
        return stop_url;
    }

    public String getLocation_type() {
        return location_type;
    }

    public String getParent_station() {
        return parent_station;
    }

    public String getPlatform_code() {
        return platform_code;
    }

    public String getCh_station_long_name() {
        return ch_station_long_name;
    }

    public String getCh_station_synonym1() {
        return ch_station_synonym1;
    }

    public String getCh_station_synonym2() {
        return ch_station_synonym2;
    }

    public String getCh_station_synonym3() {
        return ch_station_synonym3;
    }

    public String getCh_station_synonym4() {
        return ch_station_synonym4;
    }
   
   
}
