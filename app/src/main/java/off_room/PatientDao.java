package off_room;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import java.util.List;

import pojo.Appointment;
import pojo.Attachments;
import pojo.Doctor;
import pojo.Patient;
import pojo.Session;

@Dao
public interface PatientDao {


    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void addPatient(Patient patient);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void addDoctors(List<Doctor> doctors);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void addAppointements(List<Appointment> appointments);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void addSessions(List<Session> sessions);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void addAttachments(List<Attachments>  attachments);


    @Query("SELECT * FROM Patient WHERE patient_id = :patient_id")
    Patient getPatientByID(int patient_id);

    @Query("SELECT * FROM appointment WHERE status = 1")
    List<Appointment> getAcceptedAppointments();


    @Query("SELECT * FROM appointment WHERE appointment_id = :appointment_id")
    Appointment getAppointmentByID(int appointment_id);

    @Query("SELECT * FROM appointment WHERE date = :appointment_date")
    List<Appointment> getAppointmentByDate(String appointment_date);


    @Query("SELECT * FROM doctor")
    List<Doctor> getPatientDoctorList();

    @Query("SELECT * FROM Session WHERE patient_id = :patient_id")
    List<Session> getSessionsByPatientID(int patient_id);

    @Query("SELECT * FROM Session WHERE session_id = :session_id")
    Session getSessionByID(int session_id);

    @Query("SELECT * FROM Attachments WHERE sessionID = :session_id")
    List<Attachments> getAttachmentsBySessionID(int session_id);

    @Query("SELECT * FROM Attachments WHERE attachment_id = :attachment_id")
    Attachments getAttachmentByAttachmentID(int attachment_id);

}
