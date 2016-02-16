
package com.waynaut.pojo.objects;

/**
 *
 * @author Rahman Md Redwanur
 */
public class Transfer {
    private final String from_stop_id;
    private final String to_stop_id;
    private final String transfer_type;
    private final String min_transfer_time;
    
     

    public Transfer(String from_stop_id, String to_stop_id, String transfer_type, String min_transfer_time) {
        this.from_stop_id = from_stop_id;
        this.to_stop_id = to_stop_id;
        this.transfer_type = transfer_type;
        this.min_transfer_time = min_transfer_time;
    }

    public String getFrom_stop_id() {
        return from_stop_id;
    }

    public String getTo_stop_id() {
        return to_stop_id;
    }

    public String getTransfer_type() {
        return transfer_type;
    }

    public String getMin_transfer_time() {
        return min_transfer_time;
    }
    
    
}
