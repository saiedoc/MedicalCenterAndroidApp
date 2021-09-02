package pojo;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import org.jetbrains.annotations.NotNull;

@Entity
public class ClinicInfo {

    @NotNull
    @PrimaryKey
    @ColumnInfo(name = "name")
    private String clinic_name;
    @ColumnInfo(name = "address")
    private String address;
    @ColumnInfo(name = "phoneNumber")
    private String phone_number;

    public String getClinic_name() {
        return clinic_name;
    }

    public void setClinic_name(String clinic_name) {
        this.clinic_name = clinic_name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPhone_number() {
        return phone_number;
    }

    public void setPhone_number(String phone_number) {
        this.phone_number = phone_number;
    }
}
