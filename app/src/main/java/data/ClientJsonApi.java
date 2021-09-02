package data;


import com.google.gson.JsonObject;

import pojo.Appointment;
import pojo.Attachments;
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

import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ClientJsonApi {

    private static final String BASE_URL = "http://192.168.43.122:5050/";
    private ApiInterface apiInterface;
    private static ClientJsonApi INSTANCE;

    public ClientJsonApi() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        apiInterface = retrofit.create(ApiInterface.class);
    }

    public static ClientJsonApi getINSTANCE() {
        if (null == INSTANCE) {
            INSTANCE = new ClientJsonApi();
        }
        return INSTANCE;
    }

    public Call<Role> getRolefromDatabase(String username, String password) {


        return apiInterface.getRole(new UsernamePassword(username, password));

    }

    public Call<Doctor> getDocProFromDatabase(int accountID) {
        return apiInterface.getDocPro(accountID);
    }

    public Call<List<Patient>> getPatientsFromDatabase(int docid) {
        return apiInterface.getPatients(docid);
    }

    public Call<List<Appointment>> getDoctorAppointmentsFromDatabase(int doctorID) {
        return apiInterface.getDoctorAppointmentsAccepted(doctorID);
    }

    public Call<Patient> getPatientInfofromDatabase(int patientID) {
        return apiInterface.getPatient(patientID);
    }

    public Call<List<Appointment>> getPatientAppointments(int patientID,String date) {
        return apiInterface.getPatientAppointmentsAccepted(date,patientID);
    }

    public Call<UserAccount> checkAccount(int ID) {
        return apiInterface.checkAccount(ID);
    }

    public Call<Appointment> getAppointmentFromDatabase(int appointmentID) {
        return apiInterface.getAppointment(appointmentID);
    }

    public Call<List<Sickness>> getSicknessByPatientFromDatabase(int patientID) {
        return apiInterface.getSicknessByPatient(patientID);
    }

    public Call<List<Session>> getPatientSessionsFromDatabase(int patientID) {

        return apiInterface.getPatientSessions(patientID);
    }

    public Call<Session> getSessionFromDatabase(int sessionID) {

        return apiInterface.getSession(sessionID);
    }

    public Call<List<Attachments>> getAttachmentsFromDatabase(int sessionID) {

        return apiInterface.getAttachments(sessionID);
    }

    public Call<ResponseBody> getAttachmentFromDatabase(int attachmentID) {
        return apiInterface.getAttachment(attachmentID);
    }

    public Call<List<Doctor>> getDoctorsFromDatabase() {

        return apiInterface.getDoctors();
    }

    public Call<Response> deleteDoctor(int doctorID) {
        return apiInterface.deleteDoctor(doctorID);
    }

    public Call<List<Specialization>> getSpecializationsFromDatabase() {

        return apiInterface.getSpecializations();
    }

    public Call<List<Doctor>> getDoctorsByWorkdayFromDatabase(String dayname) {
        return apiInterface.getDoctorsByWorkday(dayname);
    }

    public Call<Patient> getPatientByAccountIDFromDatabase(int accountID) {
        return apiInterface.getPatientByAccountID(accountID);
    }

    public Call<List<Appointment>> getDoctorAppointmentsPendingFromDatabase(int doctorID,String date) {
        return apiInterface.getDoctorAppointmentsPending(date,doctorID);
    }

    public Call<List<Appointment>> getDoctorAppointmentsAcceptedFromDatabase(int doctorID) {
        return apiInterface.getDoctorAppointmentsAccepted(doctorID);
    }

    public Call<List<Appointment>> getDoctorAppointmentsByDateAndAcceptedFromDatabase(int doctorID, String date) {
        return apiInterface.getDoctorAppointmentsByDateAndAccepted(date,doctorID);
    }

    public Call<ResponseBody> updateAppointmentAcceptFromDatabase(int appointmentID) {
        return apiInterface.updateAppointmentAccept(appointmentID);
    }

    public Call<ResponseBody> updateAppointmentRejectFromDatabase(int appointmentID) {
        return apiInterface.updateAppointmentReject(appointmentID);
    }

    public Call<ClinicInfo> getClinicInfoFromDatabase() {
        return apiInterface.getClinicInfo();
    }

    public Call<List<Patient>> getPatientsFromDatabase(){
        return apiInterface.getPatients();
    }

    public Call<Doctor> getDoctorByDoctorIDFromDatabase(int doctorID) {
        return apiInterface.getDoctorByID(doctorID);
    }

    public Call<JsonObject> bookAppointment(JsonObject jsonObject){
       return apiInterface.bookAppointment(jsonObject);
    }

    public Call<List<DataChart>> getDataChartFromDataBase(int patientID)
    {
        return apiInterface.getDataChart(patientID);
    }

    public Call<List<Appointment>> getPatientAppointmentsAcceptedByDateFromDatabase(String date,int patientID)
    {
        return apiInterface.getPatientAppointmentsByDateAndAccepted(date,patientID);
    }

    public Call<JsonObject> sendToken(JsonObject jsonObject){

        return apiInterface.sendToken(jsonObject);

    }

}
