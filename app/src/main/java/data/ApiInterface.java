package data;


import com.google.gson.JsonObject;

import org.json.JSONObject;

import pojo.Appointment;
import pojo.Attachments;
import pojo.BookAppointmentModel;
import pojo.ClinicInfo;
import pojo.DataChart;
import pojo.Doctor;
import pojo.Patient;
import pojo.Session;
import pojo.Sickness;
import pojo.Specialization;
import pojo.Role;
import pojo.UserAccount;
import pojo.UsernamePassword;

import java.sql.Time;
import java.util.Date;
import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Response;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;
import retrofit2.http.Query;
import retrofit2.http.Streaming;


public interface ApiInterface {

    @POST("api/post/login")
    Call<Role> getRole(@Body UsernamePassword usernamePassword);

    @GET("api/get/account/id/{id}")
    Call<UserAccount> checkAccount(@Path("id") int id);

    @GET("api/get/doctor/account/id/{id}")
    Call<Doctor> getDocPro(@Path("id") int accountID);

    @GET("api/get/appointments/doctor/{doctor_id}")
    Call<List<Appointment>> getDoctorAppointments(@Path("doctor_id") int doctorID);

    @GET("api/get/patients/doctor/{id}")
    Call<List<Patient>> getPatients(@Path("id") int doctorID);

    @GET("api/get/patient/id/{id}")
    Call<Patient> getPatient(@Path("id") int patientID);

    @GET("api/get/appointments/patient/{patientID}")
    Call<List<Appointment>> getPatientAppointments(@Path("patientID") int patientID);

    @GET("api/get/appointment/id/{id}")
    Call<Appointment> getAppointment(@Path("id") int id);

    @GET("api/get/sicknesses/patient/{patientID}")
    Call<List<Sickness>> getSicknessByPatient(@Path("patientID") int patientID);

    @GET("api/get/sessions/patient/{patientID}")
    Call<List<Session>> getPatientSessions(@Path("patientID") int patientID);

    @GET("api/get/session/sessionID/{sessionID}")
    Call<Session> getSession(@Path("sessionID") int sessionID);

    @GET("api/get/attachments/session/{sessionID}")
    Call<List<Attachments>> getAttachments(@Path("sessionID") int sessionID);

    @Streaming
    @GET("api/get/attachment/download/id/{id}")
    Call<ResponseBody> getAttachment(@Path("id") int attachmentID);

    @GET("api/get/doctors")
    Call<List<Doctor>> getDoctors();

    @GET("api/delete/doctor/id/{id}")
    Call<Response> deleteDoctor(@Path("id") int doctorID);

    @GET("api/get/specializations")
    Call<List<Specialization>> getSpecializations();

    @GET("api/get/doctors/workday/{name}")
    Call<List<Doctor>> getDoctorsByWorkday(@Path("name") String dayname);

    @GET("api/get/patient/account/id/{id}")
    Call<Patient> getPatientByAccountID(@Path("id") int accountID);

    @GET("api/get/appointments/status/pending/doctor/date")
    Call<List<Appointment>> getDoctorAppointmentsPending(@Query("date") String date , @Query("id") int doctorID);

    @GET("api/get/appointments/doctor/{doctorID}/status/accept")
    Call<List<Appointment>> getDoctorAppointmentsAccepted(@Path("doctorID") int doctorID);

    @GET("api/get/appointments/status/accept/doctor/date")
    Call<List<Appointment>> getDoctorAppointmentsByDateAndAccepted(@Query("date") String date , @Query("id") int doctorID);

    @PUT("api/update/appointment/status/accept/id/{id}")
    Call<ResponseBody> updateAppointmentAccept(@Path("id") int appointmentID);

    @PUT("api/update/appointment/status/refuse/id/{id}")
    Call<ResponseBody> updateAppointmentReject(@Path("id") int appointmentID);

    @GET("api/get/clinic/info")
    Call<ClinicInfo> getClinicInfo();

    @GET("api/get/patients")
    Call<List<Patient>>getPatients();

    @GET("api/get/doctor/id/{id}")
    Call<Doctor>getDoctorByID(@Path("id") int doctorID);

    @GET("api/get/appointments/status/accept/patient/date")
    Call<List<Appointment>>getPatientAppointmentsAccepted(@Query("date") String date , @Query("id") int patientID);

    @POST("api/post/appointment")
    Call<JsonObject> bookAppointment(@Body JsonObject jsonObject);

    @GET("api/get/analyses/patient/{patientId}")
    Call<List<DataChart>>getDataChart(@Path("patientId") int patientID);

    @GET("api/get/appointments/status/accept/patient/date")
    Call<List<Appointment>> getPatientAppointmentsByDateAndAccepted(@Query("date") String date,@Query("id") int patientID);

    @POST("api/get/token")
    Call<JsonObject> sendToken(@Body JsonObject jsonObject);



    
}
