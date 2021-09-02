package pojo;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;
import androidx.room.TypeConverter;
import androidx.room.TypeConverters;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Entity
@TypeConverters(DataConnverter.class)
public class Doctor {

    @PrimaryKey
    @ColumnInfo(name = "doctor_id")
    private int doctor_id;
    @ColumnInfo(name = "name")
    private String name;
    @ColumnInfo(name = "dateOfBirth")
    private String dateOfBirth;
    @ColumnInfo(name = "gender")
    private String gender;
    @ColumnInfo(name = "email")
    private String email;
    @ColumnInfo(name = "phoneNumber")
    private String phoneNumber;
    @ColumnInfo(name = "specializations")
    private List<Specialization> specializations = new ArrayList<>();

    public List<Specialization> getSpecializations() {
        return specializations;
    }

    public String getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(String dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public void setSpecializations(List<Specialization> specializations1) {
        specializations = specializations1;
    }

    public int getDoctor_id() {
        return doctor_id;
    }

    public void setDoctor_id(int doctor_id) {
        this.doctor_id = doctor_id;
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
