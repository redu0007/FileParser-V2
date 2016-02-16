
package com.waynaut.pojo.objects;

/**
 *
 * @author Rahman Md Redwanur
 */
public class FeedInfo {
       
    private final String feedPublisherName;
    private final String feedPublisherUrl;
    private final String feedLang;
    private final String feedStartDate;
    private final String feedEndDate;
    private final String feedVersion;

    public FeedInfo(String feedPublisherName, String feedPublisherUrl, String feedLang, String feedStartDate, String feedEndDate, String feedVersion) {
        this.feedPublisherName = feedPublisherName;
        this.feedPublisherUrl = feedPublisherUrl;
        this.feedLang = feedLang;
        this.feedStartDate = feedStartDate;
        this.feedEndDate = feedEndDate;
        this.feedVersion = feedVersion;
    }

    public String getFeedPublisherName() {
        return feedPublisherName;
    }

    public String getFeedPublisherUrl() {
        return feedPublisherUrl;
    }

    public String getFeedLang() {
        return feedLang;
    }

    public String getFeedStartDate() {
        return feedStartDate;
    }

    public String getFeedEndDate() {
        return feedEndDate;
    }

    public String getFeedVersion() {
        return feedVersion;
    }
    
    
}
