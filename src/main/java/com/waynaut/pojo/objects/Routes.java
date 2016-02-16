package com.waynaut.pojo.objects;

/**
 *
 * @author Rahman Md Redwanur
 */
public class Routes {
    private final String routeId;
    private final String agencyId;
    private final String routeShortName;
    private final String routeLongName;
    private final String routeDesc;
    private final String routeType;
    private final String routeUrl;
    private final String routeColor;
    private final String routeTextColor;
    
 
    public Routes(String routeId, String agencyId, String routeShortName, String routeLongName, String routeDesc, String routeType, String routeUrl, String routeColor, String routeTextColor) {
        this.routeId = routeId;
        this.agencyId = agencyId;
        this.routeShortName = routeShortName;
        this.routeLongName = routeLongName;
        this.routeDesc = routeDesc;
        this.routeType = routeType;
        this.routeUrl = routeUrl;
        this.routeColor = routeColor;
        this.routeTextColor = routeTextColor;
    }

    public String getRouteId() {
        return routeId;
    }

    public String getAgencyId() {
        return agencyId;
    }

    public String getRouteShortName() {
        return routeShortName;
    }

    public String getRouteLongName() {
        return routeLongName;
    }

    public String getRouteDesc() {
        return routeDesc;
    }

    public String getRouteType() {
        return routeType;
    }

    public String getRouteUrl() {
        return routeUrl;
    }

    public String getRouteColor() {
        return routeColor;
    }

    public String getRouteTextColor() {
        return routeTextColor;
    }
    
    
}
