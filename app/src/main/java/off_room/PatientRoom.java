package off_room;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import pojo.Appointment;
import pojo.Attachment;
import pojo.Attachments;
import pojo.Doctor;
import pojo.Patient;
import pojo.Session;
import pojo.Sickness;


@Database(entities = {Patient.class, Doctor.class, Appointment.class, Attachments.class, Session.class, Sickness.class}
        ,exportSchema = false,version = 1)
public abstract class PatientRoom extends RoomDatabase {


    private static final String DB_NAME = "PatientRoom";
    private static PatientRoom instance;

    public static synchronized PatientRoom getInstance(Context context){
        if(instance == null){
            instance = Room.databaseBuilder(context.getApplicationContext(),PatientRoom.class,
                    DB_NAME).fallbackToDestructiveMigration().allowMainThreadQueries()
                    .build();
        }
        return instance;
    }

    public abstract PatientDao patientDao();
}
