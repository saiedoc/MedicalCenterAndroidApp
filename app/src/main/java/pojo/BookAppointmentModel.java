package pojo;

import java.sql.Time;
import java.util.Date;

public class BookAppointmentModel {

    private Date dateOfBooking ;
    private Time timeOfBooking;
    private int doctor_id;
    private int patient_id;

    public Date getDateOfBooking() {
        return dateOfBooking;
    }

    public void setDateOfBooking(Date dateOfBooking) {
        this.dateOfBooking = dateOfBooking;
    }

    public Time getTimeOfBooking() {
        return timeOfBooking;
    }

    public void setTimeOfBooking(Time timeOfBooking) {
        this.timeOfBooking = timeOfBooking;
    }

    public int getDoctor_id() {
        return doctor_id;
    }

    public void setDoctor_id(int doctor_id) {
        this.doctor_id = doctor_id;
    }

    public int getPatient_id() {
        return patient_id;
    }

    public void setPatient_id(int patient_id) {
        this.patient_id = patient_id;
    }
}
