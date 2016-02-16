package com.waynaut.pojo.objects;

/**
*
* @author Rahman Md Redwanur
*/
public class Agency {
	
    private final String agencyId;
    private final String agencyName;
    private final String agencyUrl;
    private final String agencyTimezone;
    private final String agencyLang;
    private final String agencyPhone;


    public Agency(String agencyId, String agencyName, String agencyUrl, String agencyTimezone, String agencyLang,String agencyPhone) {
        super();
        this.agencyId = agencyId;
        this.agencyName = agencyName;
        this.agencyUrl = agencyUrl;
        this.agencyTimezone = agencyTimezone;
        this.agencyLang = agencyLang;
        this.agencyPhone = agencyPhone;
    }

    public String getAgencyId() {
        return agencyId;
    }

    public String getAgencyName() {
        return agencyName;
    }

    public String getAgencyUrl() {
        return agencyUrl;
    }

    public String getAgencyTimezone() {
        return agencyTimezone;
    }

    public String getAgencyLang() {
        return agencyLang;
    }

    public String getAgencyPhone() {
        return agencyPhone;
    }


}
