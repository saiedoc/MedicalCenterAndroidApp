package pojo;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;
import androidx.room.TypeConverters;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@TypeConverters(DataConnverter.class)
@Entity
public class Patient {

    @PrimaryKey
    @ColumnInfo(name = "patient_id")
    private int patient_id;
    @ColumnInfo(name = "dateOfBirth")
    private String dateOfBirth;
    @ColumnInfo(name = "name")
    private String name;
    @ColumnInfo(name = "gender")
    private String gender;
    @ColumnInfo(name = "email")
    private String email;
    @ColumnInfo(name = "phoneNumber")
    private String phoneNumber;
    @ColumnInfo(name = "sickness")
    private List<String> sickness = new ArrayList<>();
    @Ignore
    private List<Sickness> patientSickness = new ArrayList<>();

    public List<String> getSickness() {
        return sickness;
    }

    public void setSickness(List<String> sickness) {
        this.sickness = sickness;
    }

    public List<Sickness> getPatientSickness() {
        System.out.println("Eyad ..");
        System.out.println(patientSickness.get(0).getName() + " "+patientSickness.get(0).getSickness_id());
        return patientSickness;
    }

    public void setPatientSickness(List<Sickness> patientSickness) {
        this.patientSickness = patientSickness;
    }

    public int getPatient_id() {
        return patient_id;
    }

    public void setPatient_id(int patient_id) {
        this.patient_id = patient_id;
    }

    public String getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(String dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }
}
