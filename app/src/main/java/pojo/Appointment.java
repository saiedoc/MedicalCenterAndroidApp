package pojo;


import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;
import androidx.room.TypeConverters;

import java.sql.Time;
import java.util.Date;

@Entity
@TypeConverters(DataConnverter.class)
public class Appointment {

    @PrimaryKey
    @ColumnInfo(name = "appointment_id")
    int appointment_id;
    @ColumnInfo(name = "date")
    private String dateOfBooking;
    @ColumnInfo(name = "time")
    private String timeOfBooking;
    @ColumnInfo(name = "doctor_name")
    private String doctor_name;
    @ColumnInfo(name = "patient_name")
    private String patient_name;
    @ColumnInfo(name = "status")
    private String status;


    public String getTimeOfBooking() {
        return timeOfBooking;
    }

    public void setTimeOfBooking(String timeOfBooking) {
        this.timeOfBooking = timeOfBooking;
    }

    public int getAppointment_id() {
        return appointment_id;
    }

    public void setAppointment_id(int appointment_id) {
        this.appointment_id = appointment_id;
    }

    public String getDateOfBooking() {
        return dateOfBooking;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public void setDateOfBooking(String dateOfBooking) {
        this.dateOfBooking = dateOfBooking;
    }

    public String getDoctor_name() {
        return doctor_name;
    }

    public void setDoctor_name(String doctor_name) {
        this.doctor_name = doctor_name;
    }

    public String getPatient_name() {
        return patient_name;
    }

    public void setPatient_name(String patient_name) {
        this.patient_name = patient_name;
    }
}
