package off_room;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import java.util.List;

import pojo.ClinicInfo;
import pojo.Doctor;
import pojo.Patient;

@Dao
public interface AdminDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void addClinicInfo(ClinicInfo clinicInfo);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void addDoctors(List<Doctor> doctors);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void addPatients(List<Patient> patients);



    @Query("SELECT * FROM clinicinfo ")
    ClinicInfo getClinicInfo();

    @Query("SELECT * FROM doctor")
    List<Doctor> getDoctors();

    @Query("SELECT * FROM doctor WHERE doctor_id = :doctor_id")
    Doctor getDoctorByID(int doctor_id);

    @Query("SELECT * FROM Patient")
    List<Patient> getPatients();

}
