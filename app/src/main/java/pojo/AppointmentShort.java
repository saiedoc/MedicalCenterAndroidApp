package pojo;

import java.util.Date;

public class AppointmentShort {

    private int AppointId;
    private Date date;

    public int getAppointId() {
        return AppointId;
    }

    public void setAppointId(int appointId) {
        AppointId = appointId;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }
}
