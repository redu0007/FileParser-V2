
package com.waynaut.pojo.objects;

/**
 *
 * @author Rahman Md Redwanur
 */
public class CalendarDates {
    
    private final String serviceId;
    private final String date;
    private final String exceptionType;

    public CalendarDates(String serviceId, String date, String exceptionType) {
        this.serviceId = serviceId;
        this.date = date;
        this.exceptionType = exceptionType;
    }

    public String getServiceId() {
        return serviceId;
    }

    public String getDate() {
        return date;
    }

    public String getExceptionType() {
        return exceptionType;
    }
    
    
}
